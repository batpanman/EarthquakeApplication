//
// Name                 Panagiotis Chondrodimos
// Student ID           S1517035
// Programme of Study   BSc Computing
//

package org.pchond200.gcu.earthquakeapplication;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {

private ArrayList<String> arrayOne;
private ArrayList<String> arrayTwo;
private ArrayList<String> arrayThree;
private ArrayList<String> arrayFour;
private ArrayList<String> arraySearch;
Dialog myDialog;


public static class ViewHolder extends RecyclerView.ViewHolder{

    public TextView location;
    public TextView date;
    public TextView depth;
    public TextView magn;
    private CardView card;


    public ViewHolder(View itemView) {
        super(itemView);

        location = (TextView) itemView.findViewById(R.id.titleTxt);

        date = (TextView) itemView.findViewById(R.id.date);

        depth = (TextView) itemView.findViewById(R.id.depthTxt);

        magn = (TextView) itemView.findViewById(R.id.magTxt);

        card = (CardView) itemView.findViewById(R.id.card);

    }

}

    public MyAdapter(ArrayList<String> arrayOnee, ArrayList<String> arrayTwoo, ArrayList<String> arrayThreee, ArrayList<String> arrayFourr) {
        arrayOne = arrayOnee;
        arrayTwo = arrayTwoo;
        arrayThree = arrayThreee;
        arrayFour = arrayFourr;
        arraySearch = new ArrayList<>(arrayOne);
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_view_row, parent, false);
        CardView card = (CardView) itemView.findViewById(R.id.card);

        final ViewHolder view = new ViewHolder(itemView);




        view.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.dialog_info);

                String seperateMag = arrayFour.get(view.getAdapterPosition());
                String[] seper = seperateMag.split(": ");
                seper[0] = seper[0];
                seper[1] = seper[1];

                boolean isNegative = false;

                // Check if magnitude has negative number

                if (seper[1].contains("-")) {
                    isNegative = true;
                }
                String[] noSpace = seper[1].split("");
                if (isNegative == true) {

                    // Remove the "-" symbol so that it doesn't crash later

                    noSpace = seper[1].split("-");
                    noSpace[0] = noSpace[0];
                    noSpace[1] = noSpace[1];
                    System.out.println(noSpace[0]);
                    System.out.println(noSpace[1]);
                } else {

                    // Remove the space

                    noSpace = seper[1].split(" ");
                    noSpace[0] = noSpace[0];
                    noSpace[1] = noSpace[1];
                    System.out.println(noSpace[0]);
                    System.out.println(noSpace[1]);
                }


                double magnInt = Double.valueOf(noSpace[1]);
                if (magnInt >= 0 & magnInt <= 0.8) {
                    Drawable shape=ContextCompat.getDrawable(context, R.drawable.oval_green);
                    TextView dialogMag1 = (TextView)myDialog.findViewById(R.id.dialogMag1);
                    dialogMag1.setBackground(shape);
                } else if (magnInt > 0.8 & magnInt < 1.5) {
                    Drawable shape=ContextCompat.getDrawable(context, R.drawable.oval_yellow);
                    TextView dialogMag1 = (TextView)myDialog.findViewById(R.id.dialogMag1);
                    dialogMag1.setBackground(shape);
                } else {
                    Drawable shape=ContextCompat.getDrawable(context, R.drawable.oval_shape);
                    TextView dialogMag1 = (TextView)myDialog.findViewById(R.id.dialogMag1);
                    dialogMag1.setBackground(shape);
                }

                TextView dialogLoc = (TextView) myDialog.findViewById(R.id.dialogLoc);
                TextView dialogMag = (TextView) myDialog.findViewById(R.id.dialogMag);
                TextView dialogMag2 = (TextView)myDialog.findViewById(R.id.dialogMag1);
                TextView dialogMag3 = (TextView)myDialog.findViewById(R.id.dialogMag1);
                TextView dialogDepth = (TextView)myDialog.findViewById(R.id.dialogDepth);
                TextView dialogDate = (TextView)myDialog.findViewById(R.id.dialogDate);

                dialogLoc.setText(arrayOne.get(view.getAdapterPosition()));

                dialogMag.setText(arrayFour.get(view.getAdapterPosition()));
                dialogMag2.setText(seper[1]);
                dialogMag3.setText(seper[1]);
                dialogDepth.setText(arrayThree.get(view.getAdapterPosition()));
                dialogDate.setText(arrayTwo.get(view.getAdapterPosition()));

                Toast.makeText(context,"Clicked " +String.valueOf(view.getAdapterPosition()),Toast.LENGTH_SHORT).show();

                myDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

       viewHolder.location.setText(arrayOne.get(position));

       viewHolder.date.setText(arrayTwo.get(position));

       viewHolder.depth.setText(arrayThree.get(position));

       viewHolder.magn.setText(arrayFour.get(position));
    }


    @Override
    public int getItemCount() {
        return arrayOne.size();
    }

    @Override
    public Filter getFilter() {
        return arrayFilter;
    }


    private Filter arrayFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(arraySearch);
            } else {
                String filter2 = constraint.toString().toLowerCase().trim();

                for (String item : arraySearch) {
                    if (item.toLowerCase().contains(filter2)) {
                        filtered.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayOne.clear();
            arrayOne.addAll((ArrayList<String>) results.values);

            notifyDataSetChanged();
        }


    };
}
