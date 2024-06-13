package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.connection;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Message;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

public class GETConnectionAPI extends AsyncTask<Void, Void, String> {
    RESTDBOutput context;
    ApiService apiService;
    public GETConnectionAPI(RESTDBOutput context, String baseUrl) {
        this.context = context;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }

    @Override
    protected String doInBackground(Void... voids) {

        Call<Message> call = apiService.getConnection();
        try {
            Response<Message> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getMessage();
            }
        } catch (Exception e) {
            Log.println(Log.ERROR, "GetConnectionAPI", e.toString());
            context.setLogged(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        context.setData(s);
        if (s != null) {
            context.setLogged(s);
        }
    }
}
