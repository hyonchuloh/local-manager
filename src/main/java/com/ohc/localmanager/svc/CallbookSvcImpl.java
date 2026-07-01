package com.ohc.localmanager.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.CallbookDao;
import com.ohc.localmanager.dao.vo.CallbookVo;

/**
 * 연락처 서비스 구현 클래스 (DAO 호출 위임)
 * @author 오현철
 * @date 2026.06.17
 */
@Service
public class CallbookSvcImpl implements CallbookSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());	
    private final CallbookDao callbookDao;

    public CallbookSvcImpl(CallbookDao callbookDao) {
        this.callbookDao = callbookDao;
    }

    /**
     * 연락처 테이블 초기화
     */
    @Override
    public void initialize() {
        logger.info("--- (SERVICE) CallbookSvcImpl.initialize()");
        callbookDao.initialize();
    }

    /**
     * 연락처 전체 조회
     */
    @Override
    public List<CallbookVo> getCallbooks() {
        logger.info("--- (SERVICE) CallbookSvcImpl.getCallbooks()");
        return callbookDao.getCallbooks();
    }

    /**
     * 연락처 추가
     */
    @Override
    public int addCallbook(String company, String department, String name, String phoneNumber, String email, String memo) {
        logger.info("--- (SERVICE) CallbookSvcImpl.addCallbook()");
        return callbookDao.addCallbook(company, department, name, phoneNumber, email, memo);
    }

    /**
     * 연락처 수정
     */
    @Override
    public int updateCallbook(int id, String company, String department, String name, String phoneNumber, String email, String memo) {
        logger.info("--- (SERVICE) CallbookSvcImpl.updateCallbook()");
        return callbookDao.updateCallbook(id, company, department, name, phoneNumber, email, memo);
    }

    /**
     * 연락처 삭제
     */
    @Override
    public int deleteCallbook(int id) {
        logger.info("--- (SERVICE) CallbookSvcImpl.deleteCallbook()");
        return callbookDao.deleteCallbook(id);
    }
}
