package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ZakazanoVozenje extends AppCompatActivity {

    public int ID;
    public int ID_Vozenje;

    public String Start;

    public String End;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_zakazano_vozenje);
        TextView tv1=findViewById(R.id.ImeSofer);
        TextView tv2=findViewById(R.id.PrezimeSofer);
        TextView tv3=findViewById(R.id.ModelVozilo);
        TextView tv4=findViewById(R.id.GodinaVozilo);
        TextView tv5=findViewById(R.id.Poc1);
        TextView tv6=findViewById(R.id.Kraj1);
        btn1=findViewById(R.id.Prifati);
        btn2=findViewById(R.id.Odbij);
        Intent intent=getIntent();
        ID=intent.getExtras().getInt("ID");
        ID_Vozenje=intent.getExtras().getInt("ID_Vozenje");
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ZakazanoVozenje.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String sql="SELECT * FROM Vozenja WHERE ID_Vozenje="+ID_Vozenje+";";

        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        Start=cursor.getString(10);
        End=cursor.getString(11);
        if(cursor.getInt(7)==0)
        {
            tv1.setText("Нема");
            tv2.setText("Нема");
            tv3.setText("Нема");
            tv4.setText("Нема");
            tv5.setText(Start);
            tv6.setText(End);
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
        }
        else
        {
            tv5.setText(Start);
            tv6.setText(End);

            int Vozac_ID= cursor.getInt(6);
            String sql2="SELECT * FROM Vozaci INNER JOIN Vozilo ON Vozaci.Vozilo_ID=Vozilo.Vozilo_ID WHERE Vozaci.ID_Vozac="+Vozac_ID+";";
            Cursor V=db.rawQuery(sql2,null);
            V.moveToFirst();
            tv1.setText(V.getString(1));
            tv2.setText(V.getString(2));
            tv3.setText(V.getString(12));
            tv4.setText(Integer.toString(V.getInt(13)));
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Prifati(View view)
    {
        String sql="UPDATE Vozenja SET Status=2 WHERE ID_Vozenje="+ID_Vozenje+";";
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ZakazanoVozenje.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        db.execSQL(sql);
    }

    public void Odbij(View view)
    {
        String sql="UPDATE Vozenja SET Status=0, Vozac_ID=NULL WHERE ID_Vozenje="+ID_Vozenje+";";
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(ZakazanoVozenje.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        db.execSQL(sql);

    }
}