package com.littlefox.chinese.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.adapter.listener.PlayerEventListener;
import com.littlefox.chinese.edu.common.Font;
import com.littlefox.chinese.edu.object.result.base.PlayObject;
import com.littlefox.logmonitor.Log;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> 
{
    private Context mContext;
    private int mCurrentPosition = 0;
    private ArrayList<PlayObject> mDataList = null;
    private PlayerEventListener mPlayerEventListener;
    public PlayerListAdapter(Context context, int currentPosition, ArrayList<PlayObject> list)
    {
        mContext            = context;
        mCurrentPosition    = currentPosition;
        mDataList           = list;
    }

    public void setCurrentPlayPosition(int index)
    {
        mCurrentPosition = index;
        notifyDataSetChanged();
    }

    public void setOnPlayEventListener(PlayerEventListener listener)
    {
        mPlayerEventListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.player_list_item_landscape, parent,  false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        Glide.with(mContext).load(mDataList.get(position).image_url).transition(withCrossFade()).into(holder._ItemTitleImage);

        if(mDataList.get(position).getPosition().equals(""))
        {
            holder._ItemTitleText.setText(mDataList.get(position).getTitle());
        }
        else
        {
            holder._ItemTitleText.setText(mDataList.get(position).getPosition() +". "+mDataList.get(position).getTitle());
        };


        if(mCurrentPosition == position)
        {
            holder._ItemBackground.setImageResource(R.drawable.box_yellow);
        }
        else
        {
            holder._ItemBackground.setImageResource(R.drawable.box);
        }

        holder._ItemBaseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mCurrentPosition = position;
                Log.f("mCurrentPlayIndex : "+ mCurrentPosition);
                mPlayerEventListener.onClickPlayItem(mDataList.get(position).getContentId());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id._itemBaseLayout)
        ScalableLayout _ItemBaseLayout;

        @BindView(R.id._itemBackground)
        ImageView _ItemBackground;

        @BindView(R.id._itemTitleImage)
        ImageView _ItemTitleImage;

        @BindView(R.id._itemTitleText)
        TextView _ItemTitleText;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            initFont();
        }
        
        private void initFont()
        {
            _ItemTitleText.setTypeface(Font.getInstance(mContext).getRobotoRegular());
        }
    }
}
