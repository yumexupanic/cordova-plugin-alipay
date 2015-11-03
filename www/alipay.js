
cordova.define("cordova/plugins/Alipay",function(require, exports, module) {
    
	var exec = require("cordova/exec");
    
	var Alipay = function() {};

	Alipay.prototype.pay = function(out_trade_no,subject,body,total_fee,successCallback, errorCallback, callbackUrl) {
    
		if (errorCallback == null) { errorCallback = function() {}}

		if (typeof errorCallback != "function" || typeof successCallback != "function" || !callbackUrl)  {
        
			errorCallback('参数错误！');
    
			return;
    
		}

		exec(successCallback, errorCallback, 'Alipay', 'pay', [{"out_trade_no":out_trade_no, "subject": subject,"body":body, "total_fee": total_fee, "callbackUrl": callbackUrl}]);

	};
	
    module.exports = new Alipay();

});

  
if(!window.plugins) window.plugins = {};

if (!window.plugins.Alipay) window.plugins.Alipay = cordova.require("cordova/plugins/Alipay");

 