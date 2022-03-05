package com.dream.blog.controller;

import com.dream.blog.domain.entity.Category;
import com.dream.blog.domain.dto.CategorySubmitItem;
import com.dream.blog.service.CategoryService;
import com.dream.common.utils.DateUtils;
import com.dream.common.utils.R;
import com.dream.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class CategoryController extends BaseController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/getCategoryData")
	@ResponseBody
	public R getCategoryData() {
		List<Category> categoryList = categoryService.getCategoryList(0);
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> cateModelDefault  = new HashMap<String, Object>();
		cateModelDefault.put("id", 0);
		cateModelDefault.put("code", "");
		cateModelDefault.put("name", "全部");
		maps.add(cateModelDefault);
		for (Category cate : categoryList) {
			HashMap<String, Object> cateModel = new HashMap<String, Object>();
			cateModel.put("id", cate.getId().toString());
			cateModel.put("code", cate.getCategoryCode());
			cateModel.put("name", cate.getCategoryName());
			maps.add(cateModel);
		}
		return R.success().put("data", maps);
	}
	@RequestMapping("/getCategoryList/{parentId}")
	@ResponseBody
	public R getCategoryList(@PathVariable int parentId) {
		List<Category> categoryList = categoryService.getCategoryList(parentId);
		List<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
		for (Category cate : categoryList) {
			HashMap<String, Object> cateModel = new HashMap<String, Object>();
			cateModel.put("id", cate.getId().toString());
			cateModel.put("code", cate.getCategoryCode());
			cateModel.put("name", cate.getCategoryName());
			cateModel.put("sort", cate.getSort());
			cateModel.put("level", cate.getLevel().toString());
			cateModel.put("isLeaf", cate.getIsParent() == 0);
			cateModel.put("children", new ArrayList<HashMap<String, Object>>());
			maps.add(cateModel);
		}
		return R.success().put("data", maps);
	}
	@RequestMapping("/getCategoryTreeData/{parentId}")
	@ResponseBody
	public R getCategoryTreeData(@PathVariable int parentId) {
		List<Category> data = categoryService.getAll();
		List<HashMap<String, Object>> maps = getTree(parentId,data);
		return R.success().put("data", maps);
	}
	private List<HashMap<String, Object>> getTree(int parentId,List<Category> nodelList) {
		List<HashMap<String, Object>> maps=new ArrayList<HashMap<String, Object>>();
		Iterator<Category> treeList=nodelList.stream().filter(m->m.getParentId()==parentId).iterator();
		 while (treeList.hasNext()) {  
			 Category cate = (Category) treeList.next();  
			 HashMap<String, Object> cateModel = new HashMap<String, Object>();
				cateModel.put("id", cate.getId().toString());
				cateModel.put("code", cate.getCategoryCode());
				//cateModel.put("value", cate.getcategoryCode());
				cateModel.put("name", cate.getCategoryName());
			 	cateModel.put("label", cate.getCategoryName());
				cateModel.put("sort", cate.getSort());
				cateModel.put("level", cate.getLevel().toString());
				cateModel.put("parentId", cate.getParentId().toString());
				List<HashMap<String, Object>> childrenList=getTree( cate.getId(),nodelList);
				if(!childrenList.isEmpty())
				{
					cateModel.put("children",childrenList);
				}
			 	cateModel.put("isLeaf", childrenList.isEmpty());
				maps.add(cateModel);
         }  
		return maps;
	}
	@PostMapping("/category/saveCategory")
	@ResponseBody
	public R saveCategory(@RequestBody CategorySubmitItem submitItem) {
		boolean result=false;
		Integer cateId=0;
		try {
			if(submitItem.getId()==0){
				submitItem.setStatus(1);
				submitItem.setCreateon(DateUtils.getDate());
				submitItem.setUpdateon(DateUtils.getDate());
				categoryService.saveCategory(submitItem);
				cateId=submitItem.getId();
				String code = String.format("%04d", cateId);
				if(StringUtils.isEmpty(submitItem.getParentcode())){
					code=submitItem.getParentcode()+code;
				}
				result=categoryService.updateCategoryCode(cateId,code);
			}else {
				submitItem.setUpdateon(DateUtils.getDate());
				result=categoryService.updateCategory(submitItem);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(result)
		{
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
	@RequestMapping("/category/deleteCategory/{id}")
	@ResponseBody
	public R deleteCategory(@PathVariable int id) {
		boolean result=false;
		try {
			result=categoryService.deleteCategoryById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		if(result)
		{
			return R.success("操作成功");
		}
		return R.error("操作失败");
	}
}