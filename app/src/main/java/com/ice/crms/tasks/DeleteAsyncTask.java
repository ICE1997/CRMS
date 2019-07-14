package com.ice.crms.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.ice.crms.adapters.CardClientAdapter;
import com.ice.crms.daos.DaoClientManager;
import com.ice.crms.models.ClientRelation;

import java.util.LinkedList;

public class DeleteAsyncTask extends AsyncTask<Integer, Void, Boolean> {
    private static final String TAG = DeleteAsyncTask.class.getSimpleName();
    private int position;
    private CardClientAdapter adapter;
    private LinkedList<ClientRelation> clientRelations;

    public DeleteAsyncTask(int position, CardClientAdapter adapter, LinkedList<ClientRelation> clientRelations) {
        this.position = position;
        this.adapter = adapter;
        this.clientRelations = clientRelations;
    }

    @Override
    protected Boolean doInBackground(Integer... clients) {
        Log.d(TAG, "doInBackground: " + clients[0]);
        return DaoClientManager.deleteRelationByNO(clients[0]);
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.d(TAG, "onPostExecute: 成功!!!");
            clientRelations.remove(position);
            adapter.notifyDataSetChanged();

        } else {
            Log.d(TAG, "onPostExecute: 失败!!!");
        }
    }
}