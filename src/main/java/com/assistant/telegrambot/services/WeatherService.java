package com.assistant.telegrambot.services;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Tool(description = "Get weather information for a specific city. Provide the city name to get current weather conditions.")
  public String getWeather(String cityName) {
    // For now, returning a mock response. In a real implementation,
    // you would integrate with a weather API like OpenWeatherMap, WeatherAPI, etc.
    if (cityName == null || cityName.trim().isEmpty()) {
      return "Please provide a valid city name to get weather information.";
    }

    return String.format(
        "Weather in %s: Sunny, 22°C (72°F). Light breeze from the west. Humidity: 60%%. Perfect day to go outside!",
        cityName.trim());
  }
}
