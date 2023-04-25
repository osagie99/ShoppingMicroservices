package com.surge.CloudGateway.config;

import com.surge.CloudGateway.util.JwtUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
public class Security extends AbstractGatewayFilterFactory<Security.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtUtils jwtUtils;



    public Security() {
        super(Config.class);
    }



    @Override
    public GatewayFilter apply(Security.Config config) {
        return (((exchange, chain) -> {
            exchange.getAttributes().put("csrfEnabled", false);
//            http.build();
            if (routeValidator.isSecured.test(exchange.getRequest())) {
//                Check if header has token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Auth header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null || authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtils.validateToken(authHeader);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException("Unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        }));

    }

    public static class Config {
    }
}
