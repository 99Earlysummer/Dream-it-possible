package com.dream.blog.domain.dto;

import com.dream.blog.domain.entity.Resume;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResumeSubmitItem extends Resume {

	public void initRegionItem() {
		if(this.getRegionItem()!=null&&this.getRegionItem().size()>0){
			String provinceCode = regionItem.get(0).get("code");
			String provinceName = regionItem.get(0).get("name");
			String cityCode = regionItem.get(1).get("code");
			String cityName = regionItem.get(1).get("name");
			String countyCode = regionItem.get(2).get("code");
			String countyName = regionItem.get(2).get("name");
			this.setProvince(provinceName);
			this.setProvinceCode(provinceCode);
			this.setCity(cityName);
			this.setCityCode(cityCode);
			this.setCountyCode(countyCode);
			this.setCounty(countyName);
		}
	}
	private List<Map<String,String>> regionItem;
	
}
