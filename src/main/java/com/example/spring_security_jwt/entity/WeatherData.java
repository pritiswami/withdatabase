package com.example.spring_security_jwt.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "weather_data")
@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "weather_id")
    private int weatherID;

    @Column(name="temp")
    private double temp;

    @Column(name="pressure")
    private double pressure;

    @Column(name="humidity")
    private double humidity;
}
