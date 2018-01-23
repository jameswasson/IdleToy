package com.example.james.idletoy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import Game.Game;

/**
 * Created by James on 12/21/2017.
 */

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder>{

    private static Context myContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button mainButton;
        public ViewHolder(View v) {
            super(v);
            mainButton = (Button) v.findViewById(R.id.main_button);
            mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int)mainButton.getTag();
                    Game.getGame().tryToBuyBuilding(position);
                    mainButton.setText(Game.getGame().getInfoForBuilding(position));
                    ((MainActivity)(myContext)).refreshToyRate();
                }
            });
        }
    }

    public BuildingAdapter(Context context) {
        myContext = context;
    }

    @Override
    public BuildingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.building_purchase_button, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mainButton.setText(Game.getGame().getInfoForBuilding(position));
        holder.mainButton.setTag(position);
    }

    @Override
    public int getItemCount() {
        return Game.getGame().getListOfBuildingNames().size();
    }
}