package com.example.mathterminology;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Handler;
import android.os.Looper;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String FIRST_TIME_KEY = "firstTime";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
         boolean firstTime = settings.getBoolean(FIRST_TIME_KEY, true);


         if (firstTime) {
             // Агар биринчи марта очилган бўлса, интро ёки маълумот экранини кўрсатиш
             Intent intent = new Intent(this, IntroActivity.class);
             startActivity(intent);
             finish();
         } else {
             // Агар аввал очилган бўлса, тўғридан-тўғри асосий экранга ўтиш
             setContentView(R.layout.activity_main);
             BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
             bottomNav.setOnNavigationItemSelectedListener(navListener);
             getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MainFragment()).commit();
         }



        // Dastlab fragmentni 5 soniyaga ko'rsatish
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
//                bottomNav.setOnNavigationItemSelectedListener(navListener);
////                button.setVisibility(View.GONE);
//                // 5 soniyadan keyin asosiy fragmentni ko'rsatish
//                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MainFragment()).commit();
//            }
//        }, 12000 ); // 5000 millisekund = 5 soniya
//
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
            intent.putExtra(Intent.EXTRA_TEXT, "https://t.me/Mathterminology/10");
            intent.setType("text/plain");
            startActivity(intent);
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };





}