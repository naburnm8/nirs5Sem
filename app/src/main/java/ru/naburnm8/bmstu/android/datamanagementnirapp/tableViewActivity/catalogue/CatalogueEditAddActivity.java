package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.catalogue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import com.google.android.material.textfield.TextInputLayout;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue.CatalogueAPI_POST;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue.CatalogueAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class CatalogueEditAddActivity extends AppCompatActivity implements RESTDBOutput {
    TextView nickname;
    TextView role;
    TextInputLayout nameCatalogue;
    TextInputLayout priceCatalogue;
    Button add;

    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    boolean isEditing = false;
    Catalogue object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_edit_add);
        nickname = findViewById(R.id.posting_as_text);
        role = findViewById(R.id.role_posting_as);
        nameCatalogue = findViewById(R.id.catalogueNameInput);
        priceCatalogue = findViewById(R.id.cataloguePriceInput);
        add = findViewById(R.id.submitButtonCatalogue);
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
        Catalogue catalogue = (Catalogue) getIntent().getSerializableExtra("catalogue");
        add.setOnClickListener(view -> {
            if (!isEditing){
                Catalogue toAdd = new Catalogue();
                try{
                    String name = nameCatalogue.getEditText().getText().toString().trim();
                    String price = priceCatalogue.getEditText().getText().toString().trim();
                    toAdd.setItemName(name);
                    toAdd.setItemPrice(Integer.parseInt(price));
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                CatalogueAPI_POST catalogueAPI = new CatalogueAPI_POST(this, baseUrl, toAdd, token);
                catalogueAPI.execute();
            }
            else{
                Catalogue toAdd = new Catalogue();
                toAdd.setId(object.getId());
                try{
                    String name = nameCatalogue.getEditText().getText().toString().trim();
                    String price = priceCatalogue.getEditText().getText().toString().trim();
                    toAdd.setItemName(name);
                    toAdd.setItemPrice(Integer.parseInt(price));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                CatalogueAPI_PUT catalogueAPI = new CatalogueAPI_PUT(this, baseUrl, toAdd, token);
                catalogueAPI.execute();
            }
        });
        if (catalogue == null) {
            object = null;
            return;
        }
        object = catalogue;
        isEditing = true;
        String itemName = object.getItemName();
        String itemPrice = String.valueOf(object.getItemPrice());
        nameCatalogue.getEditText().setText(itemName);
        priceCatalogue.getEditText().setText(itemPrice);
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
