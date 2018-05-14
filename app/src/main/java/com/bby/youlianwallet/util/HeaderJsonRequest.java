package com.bby.youlianwallet.util;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanyy on 2018/4/4.
 */

public class HeaderJsonRequest extends JsonObjectRequest {

    public HeaderJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    // 重写头信息，为了服务器授权
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization","");
        //如果已经登录，追加头信息
//        if(LoginHelper.isLogin())
//        {
//            headers.put(Config.HEADER_LOGIN_KEY, MyApplication.gUserID+","+MyApplication.gUserToken);
//        }

        return headers;
    }
//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("Authorization","");
//        return params;
//    }
}
