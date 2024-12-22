package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VozacActivity extends AppCompatActivity implements TimePickerFragment.TimePickerListener, RecyclerViewInterface {

    public int ID;
    public String Ime;
    public ArrayList<Vozilo> Vozila;

    public TextView tv1;

    public TextView tv2;

    public TextView tv3;

    public TextView ImeView;
    public Button btn1;
    public Button btn2;
    public EditText btn3;

    public double AVG_rating;
    public int i=0;
    public double average;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vozac);
        Intent intent=getIntent();
        ID=intent.getExtras().getInt("ID");
        Ime=intent.getExtras().getString("Ime");
        TextView tv=findViewById(R.id.izbranoVozilo);
        tv.setText("Избрано возило: ");
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(VozacActivity.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String a="SELECT AVG(Rating) FROM Vozenja WHERE Vozac_ID="+ID+" AND Status=3"+";";

        Cursor avg=db.rawQuery(a,null);
        if(avg==null)
        {
            average=0;
        }
        else
        {
            avg.moveToFirst();
            if(avg.isNull(0))
            {
                average=0;
            }
            else
            {
                average=avg.getDouble(0);
            }
        }
        TextView avg101=findViewById(R.id.avg101);
        avg101.setText(Double.toString(average));
        Cursor Vozac=db.rawQuery("SELECT Vozilo_ID FROM Vozaci WHERE ID_Vozac="+ID+";",null);
        if(Vozac!=null && Vozac.moveToFirst())
        {
            int columnIndex = Vozac.getColumnIndex("Vozilo_ID");

            if (!Vozac.isNull(columnIndex)) {
                // Get the value if it's not NULL
                int ID_Vozilo = Vozac.getInt(columnIndex);
                // Display it on the Android Activity
                Cursor Vozilo=db.rawQuery("SELECT C.Model, C.Godina FROM Vozilo AS C WHERE C.Vozilo_ID="+ID_Vozilo+";",null);
                Vozilo.moveToFirst();
                String Model=Vozilo.getString(0);
                int Godina=Vozilo.getInt(1);
                tv.setText("Возило: "+Model+" "+Godina);


            } else {
                // Handle the case when ID_Vozilo is NULL
                tv.setText("Немате избрано возило");
            }
        }





        String query_vozila="SELECT * FROM Vozilo;";
        Cursor cursor=db.rawQuery(query_vozila,null);
        cursor.moveToFirst();
        Vozila=new ArrayList<>();
        Vozilo V;
        int Id;
        String Model;
        int Godina;
        String Slika;
        do {
            // Access data for each row
            Id = cursor.getInt(0);   // Column 0
            Model = cursor.getString(1);  // Column 1
            Godina = cursor.getInt(2);  // Column 2
            Slika = cursor.getString(3);  // Column 3

            // Create a Vozilo object and add it to the list
            V = new Vozilo(Id, Model, Godina, Slika);
            Vozila.add(V);

        } while (cursor.moveToNext());

        int dolz=Vozila.size();
        RecyclerView recyclerView=findViewById(R.id.mRecyclerView);

        Vozilo_RecyclerViewAdapter adapter=new Vozilo_RecyclerViewAdapter(this,Vozila,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv1=findViewById(R.id.StartTime);
        tv2=findViewById(R.id.EndTime);
        tv3=findViewById(R.id.DollarHour);
        btn1=findViewById(R.id.btnstart);
        btn2=findViewById(R.id.btnend);
        btn3=findViewById(R.id.btnprice);
        ImeView=findViewById(R.id.welcome);
        ImeView.setText("Добредојде "+Ime+"!");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    public void StartTime(View view)
    {
        tv1=findViewById(R.id.StartTime);
        tv2=findViewById(R.id.EndTime);
        tv3=findViewById(R.id.DollarHour);
        btn1=findViewById(R.id.btnstart);
        btn2=findViewById(R.id.btnend);
        btn3=findViewById(R.id.btnprice);
        i=0;
        DialogFragment timePickerFragment =new TimePickerFragment();
        timePickerFragment.setCancelable(false);
        timePickerFragment.show(getSupportFragmentManager(),"timepicker");


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute)
    {
        String s;
        if(i==0)
        {
            s="Start time: ";
            tv1.setText(hour+":"+minute);
        }
        else
        {
            s="End time: ";
            tv2.setText(hour+":"+minute);
        }
    }

    public void EndTime(View view)
    {
        i=1;
        tv1=findViewById(R.id.StartTime);
        tv2=findViewById(R.id.EndTime);
        tv3=findViewById(R.id.DollarHour);
        btn1=findViewById(R.id.btnstart);
        btn2=findViewById(R.id.btnend);
        btn3=findViewById(R.id.btnprice);
        DialogFragment timePickerFragment =new TimePickerFragment();
        timePickerFragment.setCancelable(false);
        timePickerFragment.show(getSupportFragmentManager(),"timepicker");
    }

    public void UpdateVozac(View view)
    {
        tv1=findViewById(R.id.StartTime);
        tv2=findViewById(R.id.EndTime);
        tv3=findViewById(R.id.DollarHour);
        btn1=findViewById(R.id.btnstart);
        btn2=findViewById(R.id.btnend);
        btn3=findViewById(R.id.btnprice);

        String starttime=tv1.getText().toString().trim();
        String endtime=tv2.getText().toString().trim();
        int cena=Integer.valueOf(btn3.getText().toString().trim());
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(VozacActivity.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String query="UPDATE Vozaci SET Vreme_Pocetok='"+starttime+"', Vreme_Kraj='"+endtime+"',Cena_KM="+cena+" WHERE ID_Vozac="+ID+";";
        db.execSQL(query);
        Toast.makeText(this,"Успешно променети податоци",Toast.LENGTH_SHORT).show();
    }

    public void OdiKonVozenja(View view)
    {

        MyDatabaseHelper dbhelper=new MyDatabaseHelper(VozacActivity.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String query_vozenja="SELECT * FROM Vozenja WHERE Vozac_ID="+ID+" AND ("+"Status=1 OR Status=2);";
        Cursor vozenja=db.rawQuery(query_vozenja,null);
        if(vozenja.getCount()>0)
        {
            Toast.makeText(this,"Имате веќе закажано возење, не може да закажувате ново возење!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query="SELECT * FROM Vozenja WHERE Status=0;";
            Cursor vozenja_0=db.rawQuery(query,null);
            if(vozenja_0.getCount()>0)
            {
                Intent intent=new Intent(this, ListaKreiraniVozenja.class);
                intent.putExtra("ID",ID);
                intent.putExtra("Ime",Ime);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Нема нови возења од корисниците",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onItemClick(int position)
    {
        int Vozilo_ID=Vozila.get(position).Id;
        Vozilo V=Vozila.get(position);
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(VozacActivity.this);
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        String query="UPDATE Vozaci SET Vozilo_ID="+Vozilo_ID+" WHERE ID_Vozac="+ID+";";
        db.execSQL(query);
        TextView tv=findViewById(R.id.izbranoVozilo);
        tv.setText(V.Model+" "+V.Godina);
        Toast.makeText(this,"Возилото е променето во "+Vozila.get(position).Model,Toast.LENGTH_SHORT).show();

    }
    public void OdiKonTekovnoVozenje(View view)
    {
        MyDatabaseHelper dbhelper=new MyDatabaseHelper(VozacActivity.this);
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        String query_vozenja="SELECT * FROM Vozenja WHERE Vozac_ID="+ID+" AND Status=2;";
        Cursor vozenja=db.rawQuery(query_vozenja,null);
        if(vozenja.getCount()==0)
        {
            Toast.makeText(this,"Тековното возење не е потврдено или воопшто немате активно возење!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            vozenja.moveToFirst();
            int ID_Tekovno=vozenja.getInt(0);
            Intent intent=new Intent(this, ListaZakazaniVozenja.class);
            intent.putExtra("ID_Tekovno",ID_Tekovno);
            intent.putExtra("ID_Vozac",ID);
            intent.putExtra("Ime",Ime);
            startActivity(intent);

        }
    }

}