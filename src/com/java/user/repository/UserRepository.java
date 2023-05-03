package com.java.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java.common.DataBaseConnection;
import com.java.user.domain.UserGrade;
import com.java.user.domain.User;

public class UserRepository {

   // 커넥션 객체 받아오기
   private DataBaseConnection connection = DataBaseConnection.getInstance();

   // 회원추가
   public void addUser(User user) {
      System.out.println("repository: " + user);
      String sql = "INSERT INTO rent_users "
               + "(user_num, user_name, phone_number, user_age, user_location) "
               + "VALUES(rent_users_seq.NEXTVAL,?,?,?,?)";

      try (Connection conn = connection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, user.getUserName());
         pstmt.setString(2, user.getPhoneNumber());
         pstmt.setInt(3, user.getUserAge());
         pstmt.setString(4, user.getUserLocation());

         if (pstmt.executeUpdate() == 1) {
            System.out.println("회원가입이 정상 처리되었습니다.");
         } else {
            System.out.println("회원 가입에 실패했습니다. 관리자에게 문의하세요.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   // 회원의 이름으로 정보 검색
   public List<User> findByUserName(String userName, String sql) {
      List<User> userList = new ArrayList<>();      
      try (Connection conn = connection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
         if(!userName.equals(""))pstmt.setString(1, userName);         
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            UserGrade grade = UserGrade.valueOf(rs.getString("user_grade"));
            User user = new User(rs.getInt("user_num"),
                            rs.getString("user_name"),
                            rs.getString("phone_number"),
                            rs.getInt("user_age"),
                            grade,
                            rs.getString("user_location"));
            userList.add(user);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return userList;
   }
   
   // 회원 삭제
      public void deleteUser(int delUserNum) {
         String sql = "DELETE FROM rent_users WHERE user_num=?";
         try(Connection conn = connection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
               pstmt.setInt(1, delUserNum);
               
               if(pstmt.executeUpdate() == 1) {
                  System.out.println("\n### 회원정보가 정상 삭제되었습니다.");
               } else {
                  System.out.println("\n### 검색한 회원의 회원번호으로만 삭제가 가능합니다.");
               }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
}