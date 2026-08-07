package com.ohc.localmanager.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.LocalManagerExcelDataDao;
import com.ohc.localmanager.dao.LocalManagerExcelMetaDao;
import com.ohc.localmanager.dao.vo.LocalManagerExcelMetaVo;

/**
 * 엑셀 메타(카테고리) 서비스 구현 클래스 (DAO 호출 위임)
 * @author 오현철
 * @date 2026.06.25
 */
@Service
public class LocalManagerExcelMetaSvcImpl implements LocalManagerExcelMetaSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LocalManagerExcelMetaDao excelMetaDao;
    private final LocalManagerExcelDataDao excelDataDao;

    public LocalManagerExcelMetaSvcImpl(LocalManagerExcelMetaDao excelMetaDao, LocalManagerExcelDataDao excelDataDao) {
        this.excelMetaDao = excelMetaDao;
        this.excelDataDao = excelDataDao;
    }

    /**
     * 엑셀 메타 테이블 초기화
     */
    @Override
    public void initialize() {
        logger.info("--- (SERVICE) LocalManagerExcelMetaSvcImpl.initialize()");
        excelMetaDao.initialize();
    }

    /**
     * 엑셀 카테고리 전체 조회
     */
    @Override
    public List<LocalManagerExcelMetaVo> getExcelMetas() {
        logger.info("--- (SERVICE) LocalManagerExcelMetaSvcImpl.getExcelMetas()");
        List<LocalManagerExcelMetaVo> metas = excelMetaDao.getExcelMetas();
        for (LocalManagerExcelMetaVo meta : metas) {
            meta.setRowCount(excelDataDao.countExcelData(meta.getCategory()));
        }
        return metas;
    }

    /**
     * 엑셀 카테고리 단건 조회
     */
    @Override
    public LocalManagerExcelMetaVo getExcelMeta(String category) {
        logger.info("--- (SERVICE) LocalManagerExcelMetaSvcImpl.getExcelMeta()");
        return excelMetaDao.getExcelMeta(category);
    }

    /**
     * 엑셀 카테고리 신규 생성
     */
    @Override
    public int addExcelMeta(String category, String metaInfo, String createUser) {
        logger.info("--- (SERVICE) LocalManagerExcelMetaSvcImpl.addExcelMeta()");
        return excelMetaDao.addExcelMeta(category, metaInfo, createUser);
    }

    /**
     * 엑셀 카테고리 삭제 (해당 카테고리의 데이터도 함께 삭제)
     */
    @Override
    public int deleteExcelMeta(String category) {
        logger.info("--- (SERVICE) LocalManagerExcelMetaSvcImpl.deleteExcelMeta()");
        excelDataDao.deleteExcelDataByCategory(category);
        return excelMetaDao.deleteExcelMeta(category);
    }
}
