package com.bby.youlianwallet.base;

/**
 * Created by fanyy on 2018/4/8.
 */

public class RefreshFlag {
    private static boolean assetRefresh = false;

    public static boolean isAssetRefresh() {
        return assetRefresh;
    }

    public static void setAssetRefresh(boolean assetRefresh) {
        RefreshFlag.assetRefresh = assetRefresh;
    }
}
