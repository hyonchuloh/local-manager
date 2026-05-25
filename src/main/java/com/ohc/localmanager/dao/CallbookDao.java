package com.ohc.localmanager.dao;

import java.util.List;

import com.ohc.localmanager.dao.vo.CallbookVo;

/**
 * 연락처 DAO 인터페이스
 * @author 오현철
 * @date 2026.06.17
 */
public interface CallbookDao {

    /**
     * 테이블 초기화
     */
    public void initialize();

    /**
     * 연락처 전체 조회
     * @return 연락처 전체 리스트
     */
    public List<CallbookVo> getCallbooks();

}
