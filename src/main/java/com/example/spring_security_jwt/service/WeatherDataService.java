package com.example.spring_security_jwt.service;


import com.example.spring_security_jwt.dto.WeatherDTO;
import com.example.spring_security_jwt.dto.WeatherResponseDTO;
import com.example.spring_security_jwt.entity.WeatherData;
import com.example.spring_security_jwt.repository.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherDataService {

    private WeatherRepo weatherRepo;

    @Value("${api.key}")
    private String apikey;

    @Autowired
    public WeatherDataService(WeatherRepo weatherRepo) {
        this.weatherRepo = weatherRepo;
    }


    public WeatherData getWeatherData(String city){
        WeatherDTO whetherDTO = new WeatherDTO();
        WeatherData weatherData = new WeatherData();
        String url ="http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appId=" + apikey + "&units=metric";
        RestTemplate restTemplate=new RestTemplate();
        WeatherResponseDTO weatherResp = restTemplate.getForObject(url, WeatherResponseDTO.class);

        //all three variable data set into Entity
        weatherData.setTemp(weatherResp.getMain().getTemp());
        weatherData.setPressure(weatherResp.getMain().getPressure());
        weatherData.setHumidity(weatherResp.getMain().getHumidity());

        return weatherData;
    }

    public WeatherData saveWeatherData(String city) {
        WeatherData weatherData = getWeatherData(city);
        return weatherRepo.save(weatherData); // Save to the database
    }
}
