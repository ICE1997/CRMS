package com.ice.crms;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ice.crms.adapters.CardClientAdapter;
import com.ice.crms.daos.DaoClientManager;
import com.ice.crms.models.ClientRelation;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CardClientAdapter cardClientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetClients().execute();
    }

    private class GetClients extends AsyncTask<Void, Void, LinkedList<ClientRelation>> {

        @Override
        protected LinkedList<ClientRelation> doInBackground(Void... voids) {
            return DaoClientManager.getAllRelations();
        }

        @Override
        protected void onPostExecute(LinkedList<ClientRelation> clientRelations) {
            for(ClientRelation clientRelation : clientRelations) {
                System.out.println(clientRelation.getClientNo());
            }
            MainActivity.this.cardClientAdapter = new CardClientAdapter(clientRelations);
            RecyclerView recyclerView = findViewById(R.id.cardsClients);
            LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(cardClientAdapter);

        }
    }
}
