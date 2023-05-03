package com.java.rent.domain;

import com.java.car.domain.CarGrade;
import com.java.car.domain.CarSize;

public class Car_temp {
	
	
	private int carNum;
	private String carId;
	private String carModel;
	private String carSize;
	private String carGrade;
	private String carStatus;	
	
	public Car_temp(int carNum, String carId, String carModel, String carSize, String carGrade, String carStatus) {
		super();
		this.carNum = carNum;
		this.carId = carId;
		this.carModel = carModel;
		this.carSize = carSize;
		this.carGrade = carGrade;
		this.carStatus = carStatus;
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
	public String getCarSize() {
		return carSize;
	}
	public void setCarSize(String carSize) {
		this.carSize = carSize;
	}
	public String getCarGrade() {
		return carGrade;
	}
	public void setCarGrade(String carGrade) {
		this.carGrade = carGrade;
	}
	public String getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	
	
	
	
	

}
