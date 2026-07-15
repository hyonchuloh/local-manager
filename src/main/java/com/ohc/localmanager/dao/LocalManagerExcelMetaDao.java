package com.ohc.localmanager.dao;

import java.util.List;

import com.ohc.localmanager.dao.vo.LocalManagerExcelMetaVo;

/**
 * 엑셀 메타(카테고리) DAO 인터페이스
 * @author 오현철
 * @date 2026.06.25
 */
public interface LocalManagerExcelMetaDao {

    /**
     * 테이블 초기화
     */
    public void initialize();

    /**
     * 엑셀 메타(카테고리) 전체 조회
     * @return 메타 전체 리스트
     */
    public List<LocalManagerExcelMetaVo> getExcelMetas();

    /**
     * 엑셀 메타(카테고리) 단건 조회
     * @param category 카테고리
     * @return 메타 정보 (없으면 null)
     */
    public LocalManagerExcelMetaVo getExcelMeta(String category);

    /**
     * 엑셀 메타(카테고리) 추가
     * @param category 카테고리
     * @param metaInfo 메타정보(컬럼목록)
     * @param createUser 생성자
     * @return 추가 결과
     */
    public int addExcelMeta(String category, String metaInfo, String createUser);

    /**
     * 엑셀 메타(카테고리) 수정
     * @param category 카테고리
     * @param metaInfo 메타정보(컬럼목록)
     * @return 수정 결과
     */
    public int updateExcelMeta(String category, String metaInfo);

    /**
     * 엑셀 메타(카테고리) 삭제
     * @param category 카테고리
     * @return 삭제 결과
     */
    public int deleteExcelMeta(String category);

}
