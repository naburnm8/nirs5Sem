package ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.orders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders.OrdersAPI_POST;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders.OrdersAPI_PUT;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity.CatalogueChoiceActivity;
import ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity.ClientChoiceActivity;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrderUnited;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.editAdd.OnItemClickListener;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.editAdd.OrdersEditAddAdapter;
import ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.supply.SupplyEditAddActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class OrdersEditAddActivity extends AppCompatActivity implements OnItemClickListener {
    TextView username, role, clientName, itemName, quantityChosenText;
    Button chooseClient, addItems, submit, chooseItem, enterManually;
    EditText editDate;
    RecyclerView recyclerView;
    SeekBar seekBar;

    SharedPreferences sharedPreferences;
    SharedPreferences encryptedSharedPreferences;
    SharedPreferences sharedSettings;

    OrderUnited object;
    Catalogue currentCatalogue;
    int quantityChosen = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_edit_add);
        username = findViewById(R.id.usernameText);
        role = findViewById(R.id.roleText);
        clientName = findViewById(R.id.clientId);
        chooseClient = findViewById(R.id.chooseClientButton);
        addItems = findViewById(R.id.addAPairButton);
        submit = findViewById(R.id.submitButton);
        editDate = findViewById(R.id.editOrderDate);
        itemName = findViewById(R.id.itemNameText);
        chooseItem = findViewById(R.id.chooseAnItemButton);
        recyclerView = findViewById(R.id.ordersTupleRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        seekBar = findViewById(R.id.seekBarQuantity);
        quantityChosenText = findViewById(R.id.quantityChosen);
        enterManually = findViewById(R.id.enterManually);
        object = new OrderUnited();


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
        username.setText(encryptedSharedPreferences.getString("username", ""));
        role.setText(encryptedSharedPreferences.getString("role", ""));
        if (getIntent().hasExtra("orders")) {
            object = (OrderUnited) getIntent().getSerializableExtra("orders");
            editDate.setText(object.getDateOfTransaction());
            clientName.setText(String.format("%s %s", object.getClient().getFirstName(), object.getClient().getLastName()));
        }
        OrdersEditAddAdapter ordersEditAddAdapter = new OrdersEditAddAdapter(object.getOrders(), this);
        recyclerView.setAdapter(ordersEditAddAdapter);
        quantityChosenText.setText("1");
        chooseClient.setOnClickListener(view -> {
            Intent intent = new Intent(this, ClientChoiceActivity.class);
            startActivityForResult(intent, 1);
        });

        chooseItem.setOnClickListener(view -> {
            Intent intent = new Intent(this, CatalogueChoiceActivity.class);
            startActivityForResult(intent, 2);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantityChosenText.setText(String.valueOf(progress + 1));
                quantityChosen = progress + 1;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        addItems.setOnClickListener(view -> {
           if (currentCatalogue == null) {
               Toast.makeText(getApplicationContext(), getText(R.string.pleaseChooseACatalogue), Toast.LENGTH_SHORT).show();
               return;
           }
           if (object.getClient() == null){
               Toast.makeText(getApplicationContext(), getText(R.string.chooseAClient), Toast.LENGTH_SHORT).show();
               return;
           }
           Orders toAdd = new Orders();
           toAdd.setClient(object.getClient());
           toAdd.setDateOfTransaction(editDate.getText().toString());
           toAdd.setqItem(quantityChosen);
           toAdd.setItem(currentCatalogue);
           object.getOrders().add(toAdd);
           OrdersEditAddAdapter ordersEditAddAdapter1 = new OrdersEditAddAdapter(object.getOrders(), this);
           recyclerView.setAdapter(ordersEditAddAdapter1);
        });
        submit.setOnClickListener(view -> {
            String date = editDate.getText().toString();
            if (!SupplyEditAddActivity.isDateValid(date)){
                Toast.makeText(getApplicationContext(), getText(R.string.wrongDate), Toast.LENGTH_SHORT).show();
                return;
            }
            object.setDateOfTransaction(date);
            validateOrders();
            String baseUrl = sharedPreferences.getString("serverSocket", "");
            String token = encryptedSharedPreferences.getString("token", "");
            for (Orders order : object.getOrders()) {
                if (order.getId() == 0){
                    OrdersAPI_POST ordersAPIPost = new OrdersAPI_POST(this, baseUrl, order, token);
                    ordersAPIPost.execute();
                }
                else{
                    OrdersAPI_PUT ordersAPIPut = new OrdersAPI_PUT(this, baseUrl, order, token);
                    ordersAPIPut.execute();
                }
            }
        });
        enterManually.setOnClickListener(view -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogueView = inflater.inflate(R.layout.dialog_number_input, null);
            EditText numberInput = dialogueView.findViewById(R.id.numberInput);

            AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(this);
            dialogueBuilder.setView(dialogueView).setTitle(R.string.quantity).setPositiveButton(R.string.submit, (dialog, which) -> {
                String input = numberInput.getText().toString();
                if(!input.isEmpty()){
                    int number = Integer.parseInt(input);
                    quantityChosenText.setText(String.valueOf(number));
                    quantityChosen = number;
                }
                else{
                    dialog.dismiss();
                }
            }).setNegativeButton(R.string.cancel, (dialog, which) -> {
                dialog.dismiss();
            });

            AlertDialog dialog = dialogueBuilder.create();
            dialog.show();

        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.hasExtra("client")) {
                Clients chosen = (Clients) data.getSerializableExtra("client");
                object.setClient(chosen);
                clientName.setText(String.format("%s %s", chosen.getFirstName(), chosen.getLastName()));
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data.hasExtra("catalogue")) {
                Catalogue chosen = (Catalogue) data.getSerializableExtra("catalogue");
                currentCatalogue = chosen;
                itemName.setText(chosen.getItemName());
            }
        }
    }

    @Override
    public void onItemClick(Orders order) {
        object.getOrders().remove(order);
        OrdersEditAddAdapter ordersEditAddAdapter = new OrdersEditAddAdapter(object.getOrders(), this);
        recyclerView.setAdapter(ordersEditAddAdapter);
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    protected void validateOrders(){
        Clients chosen = object.getClient();
        String date = editDate.getText().toString();
        for (Orders order : object.getOrders()) {
            order.setDateOfTransaction(date);
            order.setClient(chosen);
        }
    }
}
