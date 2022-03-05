package com.dream.blog.controller;

import com.dream.blog.domain.entity.Partner;
import com.dream.blog.service.PartnerService;
import com.dream.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class PartnerController extends BaseController {
	@Autowired
	private PartnerService partnerService;

	@RequestMapping("/getPartnerList")
	@ResponseBody
	public R getPartnerList() {
		List<Partner> partnerList = partnerService.getAll();
		return R.success().put("data", partnerList);
	}

	@PostMapping("/partner/savePartner")
	@ResponseBody
	public R savePartner(@RequestBody Partner submitItem) {
		int id = 0;
		try {
			if (submitItem.getId()==0) {
				partnerService.savePartner(submitItem);
				id = submitItem.getId();
			} else {
				boolean result = partnerService.updatePartner(submitItem);
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

	@RequestMapping("/partner/deletePartner/{partnerId}")
	@ResponseBody
	public R deletePartner(@PathVariable int partnerId) {

		boolean result = false;
		try {
			if (partnerId > 0) {
				result = partnerService.deletePartner(partnerId);
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