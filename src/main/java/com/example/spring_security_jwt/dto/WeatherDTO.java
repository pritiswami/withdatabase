package com.example.spring_security_jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

    @JsonProperty("temp")
    private double temp;

    @JsonProperty("pressure")
    private double pressure;

    @JsonProperty("humidity")
    private double humidity;

}
