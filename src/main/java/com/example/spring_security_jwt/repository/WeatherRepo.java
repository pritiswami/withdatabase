package com.example.spring_security_jwt.repository;


import com.example.spring_security_jwt.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WeatherRepo extends JpaRepository<WeatherData,Integer> {
}
