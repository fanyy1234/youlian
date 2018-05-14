package com.bby.youlianwallet.type;

import android.view.View;

import com.bby.youlianwallet.holder.BaseViewHolder;
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

public interface TypeFactory {
    int type(Person person);
    int type(AssetDetail assetDetail);
    int type(Notice notice);
    int type(RechargeDetail rechargeDetail);
    int type(WithdrawDetail withdrawDetail);
    int type(TransferRecord transferRecord);
    int type(Yeji yeji);
    int type(FeedBack feedBack);
    int type(UsdtRecord usdtRecord);
    int type(Youlian youlian);

    BaseViewHolder createViewHolder(int type, View itemView);
}
