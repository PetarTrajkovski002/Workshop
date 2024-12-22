package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginDriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_driver);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void Login(View view)
    {
        EditText username_pole=findViewById(R.id.usernamekorisnik);
        EditText password_pole=findViewById(R.id.passwordkorisnik);

        String username=username_pole.getText().toString().trim();
        String password=password_pole.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Едно од полињата е празно.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Успешно внесени податоци.",Toast.LENGTH_SHORT).show();
            MyDatabaseHelper dbhelper=new MyDatabaseHelper(LoginDriverActivity.this);

            SQLiteDatabase db=dbhelper.getReadableDatabase();

            String query="SELECT * FROM Vozaci WHERE Username='"+username+"'AND Password='"+password+"';";
            Cursor cursor=db.rawQuery(query,null);

            if(cursor.getCount()==0)
            {
                Toast.makeText(this,"Погрешно корисничко име или лозинка!",Toast.LENGTH_SHORT).show();;
            }
            else
            {
                Toast.makeText(this,"Пронајден е возачот со тие податоци.",Toast.LENGTH_SHORT).show();;
                cursor.moveToFirst();
                int id=cursor.getInt(0);
                String ime=cursor.getString(1);
                Intent intent=new Intent(this, VozacActivity.class);
                intent.putExtra("ID",id);

                intent.putExtra("ID",id);
                intent.putExtra("Ime",ime);
                startActivity(intent);
            }

        }

    }
}