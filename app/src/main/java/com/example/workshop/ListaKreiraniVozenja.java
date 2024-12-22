package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaKreiraniVozenja extends AppCompatActivity implements RecyclerViewInterface
{
    ArrayList<Vozenje> Vozenja;
    public int ID;
    public String Ime;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Intent intent=getIntent();
        ID=intent.getExtras().getInt("ID");
        Ime=intent.getExtras().getString("Ime");
        setContentView(R.layout.activity_lista_kreirani_vozenja);
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ListaKreiraniVozenja.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String query_vozila="SELECT * FROM Vozenja WHERE Status=0;";
        Cursor cursor=db.rawQuery(query_vozila,null);
        cursor.moveToFirst();
        Vozenja=new ArrayList<>();
        Vozenje V;
        int Id;
        int Korisnik_ID;
        String Ime;
        String Prezime;
        double StartLat;
        double StartLong;
        double DestLat;
        double DestLong;
        int Status;
        String Start_Place;
        String End_Place;
        Cursor Korisnici;
        do
        {

            Id = cursor.getInt(0);
            Korisnik_ID=cursor.getInt(1);
            String query="SELECT * FROM Korisnici WHERE ID_Korisnik="+Korisnik_ID;
            Korisnici=db.rawQuery(query,null);
            Korisnici.moveToFirst();
            Ime=Korisnici.getString(1);
            Prezime=Korisnici.getString(2);
            StartLat=cursor.getDouble(2);
            StartLong=cursor.getDouble(3);
            DestLat=cursor.getDouble(4);
            DestLong=cursor.getDouble(5);
            Status=cursor.getInt(7);
            Start_Place=cursor.getString(10);
            End_Place=cursor.getString(11);
            V=new Vozenje(Id,Korisnik_ID,Ime,Prezime,StartLat,StartLong,DestLat,DestLong,Status,Start_Place,End_Place);
            Vozenja.add(V);

        } while (cursor.moveToNext());
        RecyclerView recyclerView=findViewById(R.id.kreiraniRVIEW);
        Vozenje_RecyclerViewAdapter V_RVA=new Vozenje_RecyclerViewAdapter(this,Vozenja,this);
        recyclerView.setAdapter(V_RVA);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClick(int position)
    {
        int V_id=Vozenja.get(position).Id;
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ListaKreiraniVozenja.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String query="UPDATE Vozenja SET Status=1, Vozac_ID="+ID+" WHERE ID_Vozenje="+V_id+";";
        db.execSQL(query);
        Intent intent=new Intent(this, VozacActivity.class);
        intent.putExtra("ID",ID);
        intent.putExtra("Ime",Ime);
        startActivity(intent);

    }
}