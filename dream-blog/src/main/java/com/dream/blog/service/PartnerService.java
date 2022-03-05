package com.dream.blog.service;

import com.dream.blog.domain.entity.Partner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerService {
	List<Partner> getAll();
    int savePartner(Partner submitItem);
	boolean updatePartner(Partner submitItem);
	boolean deletePartner(@Param("partnerId") int partnerId);
}
