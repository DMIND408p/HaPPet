package com.example.deepu.dogwiteration1.drawerFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.deepu.dogwiteration1.R;
import com.example.deepu.dogwiteration1.maps.GetNearByPlacesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.deepu.dogwiteration1.R.id.map;

/**
 * Created by deepu on 17/08/2017.
 */
public class Vet extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener,LocationListener{

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    double latitude;
    double longitude;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private int PROXIMITY_RADIUS = 10000;
    MapView mMapView;
    View mView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(map);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Vet Service");
        setHasOptionsMenu(true);
        mView = inflater.inflate(R.layout.vet, container, false);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.blank_menu, menu);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }else {
            getActivity().setTitle("Vet Services");
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        //  markerOptions.title("You are Here!");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        Toast.makeText(getActivity(), "Your Current Location",
                Toast.LENGTH_LONG).show();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
                    this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(getActivity(),R.raw.google_style);
        mMap.setMapStyle(styleOptions);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }


        Button btnBanks = (Button) mView.findViewById(R.id.V_Near);
//        MarkerOptions markerOptions1 = new MarkerOptions();
//                markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.pets_icon));
//                mMap.addMarker(markerOptions1);
        btnBanks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String search = "veterinary_care";
                mMap.clear();
//      Marker mrk =   mMap.addMarker(new MarkerOptions().position(new LatLng(-37.909333,145.118229)).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.pets_icon)));
//       mrk.showInfoWindow();
//                if(mMap != null){
//                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                        @Override
//                        public View getInfoWindow(Marker marker) {
//                            return null;
//                        }
//
//                        @Override
//                        public View getInfoContents(Marker marker) {
//                            View v = getActivity().getLayoutInflater().inflate(R.id.info_window,null);
//                            TextView open_Hr = (TextView) v.findViewById(R.id.time);
//                            TextView cont_No = (TextView) v.findViewById(R.id.cont_No);
//                            open_Hr.setText(marker.getTitle());
//                            cont_No.setText(marker.getSnippet());
//                            return v;
//                        }
//                    });
//
//
//                }
                String url = getUrl(latitude, longitude, search);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                GetNearByPlacesData getNearbyPlacesData = new GetNearByPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(getActivity(), "These are your Nearby VetCare! ",
                        Toast.LENGTH_LONG).show();
//                MarkerOptions markerOptions1 = new MarkerOptions();
//                markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.drawable.pets_icon));
//                mMap.addMarker(markerOptions1);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
        Button btnPet = (Button) mView.findViewById(R.id.P_Store);


        btnPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = "pet_store";
                mMap.clear();
                String url = getUrl(latitude, longitude, search);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                GetNearByPlacesData getNearbyPlacesData = new GetNearByPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(getActivity(), "These are your Nearby Petstore! ",
                        Toast.LENGTH_LONG).show();
//                MarkerOptions markerOptions2 = new MarkerOptions();
//                markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital));
//                mMap.addMarker(markerOptions2);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        });

