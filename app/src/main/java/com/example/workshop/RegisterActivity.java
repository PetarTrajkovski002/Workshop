package com.example.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Opis123), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void GoBack(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void RegisterUser(View view)
    {
        Intent intent=new Intent(this,RegisterUserActivity.class);
        startActivity(intent);
    }

    public void RegisterDriver(View view) {
        Intent intent=new Intent(this,RegisterDriverActivity.class);
        startActivity(intent);
    }
}