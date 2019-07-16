package com.ice.crms;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ice.crms.daos.DaoClientManager;
import com.ice.crms.daos.DaoOther;
import com.ice.crms.models.ClientRelation;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewClientActivity extends AppCompatActivity {
    private static final String TAG = AddNewClientActivity.class.getSimpleName();
    private static final int ADDMODE = 0;
    private static final int EDITMODE = 1;
    int MODE;
    private EditText editClientNo;
    private EditText editClientName;
    private EditText editClientAddr;
    private Spinner editClientType;
    private Spinner editClientStatus;
    private LazyDatePicker editDate;
    private Button btnSave;
    private List<String> types = new ArrayList<>();
    private List<String> status = new ArrayList<>();
    private ArrayAdapter<String> typeAdapter;
    private ArrayAdapter<String> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        registerComponents();
        loadData();
        MODE = getIntent().getIntExtra("MODE", 0);
        if (MODE == ADDMODE) {
            doAdd();
        } else if (MODE == EDITMODE) {
            doEdit();
        }
    }

    private void registerComponents() {
        editClientNo = findViewById(R.id.editClientNo);
        editClientName = findViewById(R.id.editClientName);
        editClientAddr = findViewById(R.id.editClientAddr);
        editClientType = findViewById(R.id.editClientType);
        editClientStatus = findViewById(R.id.editClientStatus);
        editDate = findViewById(R.id.editDate);
        btnSave = findViewById(R.id.btnSave);
    }

    private void doAdd() {
        btnSave.setOnClickListener(v -> {
            if ("".equals(editClientNo.getText().toString())) {
                editClientNo.requestFocus();
                editClientNo.setError("客户编号不能为空");
            } else if ("".equals(editClientName.getText().toString())) {
                editClientName.requestFocus();
                editClientName.setError("客户姓名不能为空");
            } else {
                ClientRelation clientRelation = new ClientRelation();
                clientRelation.setClientNo(Integer.parseInt(editClientNo.getText().toString()));
                clientRelation.setClientAddr(editClientAddr.getText().toString());
                clientRelation.setClientName(editClientName.getText().toString());
                clientRelation.setClientStatus(editClientStatus.getSelectedItemPosition());
                clientRelation.setClientType(editClientType.getSelectedItemPosition());
                clientRelation.setDate(editDate.getDate().getTime());
                new AMClientTask().execute(clientRelation);
            }
        });
    }

    private void doEdit() {
        Intent intent = getIntent();
        editClientNo.setEnabled(false);
        editClientNo.setText(String.format("%s", intent.getIntExtra("clientNo", 0)));
        editClientName.setText(intent.getStringExtra("clientName"));
        editClientAddr.setText(intent.getStringExtra("clientAddr"));
        editClientType.setSelection(intent.getIntExtra("clientType", 0));
        editClientStatus.setSelection(intent.getIntExtra("clientStatus", 0));
        editDate.setDate(new Date(intent.getLongExtra("date", 0L)));
        btnSave.setOnClickListener(v -> {
            if ("".equals(editClientName.getText().toString())) {
                editClientName.requestFocus();
                editClientName.setError("客户姓名不能为空");
            } else {
                ClientRelation clientRelation = new ClientRelation();
                clientRelation.setClientNo(Integer.parseInt(editClientNo.getText().toString()));
                clientRelation.setClientAddr(editClientAddr.getText().toString());
                clientRelation.setClientName(editClientName.getText().toString());
                clientRelation.setClientStatus(editClientStatus.getSelectedItemPosition());
                clientRelation.setClientType(editClientType.getSelectedItemPosition());
                clientRelation.setDate(editDate.getDate().getTime());
                new AMClientTask().execute(clientRelation);
            }

        });
    }

    private void loadData() {

        editDate.setDate(new Date());

        typeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, types);
        editClientType.setAdapter(typeAdapter);
        statusAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, status);
        editClientStatus.setAdapter(statusAdapter);

        new GetSTData().execute();
    }

    class GetSTData extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            result.add(DaoOther.getAllClientStatus());
            result.add(DaoOther.getAllClientTypes());
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> result) {
            statusAdapter.addAll(result.get(0));
            typeAdapter.addAll(result.get(1));
        }
    }

    class AMClientTask extends AsyncTask<ClientRelation, Void, Boolean> {
        @Override
        protected Boolean doInBackground(ClientRelation... clientRelations) {
            if (MODE == ADDMODE) {
                return DaoClientManager.addRelation(clientRelations[0]);
            } else if (MODE == EDITMODE) {
                return DaoClientManager.modifyRelation(clientRelations[0]);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(AddNewClientActivity.this.getApplicationContext(), "操作成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddNewClientActivity.this.getApplicationContext(), "操作失败!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
