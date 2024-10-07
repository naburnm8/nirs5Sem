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

public class OrdersAPI_PUT extends AsyncTask<Void, Void, OrdersResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Orders object;

    public OrdersAPI_PUT(RESTDBOutput context, String baseUrl, Orders object, String token) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.object = object;
    }
    @Override
    protected OrdersResponse doInBackground(Void... voids) {
        try{
            Call<Orders> call = apiService.updateOrder("Bearer " + token, object.getId(), object);
            Response<Orders> response = call.execute();
            if(response.isSuccessful()){
                if(response.body() != null){
                    Orders order = response.body();
                    OrdersResponse ordersResponse = new OrdersResponse();
                    ordersResponse.setDataSinglet(order);
                    return ordersResponse;
                }
                OrdersResponse ordersResponse = new OrdersResponse();
                ordersResponse.setDataSinglet(null);
                return ordersResponse;
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "OrdersAPI_PUT", e.toString());
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
