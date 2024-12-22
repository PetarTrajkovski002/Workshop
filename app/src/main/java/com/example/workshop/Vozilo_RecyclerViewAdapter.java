package com.example.workshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Vozilo_RecyclerViewAdapter extends RecyclerView.Adapter<Vozilo_RecyclerViewAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Vozilo> vozila;
    private final RecyclerViewInterface recyclerViewInterface;
    public Vozilo_RecyclerViewAdapter(Context context, ArrayList<Vozilo> vozila, RecyclerViewInterface recyclerViewInterface)
    {
        this.context=context;
        this.vozila=vozila;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Vozilo_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.my_row,parent,false);
        return new Vozilo_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Vozilo_RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        holder.textView1.setText(vozila.get(position).Model);
        holder.textView2.setText(Integer.toString(vozila.get(position).Godina));
        holder.textView3.setText(vozila.get(position).Slika);
    }

    @Override
    public int getItemCount()
    {
        return vozila.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView  textView1;
        TextView textView2;

        TextView  textView3;

        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface)
        {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
            textView3=itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null)
                    {
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
