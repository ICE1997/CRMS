package com.ice.crms;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ice.crms.daos.DaoOther;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClientRelationDetailActivity extends AppCompatActivity {
    private TextView textClientNo;
    private TextView textClientName;
    private TextView textClientAddr;
    private TextView textClientType;
    private TextView textClientStatus;
    private TextView textDate;
    private int typeNo = 0;
    private int statusNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_relation_detail);
        registerComponents();
        setData();
    }

    private void registerComponents() {
        textClientNo = findViewById(R.id.textClientNo);
        textClientName = findViewById(R.id.textClientName);
        textClientAddr = findViewById(R.id.textClientAddr);
        textClientType = findViewById(R.id.textClientType);
        textClientStatus = findViewById(R.id.textClientStatus);
        textDate = findViewById(R.id.textDate);
    }

    private void setData() {
        Intent intent = getIntent();
        Date date = new Date(intent.getLongExtra("date", 0L));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        textClientNo.setText(String.format("%s", intent.getIntExtra("clientNo", 0)));
        textClientName.setText(intent.getStringExtra("clientName"));
        textClientAddr.setText(intent.getStringExtra("clientAddr"));
        textDate.setText(format.format(date));
        typeNo = intent.getIntExtra("clientType", 0);
        statusNo = intent.getIntExtra("clientStatus", 0);

        new GetData().execute();
    }

    class GetData extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> result = new ArrayList<>();
            result.add(DaoOther.getStatusNameByNo(statusNo));
            result.add(DaoOther.getTypeNameByNo(typeNo));
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            textClientStatus.setText(result.get(0));
            textClientType.setText(result.get(1));
        }
    }
}
