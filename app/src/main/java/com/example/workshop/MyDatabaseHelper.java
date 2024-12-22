package com.example.workshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="CarPool2024.db";
    public static final int DATABASE_VERSION=7;

    public static final String TABLE_NAME="Korisnici";
    public static final String COLUMN_ID="ID_Korisnik";
    public static final String IME="Ime";
    public static final String PREZIME="Prezime";
    public static final String BROJ="Telefonski_Broj";
    public static final String EMAIL="E_mail";

    public static final String USERNAME="Username";


    public static final String PASSWORD="Password";

    public static final String TABLE_NAME1="Vozaci";

    public static final String COLUMN_ID1="ID_Vozac";
    public static final String IME1="Ime";

    public static final String PREZIME1="Prezime";

    public static final String BROJ1="Broj";
    public static final String EMAIL1="E_mail";

    public static final String USERNAME1="Username";
    public static final String PASSWORD1="Password";

    public static final String VREME_POCETOK="Vreme_Pocetok";
    public static final String VREME_KRAJ="Vreme_Kraj";

    public static final String CENA_KILOMETAR="Cena_KM";



    public static final String TABLE_NAME_CARS="Vozilo";

    public static final String VOZILO_ID="Vozilo_ID";


    public static final String MODEL="Model";

    public static final String GODINA="Godina";

    public static final String SLIKA="Slika";


    public static final String TABLE_VOZENJE="Vozenja";
    public static final String VOZENJE_ID="ID_Vozenje";
    public static final String KORISNIK_ID="Korisnik_ID";

    public static final String POCETNA_DEST_LAT="Pocetna_Lat";
    public static final String POCETNA_DEST_LONG="Pocetna_Long";
    public static final String KRAJNA_DEST_LAT="Krajna_Lat";
    public static final String KRAJNA_DEST_LONG="Krajna_Long";
    public static final String VOZAC_ID="Vozac_ID";
    public static final String STATUS="Status";
    public static final String RATING="Rating";
    public static final String KOMENTAR="Komentar";





    public MyDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {


        String query4="ALTER TABLE Vozenja ADD Pocetok_Ime;";
        db.execSQL(query4);
        String query5="ALTER TABLE Vozenja ADD Kraj_Ime;";
        db.execSQL(query5);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {


        onCreate(db);
    }
    void addKorisnik(String ime, String prezime, String broj, String email, String username, String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(IME,ime);
        cv.put(PREZIME,prezime);
        cv.put(BROJ,broj);
        cv.put(EMAIL,email);
        cv.put(USERNAME,username);
        cv.put(PASSWORD,password);

        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
    }
    void addVozac(String ime, String prezime, String broj, String email, String username, String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(IME1,ime);
        cv.put(PREZIME1,prezime);
        cv.put(BROJ1,broj);
        cv.put(EMAIL1,email);
        cv.put(USERNAME1,username);
        cv.put(PASSWORD1,password);

        long result=db.insert(TABLE_NAME1,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAll()
    {
        String query1="DELETE FROM Vozenja";

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query1);

        Toast.makeText(context,"Успешно исчистени табели!",Toast.LENGTH_SHORT).show();
    }

    void dodadiVozila()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MODEL, "Toyota Yaris");
        cv.put(GODINA, 2021);
        cv.put(SLIKA, "/");
        long result = db.insert(TABLE_NAME_CARS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Грешка при внес на податоците во база.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Успешно внесени податоци.", Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Peugeut 3008");
        cv.put(GODINA, 2018);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Peugeut 301");
        cv.put(GODINA, 2015);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Dacia Logan");
        cv.put(GODINA, 2009);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Volkswagen Passat");
        cv.put(GODINA, 2014);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Fiat Tipo");
        cv.put(GODINA, 2020);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Citroen C4");
        cv.put(GODINA, 2024);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Kia Sportage");
        cv.put(GODINA, 2019);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
        cv.put(MODEL, "Тoyota RAV4");
        cv.put(GODINA, 2022);
        cv.put(SLIKA, "/");
        result=db.insert(TABLE_NAME_CARS,null,cv);
        if(result==-1)
        {
            Toast.makeText(context,"Грешка при внес на податоците во база.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
        }
    }

}
