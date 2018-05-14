package com.bby.youlianwallet.type;

import android.view.View;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.holder.AssetDetailViewHolder;
import com.bby.youlianwallet.holder.BaseViewHolder;
import com.bby.youlianwallet.holder.FeedbackViewHolder;
import com.bby.youlianwallet.holder.NoticeViewHolder;
import com.bby.youlianwallet.holder.PersonViewHolder;
import com.bby.youlianwallet.holder.RechargeDetailViewHolder;
import com.bby.youlianwallet.holder.TransferRecordHolder;
import com.bby.youlianwallet.holder.UsdtRecordHolder;
import com.bby.youlianwallet.holder.WithdrawDetailViewHolder;
import com.bby.youlianwallet.holder.YejiViewHolder;
import com.bby.youlianwallet.holder.YoulianHolder;
import com.bby.youlianwallet.model.AssetDetail;
import com.bby.youlianwallet.model.FeedBack;
import com.bby.youlianwallet.model.Notice;
import com.bby.youlianwallet.model.Person;
import com.bby.youlianwallet.model.RechargeDetail;
import com.bby.youlianwallet.model.TransferRecord;
import com.bby.youlianwallet.model.UsdtRecord;
import com.bby.youlianwallet.model.WithdrawDetail;
import com.bby.youlianwallet.model.Yeji;
import com.bby.youlianwallet.model.Youlian;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class TypeFactoryForList implements TypeFactory {
    private final int TYPE_RESOURCE_PERSON = R.layout.item_recylerview;
    private final int TYPE_RESOURCE_ASSETDETAIL = R.layout.item_assetdetail;
    private final int TYPE_RESOURCE_NOTICE = R.layout.item_notice;
    private final int TYPE_RESOURCE_RECHARGEDETAIL = R.layout.item_rechargedetail;
    private final int TYPE_RESOURCE_WITHDRAWDETAIL = R.layout.item_withdrawdetail;
    private final int TYPE_RESOURCE_TRANSFERRECORD = R.layout.item_transfer_record;
    private final int TYPE_RESOURCE_YEJI = R.layout.item_yeji;
    private final int TYPE_RESOURCE_FEEDBACK = R.layout.item_feedback;
    private final int TYPE_RESOURCE_USDT = R.layout.item_usdt;
    private final int TYPE_RESOURCE_YOULIAN = R.layout.item_youlian;

    @Override
    public int type(Person person) {
        return TYPE_RESOURCE_PERSON;
    }

    @Override
    public int type(AssetDetail assetDetail) {
        return TYPE_RESOURCE_ASSETDETAIL;
    }

    @Override
    public int type(Notice notice) {
        return TYPE_RESOURCE_NOTICE;
    }

    @Override
    public int type(RechargeDetail rechargeDetail) {
        return TYPE_RESOURCE_RECHARGEDETAIL;
    }

    @Override
    public int type(WithdrawDetail withdrawDetail) {return  TYPE_RESOURCE_WITHDRAWDETAIL; }

    @Override
    public int type(TransferRecord transferRecord) {return  TYPE_RESOURCE_TRANSFERRECORD; }
    @Override
    public int type(Yeji yeji) {return  TYPE_RESOURCE_YEJI; }
    @Override
    public int type(FeedBack feedBack) {return  TYPE_RESOURCE_FEEDBACK; }
    @Override
    public int type(UsdtRecord usdtRecord) {return  TYPE_RESOURCE_USDT; }
    @Override
    public int type(Youlian youlian) {return  TYPE_RESOURCE_YOULIAN; }

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {

        if(TYPE_RESOURCE_PERSON == type){
            return new PersonViewHolder(itemView);
        }else if(TYPE_RESOURCE_ASSETDETAIL == type){
            return  new AssetDetailViewHolder(itemView);
        }else if (TYPE_RESOURCE_NOTICE == type){
            return  new NoticeViewHolder(itemView);
        }else if (TYPE_RESOURCE_RECHARGEDETAIL == type){
            return  new RechargeDetailViewHolder(itemView);
        }else if (TYPE_RESOURCE_WITHDRAWDETAIL == type){
            return  new WithdrawDetailViewHolder(itemView);
        }else if (TYPE_RESOURCE_TRANSFERRECORD == type){
            return  new TransferRecordHolder(itemView);
        }else if (TYPE_RESOURCE_YEJI == type){
            return  new YejiViewHolder(itemView);
        }else if (TYPE_RESOURCE_FEEDBACK == type){
            return  new FeedbackViewHolder(itemView);
        }else if (TYPE_RESOURCE_USDT == type){
            return  new UsdtRecordHolder(itemView);
        }else if (TYPE_RESOURCE_YOULIAN == type){
            return  new YoulianHolder(itemView);
        }

        return null;
    }
}
