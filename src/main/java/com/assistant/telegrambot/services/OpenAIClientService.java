package com.assistant.telegrambot.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIClientService {

  private final ChatClient chatClient;
  private final ToolCallbackProvider toolCallbackProvider;

  @Autowired
  public OpenAIClientService(ChatModel chatModel, ToolCallbackProvider toolCallbackProvider) {
    this.chatClient = ChatClient.builder(chatModel).build();
    this.toolCallbackProvider = toolCallbackProvider;
  }

  public String generateResponse(String userMessage) {
    try {
      String promptText = """
          You are a helpful AI assistant integrated into a Telegram bot.
          Respond to the user's message in a friendly, helpful, and concise manner.

          You have access to weather tools. When users ask about weather for a specific city,
          use the getWeather function to provide accurate weather information.

          Rule:
          Bold: Place two asterisks on both sides of the text: **bold text**.
          Italic: Place two underscores on both sides of the text: _italic text_.
          Strikethrough: Place two tildes on both sides of the text: ~~strikethrough text~~.
          Monospace (Code): Wrap text in single backticks: `monospace text`.
          Do not use markdown formatting in your response.

          User message: {userMessage}
          """;

      PromptTemplate promptTemplate = new PromptTemplate(promptText);
      Prompt prompt = promptTemplate.create(Map.of("userMessage", userMessage));

      return chatClient.prompt(prompt)
          .toolCallbacks(toolCallbackProvider)
          .call()
          .content();

    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return "I'm sorry, I encountered an error while processing your request. Please try again later.";
    }
  }
}