package com.ohc.localmanager.dao.vo;

/**
 * 엑셀 데이터 VO 클래스
 * @author 오현철
 * @date 2026.06.25
 * (구조) 순번, 카테고리, 데이터, 수정일시, 수정자
 */
public class ExcelDataVo {

    private int seqNum;
    private String category;
    private String data;
    private String updateDate;
    private String updateUser;

    // Getters and Setters
    public int getSeqNum() {
        return seqNum;
    }
    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

}
