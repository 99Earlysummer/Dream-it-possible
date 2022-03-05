package com.dream.blog.controller;

import com.dream.blog.domain.entity.Tag;
import com.dream.blog.domain.dto.TagItem;
import com.dream.blog.service.TagService;
import com.dream.common.utils.R;
import com.dream.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class TagController extends BaseController {
	@Autowired
	private TagService tagService;

	@GetMapping("/tag/getTagList")
	@ResponseBody
	public R getTagList() {
		List<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
		List<Tag> tagList = tagService.getAll();
		return getR(maps, tagList);
	}
	@RequestMapping("/tag/getTagByIds/{tagIds}")
	@ResponseBody
	public R getTagByIds(@PathVariable String tagIds) {
		try {
			if(StringUtils.isBlank(tagIds)){
				return R.error("tagIds不能为空");
			}
			List<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
			String [] ids=tagIds.split(",");
			List<String> tags= Arrays.asList(ids);
			List<Tag> tagList = tagService.getTagByIds(tags);
			return getR(maps, tagList);
		}
		catch (Exception ex){
			log.error("操作失败:{0}", ex);
			log.error(ex.getMessage());
		}
		return R.error("获取失败");
	}

	private R getR(List<HashMap<String, String>> maps, List<Tag> tagList) {
		for (Tag tag : tagList) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("value", tag.getTagName());
			map.put("alia", tag.getAliasName());
			map.put("color", tag.getColor());
			map.put("id", tag.getId().toString());
			maps.add(map);
		}
		return R.success().put("data", maps);
	}

	@RequestMapping("/tag/saveTag")
	@ResponseBody
	public R saveTag(@RequestBody TagItem submitItem) {
		int id = 0;
		try {
			if (submitItem.getId() == 0) {
				tagService.saveTag(submitItem);
				id = submitItem.getId();
			} else {
				boolean result = tagService.updateTag(submitItem);
				id=result?submitItem.getId():0;
			}

		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
		}
		if (id>0) {
			R r = R.success();
			r.put("id", id);
			r.put("msg", "操作成功");
			return r;
		}
		return R.error("操作失败");
	}

	@RequestMapping("/tag/deleteTag/{tagId}")
	@ResponseBody
	public R deleteTag(@PathVariable int tagId) {

		boolean result = false;
		try {
			if (tagId > 0) {
				result = tagService.deleteTag(tagId);
			}
		} catch (Exception e) {
			log.error("操作失败:{0}", e);
			log.error(e.getMessage());
			result = false;
		}
		if (result) {
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
}