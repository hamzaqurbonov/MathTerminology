package com.example.mathterminology;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();;
    RecyclerView recyclerView;
    MainAdapter.RecyclerViewClickListner listner;
    private MainAdapter adapter;
    Context context;
//    DatabaseReference ref = myRef.child("data");
    public  ArrayList<String> nextArrayList = new ArrayList<>();
    public  ArrayList<String> nameArrayList = new ArrayList<>();
    public Map<String, String> map = new HashMap<>();
//    MapModel mapModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);





//        final ArrayAdapter<String> myArrayAdaptrer = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,nextArrayList);


        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("demo12", "Error getting data", task.getException());
                }
                else {
                    Log.d("demo12", String.valueOf(task.getResult().getValue()));
                }
            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                String value =  snapshot.getValue(String.class);
                String value2 = (String) snapshot.getKey();

                nextArrayList.add(value2);
                nameArrayList.add(value);
                initViews();
                setOnClickListner();
//                Log.d("demo21", "nextArrayList " + nextArrayList);

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

        adapter = new MainAdapter(nextArrayList,listner);
        recyclerView.setAdapter(adapter);


    }

    private void setOnClickListner() {
//        Log.d("demo15", );
        listner = new MainAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                String key = nextArrayList.get(position);
                String name = nameArrayList.get(position);
//                Toast.makeText(MainActivity.this,  position +  " "+ key +" " + name , Toast.LENGTH_SHORT).show();
                intent.putExtra( "key",key);
                intent.putExtra( "name",name);
                startActivity(intent);
            }

        };

    }






}