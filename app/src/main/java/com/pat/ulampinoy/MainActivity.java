package com.pat.ulampinoy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.pat.ulampinoy.User.SessionManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    SessionManager sessionManager;

    NavigationView navigationView;

    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        getSupportActionBar().setTitle("");

        viewPager = findViewById(R.id.view_pager);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(this);

        viewPager.setAdapter(adapter);

        viewPager.setUserInputEnabled(false);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        navigationView.setCheckedItem(R.id.nav_home);
                        break;
                }

            }

        });

        if (savedInstanceState == null) {

            navigationView.setCheckedItem(R.id.nav_home);

            viewPager.setCurrentItem(0, false);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            viewPager.setCurrentItem(0);

        }
        else if (id == R.id.nav_logout) {

            sessionManager.logout();

            finish();

            startActivity(new Intent(MainActivity.this, AuthActivity.class));

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}