package com.pakachu.quickrmpremium;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.MyViewHolder> {

    ArrayList<String> hareket, tarih;
    ArrayList<Integer> agirlik;
    //    int[] images;
    Context context;

    public RecyAdapter(Context ct, ArrayList<String> s1, ArrayList<Integer> s2, ArrayList<String> s3) {
        context = ct;
        hareket = s1;
        agirlik = s2;
        tarih = s3;
//        images = img;
    }

    @NonNull
    @Override
    public RecyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyAdapter.MyViewHolder holder, int position) {
        String baslik = hareket.get(position);
        switch (baslik) {
            case "b": baslik="Bench Press";   holder.iv_icon.setImageResource(R.drawable.bench); break;
            case "s": baslik="Squat";   holder.iv_icon.setImageResource(R.drawable.squat); break;
            case "d": baslik="Deadlift";   holder.iv_icon.setImageResource(R.drawable.deadlift); break;

        }
        holder.tv_baslik.setText(baslik);
        holder.tv_kg.setText(agirlik.get(position).toString() +" kg");
        holder.tv_tarih.setText(tarih.get(position));

    }

    @Override
    public int getItemCount() {
        return tarih.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_baslik, tv_kg, tv_tarih;
        ImageView iv_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_baslik = itemView.findViewById(R.id.tv_baslik);
            tv_kg = itemView.findViewById(R.id.tv_kg);
            tv_tarih = itemView.findViewById(R.id.tv_tarih);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
