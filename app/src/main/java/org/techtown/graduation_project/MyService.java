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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SleepClassifyEvent;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

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
    String date = getDate();
    Cursor db_cursor;
    String TABLE_NAME;
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    GeoDatabaseHelper geoDatabaseHelper = new GeoDatabaseHelper(this);
    SigunguDatabaseHelper sigunguDatabaseHelper = new SigunguDatabaseHelper(this);

    public DisasterRowData row;

    static RequestQueue requestQueue;

    public String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");
        String now = dayTime.format(new Date(time));
        return now;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        geoDatabaseHelper.dropTable();

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            sendRequest();
        }

        sigunguDatabaseHelper.dropTable();
        getMySigungu();

//        Cursor sigunguCursor = sigunguDatabaseHelper.getSigungu();
//        while(sigunguCursor.moveToNext()){
//            Log.d("시군구 테이블: ", sigunguCursor.getString(0) + " " + sigunguCursor.getString(1));
//        }
    }

    public void sendRequest() {

        String url = "https://apixml-5d25d-default-rtdb.firebaseio.com/Msg.json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        processResponse(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void processResponse(String response) {
        List<List<Address>> list = null;
        Geocoder geocoder = new Geocoder(this);

        Gson gson = new Gson();
        DisasterMsg disasterList = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterList.DisasterMsg.row.size(); i++){
            row = disasterList.DisasterMsg.row.get(i);
            String str = row.getMsg();
            // 요청하는 재난문자를 분류하기 위해 시군구 데이터베이스를 만들었음.
            // 시군구 데이터베이스에는 사용자가 방문했던 장소들의 시군구가 기록되어있음.
            Cursor sigunguCursor = sigunguDatabaseHelper.getSigungu();
            while(sigunguCursor.moveToNext()) {
                //Log.d("현재 파싱하는 테이블: ", sigunguCursor.getString(0) + " " + sigunguCursor.getString(1));
                if (row.getLocation_name().equals(sigunguCursor.getString(0) + " 전체") ||
                        row.getLocation_name().equals(sigunguCursor.getString(1))) {
                    if (geoDatabaseHelper.GeoDB_Check(row.getMsg()) == false){
                        Pattern pattern = Pattern.compile("[(](.*?)[)]");
                        Matcher matcher = pattern.matcher(str);

                        while (matcher.find()) {  // 일치하는 게 있다면
                            //재난문자 ( ) 안 내용들 모두 들어옴

                            if (matcher.group(1).length() > 2) {
                                //요일제외 2글자 이상인 재난문자 선별
                                Pattern pattern2 = Pattern.compile(".*?(길\\b|동\\b|대로\\b|로\\b).*");
                                Matcher matcher2 = pattern2.matcher(matcher.group(1));
                                while (matcher2.find()) {
                                    // ~길, ~동, ~대로 ~로 글자 전후로 가져옴
                                    String filter = matcher2.group();

                                    int target_index;
                                    if (filter.contains("소독완료")) {
                                        target_index = filter.indexOf("소독완료");
                                        filter = filter.replace(filter.substring(target_index), "");
                                    } else if (filter.contains("방역완료")) {
                                        target_index = filter.indexOf("방역완료");
                                        filter = filter.replace(filter.substring(target_index), "");
                                    }
                                    filter = filter.replaceAll(".*감염경로.*", "");
                                    getDisasterAddress(filter, str);
                                }
                            }
                            if (matcher.group(1) == null)
                                break;
                        }
                    }
                }
            }
            sigunguCursor.close();
//            if(row.getLocation_name().equals("경기도 시흥시")){
//                Pattern pattern = Pattern.compile("[(](.*?)[)]");
//                Matcher matcher = pattern.matcher(str);
//
//                while (matcher.find()) {  // 일치하는 게 있다면
//                    //재난문자 ( ) 안 내용들 모두 들어옴
//
//                    if(matcher.group(1).length() > 2){
//                        //요일제외 2글자 이상인 재난문자 선별
//                        Pattern pattern2 = Pattern.compile(".*?(길\\b|동\\b|대로\\b|로\\b).*");
//                        Matcher matcher2 = pattern2.matcher(matcher.group(1));
//                        while(matcher2.find()){
//                            // ~길, ~동, ~대로 ~로 글자 전후로 가져옴
//                            String filter = matcher2.group();
//
//                            int target_index;
//                            if(filter.contains("소독완료")){
//                                target_index = filter.indexOf("소독완료");
//                                filter = filter.replace(filter.substring(target_index), "");
//                            }else if(filter.contains("방역완료")){
//                                target_index = filter.indexOf("방역완료");
//                                filter = filter.replace(filter.substring(target_index), "");
//                            }
//                            filter = filter.replaceAll(".*감염경로.*","");
//                            getDisasterAddress(filter,str);
//                        }
//                    }
//                    if(matcher.group(1) ==  null)
//                        break;
//                }
//            }
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                mCurrentLocation = location;
                TABLE_NAME = getDate();

                if (mDatabaseHelper.dbCheck() == false) {  // DB의 TABLE이 비어있으면
                    latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());   // 첫 위치 좌표
                    mDatabaseHelper.addData(latLng, TABLE_NAME);    // DB에 저장
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
            db_cursor = mDatabaseHelper.getCursor(TABLE_NAME);
            if (db_cursor.moveToLast()) { // db가 있으면
                Location last_location = new Location(LocationManager.GPS_PROVIDER);
                latitude = db_cursor.getDouble(1);
                longitude = db_cursor.getDouble(2);  // 위치를 전달하는 과정이고
                latLng = new LatLng(latitude, longitude);   // 꺼내온 위치를 변수에다 저장함
                last_location.setLatitude(latitude);
                last_location.setLongitude(longitude);
                Log.d(TAG, "DB 마지막 행: " + latitude + " DB 마지막 행: " + longitude);

                float distance2 = last_location.distanceTo(mCurrentLocation);
                if (distance2 < 100) {
                    return;
                } else {
                    Log.d(TAG, "데이터베이스에 현재 위치를 저장했습니다.");
                    mDatabaseHelper.addData(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), TABLE_NAME);
                }
            }
        } else {
            Log.d(TAG, "이전 위치와의 거리가 5미터 이상입니다.");
            return;
        }
    }



    public String getCurrentAddress(LatLng latlng) {


        //지오코더 GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
//            Log.d("전체 주소: ", address.getAddressLine(0));
            String[] tmp = address.getAddressLine(0).split(" ");

            if(sigunguDatabaseHelper.Sigungu_Check(tmp[1], tmp[2]) == false){
                //Log.d("추가된 주소: ", Sigungu);
                sigunguDatabaseHelper.addSigungu(tmp[1], tmp[2]);
            }
            String tmp_add = tmp[1] + " " + tmp[2];
//            Log.d(TAG, tmp_add);
//            Log.d("분리됨", tmp_add);
            return tmp_add;
        }
    }

    public void getDisasterAddress(String Sigungu, String Msg) {

        //지오코더 주소를 GPS로 변환
        Geocoder geocoder = new Geocoder(this);

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(
                    Sigungu,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return;
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return;

        } else {
            Log.d(TAG, String.valueOf(addresses.get(0)));
            LatLng covidlatlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            Msg = Msg.replaceAll("\'", "");
            geoDatabaseHelper.addData(Sigungu, covidlatlng, Msg);
//            if(geoDatabaseHelper.GeoDB_Check(Msg)){
//                geoDatabaseHelper.addData(Sigungu, covidlatlng, Msg);
//                Log.d(TAG, String.valueOf(addresses.get(0)));
//            }
            return;
        }
    }


    // 사용자 DB에서 사용자 위치의 행정구역을 뽑아내는 코드
    private void getMySigungu(){
        Cursor tCursor = mDatabaseHelper.getTableName();
        String Usertable;   // 테이블 이름을 조회해
        double db_latitude;         // 사용자 DB에서 조회한 위도
        double db_longitude;        // 사용자 DB에서 조회한 경도
        LatLng db_latlng;

        while (tCursor.moveToNext()) {
            Usertable = tCursor.getString(0);
            if(Usertable.equals("android_metadata")){
                continue;
            }else if(Usertable.equals("sqlite_sequence")){
                continue;
            }else{
                Cursor db_cursor = mDatabaseHelper.getLatLng(Usertable);    // 테이블 안에 접근해
                while (db_cursor.moveToNext()) {    // 위도 경도 빼와
                    db_latitude = db_cursor.getDouble(0);
                    db_longitude = db_cursor.getDouble(1);
                    db_latlng = new LatLng(db_latitude, db_longitude);
                    getCurrentAddress(db_latlng);

//                    if(sigunguDatabaseHelper.Sigungu_Check(Sigungu) == false){
//                        //Log.d("추가된 주소: ", Sigungu);
//                        sigunguDatabaseHelper.addSigungu(Sigungu);
//                    }
                    //Log.d("시군구:", Sigungu);
                }
            }
        }
    }

