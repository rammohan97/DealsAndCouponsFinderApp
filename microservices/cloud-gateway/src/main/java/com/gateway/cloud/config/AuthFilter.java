package com.gateway.cloud.config;

import com.gateway.cloud.dto.UserDto;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange,chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw  new RuntimeException("NO AUth token found in reqest");
            }
           String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] authtoken = authHeader.split(" ");
            if(authtoken.length !=2 || !"Bearer".equals(authtoken[0])){
                throw  new RuntimeException("Incorrect AUth token format");
            }
            return  webClientBuilder.build()
                    .post()
                    .uri("http://AUTH-SERVICE/users/validateToken?token=" + authtoken[1])
                    .retrieve().bodyToMono(UserDto.class)
                    .map(userDto ->
                        {
                            exchange.getRequest().mutate().header("x-auth-user-id",
                            String.valueOf(userDto.getId()));
                            exchange.getResponse().getHeaders().set(HttpHeaders.AUTHORIZATION,userDto.getToken());
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public static class  Config {
        // empty class as I don't need any particular configuration
    }
}
