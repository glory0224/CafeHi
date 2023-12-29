package com.cafeHi.www.common.config;

import com.cafeHi.www.resources.repository.ResourcesRepository;
import com.cafeHi.www.resources.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 프로젝트에서 공통으로 사용하는 Bean 등록
 */
@Configuration
public class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository) {
        SecurityResourceService resourceService = new SecurityResourceService(resourcesRepository);
        return resourceService;
    }

}
