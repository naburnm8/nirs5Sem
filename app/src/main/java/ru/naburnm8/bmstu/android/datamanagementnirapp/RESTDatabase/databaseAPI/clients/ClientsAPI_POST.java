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

public class ClientsAPI_POST extends AsyncTask<Void, Void, ClientsResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Clients object;

    public ClientsAPI_POST(RESTDBOutput context, String baseUrl, Clients object, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.object = object;
    }
    @Override
    protected ClientsResponse doInBackground(Void... voids) {
        try{
            Call<Clients> call = apiService.createClient("Bearer " + token, object);
            Response<Clients> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    Clients client = response.body();
                    ClientsResponse clientsResponse = new ClientsResponse();
                    clientsResponse.setDataSinglet(client);
                    return clientsResponse;
                }
                ClientsResponse clientsResponse = new ClientsResponse();
                clientsResponse.setDataSinglet(null);
                return clientsResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "ClientsAPI_POST", e.toString());
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
