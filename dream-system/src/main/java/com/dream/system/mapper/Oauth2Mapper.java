package com.dream.system.mapper;

import com.dream.common.core.domain.entity.Oauth2Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface Oauth2Mapper {
    List<Oauth2Client> getOauth2ClientByClientId(@Param("clientId") String clientId);
}
