package com.java.rent.domain;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Date;

import com.java.user.domain.UserGrade;

/*
CREATE TABLE rent_history(
    rent_num NUMBER(7) CONSTRAINT rent_pk PRIMARY KEY,
    user_num NUMBER(5) CONSTRAINT rent_user_fk REFERENCES rent_users(user_num),
    user_rent_date DATE DEFAULT sysdate,
    user_return_date DATE,
    car_rent VARCHAR2(5) DEFAULT 'false'
);

CREATE SEQUENCE rent_history_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 10000000
    NOCYCLE
    NOCACHE;
 */

public class Rent {

	private int rentNum;
	private int userNum;
	private int carNum;
	private Date userRentDate;
	private Date userExpDate;
	private Date userReturnDate;
	private String carRent;

	public Rent() {}

	public Rent(int rentNum, int userNum, int carNum, Date userRentDate, Date userExpDate, Date userReturnDate, String carRent) {
		super();
		this.rentNum = rentNum;
		this.userNum = userNum;
		this.carNum = carNum;
		this.userRentDate = userRentDate;
		this.userExpDate = userExpDate;
		this.userReturnDate = userReturnDate;
		this.carRent = carRent;
	}



	public Date getUserExpDate() {
		return userExpDate;
	}

	public void setUserExpDate(Date userExpDate) {
		this.userExpDate = userExpDate;
	}

	public int getCarNum() {
		return carNum;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public int getRentNum() {
		return rentNum;
	}

	public void setRentNum(int rentNum) {
		this.rentNum = rentNum;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public Date getUserRentDate() {
		return userRentDate;
	}

	public void setUserRentDate(Date userRentDate) {
		this.userRentDate = userRentDate;
	}

	public Date getUserReturnDate() {
		return userReturnDate;
	}

	public void setUserReturnDate(Date userReturnDate) {
		this.userReturnDate = userReturnDate;
	}

	public String getCarRent() {
		return carRent;
	}

	public void setCarRent(String carRent) {
		this.carRent = carRent;
	}


	public String form1() {
		if(carRent.equals("TRUE")) {			
			carRent = "반납완료";	
		} else {			
			carRent = "대여 or 점검 중";				
		}	

		return
				"### 렌트번호 : " + rentNum +
				", 렌트한 회원 번호 : " + userNum +
				", 렌트 차량 번호 : " + carNum +				
				", 렌트일자: " + userRentDate +
				", 예상 반납 일자: " + userExpDate +
				", 반납일자: " + userReturnDate +
				", 렌트상태: " + carRent;
	}


	public String form2() {

		return
				"### 차량 번호 : " + carNum +
				", 예상 반납 일자: " + userExpDate;
	}
	






}
