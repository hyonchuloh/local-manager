package com.ohc.localmanager.dao;

import java.util.List;

import com.ohc.localmanager.dao.vo.LocalManagerExcelDataVo;

/**
 * 엑셀 데이터 DAO 인터페이스
 * @author 오현철
 * @date 2026.06.25
 */
public interface LocalManagerExcelDataDao {

    /**
     * 테이블 초기화
     */
    public void initialize();

    /**
     * 카테고리별 엑셀 데이터 조회
     * @param category 카테고리
     * @return 해당 카테고리의 데이터 리스트
     */
    public List<LocalManagerExcelDataVo> getExcelDataList(String category);

    /**
     * 엑셀 데이터 단건 조회
     * @param seqNum 순번
     * @return 데이터 (없으면 null)
     */
    public LocalManagerExcelDataVo getExcelData(int seqNum);

    /**
     * 엑셀 데이터 추가
     * @param category 카테고리
     * @param data 데이터
     * @param updateUser 수정자
     * @return 추가 결과
     */
    public int addExcelData(String category, String data, String updateUser);

    /**
     * 엑셀 데이터 수정
     * @param seqNum 순번
     * @param data 데이터
     * @param updateUser 수정자
     * @return 수정 결과
     */
    public int updateExcelData(int seqNum, String data, String updateUser);

    /**
     * 엑셀 데이터 삭제
     * @param seqNum 순번
     * @return 삭제 결과
     */
    public int deleteExcelData(int seqNum);

    /**
     * 카테고리별 엑셀 데이터 건수 조회
     * @param category 카테고리
     * @return 데이터 건수
     */
    public int countExcelData(String category);

    /**
     * 카테고리에 속한 엑셀 데이터 전체 삭제 (카테고리 삭제 시 연계)
     * @param category 카테고리
     * @return 삭제 결과
     */
    public int deleteExcelDataByCategory(String category);

}