//        Button btnDpark = (Button) mView.findViewById(R.id.D_Parks);
//        btnDpark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.763812,144.951299)).title("A G Gillon Oval - Hope Street Brunswick").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8631,144.7789)).title("AB Shaw Reserve - Hall Ave Altona Meadows VIC 3028").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7604889,144.9814206)).title("Aberfeldie Park - The Boulevard Aberfeldie VIC 3040").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7506105,145.0047325)).title("Adams Reserve - Milton Cres. Preston VIC 3072").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8791801,145.0538421)).title("Adrie Park - Howard St Malvern East VIC 3145").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7486083,144.9823397)).title("AH Capp Reserve - Halwyn Cres. Preston VIC 3072").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8500389,144.9690106)).title("Albert Park - Aughtie Dr Melbourne VIC 3000").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8768584,145.3566179)).title("Alfred Nicholas Memorial Gardens - Sherbrooke Rd Sherbrooke VIC 3789").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7681746,145.0039745)).title("All Nations Park - Brickworks Ln Northcote VIC 3070").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7475368,144.8577803)).title("Allan Reserve - Park Dr Keilor East VIC 3033").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.938106,145.4904132)).title("Alma Treloar Reserve - Bailey Road Cockatoo VIC 3781").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7501609,144.9586023)).title("Anderson Reserve - Linda St Coburg VIC 3058").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7149503,145.0241905)).title("Arch Gibson Reserve - Boldrewood Pde. Kingsbury VIC 3083").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7149503,145.0241905)).title("Armadale Reserve - Sutherland Rd Armadale VIC 3143").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.090502,145.1305135)).title("Armstrongs Reserve - Railway Pde Seaford VIC 3198").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9167282,145.0614167)).title("Bailey Reserve - East Boundary Rd Bentleigh East VIC 3165").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9167282,145.0614167)).title("Bailey Reserve - East Boundary Rd Bentleigh East VIC 3165").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.774601,144.977753)).title("Balfe Park - John Street Brunswick VIC 3056").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7591134,145.0800124)).title("Banksia Park - Templestowe Rd Bulleen VIC 3105").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9858668,145.0398571)).title("Banksia Reserve - Oak St & Griffiths St Beaumaris VIC 3193").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.0917721,145.1822706)).title("Banyan Reserve - Luscombe Ave Carrum Downs VIC 3201\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-36.1141885,146.8879783)).title("Belvoir Park - Reuss Rd Wodonga VIC 3690").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.920645,145.0431133)).title("Bentleigh Hodgson Reserve - Arthur St Bentleigh VIC 3204\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.0501132,145.1261735)).title("Bicentennial Park (Chelsea) - Thames Promenade & Scotch Parade Chelsea VIC 3196").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.2492486,145.0316371)).title("Birdrock Beach - Esplanade Mt Martha VIC 3934").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8559885,144.7726217)).title("Bladin St Park - Bladin St Laverton VIC 3028").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7160076,144.9002483)).title("Boeing Reserve (northern end) - Boeing Rd Strathmore Heights VIC 3041").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.1080989,145.1653315)).title("Boggy Creek - Paddington Ave Carrum Downs VIC 3201").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8837807,145.1564426)).title("Bogong Reserve - Bogong Ave Glen Waverley VIC 3150").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7739084,144.7654775)).title("Bon Thomas Reserve - Quinn St Deer Park VIC 3023").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7391697,144.9347554)).title("Brearly Reserve - Heliopolis St Pascoe Vale South VIC 3044").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8455032,145.0092976)).title("Brookville Gardens - Fairbairn Rd Toorak VIC 3142").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7064413,144.9808958)).title("BT Connor Reserve - Radford Rd Reservoir VIC 3073").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8002274,144.8272722)).title("Buckingham Reserve - Buckingham Crescent Sunshine West VIC 3020").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.0996855,145.7196007)).title("Bunyip Reserve - Nar Nar Goon Longwary Road Bunyip VIC 3815").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.826048,145.0132246)).title("Burnley Park - Park Grove Richmond VIC 3121\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8637187,144.8504287)).title("Burns Reserve (Altona Dog Beach) - Altona Rd Altona VIC 3018").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.3551266,144.7640646)).title("Camerons Bight - Point Nepean Rd & Hughes Rd Sorrento VIC 3943").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.1277409,145.1292344)).title("Carder Ave Reserve - Carder Ave Seaford VIC 3198").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9616557,145.3959417)).title("Cardinia Reservoir Park - Duffys Rd Emerald VIC 3782").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7188266,144.8411538)).title("Caroline Chisholm Park - Old Calder Highway Keilor VIC 3036").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8734978,145.0319317)).title("Caulfield Park/Balaclava Rd (Caulfield North), Caulfield North, VIC, 3161, Australien").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.926419,145.067489)).title("Centenary Park (Bentleigh East) - Bignell Rd Bentleigh East VIC 3165").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.1458124,145.1764898)).title("Centenary Park (Langwarrin) - McClelland Drive Langwarrin VIC 3910\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8651143,145.0502421)).title("Central Park - Wattletree Rd & Burke Rd Malvern East VIC 3145\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7309037,145.0376243)).title("CH Sullivan Park - Blake St Preston VIC 3072").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7768349,145.0490785)).title("Chelsworth Park - Irvine Rd Ivanhoe VIC 3079").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9644363,145.047748)).title("Cheltenham Park - Park Rd Cheltenham VIC 3192").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7661496,144.8932243)).title("Chifley Drive Reserve - Chifley Dr Maribyrnong VIC 3032").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8167562,145.0007039)).title("Citizens Park - Highett St Richmond VIC 3121").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.2268192,145.1597188)).title("Clarendon Reserve - Clarendon Dr Somerville VIC 3912").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8776667,144.9865687)).title("Clarke St Reserve - Clarke St Elwood VIC 3184").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7665514,144.9539563)).title("Clifton Park (Brunswick) - Victoria Street Brunswick VIC 3056").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.833369,145.005425)).title("Como Park North/Riverbank - Alexandra Ave South Yarra VIC 3141").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7781371,144.8577342)).title("Cranwell Reserve - Cranwell St Braybrook VIC 3019\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7220529,144.9976736)).title("Crispe Park - Reservoir VIC 3073").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8148792,144.8742435)).title("Cruickshank Park - Tuppen St & Austin Cres E Yarraville VIC 3013").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7231441,145.033244)).title("CT Barling Reserve - Plenty Rd Reservoir VIC 3073").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7893961,144.9731495)).title("Curtain Square - Curtain St Carlton North VIC 3054").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7152981,145.026946)).title("CW Kirkwood Reserve - Parkland Gr. Kingsbury VIC 3083").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8712917,144.9040706)).title("Cyril Curtain Reserve - Cole St & Esplanade Williamstown VIC 3016").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8678056,145.1236853)).title("Damper Creek Reserve - Park Rd Mt Waverley VIC 3149").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9531606,145.2355458)).title("Dandenong Police Paddocks Reserve - Brady Rd & Stud Rd Dandenong North VIC 3175").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7737856,145.0337274)).title("Darebin Parklands - Yarana Rd Alphington VIC 3078").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.2592963,145.0216051)).title("Dava Beach - Esplanade Mt Martha VIC 3934").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.1666565,145.0928841)).title("Daveys Bay - Daveys Bay Rd Mt Eliza VIC 3930").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7634243,144.7634447)).title("Davitt Drive Reserve - Davitt Drive Deer Park VIC 3023\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9252285,145.0233941)).title("Dendy Park - Cummins Rd Brighton East VIC 3187\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8960745,145.2582372)).title("Dog Play Park (Knox Park Reserve) - Cnr Bunjil Way and Ferntree Gully Rd Knoxfield VIC 3180\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-38.0596989,145.4855382)).title("Don Jackson Reserve - Ahern Road Pakenham VIC 3810").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8266065,144.8835328)).title("Donald McLean Reserve - The Avenue Spotswood VIC 3015").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8780967,144.8078237)).title("Doug Grant Reserve - Queen St Altona VIC 3018").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7027274,145.0267694)).title("DR Atkinson Reserve - Argyle St Reservoir VIC 3073\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9051267,145.0638215)).title("Duncan McKinnon Reserve - North Rd & Murrumbeena Rd Murrumbeena VIC 3163\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7561959,144.9398156)).title("Dunstan Reserve - Peacock Street Brunswick West VIC 3055\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8788766,145.0469416)).title("East Caulfield Reserve - Dandenong Rd & Dudley St Caulfield East VIC 3145").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9609587,145.0100993)).title("Edward St Beach Foreshore - Edward St & Beach Rd Sandringham VIC 3191").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7142483,144.9932672)).title("Edwardes Lake - Edwardes St Reservoir VIC 3073").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8276955,144.8701874)).title("Edwards Reserve - Brunel St South Kingsville VIC 3015\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8984627,145.0390022)).title("EE Gunn Reserve - Malane St & Foch St Ormond VIC 3204\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7517605,144.9820534)).title("Egan Reserve - Rennie St Coburg VIC 3058\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.9302252,145.4602316)).title("Emerald Lake Park - Emerald Lake Rd Emerald VIC 3782").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8594319,145.1241181)).title("Federal Reserve - Federal St Mt Waverly VIC 3149\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.7686566,144.9759886)).title("Fleming Park - Victoria Street Brunswick VIC 3056\n").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(-37.8081704,145.0081761)).title("Flockhart Reserve - Flockhart St Abbotsford VIC 3067").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//
//
//
//            }
//        });
//
//
//        Button btnGroom = (Button) mView.findViewById(R.id.G_Service);
//        btnGroom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mMap.clear();
////                final Calendar c = Calendar.getInstance();
////               int mDay = c.get(Calendar.DAY_OF_MONTH);
////                String weekday = new DateFormatSymbols().getShortWeekdays()[mDay];
//                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//                Date d = new Date();
//                String dayOfTheWeek = sdf.format(d);
//
//String abc = "1662 Dandenong Rd, Oakleigh East,VIC 3166"+"Contact No.	(03) 9544 7455";
//
//                Marker aaa=  mMap.addMarker(new MarkerOptions().position(new LatLng(-37.909333,145.118229)).title("aaaa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).visible(true));
//                aaa.setTag(abc);
//
//                //mMap.setOnMarkerClickListener(this);
//
//            }
//        });
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new
                StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyAm4Q-MCnS2az9ogq40uVRbEmv76wU6GnY");
        return (googlePlacesUrl.toString());
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "permission denied",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}



