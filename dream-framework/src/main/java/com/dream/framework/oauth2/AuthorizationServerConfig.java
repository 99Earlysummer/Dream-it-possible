package com.dream.framework.oauth2;

import com.dream.framework.web.service.ClientDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Slf4j
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);
    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;
    @Autowired(required = false)
    private TokenStore redisTokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("创建两个客户端,为这两个客户端发送授权，或者通过配置文件配置");
        clients.withClientDetails(clientDetailsService);
    }

    //生成令牌
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(redisTokenStore);
    }
}
