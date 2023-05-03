package com.java.car.service;

import static com.java.common.CarCondition.*;
import static com.java.view.AppUI.*;

import java.util.ArrayList;
import java.util.List;

import com.java.car.domain.Car;
import com.java.car.domain.CarGrade;
import com.java.car.domain.CarSize;
import com.java.car.repository.CarRepository;
import com.java.common.AppService;
import com.java.common.CarCondition;


public class CarService implements AppService {

	private final CarRepository carRepository = new CarRepository();

	@Override
	public void start() {
		while(true) {
			carManagementScreen();
			int selection = inputInteger();

			switch (selection) {
			case 1:
				// 신규 차량 등록 메서드
				insertNewCar();
				break;
			case 2:
				// 차량 검색 메서드
				searchCarData();
				break;
			case 3:
				// 차량 정보 수정 메서드
				modifyCarMenu();
				break;
			case 4:
				// 차량 정보 삭제 메서드
				deleteCar();
				break;
			case 5:
				// 메인 화면으로 돌아가기
				System.out.println("\n첫 화면으로 돌아갑니다.\n");
				return;

			default:
				System.out.println("메뉴를 잘못 입력하셨습니다. 다시 입력해주세요.");
			}
			System.out.println("\n====== 계속 진행하시려면 ENTER를 누르세요 ======");
			inputString();

		}
	}

	// 차량 삭제 메서드
	private void deleteCar() {
		// 전체 차량 정보를 자동으로 검색 후 결과가 있다면 차량 번호로 타겟 선택 및 삭제 진행
		List<Car> cars = carRepository.searchCarList(ALL, "");
		showSearchResult(cars);

		if(cars.size() > 0) {
			System.out.println("정보를 삭제할 차량번호를 선택해주세요. 정보 삭제를 취소하려면 0을 입력해주세요.");
			System.out.print(">>> ");
			int inputCarNum = inputInteger();

			if(inputCarNum != 0) {
				int count = 0; // 해당 번호 차량 존재 여부 확인용 카운트
				for(Car car : cars) {
					if(car.getCarNum() == (inputCarNum)) {
						carRepository.deleteCarDB(inputCarNum);
					} else {
						count++;
					}
				}
				if(count == cars.size()) System.out.println("검색된 차량 번호를 입력하세요.");
			}
		}
	}

	// 차량 정보 수정 메서드
	private void modifyCarMenu() {
		// 전체 차량 정보를 자동으로 검색 후 결과가 있다면 차량 번호로 타겟 선택 및 수정 진행
		CarCondition condition = ALL;
		List<Car> cars = carRepository.searchCarList(condition, "");
		showSearchResult(cars);

		if(cars.size() > 0) {
			System.out.println("정보를 수정할 차량번호를 입력해주세요. 정보 수정을 취소하려면 0을 입력해주세요.");
			System.out.print(">>> ");
			int inputCarNum = inputInteger();

			if(inputCarNum != 0) {
				int count = 0; // 해당 번호 차량 존재 여부 확인용 카운트
				for(Car car : cars) {
					if(car.getCarNum() == (inputCarNum)) {
						carModifyScreen();
						int selection = inputInteger();

						switch (selection) {
						case 1:
							// 차량 ID 수정
							condition = CAR_ID;
							break;
						case 2:
							// 차량 모델명 수정
							condition = CAR_MODEL;
							break;
						case 3:
							// 차량 구분 수정
							condition = CAR_SIZE;
							break;
						case 4:
							// 차량 요금 수정
							condition = CAR_FEE;
							break;
						case 5:
							// 차량 등급 수정
							condition = CAR_GRADE;
							break;
						case 6:
							// 차량 정보 수정 취소
							System.out.println("차량 정보 수정을 취소합니다.");
							return;

						default:
							System.out.println("잘못 입력했습니다.");
						}

						String keyword = "";
						switch (condition) {
						case CAR_ID:
							System.out.print("# 수정할 차량 ID : ");
							keyword = "'" + inputString().toUpperCase() + "'";
							break;
						case CAR_MODEL:
							System.out.print("# 수정할 차량 모델명 : ");
							keyword = "'" + inputString().toUpperCase() + "'";
							break;
						case CAR_SIZE:
							System.out.println("# 수정할 차량 구분 [ Compact, Small, Midsize, Big, SUV, Van, Foreign ]");
							System.out.print(" : ");
							String ipCarSize = inputString().toUpperCase();
							CarSize carSize = null;
							try {
								carSize = CarSize.valueOf(ipCarSize);
							} catch (Exception e) {
								System.out.println("규격 외의 구분입니다. 수정을 다시 진행해주세요.");
								return;
							}
							keyword = "'" + ipCarSize + "'";
							break;
						case CAR_FEE:
							System.out.print("# 수정할 차량 요금 : ");
							keyword = Integer.toString(inputInteger());
							break;
						case CAR_GRADE:
							System.out.print("# 수정할 차량 등급 [ S, A, B, C ] : ");
							String ipCarGrade = inputString().toUpperCase();
							CarGrade carGrade = null;

							try {
								carGrade = CarGrade.valueOf(ipCarGrade);
							} catch (Exception e) {
								System.out.println("규격 외의 등급입니다. 수정을 다시 진행해주세요.");
								return;
							}
							keyword = "'" + ipCarGrade + "'";
							break;
						default:
							System.out.println("\n### 잘못 입력했습니다.");
						}
						if(condition != ALL) {
							carRepository.modifyCarDB(inputCarNum, condition, keyword);
						}
					} else {
						count++;
					}
				}
				if(count == cars.size()) System.out.println("검색된 차량 번호를 입력하세요.");
			} else {
				System.out.println("차량 정보 수정을 취소합니다.");
			}
			return;
		} else {
			System.out.println("등록된 차량이 없습니다.");
			return;
		}
	}

