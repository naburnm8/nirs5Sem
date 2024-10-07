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

public class OrdersAPI_DELETE extends AsyncTask<Void, Void, OrdersResponse> {
    RESTDBOutput context;
    ApiService apiService;
    String lastLog;
    String token;
    Orders item;

    public OrdersAPI_DELETE(RESTDBOutput context, String baseUrl, String token, Orders item) {
        this.context = context;
        this.token = token;
        this.apiService = ApiClient.getRetrofit(baseUrl).create(ApiService.class);
        this.item = item;
    }
    @Override
    protected OrdersResponse doInBackground(Void... voids) {
        try{
            int id_item = item.getId();
            Call<Void> call = apiService.deleteOrder("Bearer " + token, id_item);
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                return new OrdersResponse(true);
            }
        } catch (Exception e){
            lastLog = e.toString();
            Log.println(Log.ERROR, "OrdersAPI_DELETE", e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(OrdersResponse response) {
        super.onPostExecute(response);
        context.setLogged(lastLog);
        context.setData(response);
    }
}
