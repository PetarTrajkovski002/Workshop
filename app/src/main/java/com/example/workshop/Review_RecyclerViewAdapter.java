package com.example.workshop;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Review_RecyclerViewAdapter extends RecyclerView.Adapter<Review_RecyclerViewAdapter.MyViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<Vozenje_Review> Vozenja;
    Context context;
    ArrayList<Integer> Sliki=new ArrayList<>();



    public Review_RecyclerViewAdapter(Context context, ArrayList<Vozenje_Review> Vozenja, RecyclerViewInterface recyclerViewInterface)
    {
        this.context=context;
        this.Vozenja=Vozenja;
        this.recyclerViewInterface=recyclerViewInterface;
        int z;
        Sliki=new ArrayList<>();
        for(int i=0; i<Vozenja.size(); i++)
        {
            z =context.getResources().getIdentifier(Vozenja.get(i).Slika,"drawable",context.getPackageName());
            Sliki.add(z);
        }
    }


    @NonNull
    @Override
    public Review_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater=LayoutInflater.from(context);
        View view=Inflater.inflate(R.layout.za_ocena,parent,false);

        return new Review_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Review_RecyclerViewAdapter.MyViewHolder holder, int position)
    {
        holder.Start.setText(Vozenja.get(position).Start);
        holder.End.setText(Vozenja.get(position).End);
        holder.VozacIme.setText(Vozenja.get(position).Ime);
        holder.VozacPrezime.setText(Vozenja.get(position).Prezime);
        holder.Model.setText(Vozenja.get(position).Model);
        holder.Godina.setText(Integer.toString(Vozenja.get(position).Godina));
        holder.Komentar.setText(Vozenja.get(position).Komentar);
        holder.Rating.setImageResource(Sliki.get(position));

    }

    @Override
    public int getItemCount() {
        return Vozenja.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView Start;
        TextView End;

        TextView VozacIme;

        TextView VozacPrezime;

        TextView Model;

        TextView Godina;

        TextView Komentar;

        ImageView Rating;
        ScrollView SV;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface)
        {
            super(itemView);
            Rating=itemView.findViewById(R.id.slikaOcenka);
            Start=itemView.findViewById(R.id.Poc);
            End=itemView.findViewById(R.id.Kraj);
            VozacIme=itemView.findViewById(R.id.ImeV);
            VozacPrezime=itemView.findViewById(R.id.PrezimeV);
            Model=itemView.findViewById(R.id.model);
            Godina=itemView.findViewById(R.id.godina);
            SV=itemView.findViewById(R.id.scrollView);
            Komentar=itemView.findViewById(R.id.Komentar);
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
