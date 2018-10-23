package com.pachouri.albumslist.albumslistmodule.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pachouri.albumslist.albumslistmodule.model.AlbumsResponse;
import com.pachouri.albumslist.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pachouri.com.albumslist.R;

/**
 * Created by Ankit on 10/4/18.
 */
public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumsListAdapter.AlbumsListViewHolder> {

    private Context mContext;
    private List<AlbumsResponse> mAlbumsList;

    public AlbumsListAdapter(Context context) {
        mContext = context;
        mAlbumsList = new ArrayList<>();
    }

    @Override
    public AlbumsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_album, parent, false);
        return new AlbumsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumsListViewHolder holder, int position) {
        if (!mAlbumsList.isEmpty()) {
            holder.textViewAlbumTitle.setText(mAlbumsList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mAlbumsList.size();
    }

    public void setAlbumsList(List<AlbumsResponse> albumsList) {
        mAlbumsList.addAll(albumsList);
        notifyDataSetChanged();
    }

    public class AlbumsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textViewAlbumTitle)
        protected AppCompatTextView textViewAlbumTitle;

        public AlbumsListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CommonUtil.showToast(mContext, mContext.getString(R.string.item_click) + " " + getLayoutPosition());
        }
    }
}
