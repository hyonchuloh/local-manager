package com.ohc.localmanager.svc;

import java.util.List;

import com.ohc.localmanager.dao.CallbookDao;
import com.ohc.localmanager.dao.vo.CallbookVo;

/**
 * 연락처 서비스 인터페이스
 * @author 오현철
 * @date 2026.06.17
 */
public interface CallbookSvc {

    /**
    * 연락처 테이블 초기화
    */
    public void initialize();

    /**
     * 연락처 전체 조회
     * @return 연락처 전체 리스트
     */
    public List<CallbookVo> getCallbooks();

}
