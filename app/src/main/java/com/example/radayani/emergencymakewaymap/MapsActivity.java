package com.example.radayani.emergencymakewaymap;

import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;
import com.microsoft.azure.storage.table.TableOperation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Boolean entryFlag = false;
    Boolean exitFlag = false;
    private LocationManager locationManager=null;
    private LocationListener locationListener=null;

    //Double[] signalLatitudesArray = null;
    //Double[] signalLongitudesArray = null;
    //Float[] signalBearingArray = null;
    //String[] signalNameArray = null;
    //Location dest =null;

    // UNCOMMENT BELOW TO MAKE IT RUN
    // KONDAPUR
    Double[] signalLatitudesArray = {17.444987, 17.442003, 17.438921, 17.447546, 17.455115,  17.458608, 17.460802};

    Double[] signalLongitudesArray ={78.352711, 78.358381, 78.363567, 78.363471, 78.363796,  78.365938 , 78.366583};
    Float[] signalBearingArray = {240.0f, 296.0f, 296.0f, 200.0f, 200.0f, 212.3f, 212.0f,  };
    int i=0;
    String[] signalNameArray = {"S1 ISB Road","S2 Himagiri Hospital", "S3 Gachibowli Circle", "S4 Radisson Cross", "S5 Greenfields",
                                "S6 Kothaguda Circle", "S7 Apollo Clinic"};

    /*
    // MICROSOFT
    Double[] signalLatitudesArray = {17.4339930, 17.4323350, 17.4308690, 17.4322150 };
    Double[] signalLongitudesArray ={78.3435700, 78.3426520, 78.3434310, 78.3442950 };
    Float[] signalBearingArray = {98.0f, 41.0f, 280.0f, 205.0f};
    int i=0;
    String[] signalNameArray = {"S1 B1 Parking Turn","S2 Tennis Court Turn", "S3 B2 Building Turn", "S4 B1 Building Point"};
*/

/* // AMRITSAR POWERGRID
    Double[] signalLatitudesArray = {31.5399070, 31.5393880, 31.5393000, };
    Double[] signalLongitudesArray ={74.8917760, 74.8922410, 74.8933690};
    Float[] signalBearingArray = {186.0f, 311.0f, 273.0f, };
    int i=0;

    String[] signalNameArray = {"S1 ISB Road","S2 Himagiri Hospital", "S3 Gachibowli Circle", "S4 Radisson Cross", "S5 Greenfields",
            "S6 Kothaguda"};
*/



    Location dest = new Location(signalNameArray[i]);


    private static final String TAG = "Debug";
    private Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        pb = (ProgressBar) findViewById(R.id.progressBar1);
        //      pb.setVisibility(View.INVISIBLE);




        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 11;

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1111;

    private void requestLocationPermissionAgain() {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            new AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .create()
                    .show();

        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
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
                mGoogleMap.setMyLocationEnabled(true);


            } else {
                // Permission was denied or request was cancelled
                requestLocationPermissionAgain();
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            // requestLocationPermissionAgain();

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION );


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }

        else{
            // permission has been granted, continue as usual
            mGoogleMap.setMyLocationEnabled(true);
            Toast.makeText(getBaseContext(),"Location permission already granted", Toast.LENGTH_SHORT).show();

        }




        // Add a marker in Whitefields and move the camera
        //final LatLng signal = new LatLng(17.445024, 78.352844);       // KONDAPUR
        //final LatLng signal = new LatLng(31.5393820,74.8918190);   // AMRITSAR
        final LatLng signal = new LatLng(17.4339930, 78.3435700);
        mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(signal).title("Marker in Microsoft"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(signal));

        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

        mGoogleMap.setOnMyLocationButtonClickListener(this);


        // enable compass button, gps button, traffic and map type
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        dest.setLatitude(signalLatitudesArray[i]);
        dest.setLongitude(signalLongitudesArray[i]);
        //Toast.makeText(getBaseContext(),"!! " + signalBearingArray[i], Toast.LENGTH_SHORT).show();
        dest.setBearing(signalBearingArray[i]);        //NorthWest
        i+=1;

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getBaseContext(),"Focus on Current Location", Toast.LENGTH_SHORT).show();
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

//                    pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();
            Location location;
            try {
                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (SecurityException e) {
                alertbox("Gps Status!!", "Your GPS is: OFF");
                //dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
            }
            //locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5,locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

        return true;
    }



    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

//            Toast.makeText(getBaseContext(),"Loc Changed,Lat:" +
//                            loc.getLatitude()+ " Lng:" + loc.getLongitude(),
//                    Toast.LENGTH_SHORT).show();

//            String longitude = "Longitude: " +loc.getLongitude();
//            Log.v(TAG, longitude);
//            String latitude = "Latitude: " +loc.getLatitude();
//            Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
//            String cityName=null;
//            Geocoder gcd = new Geocoder(getBaseContext(),Locale.getDefault());


            float signalBearing = dest.getBearing();
            float vehicleBearing = loc.getBearing();
            float distance = loc.distanceTo(dest);

            Toast.makeText(getBaseContext(),"Distance : " + distance + " Bearing: " + vehicleBearing, Toast.LENGTH_SHORT).show();
            float vehicle180Bearing=0;
            if (vehicleBearing <180)
                vehicle180Bearing = vehicleBearing+180;
            if (vehicleBearing > 180)
                vehicle180Bearing = vehicleBearing-180;

            if(distance < 600 )
            {
                if(Math.abs(vehicle180Bearing-signalBearing) < 60)
                {
                    mCurrLocationMarker.setPosition(new LatLng(dest.getLatitude(),dest.getLongitude()));
                    mCurrLocationMarker.setTitle("EMERGENCY!");

                    mCurrLocationMarker.setSnippet("Free Right Lane for Emergency");
                    mCurrLocationMarker.showInfoWindow();
                    mCurrLocationMarker.setAlpha(1.0f);
                    mCurrLocationMarker.setVisible(true);

                    Toast.makeText(getBaseContext(),"Ambulance in Range",Toast.LENGTH_SHORT).show();
                    new StoreInTableTask(MapsActivity.this, distance, signalBearing, vehicleBearing, "False",
                            String.valueOf(dest.getLatitude()), String.valueOf(dest.getLongitude()),dest.getProvider().substring(3) ,
                            dest.getProvider().substring(0,2), "FALSE").execute();
                }
                if (distance < 40)
                {
                    //mCurrLocationMarker.setVisible(false);
                    mCurrLocationMarker.hideInfoWindow();
                    mCurrLocationMarker.setAlpha(0.4f);
//                    Toast.makeText(getBaseContext(),"Switch off notification",Toast.LENGTH_SHORT).show();
                    new StoreInTableTask(MapsActivity.this, distance, signalBearing, vehicleBearing, "True",
                            String.valueOf(dest.getLatitude()), String.valueOf(dest.getLongitude()), dest.getProvider().substring(3),
                            dest.getProvider().substring(0,2), "TRUE").execute();

                    //setting new signal
                    dest = new Location(signalNameArray[i]);
                    dest.setLatitude(signalLatitudesArray[i]);
                    dest.setLongitude(signalLongitudesArray[i]);
                    dest.setBearing(signalBearingArray[i]);          //NorthWest
                    i+=1;
                }
            }

            else
            {
                //Toast.makeText(getBaseContext(),"Not in Range Yet",Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
