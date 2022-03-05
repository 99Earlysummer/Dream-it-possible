package com.dream.blog.mapper;


import com.dream.blog.domain.entity.Partner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerMapper {
    List<Partner> getAll();
    int savePartner(Partner submitItem);
	boolean updatePartner(Partner submitItem);
	boolean deletePartner(@Param("partnerId") int partnerId);
}
