package com.example.spring_security_jwt.repository;

import com.example.spring_security_jwt.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {


//  Optional<UserInfo> findByName(String userName);
//
  @Query(value = "select email from user_info where name=:name", nativeQuery = true)
  List<String> findEmailIdByName(String name);


  Optional<UserInfo> findByName(String name);

  @Query(value = "select email, cc_email, bcc_email from user_info where name = :username", nativeQuery = true)
  List<String> getEmailId(String username);

  @Query(value = "select email, cc_email, bcc_email from user_info where name = :username", nativeQuery = true)
  List<List<Object>> getListEmailId(String username);
}