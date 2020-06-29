package com.coolsoft.yongle.gateway.filter.factory;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class AuthTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthTokenGatewayFilterFactory.Config> {

    private static final String AUTH_TOKEN_BEGIN = "authToken";
    private static final String KEY = "withParams";

    @Autowired
    RedisTemplate redisTemplate;

    public AuthTokenGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isWithParams()) {
                HttpRequest httpRequest = exchange.getRequest();
                String token = httpRequest.getHeaders().getFirst("token");
                if (!StringUtils.isBlank(token)) {
                    String session = "sess_" + token;
                    if (redisTemplate.hasKey(session)) {
                        String userJson = (String) redisTemplate.opsForValue().get(session);
                        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().header("user", userJson).build();
                        exchange = exchange.mutate().request(serverHttpRequest).build();
                        return chain.filter(exchange);
                    } else {
                        JSONObject message = new JSONObject();
                        message.put("status", 1002);
                        message.put("data", "illegal token");
                        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bits);
                        return exchange.getResponse().writeWith(Mono.just(buffer));
                    }
                } else {
                    JSONObject message = new JSONObject();
                    message.put("status", 1001);
                    message.put("data", "用户登录未登录");
                    byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bits);
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                }
            } else {
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {
        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
