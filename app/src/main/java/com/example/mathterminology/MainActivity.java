package com.example.mathterminology;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

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

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("");



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


//   public void search () {
//       myRef.child("Abacus").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<DataSnapshot> task) {
//               if (!task.isSuccessful()) {
//                   Log.e("demo12", "Error getting data", task.getException());
//               }
//               else {
//                   Log.d("demo12", String.valueOf(task.getResult().getValue()));
//               }
//           }
//       });
//   }
//
//
//    private void setUpRecyclerView() {
//
//        Query query = hadRef.orderBy("idUrl", Query.Direction.DESCENDING);
//
//        FirestoreRecyclerOptions<LongModel> options = new FirestoreRecyclerOptions.Builder<LongModel>().setQuery(query, LongModel.class).build();
//
//        adapter = new LongAdapter(options);
//
////        RecyclerView recyclerView = findViewById(R.id.recyclerView);
////        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerView.setAdapter(adapter);
//
//    }



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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem menuItem = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setQueryHint("Search 123");
        Log.d("demo3", "mysearch: 1");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                mysearch(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mysearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void mysearch(String str) {


        Log.d("demo3", "mysearch: 2");

    }




}