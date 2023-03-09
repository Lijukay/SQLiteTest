package com.lijukay.sqlitetestapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, qwotable, qwotable_author, qwotable_foundIn;
    Activity activity;
    int position;
    Animation translater_anim;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList qwotable, ArrayList qwotable_author, ArrayList qwotable_foundIn){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.qwotable = qwotable;
        this.qwotable_author = qwotable_author;
        this.qwotable_foundIn = qwotable_foundIn;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;

        holder.qwotable_text.setText(String.valueOf(qwotable.get(position)));
        holder.qwotable_authortext.setText(String.valueOf(qwotable_author.get(position)));
        holder.qwotable_foundintext.setText(String.valueOf(qwotable_foundIn.get(position)));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("Qwotable", String.valueOf(qwotable.get(position)));
                intent.putExtra("Author", String.valueOf(qwotable_author.get(position)));
                intent.putExtra("Found in", String.valueOf(qwotable_foundIn.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return qwotable.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView qwotable_text, qwotable_authortext, qwotable_foundintext;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            qwotable_text = itemView.findViewById(R.id.qwotable_quote);
            qwotable_authortext = itemView.findViewById(R.id.qwotable_author);
            qwotable_foundintext = itemView.findViewById(R.id.qwotable_found_in);
            cardView = itemView.findViewById(R.id.cardholder);
            translater_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            cardView.setAnimation(translater_anim);

        }
    }
}
