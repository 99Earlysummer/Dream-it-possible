package com.dream.system.service;

import com.dream.common.core.domain.entity.Oauth2Client;

import java.util.List;

public interface IOauth2Service {
    List<Oauth2Client> getOauth2ClientByClientId(String clientId);
}
