package api.RESTful;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import api.utils.Base64CoderC;
import api.utils.HttpUtil;

public class RestAPI {

	// 网站申请的密钥对
	private final static String accessKey = "";
	private final static String secretKey = "";
	
	private final static String URL = "https://api.bision.com";
	
	// 交易市场配置
	@Test
	public void getMarketConfig() {
		String text = HttpUtil.get(URL + "/data/api/v1/getMarketConfig");
		System.out.println(text);
	}
	
	// K线数据
	@Test
	public void getKLine() {
		String text = HttpUtil.get(URL + "/data/api/v1/getKLine?market=btc_usdt&type=1min&since=0");
		System.out.println(text);
	}
	
	// 聚合行情（Ticker）
	@Test
	public void getTicker() {
		String text = HttpUtil.get(URL + "/data/api/v1/getTicker?market=btc_usdt");
		System.out.println(text);
	}
	
	// 所有市场的最新 All Tickers
	@Test
	public void getTickers() {
		String text = HttpUtil.get(URL + "/data/api/v1/getTickers");
		System.out.println(text);
	}
	
	// 市场深度数据 Depth
	@Test
	public void getDepth() {
		String text = HttpUtil.get(URL + "/data/api/v1/getDepth?market=btc_usdt");
		System.out.println(text);
	}
	
	// 最近市场成交记录 Trades
	@Test
	public void getTrades() {
		String text = HttpUtil.get(URL + "/data/api/v1/getTrades?market=btc_usdt");
		System.out.println(text);
	}
	
	// 获取账户资产
	@Test
	public void getBalance() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		
		String text = HttpUtil.get(URL + "/trade/api/v1/getBalance", map);
		System.out.println(text);
	}
	
	// 委托
	@Test
	public void order() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		map.put("price", "10000.12");
		map.put("number", "1.23");
		map.put("type", 1);		// 0.sell 1.buy
		map.put("entrustType", 0);	// 0.Limited price  1.Market price matching
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		// 
		String text = HttpUtil.get(URL + "/trade/api/v1/order", map);
		System.out.println(text);
	}
	
	// 批量委托
	@Test
	public void batchOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		
		JSONArray array = new JSONArray();
		for(int i = 0; i < 10; i++) {
			JSONObject bid = new JSONObject();
			bid.put("price", "10000.123");
			bid.put("amount", "0.1");
			bid.put("type", 1);
			array.add(bid);
			JSONObject ask = new JSONObject();
			ask.put("price", "10001.123");
			ask.put("amount", "0.1");
			ask.put("type", 0);
			array.add(ask);
		}
		// put data
		String data = Base64CoderC.encode(array.toJSONString());
		
		map.put("data", data);
		
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		// 
		String text = HttpUtil.get(URL + "/trade/api/v1/batchOrder", map);
		System.out.println(text);
	}
	
	// 撤单
	@Test
	public void cancel() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		map.put("id", "156387346384491");
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		
		String text = HttpUtil.get(URL + "/trade/api/v1/cancel", map);
		System.out.println(text);
	}
	
	// 批量撤单
	@Test
	public void batchCancel() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		
		JSONArray array = new JSONArray();
		array.add("156387392184493");
		array.add("156387392184494");
		array.add("156387392184495");
		array.add("156387392184496");
		array.add("156387392184497");
		
		// put data
		String data = Base64CoderC.encode(array.toJSONString());
		
		map.put("data", data);
		
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		// 
		String text = HttpUtil.get(URL + "/trade/api/v1/batchCancel", map);
		System.out.println(text);
	}
	
	// 获取订单
	@Test
	public void getOrder() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		map.put("id", "156387346384491");
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		
		String text = HttpUtil.get(URL + "/trade/api/v1/getOrder", map);
		System.out.println(text);
	}
	
	// 获取未完成的订单
	@Test
	public void getOpenOrders() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		map.put("page", 1);
		map.put("pageSize", 10);
		
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		
		String text = HttpUtil.get(URL + "/trade/api/v1/getOpenOrders", map);
		System.out.println(text);
	}
	
	// 批量获取订单
	@Test
	public void getBatchOrders() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accesskey", accessKey);
		map.put("nonce", System.currentTimeMillis());
		map.put("market", "btc_usdt");
		
		JSONArray array = new JSONArray();
		array.add("156387392184493");
		array.add("156387392184494");
		array.add("156387392184495");
		array.add("156387392184496");
		array.add("156387392184497");
		
		// put data
		String data = Base64CoderC.encode(array.toJSONString());
		
		map.put("data", data);
		// 签名
		String signature = HttpUtil.getSignature(map, secretKey);
		map.put("signature", signature);
		
		String text = HttpUtil.get(URL + "/trade/api/v1/getBatchOrders", map);
		System.out.println(text);
	}
	
	// To be continued...
}
