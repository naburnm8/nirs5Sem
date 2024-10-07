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

import java.util.ArrayList;
import java.util.List;

public class StorageAPI_GET extends AsyncTask<Void, Void, StorageResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    public StorageAPI_GET(RESTDBOutput context, String baseUrl, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }
    @Override
    protected StorageResponse doInBackground(Void... voids) {
        try{
            Call<List<Storage>> call = apiService.getAllStorage("Bearer " + token);
            Response<List<Storage>> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    List<Storage> storages = response.body();
                    StorageResponse clientsResponse = new StorageResponse();
                    clientsResponse.setData(storages);
                    return clientsResponse;
                }
                StorageResponse storageResponse = new StorageResponse();
                storageResponse.setData(new ArrayList<>());
                return storageResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "StorageAPI_GET", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(StorageResponse response) {
        super.onPostExecute(response);
        context.setData(response);
        context.setLogged(lastLog);
    }
}
