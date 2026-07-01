package com.ohc.localmanager.ctl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JSP Controller 예제 */
@Controller
class JspController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());	

    /**
     * JSP 뷰 렌더링 예제 - 모델에 메시지를 담아 hello 뷰 반환
     */
    @GetMapping("/api/jsp")
    public String helloJsp(Model model) {
        logger.info("----------------------------------------");
        logger.info("--- URL : /api/jsp");
        logger.info("--- PARAM : none");
        model.addAttribute("message", "Hello from JSP!");
        logger.info("----------------------------------------");
        return "hello";
    }
}

/* Rest API 예제 */
@RestController
@RequestMapping("/api")
class ApiHelloController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());	

    /**
     * REST API 예제 - JSON 형태의 인사 메시지 반환
     */
    @GetMapping("/hello")
    public Map<String, String> hello() {
        logger.info("----------------------------------------");
        logger.info("--- URL(REST) : /api/hello");
        logger.info("--- PARAM : none");
        logger.info("----------------------------------------");
        return Map.of("message", "Hello, Spring Boot!");
    }
}