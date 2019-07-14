package com.ice.crms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.ice.crms.adapters.CardClientAdapter;
import com.ice.crms.layouts.AutoSwipeRefreshLayout;
import com.ice.crms.models.ClientRelation;
import com.ice.crms.tasks.ListAsyncTask;

import java.util.Date;


public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private CardClientAdapter cardClientAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.cardsClients);

        cardClientAdapter = new CardClientAdapter();
        cardClientAdapter.setMode(Attributes.Mode.Single);
        cardClientAdapter.setOnItemClickLitener((v, position) -> {

            ClientRelation clientRelation = cardClientAdapter.getItemData(position);

            Toast.makeText(MainActivity.this, "数据:" + new Date(clientRelation.getDate()).toString(), Toast.LENGTH_SHORT).show();

        });
        recyclerView.setAdapter(cardClientAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        swipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new ListAsyncTask(swipeRefreshLayout, cardClientAdapter).execute();
        });

        ((AutoSwipeRefreshLayout) swipeRefreshLayout).autoRefresh();

        findViewById(R.id.addNewClient).setOnClickListener(v->{
            addNewClientRelation(this);
        });

    }

    private void addNewClientRelation(Context context){
        Intent intent = new Intent(this,AddNewClientActivity.class);
        context.startActivity(intent);
    }
}
