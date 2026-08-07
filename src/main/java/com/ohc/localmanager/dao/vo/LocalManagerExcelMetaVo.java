package com.ohc.localmanager.dao.vo;

/**
 * 엑셀 메타(카테고리) VO 클래스
 * @author 오현철
 * @date 2026.06.25
 * (구조) 카테고리, 메타정보(컬럼목록), 생성일시, 생성자
 */
public class LocalManagerExcelMetaVo {

    private String category;
    private String metaInfo;
    private String createDate;
    private String createUser;
    private int rowCount;

    // Getters and Setters
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getMetaInfo() {
        return metaInfo;
    }
    public void setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public int getRowCount() {
        return rowCount;
    }
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

}
