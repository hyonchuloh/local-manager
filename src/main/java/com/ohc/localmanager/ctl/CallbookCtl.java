package com.ohc.localmanager.ctl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ohc.localmanager.svc.CallbookSvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
        model.addAttribute("lists", callbookSvc.getCallbooks());
        return "callbook/callbook";
    }

    @PostMapping("/callbook/insert")
    public String insertCallbook(
        @RequestParam(value = "company", required = false) String company,
        @RequestParam(value = "department", required = false) String department,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "memo", required = false) String memo,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /callbook/insert (POST)");
        logger.info("--- (PARAM) company: {}, department: {}, name: {}, phoneNumber: {}, email: {}, memo: {}", company, department, name, phoneNumber, email, memo);
        int result = callbookSvc.addCallbook(company, department, name, phoneNumber, email, memo);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return "redirect:/callbook";
    }

    @PostMapping("/callbook/update/{id}")
    public String updateCallbook(   
        @PathVariable("id") int id,
        @RequestParam(value = "company", required = false) String company,
        @RequestParam(value = "department", required = false) String department,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "memo", required = false) String memo,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /callbook/update (POST)");
        logger.info("--- (PARAM) id: {}, company: {}, department: {}, name: {}, phoneNumber: {}, email: {}, memo: {}", id, company, department, name, phoneNumber, email, memo);
        int result = callbookSvc.updateCallbook(id, company, department, name, phoneNumber, email, memo);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return "redirect:/callbook";
    }

    @PostMapping("/callbook/delete/{id}")
    public String deleteCallbook(   
        @PathVariable("id") int id,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /callbook/delete (POST)");
        logger.info("--- (PARAM) id: {}", id);
        int result = callbookSvc.deleteCallbook(id);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return "redirect:/callbook";
    }

}
