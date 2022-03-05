package com.dream.blog.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dream.blog.domain.entity.FileList;
import com.dream.blog.domain.entity.Music;
import com.dream.blog.domain.dto.MusicQueryItem;
import com.dream.blog.service.FileService;
import com.dream.blog.service.MusicService;
import com.dream.common.config.DreamAppConfig;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.PageRecord;
import com.dream.common.utils.R;
import com.dream.common.utils.file.MimeTypeUtils;
import com.dream.common.utils.qiniu.QiniuUtil;
import com.dream.framework.config.ServerConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/blog")
public class MusicController extends BaseController {
	@Autowired
	private ServerConfig serverConfig;
	@Autowired
	private MusicService musicService;
	@Autowired
	QiniuUtil qiniuUtil;
	@Autowired
	FileService fileService;
	@PostMapping("/getMusicList")
	@ResponseBody
	public R getMusicList(@RequestBody MusicQueryItem query){
		PageHelper.startPage(query.getPageIndex(),query.getPageSize(),true);
		List<Music> musicList = musicService.getMusicList(query);
		PageInfo<Music> pageInfo = new PageInfo<Music>(musicList);
		long total= pageInfo.getTotal();
		int pages = pageInfo.getPages();
		PageRecord<Music> pageRecord = new PageRecord<Music>();
		pageRecord.setRows(musicList);
		pageRecord.setCurrentPage(query.getPageIndex());
		pageRecord.setCurrentPageSize(query.getPageSize());
		pageRecord.setTotalCount(total);
		pageRecord.setTotalPage(pages);
		return R.success().put("data", pageRecord);
	}
	@GetMapping("/getTopMusicList")
	@ResponseBody
	public R getMusicList(HttpServletRequest request){
		MusicQueryItem queryItem=new MusicQueryItem();
		queryItem.setPageSize(100);
		List<Music> musicList = musicService.getMusicList(queryItem);
		return R.success().put("data", musicList);
	}
	@RequestMapping("/music/getMusicDetail/{musicId}")
	public R getMusicDetail(@PathVariable("musicId")int musicId) {
		Music musicItem=musicService.getById(musicId);
		return R.success().put("data",musicItem);
	}
	@RequestMapping("/music/saveMusic")
	@ResponseBody
	public R saveMusic(@RequestBody Music submitItem) {
		boolean result=false; 
		try {
			if(submitItem.getId()==0){
				submitItem.setCreateOn(DateUtils.getNowDate());
				submitItem.setUpdateOn(DateUtils.getNowDate());;
				result=musicService.saveMusic(submitItem);
			}
			else {
				submitItem.setUpdateOn(DateUtils.getNowDate());
				result=musicService.updateMusic(submitItem);
			}
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if(result){
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	@RequestMapping("/music/batchSaveMusic")
	@ResponseBody
	public R batchSaveMusic(@RequestBody List<Music> submitItem) {
		boolean result=false; 
		try {
			for (Music music : submitItem) {
				music.setCreateOn(DateUtils.getNowDate());
				music.setUpdateOn(DateUtils.getNowDate());
				music.setSortCode(0);
				result=musicService.saveMusic(music);
			}
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
			result=false;
		}
		if(result){
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	@PostMapping("/music/upload")
	@ResponseBody
	public HashMap<String, Object> upload(HttpServletRequest req, HttpServletResponse response) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		List<HashMap<String, Object>> fre = new ArrayList<HashMap<String, Object>>();
		List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
		// 上传文件路径
		String filePath = DreamAppConfig.getBlogUploadPath();
		try {
			for (MultipartFile file : files) {
				// 上传并返回新文件名称
				String returnUrl = qiniuUtil.uploadFile(filePath, file);//允許所有類型的文件
				String url = "http://"+returnUrl;
				String fileName=file.getOriginalFilename();
				HashMap<String, Object> fileResult = new HashMap<>();
				if (StringUtils.isNotBlank(url)) {
					fileResult.put("msg", "上传成功");
					fileResult.put("name", fileName);
					fileResult.put("status", 0);
					fileResult.put("url", url);
					fre.add(fileResult);
					//追加文件记录表
					String[] arr=fileName.split("\\.");
					FileList submitItem=new FileList();
					submitItem.setFsize(String.valueOf(file.getSize()));
					submitItem.setKey(arr[0]);
					submitItem.setHash(fileName);
					submitItem.setSuffix(arr[1]);
					String mimeType= MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
					submitItem.setMimeType(mimeType);
					submitItem.setScope("blog");
					submitItem.setUrl(url);

					fileService.saveFile(submitItem);
					Music music=new Music();
					music.setUrl(url);
					music.setTitle(fileName);
					music.setCreateOn(DateUtils.getNowDate());
					music.setUpdateOn(DateUtils.getNowDate());
					music.setSortCode(0);
					musicService.saveMusic(music);
				}
			}
			response.setHeader("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
			res.put("success", 1);
			res.put("msg", "ok");
			res.put("result", fre);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return res;
	}
	@RequestMapping("/music/deleteMusic/{musicId}")
	@ResponseBody
	public R deleteMusic(@PathVariable int musicId) {
		boolean result = false;
		try {
			result = musicService.deleteMusic(musicId);
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if (result) {
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	private final String prefixUrl = "http://music.163.com/api/playlist/detail?id=";
	private final String playUrl = "http://music.163.com/song/media/outer/url?id=";

	@GetMapping("/getPlayList")
	public R getPlayList(String listId) throws IOException {
		//拼接完整的url
		String lastUrl = prefixUrl + listId;
		//发起http请求获取歌单信息
		URL url = new URL(lastUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		String result = getResponse(conn);
		JSONArray arr = JSON.parseObject(result).getJSONObject("result").getJSONArray("tracks");
		List<Music> list = getAllMusic(arr);
		return R.success().put("data",list);
	}
	@GetMapping("/getWyCloudPlayList")
	public R getPlayList() throws IOException, InterruptedException {
		//拼接完整的url
		String lastUrl = prefixUrl + 523283787;
		List<Music> list =new ArrayList<>();

		try {
			//发起http请求获取歌单信息
			URL url = new URL(lastUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String result = getResponse(conn);
			if(result==null){
				Thread.sleep(1000);
				result = getResponse(conn);
			}
			if(result!=null){
				JSONArray arr = JSON.parseObject(result).getJSONObject("result").getJSONArray("tracks");
				list = getAllMusic(arr);
				return R.success().put("data",list);
			}
		}catch (Exception ex){
			return R.error().put("msg","获取歌单失败");
		}
		return R.success().put("data",list);
	}
	public String getResponse(HttpURLConnection conn) throws IOException {
		//设置属性
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon;)");
		conn.setRequestProperty("cookie", "appver=1.5.0.75771");
		conn.setRequestProperty("referer", "http://m1.music.126.net/");
		//开启连接
		conn.connect();
		//获取响应
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		//关闭流
		br.close();
		//关闭连接
		conn.disconnect();
		return sb.toString();
	}

	public List<Music> getAllMusic(JSONArray arr) {
		List<Music> list = new ArrayList<>();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			Music music = new Music();
			music.setTitle(obj.getString("name"));
			music.setUrl(playUrl + obj.getString("id") + ".mp3");
			music.setSinger(obj.getJSONArray("artists").getJSONObject(0).getString("name"));
			music.setConverUrl(obj.getJSONObject("album").getString("blurPicUrl"));
			list.add(music);
		}
		return list;
	}
}