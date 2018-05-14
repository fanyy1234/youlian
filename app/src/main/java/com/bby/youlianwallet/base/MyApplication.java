package com.bby.youlianwallet.base;

import java.text.DecimalFormat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.WindowManager;

public class MyApplication extends Application {

	public static RequestQueue queues;//volley框架的网络请求队列，在所有网络 请求的地方 都用到了这个 变量。
	public static int screenWidth;//屏幕宽度
	public static int screenHeight;//屏幕高度
	public static int textSize;
	public static Resources resources;
	public static DecimalFormat df;//规定double只保留小数点后两位
	public static Context context;
	public static int titleHeight;//页面title的高度
	public static int pageHeight;//屏幕高度出去title和状态栏后的高度
	public static Context appContext;
	@Override
	public void onCreate() {
		super.onCreate();

		appContext = getApplicationContext();
		df = new DecimalFormat("0.00");
		context = getApplicationContext();
		WindowManager wm = (WindowManager) getBaseContext().getSystemService(
				Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		
		titleHeight = getStatusBarHeight()+dip2px(getApplicationContext(), 46);
		pageHeight = screenHeight-titleHeight;
		
		resources = getResources();
		try {
			queues = Volley.newRequestQueue(this);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
	public int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    } 
	/** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 
	/** 
     * 获取手机状态栏高度 
     */  
	public int getStatusBarHeight() {
		  int result = 0;
		  int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		  if (resourceId > 0) {
		      result = getResources().getDimensionPixelSize(resourceId);
		  }
		  return result;
	}

	public static Context getAppContext(){
		return  appContext;
	}

	public static String getToken(){
		SharedPreferences sp = appContext.getSharedPreferences("login", Activity.MODE_PRIVATE);
		String token = sp.getString("token", "");
		return "Bearer "+token;
	}
	public static String getUserInfo(String keyName){
		SharedPreferences sp = appContext.getSharedPreferences("login", Activity.MODE_PRIVATE);
		String info = sp.getString(keyName, "");
		return info;
	}
	public static void exitApp(){
		SharedPreferences sp = appContext.getSharedPreferences("login", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}
