package com.ice.crms.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.ice.crms.R;
import com.ice.crms.models.ClientRelation;
import com.ice.crms.tasks.DeleteAsyncTask;

import java.util.LinkedList;
import java.util.List;

public class CardClientAdapter extends RecyclerSwipeAdapter<CardClientAdapter.CardsHolder> {
    private static final String TAG = CardClientAdapter.class.getSimpleName();
    private LinkedList<ClientRelation> clientRelations = new LinkedList<>();
    private LinkedList<ClientRelation> clientRelationsTemp = new LinkedList<>();
    private LinkedList<ClientRelation> clientRelationsMain = new LinkedList<>();
    private OnItemClickListener mOnItemClickListener = null;


    public CardClientAdapter() {

    }

    public void setClientRelations(LinkedList<ClientRelation> clientRelations) {
        this.clientRelations = clientRelations;
        this.clientRelationsMain = clientRelations;
    }

    public void switchClientRelations(LinkedList<ClientRelation> clientRelations) {
        this.clientRelations = clientRelations;
    }

    @NonNull
    @Override
    public CardsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_client, viewGroup, false);
        return new CardsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsHolder cardsHolder, int i) {
        cardsHolder.clientNo.setText(String.format("%s", clientRelations.get(i).getClientNo()));
        cardsHolder.clientName.setText(clientRelations.get(i).getClientName());
        cardsHolder.clientType.setText(clientRelations.get(i).getClientName());

        cardsHolder.client.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, i);
            }
        });

        cardsHolder.clientDel.setTag(clientRelations.get(i).getClientNo());
        cardsHolder.clientDel.setOnClickListener(v -> {
            Log.d(TAG, "onClick: HaLO~~" + v.getTag());
            closeItem(i);
            new DeleteAsyncTask(i, this, clientRelations).execute((int) v.getTag());
        });

        cardsHolder.clientModify.setOnClickListener(v -> {
            Log.d(TAG, "onBindViewHolder: ");
            closeItem(i);
        });

    }

    @Override
    public int getItemCount() {
        return clientRelations.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.cardsClients;
    }

    @Override
    public void openItem(int position) {
        super.openItem(position);
    }

    @Override
    public void closeItem(int position) {
        super.closeItem(position);
    }

    @Override
    public void closeAllExcept(SwipeLayout layout) {
        super.closeAllExcept(layout);
    }

    @Override
    public void closeAllItems() {
        super.closeAllItems();
    }

    @Override
    public List<Integer> getOpenItems() {
        return super.getOpenItems();
    }

    @Override
    public List<SwipeLayout> getOpenLayouts() {
        return super.getOpenLayouts();
    }

    @Override
    public void removeShownLayouts(SwipeLayout layout) {
        super.removeShownLayouts(layout);
    }

    @Override
    public boolean isOpen(int position) {
        return super.isOpen(position);
    }

    @Override
    public Attributes.Mode getMode() {
        return super.getMode();
    }

    @Override
    public void setMode(Attributes.Mode mode) {
        super.setMode(mode);
    }

    public ClientRelation getItemData(int position) {
        return clientRelations.get(position);
    }

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    public void searchByName(String name) {
        if (name != null && !"".equals(name)) {
            Log.d(TAG, "searchByName: " + name);
            clientRelationsTemp.clear();
            for (ClientRelation clientRelation : clientRelationsMain) {
                if (clientRelation.getClientName().startsWith(name)) {
                    clientRelationsTemp.add(clientRelation);
                }
            }
            switchClientRelations(clientRelationsTemp);
            notifyDataSetChanged();
        } else {
            switchClientRelations(clientRelationsMain);
            notifyDataSetChanged();
        }
    }

    public void searchByClientNo(String clientNo) {
        if (clientNo != null && !"".equals(clientNo)) {
            Log.d(TAG, "searchByClientNo: " + clientNo);
            clientRelationsTemp.clear();
            for (ClientRelation clientRelation : clientRelationsMain) {
                if (Integer.toString(clientRelation.getClientNo()).startsWith(clientNo)) {
                    clientRelationsTemp.add(clientRelation);
                }
            }
            switchClientRelations(clientRelationsTemp);
            notifyDataSetChanged();
        } else {
            switchClientRelations(clientRelationsMain);
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    class CardsHolder extends RecyclerView.ViewHolder {
        private TextView clientNo;
        private TextView clientType;
        private TextView clientName;
        private Button clientDel;
        private Button clientModify;
        private View client;

        CardsHolder(@NonNull View itemView) {
            super(itemView);
            clientNo = itemView.findViewById(R.id.clientNo);
            clientType = itemView.findViewById(R.id.deviceName);
            clientName = itemView.findViewById(R.id.num);
            clientDel = itemView.findViewById(R.id.client_del);
            client = itemView.findViewById(R.id.client);
            clientModify = itemView.findViewById(R.id.client_modify);
        }
    }
}
