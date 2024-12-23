package com.example.loginsignup.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import com.example.loginsignup.CustomeAdapter;
import com.example.loginsignup.DataModel;
import com.example.loginsignup.MyData;
import com.example.loginsignup.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_app#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_app extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Recycle View
    private ArrayList<DataModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter adapter;

    // Modal Views
    private LinearLayout modalBackground;
    private LinearLayout modalCard;
    private ImageView modalImageView;
    private TextView modalName, modalFamily, modalDescription;
    private ImageButton closeModalBtn;

    public fragment_app() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_app.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_app newInstance(String param1, String param2) {
        fragment_app fragment = new fragment_app();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app, container, false);


        // Recycle View
        dataSet = new ArrayList<>();
        recyclerView =  view.findViewById(R.id.recycleViewApp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initialize Modal Views
        modalBackground = view.findViewById(R.id.modalBackground);
        modalCard = view.findViewById(R.id.modalCard);
        modalImageView = view.findViewById(R.id.modalImageView);
        modalName = view.findViewById(R.id.modalName);
        modalFamily = view.findViewById(R.id.modalFamily);
        modalDescription = view.findViewById(R.id.modalDescription);
        closeModalBtn = view.findViewById(R.id.closeModalBtn);

        // Populate the dataset with data from MyData
        for (int i = 0; i < MyData.nameArray.length; i++) {
            dataSet.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.familyArray[i],
                    MyData.descriptionArray[i],
                    MyData.drawableArray[i],
                    MyData.id_[i]
            ));
        }

        adapter = new CustomeAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        // Search View functionality
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(newText);  // Call filter method in the adapter
                return false;
            }
        });

        // Item click listener for opening modal
        adapter.setOnItemClickListener(new CustomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataModel dataModel) {
                // Show modal and fill data
                modalBackground.setVisibility(View.VISIBLE);
                modalCard.setVisibility(View.VISIBLE);

                modalImageView.setImageResource(dataModel.getImage());
                modalName.setText(dataModel.getName());
                modalFamily.setText(dataModel.getFamily());
                modalDescription.setText(dataModel.getDescription());
            }
        });

        // Close modal when X button is clicked
        closeModalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalBackground.setVisibility(View.GONE);
                modalCard.setVisibility(View.GONE);
            }
        });

        return view;
    }
}