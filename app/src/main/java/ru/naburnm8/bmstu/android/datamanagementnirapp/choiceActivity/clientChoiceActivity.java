package ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList.OnDBandRecyclerListener;

import java.util.ArrayList;

public class clientChoiceActivity extends AppCompatActivity implements OnDBandRecyclerListener {
    RecyclerView recyclerView;
    ArrayList<Clients> clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
    }


    @Override
    public void onEditClick(Clients item) {

    }

    @Override
    public void onDeleteClick(Clients item) {

    }

    @Override
    public void setLogged(String logged) {

    }

    @Override
    public void setData(Recordable data) {

    }
}
