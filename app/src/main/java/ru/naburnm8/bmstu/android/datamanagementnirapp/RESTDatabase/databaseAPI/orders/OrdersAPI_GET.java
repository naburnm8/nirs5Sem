package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.databaseAPI.orders;

import android.os.AsyncTask;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.OrdersResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests.SupplyResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiClient;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

public class OrdersAPI_GET extends AsyncTask<Void, Void, OrdersResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    public OrdersAPI_GET(RESTDBOutput context, String baseUrl, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
    }
    @Override
    protected OrdersResponse doInBackground(Void... voids) {
        try{
            Call<List<Orders>> call = apiService.getAllOrders("Bearer " + token);
            Response<List<Orders>> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    List<Orders> supplies = response.body();
                    OrdersResponse ordersResponse = new OrdersResponse();
                    ordersResponse.setData(supplies);
                    return ordersResponse;
                }
                OrdersResponse ordersResponse = new OrdersResponse();
                ordersResponse.setData(new ArrayList<>());
                return ordersResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "OrdersAPI_GET", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(OrdersResponse response) {
        super.onPostExecute(response);
        context.setData(response);
        context.setLogged(lastLog);
    }
}
