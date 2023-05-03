package com.java.rent.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.java.car.domain.Car;
import com.java.car.domain.CarGrade;
import com.java.car.domain.CarSize;
import com.java.common.DataBaseConnection;
import com.java.rent.domain.Car_temp;
import com.java.rent.domain.Rent;

public class RentRepository {

	private static DataBaseConnection connection = DataBaseConnection.getInstance();

	//전체 대여 상황
	public static List<Rent> searchRentList(String sql,int signal){		
		List<Rent> rentList = new ArrayList<>();
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()) {				
				Rent rent = new Rent(						
						rs.getInt("rent_num"),
						rs.getInt("user_num"),
						rs.getInt("car_num"),
						rs.getDate("user_rent_date"),
						rs.getDate("user_exp_date"),
						rs.getDate("user_return_date"),
						rs.getString("car_rent")
						);				
				rentList.add(rent);
				if(signal==1)System.out.println(rent.form1());				
				else if(signal==2)System.out.println(rent.form2());
				else if(signal==3)System.out.println(rent.form2());

			}

		} catch (Exception e) {
			e.printStackTrace();			
		}

		return rentList;
	}

	public void rentprocessRenthistory(int carNum, int userNum, String expDate) {
		String sql="INSERT INTO rent_history(rent_num, user_num, car_num,user_rent_date,user_exp_date,user_return_date) "
				+ "VALUES(rent_history_seq.NEXTVAL,"+userNum+","+carNum+", sysdate,'"+expDate+"',null)";

		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(pstmt.executeUpdate() == 1) {
				System.out.printf("\n### 신규 대여가 완료되었습니다.\n");
			} else {
				System.out.println("\n### 신규 대여를 실패하였습니다.\n");
			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
	}

	public void rentprocessCar(int carNum, int userNum) {
		String sql="UPDATE cars SET user_num = "+userNum+", car_status='ONRENT' WHERE car_num = "+ carNum;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(pstmt.executeUpdate() == 1) {
				System.out.printf("\n### 차량 리스트에 신규 대여 정보가 반영되었습니다.\n");
			} else {
				System.out.println("\n### 차량 리스트에 신규 대여 정보 반영을 실패하였습니다.\n");
			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
	}
	public void extendprocessRenthistory(int rentNum, String newExpDate) {
		String sql="UPDATE rent_history SET user_exp_date = '"+newExpDate+"' WHERE rent_num = "+ rentNum;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(pstmt.executeUpdate() == 1) {
				System.out.printf("\n### 차량 리스트에 신규 대여 정보가 반영되었습니다.\n");
			} else {
				System.out.println("\n### 차량 리스트에 신규 대여 정보 반영을 실패하였습니다.\n");
			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
	}





	public int getFee(int carNum) {
		String sql="SELECT car_fee From cars WHERE car_num =" +carNum;		
		int fee=0;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {

				fee = rs.getInt("car_fee");
			}

		} catch (Exception e) {			
			e.printStackTrace();

		}
		return fee;		
	}

	public void getExpDate(int rentNum) {
		String sql="SELECT user_exp_date From rent_history WHERE rent_num =" +rentNum;		
		Date date = null;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {

				date = rs.getDate("user_exp_date");

			}
			System.out.println("\n### 기존 예상 반납 일은 [" + date+"] 입니다\n");

		} catch (Exception e) {			
			e.printStackTrace();

		}

	}

	public String rentCheck(int rentNum) {
		String check ="";
		String sql="SELECT car_rent From rent_history WHERE rent_num =" +rentNum;	
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {
				check =rs.getString("car_rent");

			}

		} catch (Exception e) {			
			e.printStackTrace();

		}
		return check;

	}
	public String rentCheckCarnum(int carNum) {
		String check ="";
		String sql="SELECT car_status From cars   WHERE car_num="+carNum;	
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {
				check =rs.getString("car_status");		
			}
			

		} catch (Exception e) {			
			e.printStackTrace();

		}
		return check;

	}

	public static int avCheck(String sql) {
		int count=0;
		List<Car_temp> carList = new ArrayList<>();
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {

				Car_temp car = new Car_temp(
						rs.getInt("car_num"),
						rs.getString("car_id"),
						rs.getString("car_model"),
						rs.getString("car_size"),
						rs.getString("car_grade"),
						rs.getString("car_status")					
						);
				System.out.println("### 차량 번호 :" +car.getCarNum()+", 차량 모델 :" +car.getCarModel()+
						" 차량 사이즈 : "+car.getCarSize()+", 차량 등급 : "+car.getCarGrade());
				count++;

			}

		} catch (Exception e) {			
			e.printStackTrace();

		}		
		return count;

	}
	public static int avCheck2(String sql) {
		int count=0;
		List<Car_temp> carList = new ArrayList<>();
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {
					
				count++;

			}

		} catch (Exception e) {			
			e.printStackTrace();

		}		
		return count;

	}

	public void returnProcessCar(int carNum) {	
		String sql="UPDATE cars SET user_num =null, car_status='AVAILABLE' WHERE car_num = "+ carNum;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(pstmt.executeUpdate() == 1) {
				System.out.printf("\n### 차량 리스트에 반납 정보가 반영되었습니다.\n");
			} else {
				System.out.println("\n### 차량 리스트에 반납 정보 반영을 실패하였습니다.\n");
			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
	}
	public int returnProcessRenthistory(int rentNum) {	
		String sql="UPDATE rent_history SET car_rent ='TRUE', user_return_date=sysdate WHERE rent_num = "+ rentNum;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			if(pstmt.executeUpdate() == 1) {
				System.out.printf("\n### 차량이 반납되었습니다. \n");
			} else {
				System.out.println("\n### 차량 반납에 실패하였습니다. \n");
			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
		
		String sql2="SELECT car_num FROM rent_history WHERE rent_num = "+ rentNum;
		int carNum=0;
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql2);
				ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()) {

				carNum = rs.getInt("car_num");

			}
		} catch (Exception e) {			
			e.printStackTrace();

		}
		return carNum;
	}
	public String accountProcessRent(int rentNum, int signal) {
		String sql ="SELECT * FROM rent_history r "
				+ "INNER JOIN rent_users u "
				+ "ON u.user_num = r.user_num "
				+ "INNER JOIN cars c "
				+ "On r.car_num = c.car_num "
				+ "WHERE rent_num="+rentNum;
		String name="";
		String today =LocalDate.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));
		Date rentDate=null;
		
		Date format1 = null;
		Date format2 = null;
		long diffDays=0;
		int fee=0;	
		String rent="";
		String retur="";
		
		
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
        
        
		
		try(Connection conn = connection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();) {	
			while(rs.next()) {

						name = rs.getString("user_name");
						rentDate = rs.getDate("user_rent_date");	
						rent = format.format(rentDate);
							
						
						fee= rs.getInt("car_fee");
						if(signal==1) {
							Date returnDate=null;	
							returnDate = rs.getDate("user_return_date");	
							retur = format.format(returnDate);								
							today=retur;
						}
						
			}
			
			try {
				 format1 = new SimpleDateFormat("yy/MM/dd").parse(rent);
				 format2 = new SimpleDateFormat("yy/MM/dd").parse(today);
				 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				System.out.println("잘못입력하셨습니다.");			
				e.printStackTrace();
			}
			
			long diffSec = (format2.getTime() - format1.getTime()) / 1000;
			diffDays = diffSec/ (24*60*60);

		} catch (Exception e) {			
			e.printStackTrace();
		}
		return "[렌트번호 "+rentNum+"/ 이용자 :" +name+ " / "+rentDate+" 부터 "+today+ " 까지 총 요금 매출 " + ((diffDays+1)*fee)+"원 입니다.]";
		
	}
	
	public long dateMan(String expDate) {
		String date1 = LocalDate.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));

		Date format1 = null;
		Date format2 = null ;
		try {
			format1 = new SimpleDateFormat("yy/MM/dd").parse(date1);
			format2 = new SimpleDateFormat("yy/MM/dd").parse(expDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("잘못입력하셨습니다.");			
			e.printStackTrace();
		}
		long diffSec = (format2.getTime() - format1.getTime()) / 1000;

		long diffDays = diffSec/ (24*60*60);
		return diffDays;
		
	}
}
