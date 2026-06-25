package com.ohc.localmanager.ctl;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohc.localmanager.svc.ExcelDataSvc;

/**
 * 엑셀 데이터 컨트롤러 (카테고리 선택 시 팝업 그리드 조회/CRUD)
 * @author 오현철
 * @date 2026.06.25
 */
@Controller
public class ExcelDataCtl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExcelDataSvc excelDataSvc;

    public ExcelDataCtl(ExcelDataSvc excelDataSvc) {
        this.excelDataSvc = excelDataSvc;
    }

    /**
     * 카테고리 그리드 화면 (팝업 진입 시 컬럼 + 행 데이터)
     */
    @GetMapping("/excel/data")
    public String getExcelData(
        @RequestParam(value = "category", required = false) String category,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/data (GET)");
        logger.info("--- (PARAM) category: {}", category);
        logger.info("----------------------------------------");
        model.addAttribute("grid", excelDataSvc.getExcelGrid(category));
        return "excel/excelData";
    }

    /**
     * 엑셀 데이터 추가
     */
    @PostMapping("/excel/data/insert")
    public String insertData(
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "data", required = false) String data,
        @RequestParam(value = "updateUser", required = false, defaultValue = "admin") String updateUser,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/data/insert (POST)");
        logger.info("--- (PARAM) category: {}, data: {}, updateUser: {}", category, data, updateUser);
        int result = excelDataSvc.addExcelData(category, data, updateUser);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return redirectToGrid(category);
    }

    /**
     * 엑셀 데이터 수정
     */
    @PostMapping("/excel/data/update/{seqNum}")
    public String updateData(
        @PathVariable("seqNum") int seqNum,
        @RequestParam(value = "category", required = false) String category,
        @RequestParam(value = "data", required = false) String data,
        @RequestParam(value = "updateUser", required = false, defaultValue = "admin") String updateUser,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/data/update (POST)");
        logger.info("--- (PARAM) seqNum: {}, category: {}, data: {}, updateUser: {}", seqNum, category, data, updateUser);
        int result = excelDataSvc.updateExcelData(seqNum, category, data, updateUser);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return redirectToGrid(category);
    }

    /**
     * 엑셀 데이터 삭제
     */
    @PostMapping("/excel/data/delete/{seqNum}")
    public String deleteData(
        @PathVariable("seqNum") int seqNum,
        @RequestParam(value = "category", required = false) String category,
        Model model) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/data/delete (POST)");
        logger.info("--- (PARAM) seqNum: {}, category: {}", seqNum, category);
        int result = excelDataSvc.deleteExcelData(seqNum);
        logger.info("--- (RESULT) {}", result);
        logger.info("----------------------------------------");
        return redirectToGrid(category);
    }

    /**
     * 현재 카테고리에 보이는 데이터를 엑셀(xlsx) 파일로 다운로드
     */
    @GetMapping("/excel/data/download")
    public ResponseEntity<byte[]> downloadExcel(@RequestParam(value = "category", required = false) String category) {
        logger.info("----------------------------------------");
        logger.info("--- (URL) /excel/data/download (GET)");
        logger.info("--- (PARAM) category: {}", category);
        byte[] file = excelDataSvc.buildExcelFile(category);
        // 한글 파일명은 RFC 5987 (filename*) 로 인코딩, 구형 브라우저용 ASCII fallback 병행
        String baseName = (category == null || category.isEmpty()) ? "excel" : category;
        String encoded = URLEncoder.encode(baseName + ".xlsx", StandardCharsets.UTF_8).replace("+", "%20");
        logger.info("--- (RESULT) {} bytes", file.length);
        logger.info("----------------------------------------");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"download.xlsx\"; filename*=UTF-8''" + encoded)
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }

    /**
     * 작업 후 해당 카테고리 그리드 목록으로 redirect.
     * 한글 카테고리는 그대로 두면 Location 헤더(ISO-8859-1)에 기록되지 못해
     * redirect 자체가 누락되므로, UTF-8 퍼센트 인코딩하여 ASCII로 만든다.
     */
    private String redirectToGrid(String category) {
        String encoded = URLEncoder.encode(category == null ? "" : category, StandardCharsets.UTF_8);
        return "redirect:/excel/data?category=" + encoded;
    }

}
