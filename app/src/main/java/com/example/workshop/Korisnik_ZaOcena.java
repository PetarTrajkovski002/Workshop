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

public class Korisnik_ZaOcena extends AppCompatActivity implements RecyclerViewInterface{

    public int ID;

    public ArrayList<Vozenje_Review> Vozenja_Review;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_korisnik_za_ocena);
        Intent intent=getIntent();
        ID=intent.getExtras().getInt("ID");
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(Korisnik_ZaOcena.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM Vozenja INNER JOIN Vozaci ON Vozenja.Vozac_ID=Vozaci.ID_Vozac INNER JOIN Vozilo ON Vozaci.Vozilo_ID=Vozilo.Vozilo_ID WHERE Korisnik_ID="+ID+" AND Status=3"+";";
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        Vozenje_Review V_R;
        int ID_Vozenje;
        int Korisnik_ID;
        String ImeVozac;
        String PrezimeVozac;
        double StartLat;
        double StartLong;
        double DestLat;
        double DestLong;

        String Start;
        String End;
        int Status;
        int Rating;
        String Model;
        int Godina;
        String ImageResource;
        String Komentar;
        Vozenja_Review=new ArrayList<>();
        do
        {
            ID_Vozenje=cursor.getInt(0);
            Korisnik_ID=cursor.getInt(1);
            ImeVozac=cursor.getString(13);
            PrezimeVozac=cursor.getString(14);
            StartLat=cursor.getDouble(2);
            StartLong=cursor.getDouble(3);
            DestLat=cursor.getDouble(4);
            DestLong=cursor.getDouble(5);
            Status=cursor.getInt(7);
            Model=cursor.getString(24);
            Godina=cursor.getInt(25);
            if(cursor.isNull(10))
            {
                Start="Не е внесено";
            }
            else
            {
                Start=cursor.getString(10);
            }
            if(cursor.isNull(11))
            {
                End="Не е внесено";
            }
            else
            {
                End=cursor.getString(11);
            }
            if(cursor.isNull(8))
            {
                Rating=0;
                ImageResource="@drawable/brojprasalnik";
            }
            else
            {
                Rating=cursor.getInt(8);
                ImageResource="@drawable/broj"+Integer.toString(Rating);
            }
            if(cursor.isNull(9))
            {
                Komentar="Не е внесено";
            }
            else
            {
                Komentar=cursor.getString(9);
            }
            V_R=new Vozenje_Review(ID_Vozenje,Korisnik_ID,ImeVozac,PrezimeVozac,StartLat,StartLong,DestLat,DestLong,Status,Start,End,ImageResource,Model,Godina,Komentar,Rating);
            Vozenja_Review.add(V_R);



        }
        while(cursor.moveToNext());
        RecyclerView recyclerView=findViewById(R.id.recview);
        Review_RecyclerViewAdapter adapter=new Review_RecyclerViewAdapter(this,Vozenja_Review,this);
        recyclerView.setAdapter(adapter);
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
        Intent intent=new Intent(this, Komentar_Rating.class);
        int Id=Vozenja_Review.get(position).Id;
        intent.putExtra("ID_Vozenje",Id);
        intent.putExtra("ID_Korisnik",ID);
        startActivity(intent);

    }
}