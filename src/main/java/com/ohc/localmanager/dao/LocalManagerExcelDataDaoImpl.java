package com.ohc.localmanager.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.ohc.localmanager.dao.vo.LocalManagerExcelDataVo;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 엑셀 데이터 DAO 구현 클래스
 * @author 오현철
 * @date 2026.06.25
 */
@Repository
public class LocalManagerExcelDataDaoImpl implements LocalManagerExcelDataDao {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LocalManagerExcelDataDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 엑셀 데이터 테이블 생성(없으면) 및 샘플 데이터('직원대장') 삽입
     */
    @Override
    public void initialize() {
        logger.info("--- (DAO) 엑셀 데이터 DB 초기화 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 초기화(이미 존재하는 경우 무시) */");
        sql.append("\n\tCREATE TABLE IF NOT EXISTS LOCAL_MANAGER_EXCEL_DB");
        sql.append("\n\t(SEQ_NUM INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY VARCHAR(255) NOT NULL, DATA TEXT, UPDATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, UPDATE_USER VARCHAR(255))");
        logger.info("--- {}\n", sql.toString());
        jdbcTemplate.execute(sql.toString());
        logger.info("--- (DAO) 엑셀 데이터 DB 초기화 완료");

        // '직원대장' 카테고리의 데이터가 존재하지 않는 경우 샘플 데이터 삽입
        if (getExcelDataList("직원대장").isEmpty()) {
            logger.info("--- (DAO) 엑셀 데이터 DB 샘플 데이터 삽입 시작");
            sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 샘플 데이터 삽입 */");
            sql.append("\n\tINSERT INTO LOCAL_MANAGER_EXCEL_DB (SEQ_NUM, CATEGORY, DATA, UPDATE_DATE, UPDATE_USER)");
            sql.append("\n\tVALUES (1, '직원대장', '1, 2020200, 홍길동, G4, 팀원, 8-6684, 01094945642, hyonchul.oh@bok.or.kr, ' || date('now','localtime') || ', admin', datetime('now','localtime'), 'admin')");
            logger.info("--- {}\n", sql.toString());
            jdbcTemplate.execute(sql.toString());
            logger.info("--- (DAO) 엑셀 데이터 DB 샘플 데이터 삽입 완료");
        }
    }

    /**
     * 카테고리별 엑셀 데이터 조회 (순번순)
     */
    @Override
    public List<LocalManagerExcelDataVo> getExcelDataList(String category) {
        logger.info("--- (DAO) 엑셀 데이터 DB 카테고리별 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 카테고리별 조회 */");
        sql.append("\n\tSELECT SEQ_NUM, CATEGORY, DATA, UPDATE_DATE, UPDATE_USER FROM LOCAL_MANAGER_EXCEL_DB WHERE CATEGORY = ? ORDER BY SEQ_NUM");
        logger.info("--- {}\n", sql.toString());
        List<LocalManagerExcelDataVo> list = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> mapRow(rs), category);
        logger.info("--- (DAO) 엑셀 데이터 DB 카테고리별 조회 완료");
        return list;
    }

    /**
     * 엑셀 데이터 단건 조회 (없으면 null)
     */
    @Override
    public LocalManagerExcelDataVo getExcelData(int seqNum) {
        logger.info("--- (DAO) 엑셀 데이터 DB 단건 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 단건 조회 */");
        sql.append("\n\tSELECT SEQ_NUM, CATEGORY, DATA, UPDATE_DATE, UPDATE_USER FROM LOCAL_MANAGER_EXCEL_DB WHERE SEQ_NUM = ?");
        logger.info("--- {}\n", sql.toString());
        try {
            LocalManagerExcelDataVo data = jdbcTemplate.queryForObject(sql.toString(), (rs, rowNum) -> mapRow(rs), seqNum);
            logger.info("--- (DAO) 엑셀 데이터 DB 단건 조회 완료");
            return data;
        } catch (EmptyResultDataAccessException e) {
            logger.info("--- (DAO) 엑셀 데이터 DB 단건 조회 결과 없음 (seqNum={})", seqNum);
            return null;
        }
    }

    /**
     * 엑셀 데이터 1건 추가
     */
    @Override
    public int addExcelData(String category, String data, String updateUser) {
        logger.info("--- (DAO) 엑셀 데이터 DB 추가 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 추가 */");
        sql.append("\n\tINSERT INTO LOCAL_MANAGER_EXCEL_DB (CATEGORY, DATA, UPDATE_USER) VALUES (?, ?, ?)");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), category, data, updateUser);
        logger.info("--- (DAO) 엑셀 데이터 DB 추가 완료");
        return result;
    }

    /**
     * 엑셀 데이터 1건 수정 (수정일시 자동 갱신)
     */
    @Override
    public int updateExcelData(int seqNum, String data, String updateUser) {
        logger.info("--- (DAO) 엑셀 데이터 DB 수정 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 수정 */");
        sql.append("\n\tUPDATE LOCAL_MANAGER_EXCEL_DB SET DATA = ?, UPDATE_USER = ?, UPDATE_DATE = CURRENT_TIMESTAMP WHERE SEQ_NUM = ?");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), data, updateUser, seqNum);
        logger.info("--- (DAO) 엑셀 데이터 DB 수정 완료");
        return result;
    }

    /**
     * 엑셀 데이터 1건 삭제
     */
    @Override
    public int deleteExcelData(int seqNum) {
        logger.info("--- (DAO) 엑셀 데이터 DB 삭제 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 삭제 */");
        sql.append("\n\tDELETE FROM LOCAL_MANAGER_EXCEL_DB WHERE SEQ_NUM = ?");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), seqNum);
        logger.info("--- (DAO) 엑셀 데이터 DB 삭제 완료");
        return result;
    }

    /**
     * 카테고리별 엑셀 데이터 건수 조회
     */
    @Override
    public int countExcelData(String category) {
        logger.info("--- (DAO) 엑셀 데이터 DB 건수 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 건수 조회 */");
        sql.append("\n\tSELECT COUNT(*) FROM LOCAL_MANAGER_EXCEL_DB WHERE CATEGORY = ?");
        logger.info("--- {}\n", sql.toString());
        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, category);
        logger.info("--- (DAO) 엑셀 데이터 DB 건수 조회 완료");
        return count == null ? 0 : count;
    }

    /**
     * 카테고리에 속한 엑셀 데이터 전체 삭제
     */
    @Override
    public int deleteExcelDataByCategory(String category) {
        logger.info("--- (DAO) 엑셀 데이터 DB 카테고리 일괄 삭제 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 데이터 DB 카테고리 일괄 삭제 */");
        sql.append("\n\tDELETE FROM LOCAL_MANAGER_EXCEL_DB WHERE CATEGORY = ?");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), category);
        logger.info("--- (DAO) 엑셀 데이터 DB 카테고리 일괄 삭제 완료");
        return result;
    }

    /**
     * ResultSet -> LocalManagerExcelDataVo 매핑
     */
    private LocalManagerExcelDataVo mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        LocalManagerExcelDataVo data = new LocalManagerExcelDataVo();
        data.setSeqNum(rs.getInt("SEQ_NUM"));
        data.setCategory(rs.getString("CATEGORY"));
        data.setData(rs.getString("DATA"));
        data.setUpdateDate(rs.getString("UPDATE_DATE"));
        data.setUpdateUser(rs.getString("UPDATE_USER"));
        return data;
    }

}
