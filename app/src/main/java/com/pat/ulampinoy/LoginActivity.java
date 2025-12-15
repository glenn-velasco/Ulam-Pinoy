package com.pat.ulampinoy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pat.ulampinoy.User.SessionManager;
import com.pat.ulampinoy.User.User;
import com.pat.ulampinoy.databinding.ActivityLoginBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    SessionManager sessionManager;

    ActivityLoginBinding binding;

    AppDatabase db;

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        db = AppDatabase.getDatabase(this);

        binding.btnSignin.setOnClickListener(v -> performLogin());

    }

    private void performLogin() {

        String email = binding.etEmail.getText().toString().trim();

        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {

            binding.etEmail.setError("Email is required");

            binding.etEmail.requestFocus();

            return;
        }

        if (password.isEmpty()) {

            binding.etPassword.setError("Password is required");

            binding.etPassword.requestFocus();

            return;
        }

        executorService.execute(() -> {

            User user = db.userDao().login(email, password);

            handler.post(() -> {

                if (user != null) {

                    sessionManager.createLoginSession(email);

                    finish();

                } else {

                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                }
            });

        });

    }

}