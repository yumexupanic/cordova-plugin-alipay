package com.yumemor.cordova.alipay;

import org.apache.cordova.CallbackContext;
import org.json.JSONObject;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.yumemor.cordova.bean.PayResult;

/**
 * 支付线程
 * 
 * @author yy
 * 
 */
public class PayRun implements Runnable {

	/**
	 * cordova.getActivity()
	 */
	private Activity cordovaActivity;
	
	/**
	 * cordova的回调
	 */
	private CallbackContext callbackContext;
	
	/**
	 * 支付信息
	 */
	private String payInfo;

	public PayRun(Activity cordovaActivity , String payInfo , CallbackContext callbackContext) {
		
		this.callbackContext = callbackContext;
		
		this.cordovaActivity = cordovaActivity;
		
		this.payInfo = payInfo;
	}
	
	@Override
	public void run() {
		
		PayTask alipay = new PayTask(cordovaActivity);
		
		String result = alipay.pay(payInfo); //支付
	
		callbackContext.success(new PayResult(result).toString()); //返回支付结果
		
	}

}
