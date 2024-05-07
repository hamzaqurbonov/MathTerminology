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



//        adapter.setItemClickListner(new MainAdapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
////                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String dokumentId = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
////                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
////                Log.d("demo22", String.valueOf( path));
////                String getName = adapter.getItem(position).getName();
////                String getId = adapter.getItem(position).getId();
//
////                String getImageUrl = adapter.getItem(position).getImageUrl();
//                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
////                intent.putExtra("getName", getName);
////                intent.putExtra("part", getId);
////                intent.putExtra("dokumentId", dokumentId);
//                startActivity(intent);
//
//            }
//        });







    }

    private void setOnClickListner() {
//        Log.d("demo15", );
        listner = new MainAdapter.RecyclerViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                String key = nextArrayList.get(position);
                String name = nameArrayList.get(position);
                Toast.makeText(MainActivity.this,  position +  " "+ key +" " + name , Toast.LENGTH_SHORT).show();
                intent.putExtra( "key",key);
                intent.putExtra( "name",name);
                startActivity(intent);
            }

        };

    }






}