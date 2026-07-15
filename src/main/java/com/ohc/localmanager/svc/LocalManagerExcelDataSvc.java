package com.ohc.localmanager.svc;

import com.ohc.localmanager.dao.vo.ExcelGridVo;

/**
 * 엑셀 데이터 서비스 인터페이스
 * @author 오현철
 * @date 2026.06.25
 */
public interface ExcelDataSvc {

    /**
    * 엑셀 데이터 테이블 초기화
    */
    public void initialize();

    /**
     * 카테고리 그리드 조회 (메타정보 컬럼 + 데이터 행 조합)
     * @param category 카테고리
     * @return 컬럼/행으로 조합된 그리드
     */
    public ExcelGridVo getExcelGrid(String category);

    /**
     * 엑셀 데이터 추가
     * @param category 카테고리
     * @param data 데이터(컬럼순서대로 콤마구분 문자열)
     * @param updateUser 수정자
     * @return 추가 결과
     */
    public int addExcelData(String category, String data, String updateUser);

    /**
     * 엑셀 데이터 수정
     * @param seqNum 순번
     * @param category 카테고리 (수정일자/수정자 자동채움용 메타 조회에 사용)
     * @param data 데이터(컬럼순서대로 콤마구분 문자열)
     * @param updateUser 수정자
     * @return 수정 결과
     */
    public int updateExcelData(int seqNum, String category, String data, String updateUser);

    /**
     * 엑셀 데이터 삭제
     * @param seqNum 순번
     * @return 삭제 결과
     */
    public int deleteExcelData(int seqNum);

    /**
     * 카테고리 그리드(컬럼 + 데이터)를 엑셀(xlsx) 파일 바이트로 생성
     * @param category 카테고리
     * @return xlsx 파일 바이트
     */
    public byte[] buildExcelFile(String category);

}
