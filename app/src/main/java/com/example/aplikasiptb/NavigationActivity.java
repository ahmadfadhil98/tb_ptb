package com.example.aplikasiptb;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.mapbox.mapboxsdk.Mapbox;
//import com.mapbox.mapboxsdk.maps.MapView;
//import com.mapbox.mapboxsdk.maps.MapboxMap;
//import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//
//public class NavigationActivity extends AppCompatActivity {
//
//    MapView mapView;
//    String tokenMapbox;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_navigation);
//
//        mapView = findViewById(R.id.mapView);
//
//        tokenMapbox = getString(R.string.mapbox_access_token);
//
//        Mapbox.getInstance(getApplicationContext(),tokenMapbox);
//
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull MapboxMap mapboxMap) {
//
//            }
//        });
//    }
//}


        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.graphics.Color;
        import android.location.Location;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import com.google.android.gms.location.FusedLocationProviderClient;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.tasks.CancellationTokenSource;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.mapbox.android.core.location.LocationEngine;
        import com.mapbox.api.directions.v5.DirectionsCriteria;
        import com.mapbox.api.directions.v5.MapboxDirections;
        import com.mapbox.api.directions.v5.models.DirectionsResponse;
        import com.mapbox.api.directions.v5.models.DirectionsRoute;
        import com.mapbox.geojson.Feature;
        import com.mapbox.geojson.LineString;
        import com.mapbox.geojson.Point;
        import com.mapbox.mapboxsdk.Mapbox;
        import com.mapbox.mapboxsdk.camera.CameraPosition;
        import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
        import com.mapbox.mapboxsdk.geometry.LatLng;
        import com.mapbox.mapboxsdk.geometry.LatLngBounds;
        import com.mapbox.mapboxsdk.maps.MapView;
        import com.mapbox.mapboxsdk.maps.MapboxMap;
        import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
        import com.mapbox.mapboxsdk.maps.Style;
        import com.mapbox.mapboxsdk.style.layers.LineLayer;
        import com.mapbox.mapboxsdk.style.layers.Property;
        import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
        import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
        import com.mapbox.mapboxsdk.utils.BitmapUtils;

        import java.util.concurrent.TimeUnit;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
//        import timber.log.Timber;

        import static com.mapbox.core.constants.Constants.PRECISION_6;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
        import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

/**
 * Use the Mapbox Java SDK to request directions from the Mapbox Directions API
 * and show the various directions profile.
 */
public class NavigationActivity extends AppCompatActivity
        implements MapboxMap.OnMapClickListener {

    private static final String TAG = "NavigationActivity";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private DirectionsRoute drivingRoute;
    private DirectionsRoute walkingRoute;
    private DirectionsRoute cyclingRoute;
    private MapboxDirections client;
    private Point origin;
    private Point destination;
    private String lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_DRIVING;
    private Button drivingButton;
    private Button walkingButton;
    private Button cyclingButton;
    private boolean firstRouteDrawn = false;
    private String[] profiles = new String[]{
            DirectionsCriteria.PROFILE_DRIVING,
            DirectionsCriteria.PROFILE_CYCLING,
            DirectionsCriteria.PROFILE_WALKING
    };
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Integer idHomestay;
    private Double latOri,longOri,latDes,longDes;
    private LatLng dest,orig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_navigation);

        idHomestay = getIntent().getIntExtra("idHomestay",0);
        longDes = getIntent().getDoubleExtra("longitude",0);
        latDes = getIntent().getDoubleExtra("latitude",0);

//        origin = Point.fromLngLat(100.6603642, -0.105881 );
        destination = Point.fromLngLat(longDes,latDes );
        dest = new LatLng(latDes,longDes);
        getCurrentLocation();

        drivingButton = findViewById(R.id.driving_profile_button);
        drivingButton.setTextColor(Color.WHITE);
        walkingButton = findViewById(R.id.walking_profile_button);
        cyclingButton = findViewById(R.id.cycling_profile_button);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
    }

    public void sitMap(){
        // Setup the MapView
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        NavigationActivity.this.mapboxMap = mapboxMap;

                        initSource(style);

                        initLayers(style);

                        getAllRoutes(false);

                        initButtonClickListeners();
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(dest);
                        builder.include(orig);

                        mapboxMap.addOnMapClickListener(NavigationActivity.this);
                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newLatLngBounds(builder.build(),300));
