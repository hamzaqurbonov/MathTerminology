package com.example.mathterminology;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dastlab fragmentni 5 soniyaga ko'rsatish
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
                bottomNav.setOnNavigationItemSelectedListener(navListener);

                // 5 soniyadan keyin asosiy fragmentni ko'rsatish
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MainFragment()).commit();
            }
        }, 15000); // 5000 millisekund = 5 soniya
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_main) {
            selectedFragment = new MainFragment();

        } else if (itemId == R.id.nav_live) {
            selectedFragment = new HistoryFragment();

        } else if (itemId == R.id.nav_like) {
            selectedFragment = new LikeFragment();

        } else if (itemId == R.id.share_send) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "https://t.me/Mathterminology/7");
            intent.setType("text/plain");
            startActivity(intent);
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };

}