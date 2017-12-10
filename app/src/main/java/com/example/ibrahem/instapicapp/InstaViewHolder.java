package com.example.ibrahem.instapicapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Eng_I on 11/20/2017.
 */

public class InstaViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public InstaViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTitle(String title) {
        TextView postTitle = (TextView) mView.findViewById(R.id.postTitle);
        postTitle.setText(title);
    }

    public void setDesc(String desc) {
        TextView postDesc = (TextView) mView.findViewById(R.id.postDesc);
        postDesc.setText(desc);
    }

    public void setImage(Context context, String image) {
        ImageView postImage = (ImageView) mView.findViewById(R.id.postImage);
        Picasso.with(context).load(image).into(postImage);
    }

    public void setUsername(String userName){
        TextView postUserName = (TextView) mView.findViewById(R.id.textUsername);
        postUserName.setText(userName);
    }
}
