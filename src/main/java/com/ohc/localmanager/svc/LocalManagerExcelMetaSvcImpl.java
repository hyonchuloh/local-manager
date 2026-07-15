package com.ohc.localmanager.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.ExcelMetaDao;
import com.ohc.localmanager.dao.vo.ExcelMetaVo;

/**
 * 엑셀 메타(카테고리) 서비스 구현 클래스 (DAO 호출 위임)
 * @author 오현철
 * @date 2026.06.25
 */
@Service
public class ExcelMetaSvcImpl implements ExcelMetaSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExcelMetaDao excelMetaDao;

    public ExcelMetaSvcImpl(ExcelMetaDao excelMetaDao) {
        this.excelMetaDao = excelMetaDao;
    }

    /**
     * 엑셀 메타 테이블 초기화
     */
    @Override
    public void initialize() {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.initialize()");
        excelMetaDao.initialize();
    }

    /**
     * 엑셀 카테고리 전체 조회
     */
    @Override
    public List<ExcelMetaVo> getExcelMetas() {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.getExcelMetas()");
        return excelMetaDao.getExcelMetas();
    }

    /**
     * 엑셀 카테고리 단건 조회
     */
    @Override
    public ExcelMetaVo getExcelMeta(String category) {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.getExcelMeta()");
        return excelMetaDao.getExcelMeta(category);
    }

    /**
     * 엑셀 카테고리 신규 생성
     */
    @Override
    public int addExcelMeta(String category, String metaInfo, String createUser) {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.addExcelMeta()");
        return excelMetaDao.addExcelMeta(category, metaInfo, createUser);
    }
}
