package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.orders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.login.LoginAPI;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders.OrdersAPI_DELETE;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders.OrdersAPI_GET;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginRequest;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.OrdersResponse;

import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrdersAdapter;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OnDBandRecyclerListener;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class OrdersViewActivity extends AppCompatActivity implements OnDBandRecyclerListener {
    RecyclerView recyclerView;
    Button addButton;
    ImageView sync;
    TextView message;
    ArrayList<Orders> objects;
    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //INVARIANT
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        recyclerView = findViewById(R.id.recyclerViewActivity);
        addButton = findViewById(R.id.addAnEntryRecycler);
        sync = findViewById(R.id.syncButton);
        message = findViewById(R.id.tokenMessageText);
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
        String baseUrl = sharedPreferences.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
        //NOT INVARIANT
        OrdersAPI_GET API_get = new OrdersAPI_GET(this, baseUrl, token);
        //NOT INVARIANT
        sync.setOnLongClickListener(view -> {
            LoginAPI loginAPI = new LoginAPI(this, baseUrl);
            String login = encryptedSharedPreferences.getString("username", "");
            String password = encryptedSharedPreferences.getString("password", "");
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPassword(password);
            loginRequest.setUsername(login);
            loginAPI.execute(loginRequest);
            return true;
        });
        //INVARIANT
        //NOT INVARIANT
        sync.setOnClickListener(view -> {
            OrdersAPI_GET API_get_1 = new OrdersAPI_GET(this, baseUrl, token);
            API_get_1.execute();
        });
        //NOT INVARIANT
        //INVARIANT
        addButton.setOnClickListener(view ->{
            if(!checkPrivilege("add")){
                Toast.makeText(getApplicationContext(), getText(R.string.noAddPermission), Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(this, OrdersEditAddActivity.class);
            startActivity(intent);
        });
        //INVARIANT
        OrdersAdapter adapter = new OrdersAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        API_get.execute();
    }

    @Override
    public void setLogged(String logged) {
        if (logged == null) {
            return;
        }
        System.out.println(logged);
        Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(Recordable data) {
        if (data == null){
            message.setVisibility(View.VISIBLE);
            return;
        }
        if(data instanceof LoginResponse){
            String token = ((LoginResponse)data).getToken();
            encryptedSharedPreferences.edit().putString("token", token).commit();
            String baseUrl = sharedPreferences.getString("serverSocket", "");
            OrdersAPI_GET API_get = new OrdersAPI_GET(this, baseUrl, token);
            API_get.execute();
            return;
        }
        if(data instanceof OrdersResponse){
            boolean deletionSuccessful = ((OrdersResponse) data).isStatus();
            if (deletionSuccessful) {
                String baseUrl = sharedPreferences.getString("serverSocket", "");
                String token = encryptedSharedPreferences.getString("token", "");
                OrdersAPI_GET API_get = new OrdersAPI_GET(this, baseUrl, token);
                API_get.execute();
                return;
            }
        }
        assert data instanceof OrdersResponse;
        OrdersResponse requestResponse = (OrdersResponse) data;
        objects = new ArrayList<>(requestResponse.getData());
        if (objects.isEmpty()){
            message.setVisibility(View.VISIBLE);
        }
        message.setVisibility(View.INVISIBLE);
        OrdersAdapter adapter = new OrdersAdapter(objects, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditClick(Orders item) {
        if (!checkPrivilege("edit")) {
            Toast.makeText(getApplicationContext(), getText(R.string.noEditPermission), Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, OrdersEditAddActivity.class);
        intent.putExtra("orders", item);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Orders item) {
        if (!checkPrivilege("delete")) {
            Toast.makeText(getApplicationContext(), getText(R.string.noDeletePermission), Toast.LENGTH_LONG).show();
            return;
        }
        String baseUrl = sharedSettings.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
        OrdersAPI_DELETE api = new OrdersAPI_DELETE(this, baseUrl, token, item);
        api.execute();
    }

    @Override
    public void onResume(){
        super.onResume();
        String baseUrl = sharedPreferences.getString("serverSocket", "");
        String token = encryptedSharedPreferences.getString("token", "");
        OrdersAPI_GET API_get = new OrdersAPI_GET(this, baseUrl, token);
        API_get.execute();
    }
    private boolean checkPrivilege (String privilege){
        String role = encryptedSharedPreferences.getString("role", "");
        if(privilege.equals("delete")){
            return role.contains("ADMINISTRATOR");
        }
        else if(privilege.equals("add")){
            return role.contains("ADMINISTRATOR");
        }
        else if(privilege.equals("edit")){
            return role.contains("ADMINISTRATOR");
        }
        return true;
    }
}
