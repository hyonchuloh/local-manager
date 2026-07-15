package com.ohc.localmanager.dao.vo;

import java.util.List;

/**
 * 엑셀 그리드 행 VO 클래스 (데이터 1건을 컬럼 단위로 분해한 결과)
 * @author 오현철
 * @date 2026.06.25
 * (구조) 순번, 셀목록(데이터 파싱), 수정일시, 수정자
 */
public class LocalManagerExcelGridRowVo {

    private int seqNum;
    private List<String> cells;
    private String updateDate;
    private String updateUser;

    // Getters and Setters
    public int getSeqNum() {
        return seqNum;
    }
    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }
    public List<String> getCells() {
        return cells;
    }
    public void setCells(List<String> cells) {
        this.cells = cells;
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
