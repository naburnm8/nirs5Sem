package ru.naburnm8.bmstu.android.datamanagementnirapp.choiceActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.catalogueList.OnDBandRecyclerListener;

import java.util.ArrayList;

public class catalogueChoiceActivity extends AppCompatActivity implements OnDBandRecyclerListener {
    RecyclerView recyclerView;
    ArrayList<Catalogue> catalogueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);
    }
    @Override
    public void onEditClick(Catalogue i){
    }

    @Override
    public void onDeleteClick(Catalogue item) {

    }

    @Override
    public void setLogged(String logged) {

    }

    @Override
    public void setData(Recordable data) {

    }
}
