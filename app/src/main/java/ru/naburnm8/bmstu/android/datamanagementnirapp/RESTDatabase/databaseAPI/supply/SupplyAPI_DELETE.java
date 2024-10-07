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

public class SupplyAPI_DELETE extends AsyncTask<Void, Void, SupplyResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Supply item;

    public SupplyAPI_DELETE(RESTDBOutput context, String baseUrl, String token, Supply item) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.item = item;
    }
    @Override
    protected SupplyResponse doInBackground(Void... voids) {
        try{
            int id_item = item.getId();
            Call<Void> call = apiService.deleteSupply("Bearer " + token, id_item);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                return new SupplyResponse(true);
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "SupplyAPI_DELETE", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(SupplyResponse response) {
        super.onPostExecute(response);
        context.setLogged(lastLog);
        context.setData(response);
    }
}
