package ru.naburnm8.bmstu.android.datamanagementnirapp;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.AsyncReceiver;
import ru.naburnm8.bmstu.android.datamanagementnirapp.database.AsyncDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.database.MicrosoftSQLOperator;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ServerSettingsActivity extends AppCompatActivity implements AsyncReceiver {
    Button saveButton, testButton;
    TextInputEditText serverSocket;
    TextView testLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_setup_activity);
        saveButton = findViewById(R.id.saveServerSettings);
        testButton = findViewById(R.id.serverTestConnectionButton);
        testButton.setOnClickListener(view -> {
            Editable socketEditable = serverSocket.getText();
            String socket = "";
            if (socketEditable != null) {
                socket = socketEditable.toString();
            }
            boolean isValidServer = testServerConnection(socket);
        });
        serverSocket = findViewById(R.id.serverSocketInput);
        testLog = findViewById(R.id.testLog);

    }
    boolean testServerConnection(String socket){

        return true;
    }

    @Override
    public void setLogged(String logged) {
        testLog.setText(logged);
    }

    @Override
    public void setData(ArrayList<ArrayList<String>> results) {
        //data is ignored in this activity
    }
}
