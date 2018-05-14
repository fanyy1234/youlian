package com.bby.youlianwallet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bby.youlianwallet.holder.BaseRecyleViewHolder;
import com.bby.youlianwallet.model.RecycleVisitable;
import com.bby.youlianwallet.type.RecycleTypeFactory;
import com.bby.youlianwallet.type.RecycleTypeFactoryForList;

import java.util.List;

public class CommonRecycleAdapter extends RecyclerView.Adapter<BaseRecyleViewHolder> {
    private RecycleTypeFactory typeFactory;
    private List<RecycleVisitable> models;
    private Context mContext;

    public CommonRecycleAdapter(List<RecycleVisitable> models,Context mContext) {
        this.models = models;
        this.typeFactory = new RecycleTypeFactoryForList();
        this.mContext = mContext;
    }

    @Override
    public BaseRecyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View itemView = View.inflate(context,viewType,null);
        return typeFactory.createViewHolder(viewType,itemView);
    }

    @Override
    public void onBindViewHolder(BaseRecyleViewHolder holder, int position) {
        holder.setUpView(models.get(position),position,this);
    }

    @Override
    public int getItemCount() {
        if(null == models){
            return  0;
        }
        return models.size();
    }


    @Override
    public int getItemViewType(int position) {
        return models.get(position).type(typeFactory);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}