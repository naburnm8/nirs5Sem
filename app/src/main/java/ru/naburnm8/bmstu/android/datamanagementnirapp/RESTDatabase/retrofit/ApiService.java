package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.retrofit;


import retrofit2.Call;
import retrofit2.http.*;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.*;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginRequest;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.LoginResponse;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels.RegisterRequest;

import java.util.List;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<Message> register(@Body RegisterRequest registerRequest);

    @GET("api/connection")
    Call<Message> getConnection();

    @GET("api/connection/info")
    Call<Message> getConnectionInfo();

    //Supply

    @GET("api/supplies")
    Call<List<Supply>> getAllSupply(@Header("Authorization") String token);

    @GET("api/supplies/{id}")
    Call<Supply> getSupplyById(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/supplies")
    Call<Supply> createSupply(@Header("Authorization") String token, @Body Supply supply);

    @PUT("api/supplies/{id}")
    Call<Supply> updateSupply(@Header("Authorization") String token, @Path("id") int id, @Body Supply supply);

    @DELETE("api/supplies/{id}")
    Call<Void> deleteSupply(@Header("Authorization") String token, @Path("id") int id);

    //Storage

    @GET("api/storage")
    Call<List<Storage>> getAllStorage(@Header("Authorization") String token);

    @GET("api/storage/{id}")
    Call<Storage> getStorageById(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/storage")
    Call<Storage> createStorage(@Header("Authorization") String token, @Body Storage storage);

    @PUT("api/storage/{id}")
    Call<Storage> updateStorage(@Header("Authorization") String token, @Path("id") int id, @Body Storage storage);

    @DELETE("api/storage/{id}")
    Call<Void> deleteStorage(@Header("Authorization") String token, @Path("id") int id);

    //Catalogue

    @GET("api/catalogue")
    Call<List<Catalogue>> getAllCatalogue(@Header("Authorization") String token);

    @GET("api/catalogue/{id}")
    Call<Catalogue> getCatalogueById(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/catalogue")
    Call<Catalogue> createCatalogue(@Header("Authorization") String token, @Body Catalogue catalogue);

    @PUT("api/catalogue/{id}")
    Call<Catalogue> updateCatalogue(@Header("Authorization") String token, @Path("id") int id, @Body Catalogue catalogue);

    @DELETE("api/catalogue/{id}")
    Call<Void> deleteCatalogue(@Header("Authorization") String token, @Path("id") int id);

    //Clients

    @GET("api/clients")
    Call<List<Clients>> getAllClients(@Header("Authorization") String token);

    @GET("api/clients/{id}")
    Call<Clients> getClientById(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/clients")
    Call<Clients> createClient(@Header("Authorization") String token, @Body Clients clients);

    @PUT("api/clients/{id}")
    Call<Clients> updateClient(@Header("Authorization") String token, @Path("id") int id, @Body Clients clients);

    @DELETE("api/clients/{id}")
    Call<Void> deleteClient(@Header("Authorization") String token, @Path("id") int id);

    //Orders

    @GET("api/orders")
    Call<List<Orders>> getAllOrders(@Header("Authorization") String token);

    @GET("api/orders/{id}")
    Call<Orders> getOrderById(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/orders")
    Call<Orders> createOrder(@Header("Authorization") String token, @Body Orders orders);

    @PUT("api/orders/{id}")
    Call<Orders> updateOrder(@Header("Authorization") String token, @Path("id") int id, @Body Orders orders);

    @DELETE("api/orders/{id}")
    Call<Void> deleteOrder(@Header("Authorization") String token, @Path("id") int id);

}
