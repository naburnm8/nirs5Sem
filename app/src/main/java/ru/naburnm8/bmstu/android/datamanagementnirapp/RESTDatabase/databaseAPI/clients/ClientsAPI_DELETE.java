package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.clients;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.CatalogueResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.ClientsResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

public class ClientsAPI_DELETE extends AsyncTask<Void, Void, ClientsResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Clients item;

    public ClientsAPI_DELETE(RESTDBOutput context, String baseUrl, String token, Clients item) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.item = item;
    }
    @Override
    protected ClientsResponse doInBackground(Void... voids) {
        try{
            int id_item = item.getId();
            Call<Void> call = apiService.deleteClient("Bearer " + token, id_item);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                return new ClientsResponse(true);
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "ClientsAPI_DELETE", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ClientsResponse response) {
        super.onPostExecute(response);
        context.setLogged(lastLog);
        context.setData(response);
    }
}
