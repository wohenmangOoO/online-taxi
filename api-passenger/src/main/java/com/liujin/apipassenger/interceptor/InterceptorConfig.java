package com.liujin.apipassenger.interceptor;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configurable
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/verification-code");
        list.add("/verification-code-check");
        list.add("/autotest");
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")

                .excludePathPatterns(list);

    }
}
