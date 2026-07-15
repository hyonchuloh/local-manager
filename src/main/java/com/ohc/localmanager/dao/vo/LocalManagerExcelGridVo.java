package com.ohc.localmanager.dao.vo;

import java.util.List;

/**
 * 엑셀 그리드 VO 클래스 (메타정보 + 데이터 조합 결과)
 * @author 오현철
 * @date 2026.06.25
 * (구조) 카테고리, 컬럼목록(메타정보 파싱), 행목록(데이터 파싱)
 */
public class LocalManagerExcelGridVo {

    private String category;
    private List<String> columns;
    private List<LocalManagerExcelGridRowVo> rows;

    // Getters and Setters
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public List<String> getColumns() {
        return columns;
    }
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    public List<LocalManagerExcelGridRowVo> getRows() {
        return rows;
    }
    public void setRows(List<LocalManagerExcelGridRowVo> rows) {
        this.rows = rows;
    }

}
