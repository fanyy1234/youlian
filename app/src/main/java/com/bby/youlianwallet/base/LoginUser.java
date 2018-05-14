package com.bby.youlianwallet.base;

public class LoginUser {
	private static String loginPhone = "";
	private static String nickName = "" ;
	private static String token = "";
	private static String recommendCode = "";
	private static long id = 0l;

	public static void initLoginUser(){
		LoginUser.loginPhone = "";
		LoginUser.nickName="";
		LoginUser.token="";
		LoginUser.recommendCode="";
		LoginUser.id=0l;
	}

	public static String getLoginPhone() {
		return MyApplication.getUserInfo("mobile");
	}

	public static void setLoginPhone(String loginPhone) {
		LoginUser.loginPhone = loginPhone;
	}

	public static String getNickName() {
		return MyApplication.getUserInfo("username");
	}

	public static void setNickName(String nickName) {
		LoginUser.nickName = nickName;
	}

	public static String getToken() {
		return MyApplication.getToken();
	}

	public static void setToken(String token) {
		LoginUser.token = token;
	}

	public static String getRecommendCode() {
		return MyApplication.getUserInfo("recommendedCode");
	}

	public static void setRecommendCode(String recommendCode) {
		LoginUser.recommendCode = recommendCode;
	}

	public static long getId() {
		return id;
	}

	public static void setId(long id) {
		LoginUser.id = id;
	}

	public static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回
		return new String(resultCharArray);
	}

}
