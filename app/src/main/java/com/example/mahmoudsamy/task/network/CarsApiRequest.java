package com.example.mahmoudsamy.task.network;

import com.example.mahmoudsamy.task.model.CarsResponse;
import com.example.mahmoudsamy.task.presenter.IPresenter;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarsApiRequest implements ICarsApiRequest {
    IPresenter mainPresenter;
    private String BaseUrl="http://api.emiratesauction.com";

    @Override
    public void getCarsFromNetwork() {


         OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

         Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(BaseUrl)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

         Retrofit retrofit =
                builder.client(httpClient.build()).build();
        // Create a very simple REST adapter which points the GitHub API endpoint.
        CarsApiServices client = retrofit.create(CarsApiServices.class);

        // Fetch a list of the Github repositories.
         Call<CarsResponse> call =
                client.getCarsList();
        //executeNetworkOperations

        call.clone().enqueue(new Callback<CarsResponse>() {
            @Override
            public void onResponse(Call<CarsResponse> call, Response<CarsResponse> response) {
                // The network call was a success and we got a response
                if (response.isSuccessful()) {
                    mainPresenter.handleSuccessResponse(response.body());

                } else {
                    //handle errors
                    mainPresenter.handelFailureResponce();
                }
            }

            @Override
            public void onFailure(Call<CarsResponse> call, Throwable t) {
                // the network call was a failure
                // TODO: handle error
                mainPresenter.handelFailureResponce();
            }
        });

    }

    @Override
    public void attachViewModel(IPresenter viewModel) {
        this.mainPresenter =viewModel;
    }

    @Override
    public void deAttachViewModel() {
        this.mainPresenter =null;
    }

}
