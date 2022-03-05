package com.dream.system.service.impl;

import com.dream.common.core.domain.entity.Oauth2Client;
import com.dream.system.mapper.Oauth2Mapper;
import com.dream.system.service.IOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Oauth2ServiceImpl implements IOauth2Service {
    @Autowired
    Oauth2Mapper oauth2Mapper;
    @Override
    public List<Oauth2Client> getOauth2ClientByClientId(String clientId) {
        return oauth2Mapper.getOauth2ClientByClientId(clientId);
    }
}
