package com.dream.blog.service.impl;

import com.dream.blog.domain.entity.Partner;
import com.dream.blog.mapper.PartnerMapper;
import com.dream.blog.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerMapper partnerMapper;
	@Override
	public List<Partner> getAll() {
		return partnerMapper.getAll();
	}

	@Override
	public int savePartner(Partner submitItem) {
		return partnerMapper.savePartner(submitItem);
	}

	@Override
	public boolean updatePartner(Partner submitItem) {
		return partnerMapper.updatePartner(submitItem);
	}

	@Override
	public boolean deletePartner(int PartnerId) {
		return partnerMapper.deletePartner(PartnerId);
	}

	
}
