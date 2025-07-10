package com.example.mathterminology;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("mathterminology");
    private ArrayList<MainFragmentModel> modalArrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    private DBMainFragment dbMainFragment;
    DbHistory dbHistory;
    DbLike dbLike;
    private DBMainFragmentAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    Toolbar toolbar;
    MenuItem menuItem;
    SearchView searchView;
    ImageButton sync, information;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.rview);
        sync = view.findViewById(R.id.btn_sync);
        information = view.findViewById(R.id.information);
        dbMainFragment = new DBMainFragment(getActivity());
        dbHistory = new DbHistory(getActivity());
        dbLike = new DbLike(getActivity());

        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");


        recyclerViewAdapter();
        Collection();
        swipeRefreshLayout();
        sync();
        AlertDialogItem();

        return view;
    }

    private void sync() {
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IntroActivity.class);
                startActivity(intent);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog();
            }
        });
    }

    public void AlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sync text!");
        builder.setMessage("Will you add new words?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dbMainFragment.deleteAllData();

                progressBar.setVisibility(View.VISIBLE);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String word = snapshot.child("word").getValue(String.class);
                            String translate = snapshot.child("translate").getValue(String.class);
                            // Янги маълумотларни базага қўшиш
                            dbMainFragment.addNewCourse(word, translate);
                        }
                        // Янги маълумотларни ўқиш ва адаптерни янгилаш
                        modalArrayList.clear(); // Аввалги маълумотларни тозалаш
                        modalArrayList.addAll(dbMainFragment.readCourses()); // Янгилари билан алмаштириш
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged(); // Адаптерга янгиланишни билдириш
                        Toast.makeText(getContext(), "Information is updated!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("FirebaseError", databaseError.getMessage());
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void recyclerViewAdapter() {
        modalArrayList = new ArrayList<>();
        modalArrayList = dbMainFragment.readCourses(); // SQLite маълумотларини ўқиш
        adapter = new DBMainFragmentAdapter(modalArrayList, getActivity()); // Адаптерга тайинлаш

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void swipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerViewAdapter();
                AlertDialogItem();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Огоҳлантириш ойнасини қуриш
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Will you leave the app?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requireActivity().finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    private void Collection() {

        if (modalArrayList.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String word = snapshot.child("word").getValue(String.class);
                        String translate = snapshot.child("translate").getValue(String.class);


                        dbMainFragment.addNewCourse(word, translate);
                    }


                    modalArrayList.clear();
                    modalArrayList.addAll(dbMainFragment.readCourses());
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseError", databaseError.getMessage());
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
        menuItem = menu.findItem(R.id.search_1);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint(Html.fromHtml("<font color =\"#FFFFFF\" >Search</font>"));

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                newText = newText.toLowerCase();
                Log.d("demo43", "onDataChange1");
                processSearch(newText);
                AlertDialogItem();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void processSearch(String query) {
        modalArrayList.clear();
        modalArrayList = dbMainFragment.searchCourses(query);

        Log.d("SearchResults", "Қидирув сўрови: " + query + " Натижалар сони: " + modalArrayList.size());

        adapter.notifyDataSetChanged();
        adapter = new DBMainFragmentAdapter(modalArrayList, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void AlertDialogItem() {


        adapter.setOnItemClickListener(new DBMainFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MainFragmentModel model = modalArrayList.get(position);
                String getWord = model.getWord();
                String getTranslate = model.getTranslate();

                dbHistory.addNewCourse(getWord, getTranslate);


                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.layout_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);

                TextView titleTextView = dialogView.findViewById(R.id.textViewTitle);
                TextView messageTextView = dialogView.findViewById(R.id.textViewMessage);
                ImageView positiveButton = dialogView.findViewById(R.id.positiveButton);
                ImageView negativeButton = dialogView.findViewById(R.id.negativeButton);
                ImageView neutralButton = dialogView.findViewById(R.id.neutralButton);

                titleTextView.setText(getWord);
                messageTextView.setText(getTranslate);

                AlertDialog dialog = builder.create();
                dialog.show();
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbLike.addNewCourse(getWord, getTranslate);
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Text is saved!", Toast.LENGTH_SHORT).show();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                neutralButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, "Lug'at so'zi: " + getWord + "\n" + "Tarjimasi: " + getTranslate);
                        intent.setType("text/plain");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    window.setAttributes(layoutParams);
                }
            }
        });

    }

}

