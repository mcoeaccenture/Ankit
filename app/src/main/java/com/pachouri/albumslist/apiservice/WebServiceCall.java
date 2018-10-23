package com.pachouri.albumslist.apiservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pachouri.albumslist.albumslistmodule.model.AlbumsResponse;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankit on 10/4/18.
 */

/**
 * Class to initialise and setup Retrofit.
 * Webservice calls are declared
 */

public class WebServiceCall {

    public static ApiServiceCalls mApiServiceCalls;
    public static WebServiceCall mWebServiceCall;

    public static WebServiceCall getWebServiceCall() {
        if (mWebServiceCall == null) {
            mWebServiceCall = new WebServiceCall();
        }
        return mWebServiceCall;
    }

    //interface to implement the callback success and failure
    public interface ApiCallBackHandler<T> {
        void onSuccess(T response);

        void onError(String message);
    }

    //Setup retrofit
    public ApiServiceCalls getApi() {
        if (mApiServiceCalls == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier
                    .STATIC);
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = gsonBuilder.create();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(loggingInterceptor);
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(ApiEndpoints.BASE_URL);
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
            retrofitBuilder.client(httpClient.build());
            Retrofit retrofit = retrofitBuilder.build();
            mApiServiceCalls = retrofit.create(ApiServiceCalls.class);
        }
        return mApiServiceCalls;
    }

    //Get Albums List Api implementation
    public void getAlbumsList(final ApiCallBackHandler<List<AlbumsResponse>> callBackHandler) {
        Call<List<AlbumsResponse>> getAlbumsList = getApi().getAlbumsList();
        getAlbumsList.enqueue(new ApiCallBack<List<AlbumsResponse>>() {
            @Override
            public void onSuccess(List<AlbumsResponse> response) {
                callBackHandler.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callBackHandler.onError(message);
            }
        });
    }
}
