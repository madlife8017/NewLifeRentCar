package com.java.rent.service;

import static com.java.view.AppUI.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import com.java.rent.domain.Rent;
import com.java.rent.repository.RentRepository;
import com.java.car.domain.Car;
import com.java.common.AppService;

public class RentService implements AppService {

	private final RentRepository rentRepository = new RentRepository();

	@Override
	public void start() {
		while(true) {
			rentManagementScreen();
			int selection = inputInteger();

			switch (selection) {
			case 1:
				
				searchRentData();
				break;
			case 2:
				
				newRent();

				break;
			case 3:
				extendRent();

				break;
			case 4:
				returnCar();

				break;
			case 5:
				accountRent();
				break;
			case 6:
			
				System.out.println("\n첫 화면으로 돌아갑니다.\n");
				return;

			default:
				System.out.println("메뉴를 잘못 입력하셨습니다. 다시 입력해주세요.");
				return;
			}
			System.out.println("\n====== 계속 진행하시려면 ENTER를 누르세요 ======");
			inputString();

		}		
	}

	private void searchRentData() {
		System.out.println("\n=============== 차량 검색 조건을 선택하세요. ===============");
		System.out.println("[ 1. 전체 대여 기록 조회 | 2. 대여 가능 차량 검색 | 3. 반납 예정 차량 검색 ]");
		System.out.print(">>> ");
		int selection = inputInteger();
		int signal =0;
		String sql;
		switch (selection) {
		case 1:
			System.out.println("\n### 전제 대여 기록을 검색합니다.");
			sql = "SELECT * FROM rent_history ORDER BY rent_num ASC";
			signal = 1;
			rentRepository.searchRentList(sql,signal);
			break;
		case 2:
			System.out.println("\n### 대여 가능 차량을 검색합니다.");
			sql = "SELECT * FROM cars WHERE car_status='AVAILABLE' ";
			rentRepository.avCheck(sql);
			break;			
		case 3:
			System.out.println("\n### 반납 예정 차량을 검색합니다.");
			System.out.println("(반납 예정일이 8일 미만인 차량을 조회합니다.)\n");
			sql = "SELECT * FROM rent_history WHERE (TO_DATE(user_exp_date, 'YYYY-MM-DD') - TO_DATE(sysdate, 'YYYY-MM-DD')) <8 and car_rent = 'false'";
			signal = 2;
			rentRepository.searchRentList(sql,signal);
			break;


		default:
			System.out.println("\n### 잘못 입력했습니다.");
		}		
	}
	
	private void accountRent() {
		System.out.println("매출 조회 하실 [렌트번호] 를 입력하세요");
		System.out.print(">>> ");
		int rentNum = inputInteger();
		int signal = 0;
		String date1 = LocalDate.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
		if(rentRepository.rentCheck(rentNum).equals("TRUE")){
			System.out.println("차량이 반납된 대여건입니다.");
			signal =1;
			System.out.println(rentRepository.accountProcessRent(rentNum,signal));
			
		
		}else if(rentRepository.rentCheck(rentNum).equals("false")){
			System.out.println("현재 대여중인 대여 건입니다. 오늘날짜("+date1+")까지 요금 매출이 조회됩니다.");
			signal =2;
			System.out.println(rentRepository.accountProcessRent(rentNum,signal));
			
			
		}
		
		
	}

	private void newRent()  {
		System.out.println("\n========================== 대여 가능 차량 ==========================\n");
		String sql = "SELECT * FROM cars WHERE car_status='AVAILABLE' ";
		rentRepository.avCheck(sql);
		System.out.println("\n================================================================");

		System.out.println("대여하실 차량 번호를 입력하세요");
		System.out.print(">>> ");
		int carNum = inputInteger();
		if(rentRepository.rentCheckCarnum(carNum).equals("ONRENT")||rentRepository.rentCheckCarnum(carNum).equals("REPAIR")){
			System.out.println("대여 혹은 점검중인 차량입니다.");
		} else if (rentRepository.rentCheckCarnum(carNum).equals("AVAILABLE") ) {

			System.out.println("회원번호를 입력하세요");
			System.out.print(">>> ");
			int userNum = inputInteger();

			System.out.println("반납예정일을 입력하세요 (YY/MM/DD)");
			System.out.print(">>> ");
			String expDate = inputString();
			System.out.println("\n========= 대여 확인 =========");
			System.out.println("### 회원 번호 : " + userNum +" 번");
			System.out.println("### 대여 차량 : " + carNum +" 번");
			System.out.println("### 반납 예정일 : " + expDate );
			System.out.println("### 대여 기간 : " +rentRepository.dateMan(expDate) +" 일");	       
			System.out.println("### 예상 비용: " + rentRepository.getFee(carNum)*rentRepository.dateMan(expDate)+" 원");
			System.out.println("==========================");
			System.out.println("\n대여를 진행하시겠습니까? (Y/N)");
			System.out.print(">>> ");
			String confrim = inputString();
			if(confrim.equals("Y")||confrim.equals("y")) {
				rentRepository.rentprocessRenthistory(carNum,userNum,expDate);
				rentRepository.rentprocessCar(carNum, userNum);
			}else{
				System.out.println("대여가 취소되었습니다.");
				return;        	
			} 

		}
	}

	private void extendRent() {
		System.out.println("대여연장 하실 [렌트번호] 를 입력하세요");
		System.out.print(">>> ");
		int rentNum = inputInteger();
		if(rentRepository.rentCheck(rentNum).equals("TRUE")){
			System.out.println("대여 중인 차량이 아닙니다.");
		} else if (rentRepository.rentCheck(rentNum).equals("false")) {
			String sql ="SELECT user_exp_date FROM rent_history where rent_num="+rentNum;
			rentRepository.getExpDate(rentNum);
			System.out.println("연장된 예상 반납일 입력하세요 (YY/MM/DD)");
			System.out.print(">>> ");
			String newExpDate = inputString();
			System.out.println("\n연장을 진행하시겠습니까? (Y/N)");
			System.out.print(">>> ");
			String confrim = inputString();
			if(confrim.equals("Y")||confrim.equals("y")) {
				rentRepository.extendprocessRenthistory(rentNum,newExpDate);
			}else {
				System.out.println("대여가 취소되었습니다.");
				return;        	
			} 
		}

	}
	
	private void returnCar() {
		System.out.println("반납 하실 [렌트번호] 를 입력하세요");
		System.out.print(">>> ");
		int rentNum = inputInteger();
		if(rentRepository.rentCheck(rentNum).equals("TRUE")){
			System.out.println("대여 중인 차량이 아닙니다.");
		} else if (rentRepository.rentCheck(rentNum).equals("false")) {
			System.out.println("\n반납를 진행하시겠습니까? (Y/N)");
			System.out.print(">>> ");
			String confrim = inputString();
			
			if(confrim.equals("Y")||confrim.equals("y")) {
				System.out.println("차량이 반납되었습니다.");				
				rentRepository.returnProcessCar(rentRepository.returnProcessRenthistory(rentNum));			
				
			}else{
				System.out.println("반납이 취소되었습니다.");
				return;        	
			} 
		}
		
	}
}
