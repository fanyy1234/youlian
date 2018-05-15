package com.bby.youlianwallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bby.youlianwallet.holder.BaseViewHolder;
import com.bby.youlianwallet.model.Visitable;
import com.bby.youlianwallet.type.TypeFactory;
import com.bby.youlianwallet.type.TypeFactoryForList;

import java.util.List;

public class CommonAdapter extends BaseRecyclerAdapter<BaseViewHolder> {
    private TypeFactory typeFactory;
    private List<Visitable> models;
    private Context mContext;
    private View.OnClickListener onClickListener;
    public CommonAdapter(List<Visitable> models, Context context) {
        this.models = models;
        this.typeFactory = new TypeFactoryForList();
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, boolean isItem) {
        holder.setUpView(models.get(position),position,this);
    }

    @Override
    public BaseViewHolder getViewHolder(View view) {
        return new BaseViewHolder(view) {
            @Override
            public void setUpView(Object model, int position, CommonAdapter adapter) {

            }
        };
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return models.get(position).type(typeFactory);
    }

    @Override
    public int getAdapterItemCount() {
        return models.size();
    }

    public void setData( List<Visitable> models) {
        this.models = models;
        notifyDataSetChanged();
    }
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        Context context = parent.getContext();

        View itemView = LayoutInflater.from(context).inflate(viewType,parent,false);
        return typeFactory.createViewHolder(viewType,itemView);
    }

    public void insert(Visitable visitable, int position) {
        insert(models, visitable, position);
    }

    public void remove(int position) {
        remove(models, position);
    }

    public void clear() {
        clear(models);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

}