package me.h.shakawat.allcricketforb.TabFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.h.shakawat.allcricketforb.Model.WorldCup;
import me.h.shakawat.allcricketforb.R;
import me.h.shakawat.allcricketforb.RecyclerView.AsiaCupRecyclerView;

public class AsiaCup_2018_Fragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private List<WorldCup> lstAsiaCup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_asia_cup_2018_, container, false);
        mRecyclerView = view.findViewById(R.id.asiaCupRecyclerViewId);
        AsiaCupRecyclerView asiaCupRecyclerView = new AsiaCupRecyclerView(getContext(),lstAsiaCup);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(asiaCupRecyclerView);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lstAsiaCup = new ArrayList<>();
        lstAsiaCup.add(new WorldCup(R.drawable.new_zeland_flag,R.drawable.south_africa,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.australia_flag,R.drawable.afghanistan_flag,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.england_flag,R.drawable.bangladesh_flag,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.india_flag,R.drawable.south_africa,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.england_flag,R.drawable.pakistan_flag,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.new_zeland_flag,R.drawable.south_africa,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.england_flag,R.drawable.australia_flag,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));
        lstAsiaCup.add(new WorldCup(R.drawable.afghanistan_flag,R.drawable.south_africa,"Play No :01","Gour : A","England Vs South Africa","30May,2019","Time : 15.30","Mirpur Stadium"));


    }

}
