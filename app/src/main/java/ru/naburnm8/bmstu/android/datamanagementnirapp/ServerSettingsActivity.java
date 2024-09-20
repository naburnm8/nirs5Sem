package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.connection.GETConnectionAPI;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.AsyncReceiver;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData.DatabaseQuery;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects.MicrosoftSQLOperator;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects.PostgreSQLOperator;
import ru.naburnm8.bmstu.android.datamanagementnirapp.database.AsyncDBOutput;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ServerSettingsActivity extends AppCompatActivity implements RESTDBOutput {
    Button saveButton, testButton;
    TextInputEditText serverSocket;
    TextView testLog;
    String serverSocketString;
    String serverSocketStringChecking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_setup_activity);
        saveButton = findViewById(R.id.saveServerSettings);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(view -> {
            SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("serverSocket", serverSocketString);
            editor.putBoolean("serverSetUp", true);
            editor.apply();
            finish();
        });
        testButton = findViewById(R.id.serverTestConnectionButton);
        testButton.setOnClickListener(view -> {
            Editable socketEditable = serverSocket.getText();
            String socket = "";
            if (socketEditable != null) {
                socket = socketEditable.toString();
            }
            serverSocketStringChecking = socket;
            testLog.setText(R.string.connecting);
            saveButton.setEnabled(false);
            testServerConnection(socket);
        });
        serverSocket = findViewById(R.id.serverSocketInput);
        testLog = findViewById(R.id.testLog);

    }
    protected void testServerConnection(String socket){
        GETConnectionAPI getConnectionAPI = new GETConnectionAPI(this, socket);
        getConnectionAPI.execute();
    }

    @Override
    public void setLogged(String logged) {
        testLog.setText(logged);
        if(logged == null){
            return;
        }
        if (logged.equals("borovik")){
            serverSocketString = serverSocketStringChecking;
            saveButton.setEnabled(true);
            testLog.setText(R.string.successfulServer);
        }
    }

    @Override
    public void setData(Recordable results) {
        //data is ignored in this activity
    }
}
