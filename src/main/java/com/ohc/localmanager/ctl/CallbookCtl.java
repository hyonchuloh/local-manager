package com.ohc.localmanager.ctl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ohc.localmanager.svc.CallbookSvc;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 연락처 컨트롤러
 * @author 오현철
 * @date 2026.06.17
 */
@Controller
public class CallbookCtl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CallbookSvc callbookSvc;

    @GetMapping("/callbook")
    public String getCallbook(Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /callbook (GET)");
        logger.info("--- (PARAM)  none");
        logger.info("----------------------------------------");
        return "callbook";
    }

}
