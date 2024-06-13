package com.example.mathterminology;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private ArrayList<HistoryModel> modalArrayList;
    private DbHistory dbHistory;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyHistory = view. findViewById(R.id.recyHistory);


        modalArrayList = new ArrayList<>();
        dbHistory = new DbHistory(getActivity());

        modalArrayList = dbHistory.readCourses();

        historyAdapter = new HistoryAdapter(modalArrayList, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyHistory.setLayoutManager(linearLayoutManager);

        recyHistory.setAdapter(historyAdapter);

        return view;
    }

}