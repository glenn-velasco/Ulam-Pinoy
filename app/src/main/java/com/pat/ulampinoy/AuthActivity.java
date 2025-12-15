package com.pat.ulampinoy;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pat.ulampinoy.User.SessionManager;
import com.pat.ulampinoy.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    SessionManager sessionManager;

    ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_auth);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {

            startActivity(new Intent(AuthActivity.this, MainActivity.class));

            finish();

        }

        binding.btnLogin.setOnClickListener(v -> {

            startActivity(new Intent(AuthActivity.this, LoginActivity.class));

            finish();

        });

        binding.btnRegister.setOnClickListener(v -> {

            startActivity(new Intent(AuthActivity.this, RegisterActivity.class));

        });


    }
}