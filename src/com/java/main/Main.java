package com.java.main;

import static com.java.view.AppUI.inputInteger;

import com.java.view.AppUI;

public class Main {

	public static void main(String[] args) {
		AppUI.Startlogo();
		AppContorller controller = new AppContorller();
		
		while(true){			
			AppUI.startScreen();
			int selectNumber = inputInteger();
			System.out.println(selectNumber);
			controller.chooseSystem(selectNumber);
		}
	}
}
