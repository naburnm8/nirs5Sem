package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.storage;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.ClientsResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.StorageResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

public class StorageAPI_DELETE extends AsyncTask<Void, Void, StorageResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Storage item;

    public StorageAPI_DELETE(RESTDBOutput context, String baseUrl, String token, Storage item) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.item = item;
    }
    @Override
    protected StorageResponse doInBackground(Void... voids) {
        try{
            int id_item = item.getIdItem();
            Call<Void> call = apiService.deleteStorage("Bearer " + token, id_item);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                return new StorageResponse(true);
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "StorageAPI_DELETE", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(StorageResponse response) {
        super.onPostExecute(response);
        context.setLogged(lastLog);
        context.setData(response);
    }
}
