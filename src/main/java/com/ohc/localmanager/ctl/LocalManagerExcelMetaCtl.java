package com.ohc.localmanager.ctl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.ohc.localmanager.svc.LocalManagerExcelMetaSvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 엑셀 카테고리 컨트롤러 (최초 진입점 - 카테고리 목록/생성)
 * @author 오현철
 * @date 2026.06.25
 */
@Controller
public class LocalManagerExcelMetaCtl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LocalManagerExcelMetaSvc excelMetaSvc;

    public LocalManagerExcelMetaCtl(LocalManagerExcelMetaSvc excelMetaSvc) {
        this.excelMetaSvc = excelMetaSvc;
    }

    /**
     * 엑셀 카테고리 목록 화면 (최초 진입점)
     */
    @GetMapping("/excel")
    public String getExcelList(Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel (GET)");
        logger.info("--- (PARAM)  none");
        logger.info("----------------------------------------");
        model.addAttribute("lists", excelMetaSvc.getExcelMetas());
        return "excel/excel";
    }

    /**
     * 엑셀 카테고리 신규 생성
     */
    @PostMapping("/excel/category/insert")
    public String insertCategory(
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "metaInfo", required = false) String metaInfo,
        @RequestParam(value = "createUser", required = false, defaultValue = "admin") String createUser,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/category/insert (POST)");
        logger.info("--- (PARAM) category: {}, metaInfo: {}, createUser: {}", category, metaInfo, createUser);
        int result = excelMetaSvc.addExcelMeta(category, metaInfo, createUser);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return "redirect:/excel";
    }

    /**
     * 엑셀 카테고리 삭제 (해당 카테고리의 데이터도 함께 삭제)
     */
    @PostMapping("/excel/category/delete")
    public String deleteCategory(
        @RequestParam(value = "category", required = false) String category,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/category/delete (POST)");
        logger.info("--- (PARAM) category: {}", category);
        int result = excelMetaSvc.deleteExcelMeta(category);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return "redirect:/excel";
    }

}