//                                .newCameraPosition(
//                                new CameraPosition.Builder()
//                                .target(new LatLng(destination.latitude(),destination.longitude()))
//                                .zoom(12).build()
//                        ),1400);

                        Toast.makeText(NavigationActivity.this,
                                R.string.instruction, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        CancellationTokenSource cts = new CancellationTokenSource();
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,cts.getToken()).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLongitude();
                    Log.i("latlng", String.valueOf(latitude));
                    Log.i("latlng", String.valueOf(longitude));
                    origin = Point.fromLngLat(longitude, latitude);
                    orig = new LatLng(latitude,longitude);
                    sitMap();
                }
            }
        });
    }

    /**
     * Load route info for each Directions API profile.
     *
     * @param fromMapClick whether the route loading is being triggered from tapping
     *                     on the map
     */
    private void getAllRoutes(boolean fromMapClick) {
        for (String profile : profiles) {
            getSingleRoute(profile, fromMapClick);
        }
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
//        getCurrentLocation();
//        destination = Point.fromLngLat(point.getLongitude(), point.getLatitude());
//        moveDestinationMarkerToNewLocation(point);
//        getAllRoutes(true);
        return true;
    }

    /**
     * Move the destination marker to wherever the map was tapped on.
     *
     * @param pointToMoveMarkerTo where the map was tapped on
     */
    private void moveDestinationMarkerToNewLocation(LatLng pointToMoveMarkerTo) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                GeoJsonSource destinationIconGeoJsonSource = style.getSourceAs(ICON_SOURCE_ID);
                if (destinationIconGeoJsonSource != null) {
                    destinationIconGeoJsonSource.setGeoJson(Feature.fromGeometry(Point.fromLngLat(
                            pointToMoveMarkerTo.getLongitude(), pointToMoveMarkerTo.getLatitude())));
                }
            }
        });
    }

    /**
     * Add the source for the Directions API route line LineLayer.
     */
    private void initSource(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addSource(new GeoJsonSource(ROUTE_SOURCE_ID));
        GeoJsonSource iconGeoJsonSource = new GeoJsonSource(ICON_SOURCE_ID,
                Feature.fromGeometry(Point.fromLngLat(destination.longitude(),
                        destination.latitude())));
        loadedMapStyle.addSource(iconGeoJsonSource);
    }

    /**
     * Set up the click listeners on the buttons for each Directions API profile.
     */
    private void initButtonClickListeners() {
        drivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.WHITE);
                walkingButton.setTextColor(Color.BLACK);
                cyclingButton.setTextColor(Color.BLACK);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_DRIVING;
                showRouteLine();
            }
        });
        walkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.BLACK);
                walkingButton.setTextColor(Color.WHITE);
                cyclingButton.setTextColor(Color.BLACK);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_WALKING;
                showRouteLine();
            }
        });
        cyclingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivingButton.setTextColor(Color.BLACK);
                walkingButton.setTextColor(Color.BLACK);
                cyclingButton.setTextColor(Color.WHITE);
                lastSelectedDirectionsProfile = DirectionsCriteria.PROFILE_CYCLING;
                showRouteLine();
            }
        });
    }

    /**
     * Display the Directions API route line depending on which profile was last
     * selected.
     */
    private void showRouteLine() {
        if (mapboxMap != null) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    // Retrieve and update the source designated for showing the directions route
                    GeoJsonSource routeLineSource = style.getSourceAs(ROUTE_SOURCE_ID);

                    // Create a LineString with the directions route's geometry and
                    // reset the GeoJSON source for the route LineLayer source
                    if (routeLineSource != null) {
                        switch (lastSelectedDirectionsProfile) {
                            case DirectionsCriteria.PROFILE_DRIVING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(drivingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            case DirectionsCriteria.PROFILE_WALKING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(walkingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            case DirectionsCriteria.PROFILE_CYCLING:
                                routeLineSource.setGeoJson(LineString.fromPolyline(cyclingRoute.geometry(),
                                        PRECISION_6));
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }

    /**
     * Add the route and icon layers to the map
     */
    private void initLayers(@NonNull Style loadedMapStyle) {
        LineLayer routeLayer = new LineLayer(ROUTE_LAYER_ID, ROUTE_SOURCE_ID);

        // Add the LineLayer to the map. This layer will display the directions route.
        routeLayer.setProperties(
                lineCap(Property.LINE_CAP_ROUND),
                lineJoin(Property.LINE_JOIN_ROUND),
                lineWidth(5f),
                lineColor(Color.parseColor("#006eff"))
        );
        loadedMapStyle.addLayer(routeLayer);

        // Add the red marker icon image to the map
        loadedMapStyle.addImage(RED_PIN_ICON_ID, BitmapUtils.getBitmapFromDrawable(
                getResources().getDrawable(R.drawable.mapbox_marker_icon_default)));

        // Add the red marker icon SymbolLayer to the map
        loadedMapStyle.addLayer(new SymbolLayer(ICON_LAYER_ID, ICON_SOURCE_ID).withProperties(
                iconImage(RED_PIN_ICON_ID),
                iconIgnorePlacement(true),
                iconAllowOverlap(true),
                iconOffset(new Float[]{0f, -9f})));
    }

    /**
     * Make a request to the Mapbox Directions API. Once successful, pass the route to the
     * route layer.
     *
     * @param profile the directions profile to use in the Directions API request
     */
    private void getSingleRoute(String profile, boolean fromMapClick) {
        client = MapboxDirections.builder()
                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(profile)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                // You can get the generic HTTP info about the response
//                Timber.d("Response code: " + response.code());
                if (response.body() == null) {
                    Toast.makeText(getApplicationContext(), "No routes found, make sure you set the right user and access token.", Toast.LENGTH_SHORT).show();
//                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Toast.makeText(getApplicationContext(), "No routes found", Toast.LENGTH_SHORT).show();
//                    Timber.e("No routes found");
                    return;
                }

                switch (profile) {
                    case DirectionsCriteria.PROFILE_DRIVING:
                        drivingRoute = response.body().routes().get(0);
                        drivingButton.setText(String.format(getString(R.string.driving_profile),
                                String.valueOf(TimeUnit.SECONDS.toMinutes(drivingRoute.duration().longValue()))));
                        if (!firstRouteDrawn) {
                            showRouteLine();
                            firstRouteDrawn = true;
                        }
                        break;
                    case DirectionsCriteria.PROFILE_WALKING:
                        walkingRoute = response.body().routes().get(0);
                        walkingButton.setText(String.format(getString(R.string.walking_profile),
                                String.valueOf(TimeUnit.SECONDS
                                        .toMinutes(walkingRoute.duration().longValue()))));
                        break;
                    case DirectionsCriteria.PROFILE_CYCLING:
                        cyclingRoute = response.body().routes().get(0);
                        cyclingButton.setText(String.format(getString(R.string.cycling_profile),
                                String.valueOf(TimeUnit.SECONDS
                                        .toMinutes(cyclingRoute.duration().longValue()))));
                        break;
                    default:
                        break;
                }
                if (fromMapClick) {
                    showRouteLine();
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Toast.makeText(NavigationActivity.this,
                        "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the Directions API request
        if (client != null) {
            client.cancelCall();
        }
        if (mapboxMap != null) {
            mapboxMap.removeOnMapClickListener(this);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        startActivity(intent);
        finish();

    }
}
