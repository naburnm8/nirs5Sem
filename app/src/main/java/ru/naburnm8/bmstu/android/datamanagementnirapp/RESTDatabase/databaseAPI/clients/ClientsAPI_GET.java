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

import java.util.ArrayList;
import java.util.List;

public class ClientsAPI_GET extends AsyncTask<Void, Void, ClientsResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    public ClientsAPI_GET(RESTDBOutput context, String baseUrl, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }
    @Override
    protected ClientsResponse doInBackground(Void... voids) {
        try{
            Call<List<Clients>> call = apiService.getAllClients("Bearer " + token);
            Response<List<Clients>> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    List<Clients> clients = response.body();
                    ClientsResponse clientsResponse = new ClientsResponse();
                    clientsResponse.setData(clients);
                    return clientsResponse;
                }
                ClientsResponse clientsResponse = new ClientsResponse();
                clientsResponse.setData(new ArrayList<>());
                return clientsResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "ClientsAPI_GET", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ClientsResponse response) {
        super.onPostExecute(response);
        context.setData(response);
        context.setLogged(lastLog);
    }
}
