package com.yumemor.cordova.alipay;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yumemor.cordova.util.SignUtils;

/**
 * 
 * 基于Cordova实现的支付宝插件
 * 
 * @author yy
 *
 */
public class AlipayPlugin extends CordovaPlugin {

	public boolean execute(String action , JSONArray args , CallbackContext callbackContext) {

		if (action.equals("pay")) {	// 支付动作
			
			try {
				
				if(args.isNull(0)){
					
					callbackContext.error("无效的参数");
					
					return false;
					
				}else{
					
					JSONObject json = args.optJSONObject(0);
					
					String out_trade_no = json.optString("out_trade_no");	//订单号
					
					String subject = json.optString("subject");	//商品名称
					
					String body = json.optString("body");	//商品内容
					
					String total_fee = json.optString("total_fee");	//金额
					
					String url = json.optString("callbackUrl");	//回调地址

					String orderInfo = getOrderInfo(out_trade_no , subject,body , total_fee , url);

					String sign = sign(orderInfo);	//签名
					
					try {
						
						sign = URLEncoder.encode(sign, "UTF-8"); //URL编码
						
					} catch (UnsupportedEncodingException e) {
						
						callbackContext.error("签名错误！");
						
						return false;
					}
					
					String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
					
					PayRun payRun = new PayRun(cordova.getActivity(),payInfo,callbackContext);

					Thread payThread = new Thread(payRun);
					
					payThread.start();	//启动线程进行支付
					
				}
				
				return true;
				
			} catch (Exception ex) {
				
				callbackContext.error("支付失败！");
				
				return false;
			}

		} else {

			callbackContext.error("无效的Action");
			
			return false;
		}

	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String out_trade_no, String subject,String body, String price, String url) {
		
		StringBuffer sb = new StringBuffer();
		
		// 合作者身份ID
		sb.append("partner=" + "\"" + PayKeys.DEFAULT_PARTNER + "\"");

		// 卖家支付宝账号
		sb.append("&seller_id=" + "\"" + PayKeys.DEFAULT_SELLER + "\"");

		// 商户网站唯一订单号
		sb.append("&out_trade_no=" + "\"" + out_trade_no + "\"");

		// 商品名称
		sb.append("&subject=" + "\"" + subject + "\"");

		// 商品详情
		sb.append("&body=" + "\"" + body + "\"");

		// 商品金额
		sb.append("&total_fee=" + "\"" + price + "\"");

		// 服务器异步通知页面路径 //服务器异步通知页面路径 参数 notify_url，如果商户没设定，则不会进行该操作
		sb.append("&notify_url=" + "\"" + url + "\"");

		// 接口名称， 固定值
		sb.append("&service=\"mobile.securitypay.pay\"");

		// 支付类型， 固定值
		sb.append("&payment_type=\"1\"");

		// 参数编码， 固定值
		sb.append("&_input_charset=\"utf-8\"");

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		sb.append("&it_b_pay=\"30m\"");

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		sb.append("&return_url=\"m.alipay.com\"");

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return sb.toString();
		
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, PayKeys.PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}