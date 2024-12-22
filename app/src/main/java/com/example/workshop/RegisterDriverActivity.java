package com.example.workshop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_driver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Register(View view) {
        EditText ime_pole=findViewById(R.id.ime);
        EditText prezime_pole=findViewById(R.id.prezime);
        EditText broj_pole=findViewById(R.id.broj);
        EditText email_pole=findViewById(R.id.email);
        EditText username_pole=findViewById(R.id.username);
        EditText password_pole=findViewById(R.id.password);
        EditText confirm_password_pole=findViewById(R.id.confirm_password);

        String ime=ime_pole.getText().toString().trim();
        String prezime=prezime_pole.getText().toString().trim();
        String broj=broj_pole.getText().toString().trim();
        String email=email_pole.getText().toString().trim();
        String username=username_pole.getText().toString().trim();
        String password=password_pole.getText().toString().trim();
        String confirm_password=confirm_password_pole.getText().toString().trim();
        if(ime.isEmpty() || prezime.isEmpty() || broj.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm_password.isEmpty())
        {
            Toast.makeText(this,"Барем едно од полињата не е пополнето.",Toast.LENGTH_SHORT).show();
        }
        else if(password.equals(confirm_password)==false)
        {
            Toast.makeText(this,"Лозинките не се совпаѓаат.",Toast.LENGTH_SHORT).show();

        }
        else
        {
            MyDatabaseHelper db=new MyDatabaseHelper(RegisterDriverActivity.this);
            db.addVozac(ime,prezime,broj,email,username,password);



        }


    }
}