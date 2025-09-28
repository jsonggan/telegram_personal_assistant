package com.assistant.telegrambot;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.assistant.telegrambot.services.WeatherService;

@SpringBootApplication
public class AssistantTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssistantTelegramBotApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
	}
}
