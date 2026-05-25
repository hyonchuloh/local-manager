package com.ohc.localmanager.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ohc.localmanager.dao.vo.CallbookVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 연락처 DAO 구현 클래스
 * @author 오현철
 * @date 2026.06.17
 */
@Repository
public class CallbookDaoImpl implements CallbookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize() {

        logger.info("--- (DAO) 연락처 DB 초기화 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 연락처 DB 초기화(이미 존재하는 경우 무시) */");
        sql.append("\n\tCREATE TABLE IF NOT EXISTS LOCAL_MANAGER_CALLBOOK");
        sql.append("\n\t(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(255), PHONE VARCHAR(255), EMAIL VARCHAR(255), ADDRESS VARCHAR(255), MEMO TEXT, CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        logger.info("--- {}\n", sql.toString());    
        jdbcTemplate.execute(sql.toString());
        logger.info("--- (DAO) 연락처 DB 초기화 완료");

        // '홍길동'이 존재하지 않는 경우 샘플 데이터 삽입
        if (getCallbooks().stream().noneMatch(cb -> "홍길동".equals(cb.getName()))) {
            logger.info("--- (DAO) 연락처 DB 샘플 데이터 삽입 시작");
            sql = new StringBuffer("\n\n\t/* 연락처 DB 샘플 데이터 삽입 */");
            sql.append("\n\tINSERT INTO LOCAL_MANAGER_CALLBOOK (NAME, PHONE, EMAIL, ADDRESS, MEMO) VALUES ('홍길동', '010-1234-5678', 'hong@example.com', '서울시 강남구', '친구')");
            logger.info("--- {}\n", sql.toString());
            jdbcTemplate.execute(sql.toString());
            logger.info("--- (DAO) 연락처 DB 샘플 데이터 삽입 완료");
        }
        
    }

    @Override
    public List<CallbookVo> getCallbooks() {
        logger.info("--- (DAO) 연락처 DB 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 연락처 DB 조회 */");
        sql.append("\n\tSELECT ID, NAME, PHONE, EMAIL, ADDRESS, MEMO, CREATED_AT, UPDATED_AT FROM LOCAL_MANAGER_CALLBOOK");
        logger.info("--- {}\n", sql.toString());
        List<CallbookVo> callbooks = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            CallbookVo callbook = new CallbookVo();
            callbook.setId(rs.getInt("ID"));
            callbook.setName(rs.getString("NAME"));
            callbook.setPhoneNumber(rs.getString("PHONE"));
            callbook.setEmail(rs.getString("EMAIL"));
            callbook.setAddress(rs.getString("ADDRESS"));
            callbook.setMemo(rs.getString("MEMO"));
            callbook.setCreatedAt(rs.getString("CREATED_AT"));
            callbook.setUpdatedAt(rs.getString("UPDATED_AT"));
            return callbook;
        });
        logger.info("--- (DAO) 연락처 DB 조회 완료");
        return callbooks;
    }

}
