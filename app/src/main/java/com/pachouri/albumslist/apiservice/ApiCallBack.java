package com.pachouri.albumslist.apiservice;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit on 10/4/18.
 */

/**
 * Abstract class ApiCallBack<T> generic type of callback class to implement the response of an api
 * with success and failure abstract methods
 *
 * @param <T>
 */

public abstract class ApiCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        boolean isSuccessFullResponse = false;
        T apiResponse = null;
        if (response != null && response.isSuccessful()) {
            apiResponse = response.body();
            Log.d("body", "" + apiResponse.toString());
            if (apiResponse != null) {
                isSuccessFullResponse = true;
            } else {
                throw new RuntimeException("Please ensure 'T' extends ApiResponse");
            }
        }
        if (isSuccessFullResponse) {
            onSuccess(apiResponse);
        } else {
            onFailure("Error parsing response");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(t.getLocalizedMessage());
        Log.v("Error: ", " : " + t.getLocalizedMessage());
    }

    public abstract void onSuccess(T response);

    public abstract void onFailure(String message);
}
