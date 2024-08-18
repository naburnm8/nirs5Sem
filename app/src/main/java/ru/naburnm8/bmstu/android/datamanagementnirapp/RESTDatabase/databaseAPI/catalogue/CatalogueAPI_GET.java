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

public class CatalogueAPI_GET extends AsyncTask<Void, Void, CatalogueResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    public CatalogueAPI_GET(RESTDBOutput context, String baseUrl, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }
    @Override
    protected CatalogueResponse doInBackground(Void... voids) {
        try{
            Call<List<Catalogue>> call = apiService.getAllCatalogue(token);
            Response<List<Catalogue>> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    List<Catalogue> catalogues = response.body();
                    CatalogueResponse catalogueResponse = new CatalogueResponse();
                    catalogueResponse.setData(catalogues);
                    return catalogueResponse;
                }
                CatalogueResponse catalogueResponse = new CatalogueResponse();
                catalogueResponse.setData(new ArrayList<>());
                return catalogueResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "CatalogueAPI_GET", e.toString());
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
