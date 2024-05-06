package com.example.mathterminology;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();;
    RecyclerView recyclerView;
//    DatabaseReference ref = myRef.child("data");
    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    public Map<String, String> map = new HashMap<>();
//    MapModel mapModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();




        final ArrayAdapter<String> myArrayAdaptrer = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,nextArrayList);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                String value =  snapshot.getValue(String.class);
                String value2 = (String) snapshot.getKey();

                nextArrayList.add(value);

                Log.d("demo21", "nextArrayList " + nextArrayList);

//                map.put(value2,value);
// --------------------  Mapni ishakillantirib beradi ----------
//                for (Map.Entry<String, String> me :
//                        map.entrySet()) {
//
//                    // Printing keys
//                    System.out.print(me.getKey() + ":");
//                    System.out.println(me.getValue());
//
//                    Log.d("demo21", "Map " + me);
//                }
//
//                Log.d("demo21", "nextArrayList " + value2 + " + " + value + " " + map);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                myArrayAdaptrer.notifyDataSetChanged();

//                Log.d("demo21", "myArrayAdaptrer " +  String.valueOf(myArrayAdaptrer));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void initViews() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private void refreshAdapter (List<Member>members) {
        MainAdapter adapter = new MainAdapter(context, members);
        recyclerView.setAdapter(adapter);
    }




}