	// 차량 검색 정보 출력 메서드
	private void showSearchResult(List<Car> cars) {

		// 전달 받은 차량 리스트가 있다면 출력
		if(cars.size() > 0) {
			for(Car car : cars) {
				System.out.println(car);
			}
			System.out.printf("\n============================ 차량 검색 결과 (총 %d건) ============================\n", cars.size());
		} else {
			System.out.println("\n### 검색 결과가 없습니다.");
		}

		return;

	}

	// 차량 검색 메서드
	private void searchCarData() {
		System.out.println("\n=============== 차량 검색 조건을 선택하세요. ===============");
		System.out.println("[ 1. 차종 검색 | 2. 차량 ID 검색 | 3. 이용 중인 차량 검색 | 4. 렌트 가능 차량 검색 | 5. 전체 차량 검색 ]");
		System.out.print(">>> ");
		int selection = inputInteger();

		CarCondition condition = ALL;
		String keyword = "";
		List<Car> cars = new ArrayList<>();

		switch (selection) {
		case 1:
			System.out.println("\n### 차종으로 검색합니다.");
			condition = CAR_MODEL;
			System.out.print("# 검색할 차량 모델명 : ");
			keyword = "'%" + inputString().toUpperCase() + "%'";
			cars = carRepository.searchCarList(condition, keyword);
			showSearchResult(cars);
			break;
		case 2:
			System.out.println("\n### 차량 ID로 검색합니다.");
			condition = CAR_ID;
			System.out.print("# 검색할 차량 ID : ");
			keyword = "'%" + inputString().toUpperCase() + "%'";
			cars = carRepository.searchCarList(condition, keyword);
			showSearchResult(cars);
			break;
//		case 3:
//			System.out.println("\n### 차량 상태로 검색합니다.");
//			condition = CAR_STATUS;
//			break;
		case 3:
			System.out.println("\n### 이용 중인 차량을 검색합니다.");
			condition = ONRENT;
			cars = carRepository.searchCarList(condition, keyword);
			showSearchResult(cars);
			rentUserSearch(cars);
			break;
		case 4:
			System.out.println("\n### 렌트 가능한 차량을 검색합니다.");
			condition = AVAILABLE;
			cars = carRepository.searchCarList(condition, keyword);
			showSearchResult(cars);
			break;
		case 5:
			System.out.println("\n### 전체 정보를 검색합니다.");
			cars = carRepository.searchCarList(condition, keyword);
			showSearchResult(cars);
			break;

		default:
			System.out.println("\n### 잘못 입력했습니다.");
		}

		return;

	}

	// 이용 중인 차량의 사용자 정보 검색 메서드
	private void rentUserSearch(List<Car> cars) {
		System.out.println("이용 중인 차량의 사용자 정보를 검색하시겠습니까? [Y / N]");
		System.out.print(">>> ");
		String anwser = inputString().toUpperCase();
		
		if(anwser.equals("Y")) {
			System.out.println(" 사용자 정보를 검색할 차량번호를 입력해주세요. 검색을 취소하려면 0을 입력해주세요.");
			System.out.print(">>> ");
			int searchNum = inputInteger();
			
			if(searchNum != 0) {
				int count = 0; // 해당 번호 차량 존재 여부 확인용 카운트
				for(Car car : cars) {
					if(car.getCarNum() == (searchNum)) {
						// 이용 중인 차량의 사용자 정보를 DB에서 검색하는 메서드
						carRepository.rentUserSearchDB(searchNum);
					}
				}
				if(count == cars.size()) System.out.println("검색된 차량 번호를 입력하세요.");
			} else {
				System.out.println("차량 정보 수정을 취소합니다.");
			}
		} else if(anwser.equals("N")) {
			System.out.println("검색 메뉴로 돌아갑니다.");
			return;
		} else {
			System.out.println("잘못 입력하셨습니다.");
		}
	}

	// 신규 차량 등록 메서드
	private void insertNewCar() {

		System.out.println("\n======= 신규 차량을 등록합니다. =======");
		System.out.print("# 차량 ID : ");
		String carId = inputString().toUpperCase();

		System.out.print("# 차량 모델명 : ");
		String carModel = inputString().toUpperCase();

		System.out.println("# 차량 구분 [ Compact, Small, Midsize, Big, SUV, Van, Foreign ]");
		System.out.print(" : ");
		String ipCarSize = inputString().toUpperCase();
		CarSize carSize = null;

		try {
			carSize = CarSize.valueOf(ipCarSize);
		} catch (Exception e) {
			System.out.println("규격 외의 구분입니다. 등록을 다시 진행해주세요.");
			return;
		}

		System.out.print("# 차량 대여 비용 : ");
		int carFee = inputInteger();

		System.out.print("# 차량 등급 [ S, A, B, C ] : ");
		String ipCarGrade = inputString().toUpperCase();
		CarGrade carGrade = null;

		try {
			carGrade = CarGrade.valueOf(ipCarGrade);
		} catch (Exception e) {
			System.out.println("규격 외의 등급입니다. 등록을 다시 진행해주세요.");
			return;
		}

		Car newCar = new Car();
		newCar.setCarId(carId);
		newCar.setCarModel(carModel);
		newCar.setCarSize(carSize);
		newCar.setCarFee(carFee);
		newCar.setCarGrade(carGrade);

		carRepository.addCar(newCar);

	}

}
