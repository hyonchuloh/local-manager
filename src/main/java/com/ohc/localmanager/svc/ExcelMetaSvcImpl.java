package com.ohc.localmanager.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.ExcelMetaDao;
import com.ohc.localmanager.dao.vo.ExcelMetaVo;

@Service
public class ExcelMetaSvcImpl implements ExcelMetaSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExcelMetaDao excelMetaDao;

    public ExcelMetaSvcImpl(ExcelMetaDao excelMetaDao) {
        this.excelMetaDao = excelMetaDao;
    }

    @Override
    public void initialize() {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.initialize()");
        excelMetaDao.initialize();
    }

    @Override
    public List<ExcelMetaVo> getExcelMetas() {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.getExcelMetas()");
        return excelMetaDao.getExcelMetas();
    }

    @Override
    public ExcelMetaVo getExcelMeta(String category) {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.getExcelMeta()");
        return excelMetaDao.getExcelMeta(category);
    }

    @Override
    public int addExcelMeta(String category, String metaInfo, String createUser) {
        logger.info("--- (SERVICE) ExcelMetaSvcImpl.addExcelMeta()");
        return excelMetaDao.addExcelMeta(category, metaInfo, createUser);
    }
}
