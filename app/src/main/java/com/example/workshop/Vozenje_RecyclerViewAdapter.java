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

public class Vozenje_RecyclerViewAdapter extends RecyclerView.Adapter<Vozenje_RecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;

    ArrayList<Vozenje> Vozenja;
    public Vozenje_RecyclerViewAdapter(Context context, ArrayList<Vozenje> Vozenja, RecyclerViewInterface recyvlerViewInterface)
    {
        this.context=context;
        this.Vozenja=Vozenja;
        this.recyclerViewInterface=recyvlerViewInterface;
    }

    @NonNull
    @Override
    public Vozenje_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater Inflater= LayoutInflater.from(context);
        View view=Inflater.inflate(R.layout.zakazani_row,parent,false);
        return new Vozenje_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Vozenje_RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        holder.ImeView.setText("Име:"+Vozenja.get(position).Ime);
        holder.PrezimeView.setText("Презиме:"+Vozenja.get(position).Prezime);
        holder.Start.setText("Почетно место: "+Vozenja.get(position).Start_Place);
        holder.Destination.setText("Крајно место: "+Vozenja.get(position).Destination_Place);


    }

    @Override
    public int getItemCount()
    {
        return Vozenja.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView ImeView;
        TextView PrezimeView;
        TextView Start;
        TextView Destination;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView2);
            ImeView=itemView.findViewById(R.id.rowIme);
            PrezimeView=itemView.findViewById(R.id.rowPrezime);
            Start=itemView.findViewById(R.id.rowLat1);
            Destination=itemView.findViewById(R.id.rowLat2);

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
