package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.storage;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.ClientsResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.StorageResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

public class StorageAPI_POST extends AsyncTask<Void, Void, StorageResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Storage object;

    public StorageAPI_POST(RESTDBOutput context, String baseUrl, Storage object, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.object = object;
    }
    @Override
    protected StorageResponse doInBackground(Void... voids) {
        try{
            Call<Storage> call = apiService.createStorage("Bearer " + token, object);
            Response<Storage> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    Storage client = response.body();
                    StorageResponse storageResponse = new StorageResponse();
                    storageResponse.setDataSinglet(client);
                    return storageResponse;
                }
                StorageResponse storageResponse = new StorageResponse();
                storageResponse.setDataSinglet(null);
                return storageResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "StorageAPI_POST", e.toString());
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
