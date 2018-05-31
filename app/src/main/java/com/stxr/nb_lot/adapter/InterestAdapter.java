package com.stxr.nb_lot.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.InterestEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stxr on 2018/5/29.
 */

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    private List<InterestEntity> interestEntityList;
    private Context context;
    private OnItemClick mListener;
    public InterestAdapter(List<InterestEntity> interestEntityList, Context context) {
        this.interestEntityList = interestEntityList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final InterestEntity interest = interestEntityList.get(position);
        if (interest.getPicList() != null) {
            holder.tv_name.setText(interest.getName());
            holder.tv_price.setText("票价：0.01元");
            holder.tv_summary.setText(interest.getSummary());
            InterestEntity.Pic pic = interest.getPicList().get(0);
            Glide.with(context).load(pic.getPicUrl()).into(holder.imageView);
        }
        holder.cv_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(interest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tv_name;

        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_summary)
        TextView tv_summary;

        @BindView(R.id.iv_interest)
        ImageView imageView;
        @BindView(R.id.cv_interest)
        CardView cv_interest;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClick listener) {
        mListener = listener;
    }
     public interface OnItemClick {
        void onClick(InterestEntity entity);
    }

}
