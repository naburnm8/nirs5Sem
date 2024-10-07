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

public class SupplyAPI_POST extends AsyncTask<Void, Void, SupplyResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Supply object;

    public SupplyAPI_POST(RESTDBOutput context, String baseUrl, Supply object, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.object = object;
    }
    @Override
    protected SupplyResponse doInBackground(Void... voids) {
        try{
            Call<Supply> call = apiService.createSupply("Bearer " + token, object);
            Response<Supply> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    Supply supply = response.body();
                    SupplyResponse supplyResponse = new SupplyResponse();
                    supplyResponse.setDataSinglet(supply);
                    return supplyResponse;
                }
                SupplyResponse supplyResponse = new SupplyResponse();
                supplyResponse.setDataSinglet(null);
                return supplyResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "SupplyAPI_POST", e.toString());
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
