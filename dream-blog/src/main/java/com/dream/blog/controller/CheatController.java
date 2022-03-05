package com.dream.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.R;
import com.dream.common.utils.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class CheatController extends BaseController {
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static RequestConfig requestConfig;
	@GetMapping("/cheat/updateStep/{count}")
	@ResponseBody
	public R updateStep(@PathVariable int count) {
		try {
			Map<String,Object> userItem=login();
			String accessToken=userItem.get("accessToken").toString();
			String userId=userItem.get("userId").toString();
			Map<String,Object> result=updateData(userId,accessToken,count);
			System.out.println(result);
			return R.success();
		}
		catch (Exception ex){
			log.error("操作失败:{0}", ex);
			log.error(ex.getMessage());
		}
		return R.error("获取失败");
	}
	public Map<String,Object> login() throws Exception {
		Map<String,Object> result=new HashMap<>();
		String url="https://sports.lifesense.com/sessions_service/login?systemType=2&version=4.6.7";
		String jsonData="{\n" +
				"    \"loginName\": \"18813095686\",\n" +
				"    \"password\": \"5438805c815e6be485c84b32e58bdff6\",\n" +
				"    \"clientId\": \"49a41c9727ee49dda3b190dc907850cc\",\n" +
				"    \"roleType\": 0,\n" +
				"    \"appType\": 6\n" +
				"}";
		String jsonResult=doPostJson(url,jsonData,"");
		Map<String,Object> res= JSON.parseObject(jsonResult,new TypeReference<HashMap<String,Object>>() {});
		if(res.size()>0&&res.get("code").toString().equals("200")){
			result=JSON.parseObject(res.get("data").toString(),new TypeReference<HashMap<String,Object>>() {});
		}
		return result;
	}
	public Map<String,Object> updateData(String userId,String token,Integer totalStep) throws Exception {
		Map<String,Object> para=new HashMap<>();
		Map<String,Object> item=new HashMap<>();
		String url="https://sports.lifesense.com/sport_service/sport/sport/uploadMobileStepV2?systemType=2&version=4.6.7";
		item.put("DataSource",2);
		item.put("active",1);
		item.put("calories",2500);
		item.put("dataSource",2);
		item.put("deviceId","M_NULL");
		item.put("distance",totalStep/3);
		item.put("exerciseTime",0);
		item.put("isUpload",0);
		item.put("measurementTime", DateUtils.getDate());
		item.put("priority",0);
		item.put("step",totalStep);
		item.put("type",2);
		item.put("updated",DateUtils.getNowDate().getTime());
		item.put("userId",userId);
		List<Map<String,Object>> params=new ArrayList<>();
		params.add(item);
		para.put("list",params);
		String jsonData=JSON.toJSONString(para);
		String jsonResult=doPostJson(url,jsonData,token);
		item= JSON.parseObject(jsonResult,new TypeReference<HashMap<String,Object>>() {});
		return item;
	}
	public  String doPostJson(String url, String json,String token) throws Exception {
		final String APPLICATION_JSON = "application/json;charset=utf-8";
		final String CONTENT_TYPE_TEXT_JSON = "text/json";
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		//设置请求体为 字符串
		StringEntity stringEntity = new StringEntity(json, "UTF-8");
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("Content-Type",APPLICATION_JSON);
		httpPost.addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.1.2; LIO-AN00 Build/LIO-AN00)");
		if(StringUtils.isNotBlank(token)){
			httpPost.addHeader("Cookie",String.format("accessToken=%s",token));
		}
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			// 判断返回状态是否为200
			int statusCode = response.getStatusLine().getStatusCode();
			String newuri = "";
			if (statusCode == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			} else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
				newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
				System.out.println(newuri);
				httpPost = new HttpPost(newuri);
				httpPost.setHeader("Content-Type", APPLICATION_JSON);
				stringEntity.setContentType(CONTENT_TYPE_TEXT_JSON);
				response = httpClient.execute(httpPost);
				statusCode = response.getStatusLine().getStatusCode();
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
}