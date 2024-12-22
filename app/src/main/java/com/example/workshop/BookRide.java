package com.example.workshop;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookRide extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private TextView tv;
    private SearchView mySearchView1;
    private SearchView mySearchView2;
    public LatLng currentloc;

    public LatLng marker1;

    public int ID;

    public String Ime;
    public LatLng Source;
    public LatLng Destination;
    public LatLng marker2;
    public String location1="";
    public String location2="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_ride);
        mySearchView1=findViewById(R.id.searchview1);
        mySearchView2=findViewById(R.id.searchview2);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        } else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Handle the intent data
        Intent intent = getIntent();
        ID = intent.getExtras().getInt("ID");
        Ime = intent.getExtras().getString("Ime");
        tv = findViewById(R.id.tv1);
        tv.setText("Добредојде " + Ime + "!");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Apply system bars insets (for edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the map
       // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mySearchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                location1=mySearchView1.getQuery().toString();
                List<Address> addressList=null;
                if(location1!=null)
                {
                    Geocoder geocoder=new Geocoder(BookRide.this);
                    try {
                        addressList=geocoder.getFromLocationName(location1,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addressList.size()>0)
                    {
                        Address address=addressList.get(0);
                        Source=new LatLng(address.getLatitude(),address.getLongitude());
                        if(Source!=null)
                        {
                            mMap.addMarker(new MarkerOptions().position(Source).title("Почетна дестинација"));
                            //drawRoute(Source,Destination);
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Source,10));
                        }

                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mySearchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                 location2=mySearchView2.getQuery().toString();
                List<Address> addressList=null;
                if(location2!=null)
                {
                    Geocoder geocoder=new Geocoder(BookRide.this);
                    try {
                        addressList=geocoder.getFromLocationName(location2,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(addressList.size()>0)
                    {
                        Address address=addressList.get(0);
                         Destination=new LatLng(address.getLatitude(),address.getLongitude());
                        if(Destination!=null && Source!=null)
                        {
                            mMap.addMarker(new MarkerOptions().position(Destination).title("Дестинцаија"));
                            drawRoute(Source,Destination);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Destination,10));
                        }

                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    // Get current location
    private void getCurrentLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // If location is found
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            currentloc=new LatLng(latitude,longitude);
                            markLocationOnMap(latitude, longitude);
                        }
                    }
                });
    }

    // Mark location on map
    private void markLocationOnMap(double latitude, double longitude)
    {
        LatLng currentLocation = new LatLng(latitude, longitude);

        // Add a marker at the current location
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));

            // Define target location (example: specific destination coordinates)
            //LatLng targetLocation = new LatLng(42.0000, 21.0000); // Example destination

            // Add a marker at the target location
            //mMap.addMarker(new MarkerOptions().position(targetLocation).title("Target Location"));

            // Draw a polyline between current location and target location
            //drawRoute(currentLocation, targetLocation);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));  // Zoom to the current location
        }
    }

    // Draw a path between two locations
    private void drawRoute(LatLng from, LatLng to) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + from.latitude + "," + from.longitude
                + "&destination=" + to.latitude + "," + to.longitude
                + "&key=AIzaSyDb5eYBbPiu05tOtSgNqMYDxl88yKwqBc0";

        // Assuming you're using an AsyncTask or any network library (like Retrofit) to fetch the route
        new FetchRouteTask().execute(url);
    }

    public void KreirajVozenje(View view)
    {
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(BookRide.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM Vozenja WHERE Korisnik_ID="+ID+" AND Status<>3;";
        Cursor vozenja=db.rawQuery(sql,null);
        Button btn=findViewById(R.id.createride);
        if(vozenja.getCount()>0)
        {
            Toast.makeText(this,"Веќе имате закажано возење, не можете да креирате ново додека ви трае сегашната сесија!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            db=dbhelper.getWritableDatabase();
            ContentValues cv=new ContentValues();
            cv.put("Korisnik_ID",ID);
            cv.put("Pocetna_Lat",Source.latitude);
            cv.put("Pocetna_Long",Source.longitude);
            cv.put("Krajna_Lat",Destination.latitude);
            cv.put("Krajna_Long",Destination.longitude);
            cv.put("Status",0); //Za pocetok e 0;
            if(location1.isEmpty()||location2.isEmpty())
            {
                Toast.makeText(this,"Едно од полињата не е внесено како што треба!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                cv.put("Pocetok_Ime",location1);
                cv.put("Kraj_Ime",location2);
                long result=db.insert("Vozenja",null,cv);

                if(result==-1)
                {
                    Toast.makeText(this,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    public void PrikaziTekovno(View view)
    {
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(BookRide.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM Vozenja WHERE Korisnik_ID="+ID+" AND Status<>3;";
        Cursor vozenja=db.rawQuery(sql,null);
        Button btn=findViewById(R.id.createride);
        if(vozenja.getCount()==0)
        {
            Toast.makeText(this,"Немате текововно закажано возење, изберете дестинација!",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Intent intent=new Intent(this, ZakazanoVozenje.class);
            vozenja.moveToFirst();
            int ID_Vozenje=vozenja.getInt(0);
            intent.putExtra("ID",ID);
            intent.putExtra("ID_Vozenje",ID_Vozenje);
            startActivity(intent);
        }
    }

    public void MoiVozenja(View view)
    {
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(BookRide.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM Vozenja WHERE Korisnik_ID="+ID+" AND Status=3"+";";
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"Немате завршени возења!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(this, Korisnik_ZaOcena.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        }
    }

    // AsyncTask to fetch the route from the Google Directions API
    private class FetchRouteTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                response = convertInputStreamToString(inputStream);
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            // Parse the Directions API response to extract the polyline points and draw the route
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray routes = jsonObject.getJSONArray("routes");
                JSONObject route = routes.getJSONObject(0);
                JSONObject polyline = route.getJSONObject("overview_polyline");
                String points = polyline.getString("points");

                List<LatLng> decodedPath = decodePolyline(points);
                if (mMap != null) {
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(decodedPath).width(10).color(Color.BLUE);
                    mMap.addPolyline(polylineOptions);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Decode the polyline points into LatLng
    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> polyline = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int shift = 0, result = 0;
            while (true) {
                int b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
                if (b < 0x20) break;
            }
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            while (true) {
                int b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
                if (b < 0x20) break;
            }
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            polyline.add(p);
        }
        return polyline;
    }

    // Utility to convert InputStream to String
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        return stringBuilder.toString();
    }

    // Handle permission result for location access
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the location
                getCurrentLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // When the map is ready, initialize it
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // If permission granted, enable location layer on the map
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Optionally, set a default marker at the origin (this can be removed if not needed)
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Default Location"));
    }



}
