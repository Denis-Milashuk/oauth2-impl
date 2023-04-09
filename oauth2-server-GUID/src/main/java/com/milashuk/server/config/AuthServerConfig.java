package com.milashuk.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("client_id")
                .secret("client_secret")
                .authorizedGrantTypes("password","authorization_code", "client_credentials", "refresh_token")
                .scopes("read", "info")
                .redirectUris("http://localhost:9090/home")
                .and()
                .withClient("resourceserver")
                .secret("resourceserversecret");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }

    /** Long configuration
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        var clientDetailsService = new InMemoryClientDetailsService();

        var clientDetails = new BaseClientDetails();
        clientDetails.setClientId("client_id");
        clientDetails.setClientSecret("client_secret");
        clientDetails.setScope(List.of("read"));
        clientDetails.setAuthorizedGrantTypes(List.of("password"));

        clientDetailsService.setClientDetailsStore(Map.of("client", clientDetails));

        clients.withClientDetails(clientDetailsService);
    }*/
}
