package com.java.user.domain;

/*
CREATE TABLE rent_users(
    user_num NUMBER(5) CONSTRAINT user_pk PRIMARY KEY,
    user_name VARCHAR2(20) NOT NULL,
    phone_number VARCHAR2(30) NOT NULL,
    user_age NUMBER(3) NOT NULL,
    user_grade VARCHAR2(10) DEFAULT 'BRONZE',
    user_location VARCHAR2(20) NOT NULL
);

CREATE SEQUENCE rent_users_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE;
 */

public class User {

	private int userNum;
	private String userName;
	private String phoneNumber;
	private int userAge; // 나이도 추가되었네?
	private UserGrade userGrade;
	private String userLocation; // 사는 지역이 추가되었네?
    // 가지고 있는 요금 : 가격에 따라 선택가능한거
	
	public User() {}

	public User(int userNum, String userName, String phoneNumber, int userAge, UserGrade userGrade,
			String userLocation) {
		super();
		this.userNum = userNum;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.userAge = userAge;
		this.userGrade = userGrade;
		this.userLocation = userLocation;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public UserGrade getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(UserGrade userGrade) {
		this.userGrade = userGrade;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	@Override
	public String toString() {
		return  "### 회원 번호 : " + userNum +
				", 회원 이름 : " + userName +
				", 핸드폰 번호 : " + phoneNumber  +
				", 회원 나이 : " + userAge + "세" +
				", 회원 등급: " + userGrade +
				", 회원 거주 지역 : " + userLocation;
	}



}
