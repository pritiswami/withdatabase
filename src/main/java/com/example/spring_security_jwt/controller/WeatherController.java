package com.example.spring_security_jwt.controller;


import com.example.spring_security_jwt.entity.WeatherData;
import com.example.spring_security_jwt.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherDataService weatherDataService;

    @Autowired
    public WeatherController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping("/abc")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherData> getWeather(@RequestParam("city") String city, Model model) {
        WeatherData response = weatherDataService.getWeatherData(city);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    public WeatherData saveWeather(@RequestParam String city) {
        return weatherDataService.saveWeatherData(city);
    }


//
//        if(weatherResponse !=null){
//            model.addAttribute("city",weatherResponse.getName());
//            model.addAttribute("country",weatherResponse.getSys().getCountry());
//            model.addAttribute("weatherDescription",weatherResponse.getWeather().get(0).getDescription());
//            model.addAttribute("temperature",weatherResponse.getMain().getTemp());
//            model.addAttribute("humidity",weatherResponse.getMain().getHumidity());
//            model.addAttribute("windspeed",weatherResponse.getWind().getSpeed());
//            String weatherIcon = "wi wi-owm-" +weatherResponse.getWeather().get(0).getId();
//            model.addAttribute("weatherIcon",weatherIcon);
//        } else {
//            model.addAttribute("error","City not found");
//        }
}

