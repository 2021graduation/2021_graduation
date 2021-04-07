package org.techtown.sqliteexample2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service
{
    String TAG = "Service";
    Location tmp_location = new Location("");
    Location mCurrentLocation = new Location("");
    private Location location;
    int markcount = 0;
    Location a = new Location("");

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                mCurrentLocation = location;
                Log.d(TAG, "####: 현재위치 위도: " + String.valueOf(mCurrentLocation.getLatitude()) +
                        "경도: " + String.valueOf(mCurrentLocation.getLongitude()));

                if (tmp_location.getLatitude() < 1) {
                    // 첫 위치 저장
                    tmp_location = mCurrentLocation;
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(tmp_location.getLatitude(), tmp_location.getLongitude()));
                    marker.visible(true);
                    Log.d(TAG, "####: tmp 위도: " + String.valueOf(tmp_location.getLatitude()) +
                            "경도: " + String.valueOf(tmp_location.getLongitude()));
                } else {
                    compareLocation(tmp_location, mCurrentLocation);
                    tmp_location = mCurrentLocation;
                }
            }
        }
    };

    private void compareLocation(Location tmp_location, Location mCurrentLocation){
        MarkerOptions marker = new MarkerOptions();
        float distance = mCurrentLocation.distanceTo(tmp_location);
        Log.d(TAG,"tmp 와 Current 사이 거리: " + distance);

        if(distance < 30){
            if(markcount == 0){
                LatLng Current = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                mDatabaseHelper.addData(Current);
                Log.d(TAG, "위치 저장함");
                //Marker marker1 = addMarker(mCurrentLocation, marker, distance);
                //첫번째 마커 위치 저장
                a = mCurrentLocation;
                markcount++;
            }else{
                Log.d(TAG, "진입");
                // 두번째 마커부터 비교
                if(a.distanceTo(mCurrentLocation) < 30){ // 만약 첫번째 마커와 위치 차이가 5미터 이내면 (없으면),
//                    marker1.remove();                   // 찍었던 마커를 삭제함
                    a = mCurrentLocation;               // 현재 위치를 이전 위치로 업데이트
                    Log.d(TAG, "저장하지않음");
                }else{
                    Log.d(TAG, "마크 카운트 0으로 초기화함");
                    markcount = 0;
                }
            }
        }
    }

//    private Marker addMarker(Location location, String markerTitle, String markerSnippet){
//        Marker marker1;
//        MarkerOptions marker = new MarkerOptions();
//        marker.position(new LatLng(location.getLatitude(), location.getLongitude()));
//        marker.title(markerTitle);
//        marker.snippet(markerSnippet);
//        marker.draggable(true);
//        marker1 = mMap.addMarker(marker);
//
//        return marker1;
//    }

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
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
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
//
//        createNotificationChannel();
//
//        Intent intent1 =new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
//
//        Notification notification = new NotificationCompat.Builder(this,"ChannelId1")
//                .setContentTitle("My App tutorial")
//                .setContentText("어플리케이션 실행중")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent).build();
//
//
//        startForeground(1,notification);
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
