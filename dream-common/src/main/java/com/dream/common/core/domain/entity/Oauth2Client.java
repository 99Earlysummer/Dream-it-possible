package com.dream.common.core.domain.entity;

import lombok.Data;

@Data
public class Oauth2Client {
    private int id;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String grantType;
    private String scope;
}
