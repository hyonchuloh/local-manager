package com.ohc.localmanager.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.CallbookDao;
import com.ohc.localmanager.dao.vo.CallbookVo;

@Service
public class CallbookSvcImpl implements CallbookSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());	
    @Autowired
    private CallbookDao callbookDao;

    @Override
    public void initialize() {
        logger.info("--- (SERVICE) CallbookSvcImpl.initialize()");
        callbookDao.initialize();
    }

    @Override
    public List<CallbookVo> getCallbooks() {
        logger.info("--- (SERVICE) CallbookSvcImpl.getCallbooks()");
        return callbookDao.getCallbooks();
    }
}
