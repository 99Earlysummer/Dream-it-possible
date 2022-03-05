package com.dream.framework.web.service;

import com.dream.common.core.domain.entity.Oauth2Client;
import com.dream.system.service.IOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
@Component
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private IOauth2Service oauth2Service;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        List<Oauth2Client> clients1 = oauth2Service.getOauth2ClientByClientId(clientId);
        if (clients1 == null || clients1.size() == 0) {
            throw new ClientRegistrationException("clientId无效");
        }
        Oauth2Client client = clients1.get(0);
        String clientSecretAfterEncoder = bCryptPasswordEncoder.encode(client.getClientSecret());
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(clientSecretAfterEncoder);
        clientDetails.setRegisteredRedirectUri(new HashSet<>(Arrays.asList(client.getRedirectUrl().split(","))));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().split(",")));
        clientDetails.setScope(Arrays.asList(client.getScope().split(",")));
        return clientDetails;
    }
}
