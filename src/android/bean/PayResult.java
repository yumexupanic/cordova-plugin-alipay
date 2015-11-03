package com.yumemor.cordova.bean;

/**
 * 支付结果
 * 
 * @author yy
 * 
 */
public class PayResult {
	
	/**
	 * 返回状态
	 */
	private String resultStatus;
	
	/**
	 * 结果 
	 */
	private String result;
	
	/**
	 * 备忘录
	 */
	private String memo;

	public PayResult(String rawResult) {
		
		String[] resultParams = rawResult.split(";");
		
		for (String resultParam : resultParams) {
		
			if (resultParam.startsWith("resultStatus")) {
			
				resultStatus = gatValue(resultParam, "resultStatus");
				
				continue;
				
			}
			
			if (resultParam.startsWith("result")) {

				result = gatValue(resultParam, "result");
			
				continue;
				
			}
			
			if (resultParam.startsWith("memo")) {
			
				memo = gatValue(resultParam, "memo");
				
				continue;
				
			}
		}
	}
	
	@Override
	public String toString() {
		
		return String.format("{'resultStatus':%s,'result':%s,'memo':%s}",resultStatus , result , memo);
	
	}

	private String gatValue(String content, String key) {
		
		String prefix = key + "={";
		
		return content.substring(content.indexOf(prefix) + prefix.length() , content.lastIndexOf("}"));
	}
	
}
