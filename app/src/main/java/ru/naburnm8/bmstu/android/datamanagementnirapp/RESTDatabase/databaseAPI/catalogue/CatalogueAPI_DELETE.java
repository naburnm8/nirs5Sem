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

public class CatalogueAPI_DELETE extends AsyncTask<Void, Void, CatalogueResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Catalogue item;

    public CatalogueAPI_DELETE(RESTDBOutput context, String baseUrl, String token, Catalogue item) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.item = item;
    }
    @Override
    protected CatalogueResponse doInBackground(Void... voids) {
        try{
            int id_item = item.getId();
            Call<Void> call = apiService.deleteCatalogue("Bearer " + token, id_item);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                return new CatalogueResponse(true);
            }
            /*
            Call<List<Catalogue>> call = apiService.getAllCatalogue("Bearer " + token);
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
            }*/
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "CatalogueAPI_DELETE", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CatalogueResponse response) {
        super.onPostExecute(response);
        context.setLogged(lastLog);
        context.setData(response);
    }
}
