package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.supply;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.StorageResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.SupplyResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

public class SupplyAPI_GET extends AsyncTask<Void, Void, SupplyResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    public SupplyAPI_GET(RESTDBOutput context, String baseUrl, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }
    @Override
    protected SupplyResponse doInBackground(Void... voids) {
        try{
            Call<List<Supply>> call = apiService.getAllSupply("Bearer " + token);
            Response<List<Supply>> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    List<Supply> supplies = response.body();
                    SupplyResponse supplyResponse = new SupplyResponse();
                    supplyResponse.setData(supplies);
                    return supplyResponse;
                }
                SupplyResponse supplyResponse = new SupplyResponse();
                supplyResponse.setData(new ArrayList<>());
                return supplyResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "SupplyAPI_GET", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(SupplyResponse response) {
        super.onPostExecute(response);
        context.setData(response);
        context.setLogged(lastLog);
    }
}
