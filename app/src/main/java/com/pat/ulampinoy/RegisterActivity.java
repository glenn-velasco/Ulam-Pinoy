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
import com.pat.ulampinoy.databinding.ActivityRegisterBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    SessionManager sessionManager;

    ActivityRegisterBinding binding;

    AppDatabase db;

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        db = AppDatabase.getDatabase(this);

        binding.btnRegister.setOnClickListener(v -> performRegister());

    }

    private void performRegister() {

        String name = binding.etUsername.getText().toString();

        String email = binding.etEmail.getText().toString();

        String password = binding.etPassword.getText().toString();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){

            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();

            return;
        }

        executorService.execute(() -> {

            User existingUser = db.userDao().checkUserExists(email);

            if (existingUser == null) {

                User newUser = new User(name, email, password);

                db.userDao().registerUser(newUser);

                handler.post(() -> {

                    Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

                    finish();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                    sessionManager.createLoginSession(email);

                });

            } else {

                handler.post(() -> {

                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();

                });

            }

        });

    }

}