package com.example.mathterminology;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

/**
// * A simple {@link Fragment} subclass.
// * Use the {@link MainFragment#newInstance} factory method to
// * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public MainFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MainFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MainFragment newInstance(String param1, String param2) {
//        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//
//    }

    RecyclerView rview;
    myadapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =    inflater.inflate(R.layout.fragment_main, container, false);



        rview = view.findViewById(R.id.rview);

//        ((AppCompatActivity)getActivity()).setSupportActionBar(view.findViewById(R.id.toolbar));
//        ((AppCompatActivity)getActivity()). getSupportActionBar().setTitle("");



        setUpRecyclerView();

        return view;
    }





    private void setUpRecyclerView() {


//        rview = findViewById(R.id.rview);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology"), model.class)
                        .build();

        adapter = new myadapter(options);
        rview.setAdapter(adapter);

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String dokumentId = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
//                Log.d("demo22", String.valueOf( path));
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

//                String getImageUrl = adapter.getItem(position).getImageUrl();
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);
//                intent.putExtra("dokumentId", dokumentId);
                startActivity(intent);

            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        ((AppCompatActivity)getActivity()). getMenuInflater().inflate(R.menu.menu_item,menu);
//
//        MenuItem item = menu.findItem(R.id.search_1);
//
//        SearchView searchView = (SearchView)item.getActionView();
//        searchView.setQueryHint("Qidiruv");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processSearch(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    void processSearch(String s) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology").orderByChild("word").startAt(s).endAt(s + "\uf8ff") ,model.class)
                        .build();
        adapter = new myadapter(options);
        adapter.startListening();
        rview.setAdapter(adapter);

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String dokumentId = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
//                Log.d("demo22", String.valueOf( path));
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

//                String getImageUrl = adapter.getItem(position).getImageUrl();
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);
//                intent.putExtra("dokumentId", dokumentId);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        rview.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }


}