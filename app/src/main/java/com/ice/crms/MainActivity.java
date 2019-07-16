package com.ice.crms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.daimajia.swipe.util.Attributes;
import com.ice.crms.adapters.CardClientAdapter;
import com.ice.crms.layouts.AutoSwipeRefreshLayout;
import com.ice.crms.models.ClientRelation;
import com.ice.crms.tasks.ListAsyncTask;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CardClientAdapter cardClientAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner searchType;
    private EditText searchContent;
    private RecyclerView recyclerView;
    private TextWatcher searchByName = new searchByNameListener();
    private TextWatcher searchByNo = new searchByNoListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.cardsClients);
        searchType = findViewById(R.id.searchType);
        searchContent = findViewById(R.id.searchContent);

        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                switch (position) {
                    case 0:
                        searchContent.removeTextChangedListener(searchByName);
                        searchContent.addTextChangedListener(searchByNo);
                        break;
                    case 1:
                        searchContent.removeTextChangedListener(searchByNo);
                        searchContent.addTextChangedListener(searchByName);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchContent.addTextChangedListener(new searchByNoListener());
            }
        });


        cardClientAdapter = new CardClientAdapter();
        cardClientAdapter.setMode(Attributes.Mode.Single);
        cardClientAdapter.setOnItemClickListener((v, position) -> {

            ClientRelation clientRelation = cardClientAdapter.getItemData(position);
            Intent intent = new Intent(this, ClientRelationDetailActivity.class);
            intent.putExtra("clientNo", clientRelation.getClientNo());
            intent.putExtra("clientName", clientRelation.getClientName());
            intent.putExtra("date", clientRelation.getDate());
            intent.putExtra("clientStatus", clientRelation.getClientStatus());
            intent.putExtra("clientType", clientRelation.getClientType());
            intent.putExtra("clientAddr", clientRelation.getClientAddr());
            startActivity(intent);
        });

        cardClientAdapter.setmOnModifyClickListener((v, position) -> {
            ClientRelation clientRelation = cardClientAdapter.getItemData(position);
            Intent intent = new Intent(this, AddNewClientActivity.class);
            intent.putExtra("MODE", 1);
            intent.putExtra("clientNo", clientRelation.getClientNo());
            intent.putExtra("clientName", clientRelation.getClientName());
            intent.putExtra("date", clientRelation.getDate());
            intent.putExtra("clientStatus", clientRelation.getClientStatus());
            intent.putExtra("clientType", clientRelation.getClientType());
            intent.putExtra("clientAddr", clientRelation.getClientAddr());
            startActivity(intent);
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

        findViewById(R.id.addNewClient).setOnClickListener(v -> {
            addNewClientRelation(this);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AutoSwipeRefreshLayout) swipeRefreshLayout).autoRefresh();
    }

    private void addNewClientRelation(Context context) {
        Intent intent = new Intent(this, AddNewClientActivity.class);
        context.startActivity(intent);
    }

    class searchByNoListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            cardClientAdapter.searchByClientNo(s.toString());
        }
    }

    class searchByNameListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            cardClientAdapter.searchByName(s.toString());
        }
    }
}
