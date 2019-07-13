package com.ice.crms.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.crms.R;
import com.ice.crms.models.ClientRelation;

import java.util.LinkedList;
import java.util.Random;

public class CardClientAdapter extends RecyclerView.Adapter<CardClientAdapter.CardsHolder> {
    LinkedList<ClientRelation> clientRelations;


    public CardClientAdapter(LinkedList<ClientRelation> clientRelations) {
        this.clientRelations = clientRelations;
    }
    @NonNull
    @Override
    public CardsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_client,viewGroup,false);
        return new CardsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsHolder cardsHolder, int i) {
        cardsHolder.num.setText(Integer.toString(clientRelations.get(i).getClientNo()));
        Random random = new Random();
        cardsHolder.pic.setBackgroundColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        cardsHolder.itemView.setId(clientRelations.get(i).getClientNo());
        cardsHolder.deviceName.setText(clientRelations.get(i).getClientName());
    }
    
    @Override
    public int getItemCount() {
        return clientRelations.size();
    }

    protected class CardsHolder extends RecyclerView.ViewHolder {
        private ImageView pic;
        private TextView deviceName;
        private TextView num;

        public CardsHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            deviceName = itemView.findViewById(R.id.deviceName);
            num = itemView.findViewById(R.id.num);
        }
    }
}
