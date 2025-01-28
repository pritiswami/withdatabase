package com.example.spring_security_jwt.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {


      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;
      private String name;
     private String email;
      private String roles;
      private String password;
      private String ccEmail;
      private String bccEmail;


    public UserInfo(String[] toEmailArray, String[] ccEmailArray, String[] bccEmailArray) {
    }
}
