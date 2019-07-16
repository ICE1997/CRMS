package com.ice.crms.tasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ice.crms.adapters.CardClientAdapter;
import com.ice.crms.daos.DaoClientManager;
import com.ice.crms.models.ClientRelation;

import java.util.LinkedList;

public class ListAsyncTask extends AsyncTask<Void, Void, LinkedList<ClientRelation>> {
    @SuppressLint("StaticFieldLeak")
    private SwipeRefreshLayout processWiget;
    private CardClientAdapter adapter;

    public ListAsyncTask(SwipeRefreshLayout processWiget, CardClientAdapter adapter) {
        this.processWiget = processWiget;
        this.adapter = adapter;
    }

    @Override
    protected LinkedList<ClientRelation> doInBackground(Void... voids) {
        return DaoClientManager.getAllRelations();
    }

    @Override
    protected void onPostExecute(LinkedList<ClientRelation> clientRelations) {
        for (ClientRelation clientRelation : clientRelations) {
            System.out.println(clientRelation.getClientNo());
        }
        adapter.setClientRelations(clientRelations);
        adapter.notifyDataSetChanged();
        processWiget.setRefreshing(false);
    }
}
