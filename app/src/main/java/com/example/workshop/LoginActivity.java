package com.example.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void GoBack(View view)
    {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void LoginUser(View view)
    {
        Intent intent=new Intent(this, LoginUserActivity.class);
        startActivity(intent);
    }

    public void LoginDriver(View view)
    {
        Intent intent=new Intent(this, LoginDriverActivity.class);
        startActivity(intent);
    }
}