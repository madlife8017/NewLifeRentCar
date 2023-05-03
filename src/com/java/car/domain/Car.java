package com.java.car.domain;

/*
CREATE TABLE cars(
   car_num NUMBER(5) CONSTRAINT car_pk PRIMARY KEY,
   car_id VARCHAR2(20) NOT NULL,
   car_model VARCHAR2(50) NOT NULL,
   car_size VARCHAR2(20) NOT NULL,
   car_fee NUMBER(7) NOT NULL,
   car_grade VARCHAR2(1) NOT NULL,
   car_status VARCHAR2(10) DEFAULT 'available',
   user_num NUMBER(5) CONSTRAINT car_user_fk REFERENCES rent_users(user_num)
);

CREATE SEQUENCE cars_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE;
 */

public class Car {

	private int carNum;
	private String carId;
	private String carModel;
	private CarSize carSize;
	private int carFee;
	private CarGrade carGrade;
	private String carStatus;
	private int userNum;

	public Car() {}

	public Car(int carNum, String carId, String carModel, CarSize carSize, int carFee, CarGrade carGrade,
			String carStatus, int userNum) {
		super();
		this.carNum = carNum;
		this.carId = carId;
		this.carModel = carModel;
		this.carSize = carSize;
		this.carFee = carFee;
		this.carGrade = carGrade;
		this.carStatus = carStatus;
		this.userNum = userNum;
	}

	public int getCarNum() {
		return carNum;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public CarSize getCarSize() {
		return carSize;
	}

	public void setCarSize(CarSize carSize) {
		this.carSize = carSize;
	}

	public int getCarFee() {
		return carFee;
	}

	public void setCarFee(int carFee) {
		this.carFee = carFee;
	}

	public CarGrade getCarGrade() {
		return carGrade;
	}

	public void setCarGrade(CarGrade carGrade) {
		this.carGrade = carGrade;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	@Override
	public String toString() {

		return
				"### 차량번호 : " + carNum +
				", 차량ID : " + carId +
				", 차량 모델명 : " + carModel +
				", 차량 구분 : " + carSize +
				", 차량 렌트 요금 : " + carFee + "원" +
				", 차량등급 : " + carGrade +
				", 차량상태 : " + carStatus +
				", 렌트한 회원 번호 : " + userNum;
	}



}
