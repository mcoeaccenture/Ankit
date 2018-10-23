package com.pachouri.albumslist.albumslistmodule.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pachouri.albumslist.albumslistmodule.adapter.AlbumsListAdapter;
import com.pachouri.albumslist.albumslistmodule.model.AlbumsResponse;
import com.pachouri.albumslist.apiservice.WebServiceCall;
import com.pachouri.albumslist.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pachouri.com.albumslist.R;

public class HomeActivity extends AppCompatActivity {

    private static String STATE_ALBUMS_LIST = "state_albums_list";

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.progressBar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.recyclerViewAlbumsList)
    protected RecyclerView mRecyclerViewAlbumsList;

    @BindView(R.id.layoutErrorContainer)
    protected LinearLayout mLayoutErrorContainer;

    @BindView(R.id.textViewErrorTitle)
    protected AppCompatTextView mTextViewErrorTitle;

    @BindView(R.id.textViewErrorSubTitle)
    protected AppCompatTextView mTextViewErrorSubTitle;

    private AlbumsListAdapter mAlbumsListAdapter;
    private ArrayList<AlbumsResponse> mAlbumsResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setAlbumsListAdapter();
        getAlbumsListApiCall();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_ALBUMS_LIST, mAlbumsResponseList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAlbumsResponseList = savedInstanceState.getParcelableArrayList(STATE_ALBUMS_LIST);
    }

    @OnClick(R.id.textViewTryAgain)
    protected void onTryAgainClicked() {
        getAlbumsListApiCall();
    }

    /**
     * set AlbumsListAdapter method
     */
    private void setAlbumsListAdapter() {
        if (mAlbumsListAdapter == null) {
            mAlbumsListAdapter = new AlbumsListAdapter(HomeActivity.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager
                    .VERTICAL, false);
            mRecyclerViewAlbumsList.setLayoutManager(linearLayoutManager);
            mRecyclerViewAlbumsList.setAdapter(mAlbumsListAdapter);
        }
    }


    /**
     * Method to call the album list api
     */
    private void getAlbumsListApiCall() {
        mLayoutErrorContainer.setVisibility(View.GONE);
        if (CommonUtil.isInternetConnected(HomeActivity.this)) {
            if (null != mProgressBar) mProgressBar.setVisibility(View.VISIBLE);
            WebServiceCall webServiceCall = WebServiceCall.getWebServiceCall();
            if (null != webServiceCall) {
                webServiceCall.getAlbumsList(new WebServiceCall.ApiCallBackHandler<List<AlbumsResponse>>() {
                    @Override
                    public void onSuccess(List<AlbumsResponse> response) {
                        if (null != mProgressBar && !isFinishing() && null != response) {
                            mProgressBar.setVisibility(View.GONE);
                            mLayoutErrorContainer.setVisibility(View.GONE);
                            if (mAlbumsListAdapter != null) {
                                Collections.sort(response);
                                mAlbumsListAdapter.setAlbumsList(response);
                                mAlbumsResponseList.addAll(response);
                            }
                        }
                    }

                    @Override
                    public void onError(String message) {
                        showSomethingWrongLayout();
                    }
                });
            }
        } else {
            showInternetLayout();
        }
    }

    /**
     * Method to show internet layout
     */
    private void showInternetLayout() {
        if (!isFinishing() && null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
            mLayoutErrorContainer.setVisibility(View.VISIBLE);
            mTextViewErrorTitle.setText(getString(R.string.error_oops));
            mTextViewErrorSubTitle.setText(getString(R.string.internet_not_found));
        }
    }

    /**
     * Method to show something went wrong layout
     */
    private void showSomethingWrongLayout() {
        if (!isFinishing() && null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
            mLayoutErrorContainer.setVisibility(View.VISIBLE);
            mTextViewErrorTitle.setText(getString(R.string.error_sorry));
            mTextViewErrorSubTitle.setText(getString(R.string.something_wrong));
        }
    }
}
