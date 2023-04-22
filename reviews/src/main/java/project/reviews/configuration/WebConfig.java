package project.reviews.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.reviews.login.LoginCheckInterceptor;

/*
* 2022-10-18
* LoginCheckInterceptor 설정
* WebMvcConfigurer가 제공하는 addInterceptors를 사용해서 인터셉터를 등록할 수 있다.
* */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/community/**", "/memberInfo/**")
                .excludePathPatterns("/community/list", "/community/read/*");
    }
}