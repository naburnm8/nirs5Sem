package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue.CatalogueAPI_GET;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders.OrdersAPI_GET;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.OrdersResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.misc.OnGenerationListener;
import ru.naburnm8.bmstu.android.datamanagementnirapp.misc.ReportData;
import ru.naburnm8.bmstu.android.datamanagementnirapp.misc.ReportGenerator;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrderUnited;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrdersAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class AccountSettingsActivity extends AppCompatActivity implements OnGenerationListener {
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesEncrypted;
    SharedPreferences sharedSettings;
    Button logoutButton, settingsButton, generateAReportBtn;
    TextView usernameText, roleText;

    String startDateChosen;
    String endDateChosen;

    boolean permittedBackpress = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        try{
            String masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferencesEncrypted = EncryptedSharedPreferences.create("account", masterKeys,
                    getApplicationContext(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        sharedSettings = getSharedPreferences("settings", MODE_PRIVATE);
        logoutButton = findViewById(R.id.logoutBtn);
        settingsButton = findViewById(R.id.settingsBtn);
        usernameText = findViewById(R.id.usernameText);
        roleText = findViewById(R.id.roleText);
        generateAReportBtn = findViewById(R.id.generateAReportButton);
        usernameText.setText(sharedPreferencesEncrypted.getString("username", "ERROR"));
        roleText.setText(sharedPreferencesEncrypted.getString("role", "ERROR"));
        logoutButton.setOnClickListener(view -> {
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply();
            sharedPreferencesEncrypted.edit().putString("username", "").apply();
            sharedPreferencesEncrypted.edit().putString("role", "").apply();
            sharedPreferencesEncrypted.edit().putString("token", "").apply();
            sharedPreferencesEncrypted.edit().putString("password", "").apply();
            finish();
        });
        generateAReportBtn.setOnClickListener(v ->{
            showReportDialog();
        });

        settingsButton.setOnClickListener(view -> {
            showProtectionDialogue();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        usernameText.setText(sharedPreferencesEncrypted.getString("username", "ERROR"));
        roleText.setText(sharedPreferencesEncrypted.getString("role", "ERROR"));
    }

    @Override
    public void setLogged(String logged) {
        if (logged == null){
            return;
        }
        Toast.makeText(getApplicationContext(), logged, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setData(Recordable data) {
        if (data != null){
            if (data instanceof OrdersResponse){
                OrdersResponse ordersResponse = (OrdersResponse) data;
                ArrayList<OrderUnited> orderUnitedArrayList = OrdersAdapter.getUnitedOrdersList(new ArrayList<>(ordersResponse.getData()));
                OrderUnited startOrder = new OrderUnited();
                OrderUnited endOrder = new OrderUnited();
                startOrder.setDateOfTransaction(startDateChosen);
                endOrder.setDateOfTransaction(endDateChosen);
                ArrayList<OrderUnited> chosenOrders = new ArrayList<>();
                for (OrderUnited order: orderUnitedArrayList){
                    if(OrderUnited.compareDates(order, startOrder) >= 0 && OrderUnited.compareDates(order,endOrder) <= 0){
                        chosenOrders.add(order);
                    }
                }
                String todayDate = OrderUnited.getTodayDate();
                InputStream inputStream = null;
                ReportData reportData = new ReportData(startDateChosen, endDateChosen, sharedPreferencesEncrypted.getString("username", "ERROR"), todayDate, chosenOrders.size(), OrderUnited.getTotal(chosenOrders), chosenOrders);
                try {
                    inputStream = getAssets().open("template.docx");
                } catch (Exception e){
                    Log.println(Log.ERROR, "Account Settings Activity", e.toString());
                    return;
                }

                File fileDir = getFilesDir();
                ReportGenerator reportGenerator = new ReportGenerator(inputStream, this, fileDir);
                reportGenerator.execute(reportData);
            }
        }
    }
    @Override
    public void onBackPressed(){
        if (!permittedBackpress){
            Toast.makeText(this, "Wait for generation completion", Toast.LENGTH_LONG).show();
            return;
        }
        super.onBackPressed();
    }

    protected void showProtectionDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirmation);
        builder.setMessage(R.string.confirmationMessage);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            sharedPreferencesEncrypted.edit().putString("username", "").apply();
            sharedPreferencesEncrypted.edit().putString("role", "").apply();
            sharedPreferencesEncrypted.edit().putString("token", "").apply();
            sharedPreferences.edit().clear().apply();
            sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();

            restart();
        });

        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void showReportDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_generate_report, null);
        EditText startDateField = dialogView.findViewById(R.id.startDateField);
        EditText endDateField = dialogView.findViewById(R.id.endDateField);
        Button okButton = dialogView.findViewById(R.id.okButton);
        startDateField.setOnClickListener(v -> showDatePickerDialog(startDateField));
        endDateField.setOnClickListener(v -> showDatePickerDialog(endDateField));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        okButton.setOnClickListener(v -> {
            String startDate = startDateField.getText().toString();
            String endDate = endDateField.getText().toString();
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                startDateChosen = startDate;
                endDateChosen = endDate;
                requestOrders();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please select both dates", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    private void showDatePickerDialog(EditText dateField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String normalisedMonth = String.valueOf(selectedMonth + 1);
                    String normalisedDay = String.valueOf(selectedDay);
                    if(normalisedMonth.length() == 1){
                        normalisedMonth = "0" + normalisedMonth;
                    }
                    if(normalisedDay.length() == 1){
                        normalisedDay = "0" + normalisedDay;
                    }
                    String selectedDate = selectedYear + "-" + normalisedMonth + "-" + normalisedDay;
                    dateField.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void requestOrders(){
        String baseUrl = sharedPreferences.getString("serverSocket", "");
        String token = sharedPreferencesEncrypted.getString("token", "");
        OrdersAPI_GET API_get = new OrdersAPI_GET(this, baseUrl, token);
        permittedBackpress = false;
        API_get.execute();
    }

    @Override
    public void onGenerationResult(String status) {
        if(status.contains("Suc")){
            Toast.makeText(this, "Report created", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Failed to create report", Toast.LENGTH_SHORT).show();
        }
        permittedBackpress = true;
    }
}
