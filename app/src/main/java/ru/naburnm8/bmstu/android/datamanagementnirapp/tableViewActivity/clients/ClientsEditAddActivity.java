package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.clients;

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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients.ClientsAPI_POST;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients.ClientsAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ClientsEditAddActivity extends AppCompatActivity implements RESTDBOutput {
    TextView nickname;
    TextView role;
    TextInputLayout nameClient;
    TextInputLayout phoneClient;
    Button add;

    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    boolean isEditing = false;
    Clients object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clients_edit_add);
        nickname = findViewById(R.id.posting_as_text);
        role = findViewById(R.id.role_posting_as);
        nameClient = findViewById(R.id.clientNameInput);
        phoneClient = findViewById(R.id.clientPhoneInput);
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
        Clients object_received = (Clients) getIntent().getSerializableExtra("client");
        add.setOnClickListener(view -> {
            if (!isEditing){
                Clients toAdd = new Clients();
                try{
                    String name = nameClient.getEditText().getText().toString().trim();
                    String phone = phoneClient.getEditText().getText().toString().trim();
                    String[] parts = name.split(" ");
                    if (parts.length < 2){
                        Toast.makeText(getApplicationContext(), getText(R.string.wrongName), Toast.LENGTH_LONG).show();
                        return;
                    }
                    toAdd.setPhone(phone);
                    toAdd.setFirstName(parts[0]);
                    toAdd.setLastName(parts[1]);
                    if (parts.length == 3){
                        toAdd.setPatronymic(parts[2]);
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                ClientsAPI_POST postAPI = new ClientsAPI_POST(this, baseUrl, toAdd, token);
                postAPI.execute();
            }
            else{
                Clients toAdd = new Clients();
                toAdd.setId(object_received.getId());
                try{
                    String name = nameClient.getEditText().getText().toString().trim();
                    String phone = phoneClient.getEditText().getText().toString().trim();
                    String[] parts = name.split(" ");
                    if (parts.length < 2){
                        Toast.makeText(getApplicationContext(), getText(R.string.wrongName), Toast.LENGTH_LONG).show();
                        return;
                    }
                    toAdd.setPhone(phone);
                    toAdd.setFirstName(parts[0]);
                    toAdd.setLastName(parts[1]);
                    if (parts.length == 3){
                        toAdd.setPatronymic(parts[2]);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                ClientsAPI_PUT putAPI = new ClientsAPI_PUT(this, baseUrl, toAdd, token);
                putAPI.execute();
            }
        });
        if (object_received == null) {
            this.object = null;
            return;
        }
        this.object = object_received;
        isEditing = true;
        String clientName = this.object.getFirstName() + " " + this.object.getLastName() + " " + this.object.getPatronymic();
        String clientPhone = this.object.getPhone();
        nameClient.getEditText().setText(clientName);
        phoneClient.getEditText().setText(clientPhone);
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
