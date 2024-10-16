package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.supply;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.supply.SupplyAPI_POST;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.supply.SupplyAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;
import ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity.CatalogueChoiceActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SupplyEditAddActivity extends AppCompatActivity implements RESTDBOutput {
    TextView nickname;
    TextView role;
    Button add;
    Button choose;
    TextView catalogueName;
    EditText date;
    EditText quantity;

    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    boolean isEditing = false;
    Supply object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_edit_add);

        nickname = findViewById(R.id.posting_as_text);
        role = findViewById(R.id.role_posting_as);
        add = findViewById(R.id.submitButtonCatalogue);
        choose = findViewById(R.id.choiceInvokeButton);
        catalogueName = findViewById(R.id.CatalogueItemChosen);
        date = findViewById(R.id.editSupplyDate);
        quantity = findViewById(R.id.editSupplyNumber);

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
        object = (Supply) getIntent().getSerializableExtra("supply");
        if (object != null) {
            isEditing = true;
            catalogueName.setText(object.getItem().getItemName());
            date.setText(object.getDateOfArrival());
            quantity.setText(String.valueOf(object.getQuantity()));
        }
        else{
            object = new Supply();
            catalogueName.setText(getText(R.string.chooseAnItem));
        }
        add.setOnClickListener(view -> {
            String baseUrl = sharedPreferences.getString("serverSocket", "");
            String token = encryptedSharedPreferences.getString("token", "");
            String date1;
            int quantity1;
            if (isDateValid(date.getText().toString())) {
                date1 = date.getText().toString();
            }
            else{
                Toast.makeText(this, getText(R.string.wrongDate), Toast.LENGTH_LONG).show();
                return;
            }
            try {
                quantity1 = Integer.parseInt(quantity.getText().toString());
            } catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            if (!isEditing) {
                object.setDateOfArrival(date1);
                if(object.getItem() == null){
                    Toast.makeText(this, getText(R.string.chooseAnItem), Toast.LENGTH_LONG).show();
                    return;
                }
                object.setQuantity(quantity1);
                SupplyAPI_POST apiPost = new SupplyAPI_POST(this, baseUrl, object, token);
                apiPost.execute();
            }
            else{
                object.setDateOfArrival(date1);
                if(object.getItem() == null){
                    Toast.makeText(this, getText(R.string.chooseAnItem), Toast.LENGTH_LONG).show();
                    return;
                }
                object.setQuantity(quantity1);
                SupplyAPI_PUT apiPut = new SupplyAPI_PUT(this, baseUrl, object, token);
                apiPut.execute();
            }
        });
        choose.setOnClickListener(view -> {
            Intent intent = new Intent(this, CatalogueChoiceActivity.class);
            startActivityForResult(intent, 1);
        });

        /*
        String baseUrl = sharedPreferences.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
         */

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.hasExtra("catalogue")) {
                Catalogue chosen = (Catalogue) data.getSerializableExtra("catalogue");
                object.setItem(chosen);
                catalogueName.setText(chosen.getItemName());
            }
        }
    }
    public static boolean isDateValid(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formatter.setLenient(false);
        try {
            Date date1 = formatter.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
