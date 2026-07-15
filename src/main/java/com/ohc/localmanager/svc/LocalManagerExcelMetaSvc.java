package com.ohc.localmanager.svc;

import java.util.List;

import com.ohc.localmanager.dao.vo.ExcelMetaVo;

/**
 * 엑셀 메타(카테고리) 서비스 인터페이스
 * @author 오현철
 * @date 2026.06.25
 */
public interface ExcelMetaSvc {

    /**
    * 엑셀 메타 테이블 초기화
    */
    public void initialize();

    /**
     * 엑셀 카테고리 전체 조회 (최초 진입점 목록)
     * @return 카테고리 전체 리스트
     */
    public List<ExcelMetaVo> getExcelMetas();

    /**
     * 엑셀 카테고리 단건 조회
     * @param category 카테고리
     * @return 메타 정보 (없으면 null)
     */
    public ExcelMetaVo getExcelMeta(String category);

    /**
     * 엑셀 카테고리 신규 생성
     * @param category 카테고리
     * @param metaInfo 메타정보(컬럼목록)
     * @param createUser 생성자
     * @return 추가 결과
     */
    public int addExcelMeta(String category, String metaInfo, String createUser);

}
