package ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients.ClientsAPI_GET;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.ClientsResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList.ClientChoiceAdapter;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList.OnDBandRecyclerListener;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class ClientChoiceActivity extends AppCompatActivity implements OnDBandRecyclerListener {
    RecyclerView recyclerView;
    ArrayList<Clients> clientList;
    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        sharedSettings = getSharedPreferences("settings", MODE_PRIVATE);
        try {
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            encryptedSharedPreferences = EncryptedSharedPreferences.create("account", masterKeys,
                    getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclerChoice);
        ClientChoiceAdapter adapter = new ClientChoiceAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String baseUrl = sharedSettings.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
        ClientsAPI_GET getApi = new ClientsAPI_GET(this, baseUrl, token);
        getApi.execute();
    }

    @Override
    public void onEditClick(Clients item) {
        //pass
    }

    @Override
    public void onDeleteClick(Clients item) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("client", item);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void setLogged(String logged) {
        if (logged != null) {
            Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setData(Recordable data) {
        if (data instanceof ClientsResponse) {
            ClientsResponse response = (ClientsResponse) data;
            clientList = new ArrayList<>(response.getData());
            ClientChoiceAdapter adapter = new ClientChoiceAdapter(clientList, this);
            recyclerView.setAdapter(adapter);
        }
    }
}
