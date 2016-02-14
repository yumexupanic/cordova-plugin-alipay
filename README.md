
## cordova集成支付宝插件

### 前言

插件编写于15年11月份，如果之后支付宝更换了支付方式，本插件会失效，在此之前可以正常使用

#### 说明
* SDK

	需要和支付宝签约`移动支付`，然后下载[SDK](http://download.alipay.com/public/api/base/WS_MOBILE_PAY_SDK_BASE.zip)，里面有Android、IOS提供了完整的文档，看需求选择业务需要的。

* 支持平台

	Android

#### 使用

在添加插件之前，需要配置与支付宝签约的帐号密匙

**yourProject\plugins\alipay\src\android\alipay\PayKeys.java**
	
	package com.yumemor.cordova.alipay;

	/**
	 * 商户配置信息
	 * 
	 * @author yy
	 * 
	 */
	public final class PayKeys {

		// 合作身份者id，以2088开头的16位纯数字
		public static final String DEFAULT_PARTNER = "";
	
		// 收款支付宝账号
		public static final String DEFAULT_SELLER = "";
	
		// 商户私钥 自助生成
		public static final String PRIVATE = "";
	
		// 支付宝默认公钥 请勿修改
		public static final String PUBLIC = "";

	}
	
**添加插件到你的项目**
	
	cordova plugins add cordova-plugin-alipay-pay

**js调用**

	window.plugins.Alipay.pay(out_trade_no,subject,body,total_fee,successCallback,errorCallback,callbackUrl);

**参数说明:**

* out_trade_no: 订单号(不能重复)
* subject: 商品名称
* body:	描述
* total_fee: 价格
* successCallback: 成功回调
* errorCallback: 失败回调
* callbackUrl: 回调地址/支付宝调用
	
当调用方法后如果安装了支付宝会调用支付宝进行支付，如果没有则用HTML5支付。

#### 截图

**支付宝:**
	
![image](http://7xnxsw.com1.z0.glb.clouddn.com/cordova-alipay-plugin/Screenshot_2015-11-02-14-08-50.png)

**HTML5:**
	
![image](http://7xnxsw.com1.z0.glb.clouddn.com/cordova-alipay-plugin/Screenshot_2015-11-02-14-09-23.png)

**回调:**
	
![image](http://7xnxsw.com1.z0.glb.clouddn.com/cordova-alipay-plugin/Screenshot_2015-11-02-14-09-03.png)

* 注意

	如果配置错了密匙，是不会打开支付界面的。