//GeoDatabaseHelper geoDatabaseHelper = new GeoDatabaseHelper(this);
//        Cursor data = geoDatabaseHelper.getGeoDB();
//        double tmp_latitude;        // GeoDB에서 가져온 위도를 저장할 변수
//        double tmp_longitude;       // GeoDB에서 가져온 경도를 저장할 변수
//        LatLng tmp_LatLng;          // 위도, 경도를 합친 좌표
//
//        int i = 0;
//
//        while(data.moveToNext()) {
//            Location GeoDB_location = new Location(LocationManager.GPS_PROVIDER);
//            tmp_latitude = data.getDouble(1);
//            tmp_longitude = data.getDouble(2);
//            /*
//            거리 비교를 위해 location 변수에 GeoDB에서 가져온 위도 경도 세팅
//             */
//            GeoDB_location.setLatitude(tmp_latitude);
//            GeoDB_location.setLongitude(tmp_longitude);
//    tmp_LatLng = new LatLng(tmp_latitude, tmp_longitude);
//            Log.d("GeoDB에서 가져온 LatLng", String.valueOf(tmp_LatLng));
//
//    Cursor db_cursor = mDatabaseHelper.getLatLng(tablename);
//            while (db_cursor.moveToNext()) {
//        Location db_location = new Location(LocationManager.GPS_PROVIDER);
//        db_latitude = db_cursor.getDouble(0);
//        db_longitude = db_cursor.getDouble(1);
//        db_location.setLatitude(db_latitude);
//        db_location.setLongitude(db_longitude);
//        if (db_location.distanceTo(GeoDB_location) < 100) {
//            i++;
//        }
//    }
//}



//    private void getLatLng() {
//        Log.d(TAG, "populateListView: Displaying data in the View");
//        Location db_location = new Location(LocationManager.GPS_PROVIDER);
//        Cursor tCursor = mDatabaseHelper.getTableName();
//        Cursor data = mDatabaseHelper.getLocation(date);
//        while (data.moveToNext()) {
//            latitude = data.getDouble(1);
//            longitude = data.getDouble(2);
//            latLng = new LatLng(latitude, longitude);
//            String Sigungu = getCurrentAddress(latLng);
//
//            Log.d(TAG, "주소: " + Sigungu);
//        }
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
        //locationRequest.setInterval(600000);    // 10분 주기
        //locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    //stopLocationService();
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
