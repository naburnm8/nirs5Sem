package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginRequest;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;




public class LoginAPI extends AsyncTask<LoginRequest, Void, LoginResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;

    public LoginAPI(RESTDBOutput context, String baseUrl) {
        this.context = context;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        try{
            Call<LoginResponse> call = apiService.login(loginRequests[0]);
            Response<LoginResponse> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() == null){
                    Context dbOutputContexted = (Context) context;
                    lastLog = dbOutputContexted.getString(R.string.wrongUsernameOrPassword);
                }
                return response.body();
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "LoginAPI", lastLog);
        }
        Context dbOutputContexted = (Context) context;
        lastLog = dbOutputContexted.getString(R.string.wrongUsernameOrPassword);
        return null;
    }

    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        super.onPostExecute(loginResponse);
        context.setData(loginResponse);
        context.setLogged(lastLog);
    }
}
