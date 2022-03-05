package com.dream.blog.service;

import com.dream.blog.domain.dto.BannerItem;
import com.dream.blog.domain.dto.BannerQueryItem;
import com.dream.blog.domain.dto.BannerSubmitItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerService {
	List<BannerItem> getBannerList(BannerQueryItem queryItem);

	BannerItem getById(@Param("bannerId") int bannerId);

	boolean saveBanner(BannerSubmitItem submitItem);

	boolean updateBanner(BannerSubmitItem submitItem);

	boolean deleteBanner(@Param("bannerId") int bannerId);
}
