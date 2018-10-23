package com.pachouri.albumslist.apiservice;

import com.pachouri.albumslist.albumslistmodule.model.AlbumsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ankit on 10/4/18.
 */
public interface ApiServiceCalls {
    //Get call to get the albums list
    @GET(ApiEndpoints.ENDPOINT_ALBUMS_LIST)
    Call<List<AlbumsResponse>> getAlbumsList();
}
