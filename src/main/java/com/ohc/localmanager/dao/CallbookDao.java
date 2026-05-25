package com.ohc.localmanager.dao;

import java.util.List;

import com.ohc.localmanager.dao.vo.CallbookVo;

/**
 * 연락처 DAO 인터페이스
 * @author 오현철
 * @date 2026.06.17
 */
public interface CallbookDao {

    /**
     * 테이블 초기화
     */
    public void initialize();

    /**
     * 연락처 전체 조회
     * @return 연락처 전체 리스트
     */
    public List<CallbookVo> getCallbooks();

    /**
     * 연락처 추가
     * @param company 회사
     * @param department 부서
     * @param name 이름
     * @param phoneNumber 전화번호
     * @param email 이메일
     * @param memo 메모
     * @return 추가 결과
     */
    public int addCallbook(String company, String department, String name, String phoneNumber, String email, String memo);

    /**
     * 연락처 수정
     * @param id 연락처 ID
     * @param company 회사
     * @param department 부서
     * @param name 이름
     * @param phoneNumber 전화번호
     * @param email 이메일
     * @param memo 메모
     * @return 수정 결과
     */
    public int updateCallbook(int id, String company, String department, String name, String phoneNumber, String email, String memo);

    /**
     * 연락처 삭제
     * @param id 연락처 ID
     * @return 삭제 결과
     */
    public int deleteCallbook(int id);

}
