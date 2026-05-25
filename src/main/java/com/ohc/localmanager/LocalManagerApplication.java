package com.ohc.localmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class LocalManagerApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(LocalManagerApplication.class);
    @Autowired
    private com.ohc.localmanager.svc.CallbookSvc callbookSvc;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(LocalManagerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(LocalManagerApplication.class, args);
    }

    /**
     * 초기 데이터베이스 설정이나 초기화 작업을 수행하는 메서드
     * Spring Boot 애플리케이션이 시작된 후에 자동으로 호출
     */
    @PostConstruct
    public void initDatabase() {
        logger.info("----------------------------------------");
        logger.info("--- (APPLICATION) LocalManagerApplication.initDatabase()");
        callbookSvc.initialize();
        logger.info("----------------------------------------");
    }
}
