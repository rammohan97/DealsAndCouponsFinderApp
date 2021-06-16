package com.gateway.cloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {
    @RequestMapping("/orderFallBack")
    public Mono<String> orderServiceFallBack(){
        return Mono.just("Order service is taking too long to respond or down. Please try gaian ");
    }
    @RequestMapping("/productsFallBack")
    public Mono<String> productsServiceFallBack(){
        return Mono.just("products service is taking too long to respond or down. Please try gaian ");
    }
    @RequestMapping("/couponFallBack")
    public Mono<String> couponServiceFallBack(){
        return Mono.just("coupons service is taking too long to respond or down. Please try gaian ");
    }
}
