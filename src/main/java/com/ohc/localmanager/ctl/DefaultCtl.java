package com.ohc.localmanager.ctl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 기본 URL 처리를 위한 디폴트 컨트롤러
 */
@Controller
public class DefaultCtl {

    @GetMapping("/")
    public String index(Model model) {
        
        return "index";
    }
}
