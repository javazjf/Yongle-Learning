package com.coolsoft.yongle.user.basic.api.configuration;

import com.coolsoft.yongle.user.basic.api.fallback.UserApiFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceApiConfiguration {

    @Bean
    public UserApiFallback userApiFallback() {
        return new UserApiFallback();
    }
}
