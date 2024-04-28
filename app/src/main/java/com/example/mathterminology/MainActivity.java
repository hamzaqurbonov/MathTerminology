package com.example.mathterminology;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    DatabaseReference ref = myRef.child("data");
    public  static ArrayList<String> nextArrayList = new ArrayList<>();
    public Map<String, String> map = new HashMap<>();
//    MapModel mapModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");


//        final ArrayAdapter<String> myArrayAdaptrer = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,nextArrayList);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                MapModel city = snapshot.toObject(MapModel.class);

                String value =  snapshot.getValue(String.class);
                String value2 = (String) snapshot.getKey();
                nextArrayList.add(value);

                map.put(value2,value);

//                map.put(new MapModel(value2));
//                myArrayAdaptrer.notifyDataSetChanged();

                for (Map.Entry<String, String> me :
                        map.entrySet()) {

                    // Printing keys
                    System.out.print(me.getKey() + ":");
                    System.out.println(me.getValue());

                    Log.d("demo21", "Map " + me);
                }

                Log.d("demo21", "nextArrayList " + value2 + " + " + value + " " + map);
//                Log.d("demo21", "mapModel " + mapModel.getName() );

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
}