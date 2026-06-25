package com.ohc.localmanager.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.ohc.localmanager.dao.vo.ExcelMetaVo;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 엑셀 메타(카테고리) DAO 구현 클래스
 * @author 오현철
 * @date 2026.06.25
 */
@Repository
public class ExcelMetaDaoImpl implements ExcelMetaDao {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ExcelMetaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void initialize() {
        logger.info("--- (DAO) 엑셀 메타 DB 초기화 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 초기화(이미 존재하는 경우 무시) */");
        sql.append("\n\tCREATE TABLE IF NOT EXISTS LOCAL_MANAGER_EXCEL_META");
        sql.append("\n\t(CATEGORY VARCHAR(255) PRIMARY KEY, META_INFO TEXT, CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CREATE_USER VARCHAR(255))");
        logger.info("--- {}\n", sql.toString());
        jdbcTemplate.execute(sql.toString());
        logger.info("--- (DAO) 엑셀 메타 DB 초기화 완료");

        // '직원대장' 카테고리가 존재하지 않는 경우 샘플 데이터 삽입
        if (getExcelMeta("직원대장") == null) {
            logger.info("--- (DAO) 엑셀 메타 DB 샘플 데이터 삽입 시작");
            sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 샘플 데이터 삽입 */");
            sql.append("\n\tINSERT INTO LOCAL_MANAGER_EXCEL_META (CATEGORY, META_INFO, CREATE_DATE, CREATE_USER)");
            sql.append("\n\tVALUES ('직원대장', '순번, 직원번호, 직원성명, 직급, 직책, 전화번호, 휴대전화번호, 이메일, 수정일자, 수정자', datetime('now','localtime'), 'admin')");
            logger.info("--- {}\n", sql.toString());
            jdbcTemplate.execute(sql.toString());
            logger.info("--- (DAO) 엑셀 메타 DB 샘플 데이터 삽입 완료");
        }
    }

    @Override
    public List<ExcelMetaVo> getExcelMetas() {
        logger.info("--- (DAO) 엑셀 메타 DB 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 조회 */");
        sql.append("\n\tSELECT CATEGORY, META_INFO, CREATE_DATE, CREATE_USER FROM LOCAL_MANAGER_EXCEL_META ORDER BY CREATE_DATE");
        logger.info("--- {}\n", sql.toString());
        List<ExcelMetaVo> metas = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> mapRow(rs));
        logger.info("--- (DAO) 엑셀 메타 DB 조회 완료");
        return metas;
    }

    @Override
    public ExcelMetaVo getExcelMeta(String category) {
        logger.info("--- (DAO) 엑셀 메타 DB 단건 조회 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 단건 조회 */");
        sql.append("\n\tSELECT CATEGORY, META_INFO, CREATE_DATE, CREATE_USER FROM LOCAL_MANAGER_EXCEL_META WHERE CATEGORY = ?");
        logger.info("--- {}\n", sql.toString());
        try {
            ExcelMetaVo meta = jdbcTemplate.queryForObject(sql.toString(), (rs, rowNum) -> mapRow(rs), category);
            logger.info("--- (DAO) 엑셀 메타 DB 단건 조회 완료");
            return meta;
        } catch (EmptyResultDataAccessException e) {
            logger.info("--- (DAO) 엑셀 메타 DB 단건 조회 결과 없음 (category={})", category);
            return null;
        }
    }

    @Override
    public int addExcelMeta(String category, String metaInfo, String createUser) {
        logger.info("--- (DAO) 엑셀 메타 DB 추가 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 추가 */");
        sql.append("\n\tINSERT INTO LOCAL_MANAGER_EXCEL_META (CATEGORY, META_INFO, CREATE_USER) VALUES (?, ?, ?)");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), category, metaInfo, createUser);
        logger.info("--- (DAO) 엑셀 메타 DB 추가 완료");
        return result;
    }

    @Override
    public int updateExcelMeta(String category, String metaInfo) {
        logger.info("--- (DAO) 엑셀 메타 DB 수정 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 수정 */");
        sql.append("\n\tUPDATE LOCAL_MANAGER_EXCEL_META SET META_INFO = ? WHERE CATEGORY = ?");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), metaInfo, category);
        logger.info("--- (DAO) 엑셀 메타 DB 수정 완료");
        return result;
    }

    @Override
    public int deleteExcelMeta(String category) {
        logger.info("--- (DAO) 엑셀 메타 DB 삭제 시작");
        StringBuffer sql = new StringBuffer("\n\n\t/* 엑셀 메타 DB 삭제 */");
        sql.append("\n\tDELETE FROM LOCAL_MANAGER_EXCEL_META WHERE CATEGORY = ?");
        logger.info("--- {}\n", sql.toString());
        int result = jdbcTemplate.update(sql.toString(), category);
        logger.info("--- (DAO) 엑셀 메타 DB 삭제 완료");
        return result;
    }

    /**
     * ResultSet -> ExcelMetaVo 매핑
     */
    private ExcelMetaVo mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        ExcelMetaVo meta = new ExcelMetaVo();
        meta.setCategory(rs.getString("CATEGORY"));
        meta.setMetaInfo(rs.getString("META_INFO"));
        meta.setCreateDate(rs.getString("CREATE_DATE"));
        meta.setCreateUser(rs.getString("CREATE_USER"));
        return meta;
    }

}
