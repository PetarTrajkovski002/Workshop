package com.example.workshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Komentar_Rating extends AppCompatActivity {

    public int ID_Vozenje;
    public int ID_Korisnik;

    EditText et;

   NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_komentar_rating);
        Intent intent=getIntent();
        ID_Vozenje=intent.getExtras().getInt("ID_Vozenje");
        ID_Korisnik=intent.getExtras().getInt("ID_Korisnik");
        et=findViewById(R.id.Comment);
        numberPicker=findViewById(R.id.picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Podnesi(View view)
    {
        String comment=et.getText().toString();
        int rejting=numberPicker.getValue();
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(Komentar_Rating.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String sql="UPDATE Vozenja SET Rating="+rejting+", Komentar='"+comment+"' WHERE ID_Vozenje="+ID_Vozenje+";";;
        db.execSQL(sql);
        Toast.makeText(this, "Успешно дадена оценка!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,Korisnik_ZaOcena.class);
        intent.putExtra("ID",ID_Korisnik);




    }
}