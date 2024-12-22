package com.example.workshop;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class ListaZakazaniVozenja extends AppCompatActivity implements OnMapReadyCallback {

    public String Ime;
    public int ID_Vozac;
    public int ID_Tekovno;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    public LatLng Start;

    public LatLng currentloc;
    public LatLng Destination;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_zakazani_vozenja);
        Intent intent=getIntent();
        ID_Vozac=intent.getExtras().getInt("ID_Vozac");
        ID_Tekovno=intent.getExtras().getInt("ID_Tekovno");
        TextView t1=findViewById(R.id.DetaliIme);
        TextView t2=findViewById(R.id.DetaliPrezime);
        TextView t3=findViewById(R.id.VozacPoc);
        TextView t4=findViewById(R.id.VozacKraj);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        } else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_vozenje);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ListaZakazaniVozenja.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();





        String query="SELECT * FROM Vozenja AS V INNER JOIN Korisnici AS K ON V.Korisnik_ID=K.ID_Korisnik WHERE ID_Vozenje="+ID_Tekovno+";";
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.getCount()==0)
        {
            Toast.makeText(this,"Нема тековни возења",Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();
            Toast.makeText(this,"Има тековни возења",Toast.LENGTH_SHORT).show();
            String Ime=cursor.getString(13);
            String Prezime=cursor.getString(14);
            Start=new LatLng(cursor.getDouble(2),cursor.getDouble(3));
            Destination=new LatLng(cursor.getDouble(4),cursor.getDouble(5));
            t1.setText(Ime);
            t2.setText(Prezime);
            t3.setText(cursor.getString(10));
            t4.setText(cursor.getString(11));

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
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
                           // markLocationOnMap(latitude, longitude);
                        }
                    }
                });
    }
    private void markLocationOnMap(double latitude, double longitude)
    {
        LatLng currentLocation = new LatLng(latitude, longitude);

        // Add a marker at the current location
        if (mMap != null) {
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));

            // Define target location (example: specific destination coordinates)
            LatLng targetLocation = new LatLng(42.0000, 21.0000); // Example destination

            // Add a marker at the target location
            mMap.addMarker(new MarkerOptions().position(targetLocation).title("Target Location"));

            // Draw a polyline between current location and target location
            //drawRoute(currentLocation, targetLocation);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));  // Zoom to the current location
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable location if permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Check if Start and Destination are initialized
        if (Start != null && Destination != null) {
            mMap.addMarker(new MarkerOptions().position(Start).title("Почеток"));
            mMap.addMarker(new MarkerOptions().position(Destination).title("Дестинцаија"));

            drawRoute(Start, Destination);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Destination, 10));
        }
    }
    private void drawRoute(LatLng from, LatLng to) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + from.latitude + "," + from.longitude
                + "&destination=" + to.latitude + "," + to.longitude
                + "&key=AIzaSyDb5eYBbPiu05tOtSgNqMYDxl88yKwqBc0";

        // Assuming you're using an AsyncTask or any network library (like Retrofit) to fetch the route
        new FetchRouteTask().execute(url);
    }

    public void ZavrsiVozenje(View view)
    {
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ListaZakazaniVozenja.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="UPDATE Vozenja SET Status=3 WHERE ID_Vozenje="+ID_Tekovno+";";
        db.execSQL(sql);
        Toast.makeText(this,"Возењето беше успешно завршено!",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,VozacActivity.class);
        intent.putExtra("Ime",Ime);
        intent.putExtra("ID",ID_Vozac);
        startActivity(intent);

    }

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
}