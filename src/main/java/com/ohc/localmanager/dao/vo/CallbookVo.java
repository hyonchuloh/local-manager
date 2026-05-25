package com.ohc.localmanager.dao.vo;

import java.security.Timestamp;

/**
 * 연락처 VO 클래스
 * @author 오현철
 * @date 2026.06.17
 * (구조) ID, 이름, 전화번호, 이메일, 주소, 메모, 생성일시, 수정일시
 */
public class CallbookVo {

    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String memo;
    private String createdAt;
    private String updatedAt;

    // Getters and Setters 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
