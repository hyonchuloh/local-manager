package com.ohc.localmanager.svc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ohc.localmanager.dao.LocalManagerExcelDataDao;
import com.ohc.localmanager.dao.vo.LocalManagerExcelDataVo;
import com.ohc.localmanager.dao.vo.LocalManagerExcelGridRowVo;
import com.ohc.localmanager.dao.vo.LocalManagerExcelGridVo;
import com.ohc.localmanager.dao.vo.LocalManagerExcelMetaVo;

/**
 * 엑셀 데이터 서비스 구현 클래스 (그리드 조합, audit 컬럼 자동채움, xlsx 생성)
 * @author 오현철
 * @date 2026.06.25
 */
@Service
public class LocalManagerExcelDataSvcImpl implements LocalManagerExcelDataSvc {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LocalManagerExcelDataDao excelDataDao;
    private final LocalManagerExcelMetaSvc excelMetaSvc;

    public LocalManagerExcelDataSvcImpl(LocalManagerExcelDataDao excelDataDao, LocalManagerExcelMetaSvc excelMetaSvc) {
        this.excelDataDao = excelDataDao;
        this.excelMetaSvc = excelMetaSvc;
    }

    /**
     * 엑셀 데이터 테이블 초기화
     */
    @Override
    public void initialize() {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.initialize()");
        excelDataDao.initialize();
    }

    /**
     * 메타정보(컬럼) + 데이터(행)를 조합하여 그리드 화면용 VO 생성
     */
    @Override
    public LocalManagerExcelGridVo getExcelGrid(String category) {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.getExcelGrid()");

        LocalManagerExcelGridVo grid = new LocalManagerExcelGridVo();
        grid.setCategory(category);

        // 컬럼 = 메타정보(컬럼목록) 파싱
        LocalManagerExcelMetaVo meta = excelMetaSvc.getExcelMeta(category);
        grid.setColumns(meta == null ? new ArrayList<>() : splitCsv(meta.getMetaInfo()));

        // 행 = 카테고리별 데이터 파싱
        List<LocalManagerExcelGridRowVo> rows = new ArrayList<>();
        for (LocalManagerExcelDataVo data : excelDataDao.getExcelDataList(category)) {
            LocalManagerExcelGridRowVo row = new LocalManagerExcelGridRowVo();
            row.setSeqNum(data.getSeqNum());
            row.setCells(splitCsv(data.getData()));
            row.setUpdateDate(data.getUpdateDate());
            row.setUpdateUser(data.getUpdateUser());
            rows.add(row);
        }
        // 첫번째 컬럼 기준 오름차순 정렬
        rows.sort(Comparator.comparing(row -> row.getCells().isEmpty() ? "" : row.getCells().get(0)));
        grid.setRows(rows);

        return grid;
    }

    /**
     * 엑셀 데이터 추가 (수정일자/수정자 컬럼 자동채움 후 저장)
     */
    @Override
    public int addExcelData(String category, String data, String updateUser) {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.addExcelData()");
        return excelDataDao.addExcelData(category, applyAuditColumns(category, data, updateUser), updateUser);
    }

    /**
     * 엑셀 데이터 수정 (수정일자/수정자 컬럼 자동채움 후 저장)
     */
    @Override
    public int updateExcelData(int seqNum, String category, String data, String updateUser) {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.updateExcelData()");
        return excelDataDao.updateExcelData(seqNum, applyAuditColumns(category, data, updateUser), updateUser);
    }

    /**
     * 수정일자/수정자 컬럼은 사용자 입력이 아니라 저장 시점에 자동으로 채운다.
     * metaInfo에서 해당 컬럼의 위치를 찾아 현재 날짜/수정자 값으로 덮어쓴다.
     * (audit 컬럼 위치가 어디에 있든 컬럼명 기준으로 처리)
     * @param category 카테고리 (컬럼 구성 조회용)
     * @param data 클라이언트가 보낸 콤마구분 데이터
     * @param updateUser 수정자
     * @return 수정일자/수정자가 자동 반영된 데이터
     */
    private String applyAuditColumns(String category, String data, String updateUser) {
        LocalManagerExcelMetaVo meta = excelMetaSvc.getExcelMeta(category);
        if (meta == null) {
            return data;
        }
        List<String> columns = splitCsv(meta.getMetaInfo());
        List<String> cells = new ArrayList<>(splitCsv(data));
        // 컬럼 수보다 셀이 부족하면 빈 값으로 채워 위치를 맞춘다
        while (cells.size() < columns.size()) {
            cells.add("");
        }
        String today = LocalDate.now().toString(); // yyyy-MM-dd
        for (int i = 0; i < columns.size(); i++) {
            if ("수정일자".equals(columns.get(i))) {
                cells.set(i, today);
            } else if ("수정자".equals(columns.get(i))) {
                cells.set(i, updateUser);
            }
        }
        return String.join(", ", cells);
    }

    /**
     * 엑셀 데이터 삭제
     */
    @Override
    public int deleteExcelData(int seqNum) {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.deleteExcelData()");
        return excelDataDao.deleteExcelData(seqNum);
    }

    /**
     * 그리드(컬럼 헤더 + 데이터 행)를 Apache POI로 xlsx 파일 바이트로 생성
     */
    @Override
    public byte[] buildExcelFile(String category) {
        logger.info("--- (SERVICE) LocalManagerExcelDataSvcImpl.buildExcelFile()");
        LocalManagerExcelGridVo grid = getExcelGrid(category);
        List<String> columns = grid.getColumns();
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(
                    (category == null || category.isEmpty()) ? "Sheet1" : category));

            // 헤더 행 = 메타정보 컬럼
            Row header = sheet.createRow(0);
            for (int i = 0; i < columns.size(); i++) {
                header.createCell(i).setCellValue(columns.get(i));
            }

            // 데이터 행 = 그리드 행(컬럼 순서대로)
            int rowIdx = 1;
            for (LocalManagerExcelGridRowVo row : grid.getRows()) {
                Row dataRow = sheet.createRow(rowIdx++);
                List<String> cells = row.getCells();
                for (int i = 0; i < columns.size(); i++) {
                    String value = (cells != null && i < cells.size()) ? cells.get(i) : "";
                    dataRow.createCell(i).setCellValue(value);
                }
            }

            // 컬럼 너비 자동 조정
            for (int i = 0; i < columns.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("엑셀 파일 생성 실패: " + category, e);
        }
    }

    /**
     * 콤마구분 문자열을 컬럼/셀 리스트로 분해 (각 토큰 trim)
     * @param raw 콤마구분 문자열
     * @return 분해된 리스트 (null/공백이면 빈 리스트)
     */
    private List<String> splitCsv(String raw) {
        List<String> result = new ArrayList<>();
        if (raw == null || raw.trim().isEmpty()) {
            return result;
        }
        for (String token : raw.split(",")) {
            result.add(token.trim());
        }
        return result;
    }
}
