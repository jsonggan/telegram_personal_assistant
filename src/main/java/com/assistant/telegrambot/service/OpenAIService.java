package com.assistant.telegrambot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIService {

  private final ChatClient chatClient;

  @Autowired
  public OpenAIService(ChatModel chatModel) {
    this.chatClient = ChatClient.builder(chatModel).build();
  }

  public String generateResponse(String userMessage) {
    try {
      String promptText = """
          You are a helpful AI assistant integrated into a Telegram bot.
          Respond to the user's message in a friendly, helpful, and concise manner.
          If the user asks about weather, you can use weather tools if available.

          User message: {userMessage}
          """;

      PromptTemplate promptTemplate = new PromptTemplate(promptText);
      Prompt prompt = promptTemplate.create(Map.of("userMessage", userMessage));

      return chatClient.prompt(prompt).call().content();

    } catch (Exception e) {
      return "I'm sorry, I encountered an error while processing your request. Please try again later.";
    }
  }
}