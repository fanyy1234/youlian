package com.bby.youlianwallet.util;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

public class StringUtil {

	public static SpannableStringBuilder changeColor(String styleString) {
		int bstart=0;
        int bend=styleString.indexOf(":")+1;
        SpannableStringBuilder style=new SpannableStringBuilder(styleString);
        style.setSpan(new ForegroundColorSpan(Color.RED),bstart,bend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
	}

	public static List<String> stringToList(String string){
		List<String> list = new ArrayList<>();
		if (string.contains("f,y,y")) {
			String[] arr = string.split("f,y,y");
			int length = arr.length;
			for (int i = 0; i < length; i++) {
				list.add(arr[i]);
			}
		}
		else {
			list.add(string);
		}
		return list;
	}
	
	public static String listToString(List<String> list){
		String string="";
		int size = list.size();
		if (size==0) {
			
		}
		else if(size==1){
			string = list.get(0);
		}
		else {
			for (int i = 0; i < size; i++) {
				if (i==0) {
					string = list.get(0);
				}
				else {
					string = string + "f,y,y" +list.get(i);
				}
			}
		}
		return string;
	}
	public static String listToString2(List<String> list){
		String string="";
		int size = list.size();
		if (size==0) {
			
		}
		else if(size==1){
			string = list.get(0);
		}
		else {
			for (int i = 0; i < size; i++) {
				if (i==0) {
					string = list.get(0);
				}
				else {
					string = string + "," +list.get(i);
				}
			}
		}
		return string;
	}
	public static String appendToString(String oldString,String newString){
		String string = "";
		if (oldString.equals("")) {
			string = newString ;
		}
		else if (oldString.contains(newString)) {
			List<String> list = stringToList(oldString);
			List<String> newList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).equals(newString)) {
					newList.add(list.get(i));
				}
			}
			newList.add(newString);
			string = listToString(newList);
		}
		else {
			string = oldString + "f,y,y" +newString;
		}
		return string;
	}
	
	public static Boolean isQiniuImg(String imgurl){
		if (imgurl.contains("img.eaehw.com")) {
			return true;
		}
		else{
			return false;
		}
	}
	//座机手机号验证
	public static boolean isPhoneNumberValid(String phoneNumber) {
//		boolean isValid = false;
//		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
//		CharSequence inputStr = phoneNumber;
//		Pattern pattern = Pattern.compile(expression);
//		Matcher matcher = pattern.matcher(inputStr);
//		if (matcher.matches()) {
//			isValid = true;
//		}
//		return isValid;
		
		if (phoneNumber.length()<7) {
			return false;
		}
		else {
			return true;
		}
	}
	//手机号验证
	public static boolean isMobileNO(String mobiles) {  
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        String telRegex = "[1][34578]\\d{9}" ;
        if (TextUtils.isEmpty(mobiles)){
        	return false ;  
        }
        else {
        	return mobiles.matches( telRegex ) ;  
        }
    }

    public static boolean allIsNotEmpty(String ... strings){
		for(String s : strings){
			if (s.equals("")){
				return false;
			}
		}
		return true;
	}
}
