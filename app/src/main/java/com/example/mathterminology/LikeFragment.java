package com.example.mathterminology;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class LikeFragment extends Fragment {


    private ArrayList<LakeModel> modalArrayList;
    private DbLike dbLike;
    private LikeAdapter likeAdapter;
    private RecyclerView recyWiewLike;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyWiewLike = view. findViewById(R.id.recyLike);


        modalArrayList = new ArrayList<>();
        dbLike = new DbLike(getActivity());

        modalArrayList = dbLike.readCourses();

        likeAdapter = new LikeAdapter(modalArrayList, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyWiewLike.setLayoutManager(linearLayoutManager);

        recyWiewLike.setAdapter(likeAdapter);

        return view;
    }
}