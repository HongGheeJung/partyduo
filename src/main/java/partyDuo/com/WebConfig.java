package partyDuo.com;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // static 하위 모든 리소스를 서빙하기 위한 설정
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
