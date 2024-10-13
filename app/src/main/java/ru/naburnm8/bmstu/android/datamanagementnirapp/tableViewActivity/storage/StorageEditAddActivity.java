package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import com.google.android.material.textfield.TextInputLayout;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients.ClientsAPI_POST;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients.ClientsAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.storage.StorageAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity.catalogueChoiceActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class StorageEditAddActivity extends AppCompatActivity implements RESTDBOutput {
    TextView nickname;
    TextView role;
    Button add;
    Button choose;
    TextView catalogueName;
    EditText quantity;

    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    Storage object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage_edit_add);

        nickname = findViewById(R.id.posting_as_text);
        role = findViewById(R.id.role_posting_as);
        add = findViewById(R.id.submitButtonCatalogue);
        choose = findViewById(R.id.choiceInvokeButton);
        catalogueName = findViewById(R.id.CatalogueItemChosen);
        quantity = findViewById(R.id.editStorageNumber);

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
        nickname.setText(encryptedSharedPreferences.getString("username", ""));
        role.setText(encryptedSharedPreferences.getString("role", ""));
        object = (Storage) getIntent().getSerializableExtra("storage");
        if (object == null) {
            finish();
        }
        catalogueName.setText(object.getItem().getItemName());
        quantity.setText(String.valueOf(object.getQuantity()));
        choose.setEnabled(false);
        choose.setVisibility(View.INVISIBLE);
        add.setOnClickListener(view -> {
            try{
                int new_quantity = Integer.parseInt(quantity.getText().toString());
                object.setQuantity(new_quantity);
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                StorageAPI_PUT putAPI = new StorageAPI_PUT(this, baseUrl, object, token);
                putAPI.execute();
            }
            catch (NumberFormatException e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setLogged(String logged) {
        System.out.println(logged);
    }

    @Override
    public void setData(Recordable data) {
        System.out.println(data);
        finish();
    }

}
