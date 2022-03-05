package com.dream.blog.service.impl;

import com.dream.blog.domain.dto.BannerItem;
import com.dream.blog.domain.dto.BannerQueryItem;
import com.dream.blog.domain.dto.BannerSubmitItem;
import com.dream.blog.mapper.BannerMapper;
import com.dream.blog.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

	@Override
	public List<BannerItem> getBannerList(BannerQueryItem queryItem) {
		
		List<BannerItem> list=bannerMapper.getBannerList(queryItem);
		int i=1;
		for (BannerItem bannerItem : list) {
			int num=(queryItem.getPageIndex()-1)*queryItem.getPageSize()+i;
			bannerItem.setSerialNumber(num);
			i++;
		}
		return list;
	}

	
	@Override
	public BannerItem getById(int bannerId) {
	
		return bannerMapper.getById(bannerId);
	}

	@Override
	public boolean saveBanner(BannerSubmitItem submitItem) {
		return bannerMapper.saveBanner(submitItem);
	}

	@Override
	public boolean updateBanner(BannerSubmitItem submitItem) {
		return bannerMapper.updateBanner(submitItem);
	}

	@Override
	public boolean deleteBanner(int bannerId) {
		return bannerMapper.deleteBanner(bannerId);
	}
	
}
