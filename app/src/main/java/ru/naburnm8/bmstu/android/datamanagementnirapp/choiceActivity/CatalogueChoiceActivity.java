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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue.CatalogueAPI_GET;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.CatalogueResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.catalogueList.CatalogueChoiceAdapter;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.catalogueList.OnDBandRecyclerListener;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class CatalogueChoiceActivity extends AppCompatActivity implements OnDBandRecyclerListener {
    RecyclerView recyclerView;
    ArrayList<Catalogue> catalogueList;
    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        sharedSettings = getSharedPreferences("settings", MODE_PRIVATE);
        try{
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            encryptedSharedPreferences = EncryptedSharedPreferences.create("account", masterKeys,
                    getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        recyclerView = findViewById(R.id.recyclerChoice);
        CatalogueChoiceAdapter adapter = new CatalogueChoiceAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String baseUrl = sharedSettings.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
        CatalogueAPI_GET getApi = new CatalogueAPI_GET(this, baseUrl, token);
        getApi.execute();

    }
    @Override
    public void onEditClick(Catalogue item){
        //pass
    }

    @Override
    public void onDeleteClick(Catalogue item) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("catalogue", item);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void setLogged(String logged) {
        if(logged != null){
            Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setData(Recordable data) {
        if(data instanceof CatalogueResponse){
            CatalogueResponse response = (CatalogueResponse) data;
            catalogueList = new ArrayList<>(response.getData());
            CatalogueChoiceAdapter adapter = new CatalogueChoiceAdapter(catalogueList, this);
            recyclerView.setAdapter(adapter);
        }
    }
}
