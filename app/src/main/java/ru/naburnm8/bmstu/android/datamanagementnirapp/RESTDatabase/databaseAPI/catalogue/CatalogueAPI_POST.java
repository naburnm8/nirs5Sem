package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.catalogue;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.CatalogueResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

public class CatalogueAPI_POST extends AsyncTask<Void, Void, CatalogueResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Catalogue object;

    public CatalogueAPI_POST(RESTDBOutput context, String baseUrl, Catalogue object, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.object = object;
    }
    @Override
    protected CatalogueResponse doInBackground(Void... voids) {
        try{
            Call<Catalogue> call = apiService.createCatalogue("Bearer " + token, object);
            Response<Catalogue> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    Catalogue catalogue = response.body();
                    CatalogueResponse catalogueResponse = new CatalogueResponse();
                    catalogueResponse.setDataSinglet(catalogue);
                    return catalogueResponse;
                }
                CatalogueResponse catalogueResponse = new CatalogueResponse();
                catalogueResponse.setDataSinglet(null);
                return catalogueResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "CatalogueAPI_POST", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(CatalogueResponse response) {
        super.onPostExecute(response);
        context.setData(response);
        context.setLogged(lastLog);
    }
}
