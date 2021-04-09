package org.techtown.graduation_project;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyService extends Service {
    String TAG = "Service";
    Location tmp_location = new Location("");
    Location mCurrentLocation = new Location("");
    private Location location;
    int markcount = 0;
    Location saveLocation = new Location("");

    double latitude;
    double longitude;
    LatLng latLng;
    String date;

    private static final String TABLE_NAME = getDate();

    public static final String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");
        String now = dayTime.format(new Date(time));
        return now;
    }

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                mCurrentLocation = location;

                if (mDatabaseHelper.dbCheck(TABLE_NAME) == false) {  // DB의 TABLE이 비어있으면
                    latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());   // 첫 위치 좌표
                    mDatabaseHelper.addData(latLng);    // DB에 저장
                    tmp_location = mCurrentLocation;    // tmp_location 이라는 변수에 현재 위치 저장

                    Log.d(TAG, "데이터베이스가 비어있어서 현재 위치를 저장합니다.\n 위도: " + String.valueOf(mCurrentLocation.getLatitude()) +
                            "경도: " + String.valueOf(mCurrentLocation.getLongitude()));
                } else {
                    // 두번째 위치 좌표부터
                    // 이전 위치인 tmp_location과 현재 위치인 mCurrentLocation을 비교한다.
                    compareLocation(tmp_location, mCurrentLocation);
                    // 비교가 끝나면 현 위치를 이전 위치로 지정
                    tmp_location = mCurrentLocation;
                }
            }
        }
    };

    private void compareLocation(Location tmp_location, Location mCurrentLocation) {
        MarkerOptions marker = new MarkerOptions();
        float distance = mCurrentLocation.distanceTo(tmp_location);
        Log.d(TAG, "tmp 와 Current 사이 거리: " + distance);

        /*
        비슷하거나 같은 위치가 중복해서 찍히지 않게끔 만드는 조건문

        이전위치와 현위치 사이 거리가 5미터 이하이고,
        데이터베이스에 저장된 마지막 위치와의 거리가 100미터 이상이면,
        유의미한 이동동선의 변화라고 생각하고 사용자 이동동선을 기록함.

        마커가 너무 과도하게 찍히면서, 지도가 무거워지거나 가독성이 떨어지는 것을 방지하기 위함
        */
        if (distance < 5) {  // 이전 위치와 현 위치 사이의 거리가 5미터 이하면
            if (mDatabaseHelper.dbCheck(TABLE_NAME)) { // db가 있으면
                Cursor last = mDatabaseHelper.getLocation(TABLE_NAME); // db를 가리키는 커서를 선언하고
                last.moveToLast();  // db에 가장 마지막으로 저장된 (즉, 저장된 위치 중에서 제일 최근의 것) 위치를 불러옴
                Location last_location = new Location(LocationManager.GPS_PROVIDER);
                latitude = last.getDouble(1);
                longitude = last.getDouble(2);  // 위치를 전달하는 과정이고
                latLng = new LatLng(latitude, longitude);   // 꺼내온 위치를 변수에다 저장함
                last_location.setLatitude(latitude);
                last_location.setLongitude(longitude);
                Log.d(TAG, "DB 마지막 행: " + latitude + " DB 마지막 행: " + longitude);

                float distance2 = last_location.distanceTo(mCurrentLocation);
                if (distance2 < 100) {
                    return;
                } else {
                    Log.d(TAG, "데이터베이스에 현재 위치를 저장했습니다.");
                    mDatabaseHelper.addData(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                }
            }
        } else {
            Log.d(TAG, "거리가 30미터 이상입니다.");
            return;
        }
    }


//            private void compareLocation (Location tmp_location, Location mCurrentLocation){
//                MarkerOptions marker = new MarkerOptions();
//                float distance = mCurrentLocation.distanceTo(tmp_location);
//                Log.d(TAG, "tmp 와 Current 사이 거리: " + distance);
//
//        /*
//        비슷하거나 같은 위치가 중복해서 찍히지 않게끔 만드는 조건문
//         */
//                if (distance < 30) {  // 이전 위치와 현 위치 사이의 거리가 30미터 이하면
//                    if (markcount == 0) { // 디비에 저장될 수 있는 조건이 만족했을 떄, 디비에 저장함.
//
//                        LatLng Current = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//                        mDatabaseHelper.addData(Current);
//                        Log.d(TAG, "데이터베이스에 현재 위치를 저장했습니다.");
//                        saveLocation = mCurrentLocation;
//                        markcount++;
//                    } else {
//                        Log.d(TAG, "진입");
//                        if (saveLocation.distanceTo(mCurrentLocation) < 30) {
//                            // 디비에 가장 마지막에 저장된 위치와 현재 위치 사이의 거리가 30미터 이하면
//                            // 근방에 있었던 걸로 간주하고 이 위치는 디비에 저장을 하지 않는다.
//                            //
//                            // saveLocation = mCurrentLocation;
//                            Log.d(TAG, "중복마커입니다. 저장하지 않습니다.");
//                        } else {
//                            Log.d(TAG, "위치 저장을 준비합니다.");
//                            markcount = 0;
//                        }
//                    }
//                }

                    /*
                    saveLocation 과 tmp_location의 차이가 뭘까.
                    saveLocation 은 디비에 저장될때마다 업데이트됨.
                    tmp_location 은 새로운 위치가 들어올때마다 업데이트됨.
                     */


//                if(mDatabaseHelper.dbCheck(TABLE_NAME)){ // db가 있으면
//                    Cursor last = mDatabaseHelper.getLocation(TABLE_NAME); // db를 가리키는 커서를 선언하고
//                    last.moveToLast();  // db에 가장 마지막으로 저장된 (즉, 저장된 위치 중에서 제일 최근의 것) 위치를 불러옴
//                    Location last_location = new Location(LocationManager.GPS_PROVIDER);
//                    latitude = last.getDouble(1);
//                    longitude = last.getDouble(2);  // 위치를 전달하는 과정이고
//                    latLng = new LatLng(latitude, longitude);   // 꺼내온 위치를 변수에다 저장함
//                    last_location.setLatitude(latitude);
//                    last_location.setLongitude(longitude);
//                    Log.d(TAG, "DB 마지막 행: " + latitude + " DB 마지막 행: " + longitude);
//
//                    LatLng Current = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());   // 현재 위치 좌표
//                    if(last_location.distanceTo(mCurrentLocation) < 30){    // 가장 마지막에 저장된 위치와 현재 위치 사이의 거리가 30미터 이하면
//                        return;
//                    }
//                    else{   // 가장 마지막에 저장된 위치와 현재 위치 사이의 거리가 30미터 이상이면
//                        mDatabaseHelper.addData(Current);
//                        Log.d(TAG, "위치 저장함");
//                        a = mCurrentLocation;
//                        markcount++;
//                    }
//                }else{  // db가 없으면
//                    latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//                    mDatabaseHelper.addData(latLng);
//                }
//                //Marker marker1 = addMarker(mCurrentLocation, marker, distance);
//                //첫번째 마커 위치 저장
//            }else{
//                Log.d(TAG, "진입");
//                // 두번째 마커부터 비교
//                if(a.distanceTo(mCurrentLocation) < 30){ // 만약 첫번째 마커와 위치 차이가 5미터 이내면 (없으면),
////                    marker1.remove();                   // 찍었던 마커를 삭제함
//                    a = mCurrentLocation;               // 현재 위치를 이전 위치로 업데이트
//                    Log.d(TAG, "저장하지않음");
//                }else{
//                    Log.d(TAG, "마크 카운트 0으로 초기화함");
//                    markcount = 0;
//                }
//       }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startLocationService() {
        String channelId = "location_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(),
                channelId
        );
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(false);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null
                    && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelId,
                        "Location Service",
                        NotificationManager.IMPORTANCE_HIGH
                );
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        //locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(Constants.LOCATION_SERVICE_ID, builder.build());
    }

    private void stopLocationService(){
        LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
    }


    public int onStartCommand(Intent intent, int flags, int startId){
        if(intent != null){
            String action = intent.getAction();
            if(action != null){
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    startLocationService();
                }else if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService();
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        // os 가 oreo 보다 높은지 체크함
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

}
