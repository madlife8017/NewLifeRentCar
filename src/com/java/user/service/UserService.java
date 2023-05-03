package com.java.user.service;

import static com.java.view.AppUI.*;

import java.util.ArrayList;
import java.util.List;

import com.java.common.AppService;
import com.java.user.domain.User;
import com.java.user.repository.UserRepository;

public class UserService implements AppService {

	private final UserRepository userRepository = new UserRepository();

	@Override
	public void start() {
		while(true) {
			userManagementScreen();
			int selection = inputInteger();

			switch (selection) {
			case 1:
				join();
				break;
			case 2:
				showSearchResult();
				break;
			case 3:
				deleteUser();
				break;
			case 4:
				return; //메인 화면으로 돌아가기

			default:
				System.out.println("메뉴를 다시 입력하세요.");
			}
			System.out.println("\n====== 계속 진행하시려면 ENTER를 누르세요 ======");
			inputString();
		}
	}

	// 회원 추가 비즈니스 로직
	private void join() {
		System.out.println("\n====== 회원 가입을 진행합니다. ======");
		System.out.print("# 회원명: ");
		String name = inputString();

		System.out.print("# 전화번호: ");
		String phone = inputString();

		System.out.print("# 나이: ");
		int age = inputInteger();

		System.out.print("# 거주지: ");
		String location = inputString();

		User user = new User();
		user.setUserName(name);
		user.setPhoneNumber(phone);
		user.setUserAge(age);
		user.setUserLocation(location);

		userRepository.addUser(user);
	}

	private int searchChoose() {
		System.out.println("\n=============== 회원 검색 조건을 선택하세요. ===============");
		System.out.println("[ 1. 전체 회원 | 2. 개인 회원 ]");
		System.out.print(">>> ");
		int selection = inputInteger();


		switch (selection) {
		case 1:
			System.out.println("\n### 전체 회원을 검색합니다.");   

			return 1;         

		case 2:
			System.out.println("\n### 개인 회원을 검색합니다.");   

			return 2;   

		default:
			System.out.println("\n### 잘못 입력했습니다.");
		}
		return 0;

	}
	//회원 이름으로 검색 비즈니스 로직

	private List<User> searchUser() {
		System.out.println("\n### 조회할 회원의 이름을 입력하세요.");
		System.out.println(">>> ");
		String name = inputString();
		String sql = "SELECT * FROM rent_users WHERE user_name=?";
		return userRepository.findByUserName(name,sql);
	}
	private List<User> searchUserTotal() {
		String name = "";
		String sql = "SELECT * FROM rent_users";
		return userRepository.findByUserName(name,sql);
	}

	private int showSearchResult() {
		int signal = searchChoose();
		List<User> users = new ArrayList<>();

		if(signal==1) users = searchUserTotal();
		else if(signal==2) users = searchUser();      


		if(!users.isEmpty()) {
			System.out.println("\n======================= 회원 조회 결과 =======================");
			for(User user : users) {
				System.out.println(user);
			}
		} else {
			System.out.println("\n### 조회 결과가 없습니다.");
		}
		return users.size();
	}

	//회원 탈퇴 비즈니스 로직
	private void deleteUser() {
		if(showSearchResult() > 0) {
			System.out.println("\n### 탈퇴할 회원의 번호를 입력하세요.");
			System.out.print(">>> ");
			int delUserNum = inputInteger();
			userRepository.deleteUser(delUserNum);
		}
	}
}