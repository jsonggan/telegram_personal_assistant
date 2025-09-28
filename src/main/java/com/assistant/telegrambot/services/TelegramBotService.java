package com.assistant.telegrambot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class TelegramBotService implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
  private final TelegramClient telegramClient;
  private final String botToken;
  private final OpenAIClientService openAIService;

  @Autowired
  public TelegramBotService(@Value("${telegram.bot.token}") String botToken,
      OpenAIClientService openAIService) {
    this.botToken = botToken;
    this.openAIService = openAIService;
    telegramClient = new OkHttpTelegramClient(botToken);
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public LongPollingUpdateConsumer getUpdatesConsumer() {
    return this;
  }

  @Override
  public void consume(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String userMessage = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();
      String userName = update.getMessage().getFrom().getFirstName();

      String aiResponse = generateAIResponse(userMessage, userName);

      SendMessage message = SendMessage
          .builder()
          .chatId(chatId)
          .text(aiResponse)
          .build();

      try {
        telegramClient.execute(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
        sendErrorMessage(chatId);
      }
    }
  }

  private String generateAIResponse(String userMessage, String userName) {
    try {
      String contextualMessage = String.format("User %s says: %s", userName, userMessage);

      return openAIService.generateResponse(contextualMessage);
    } catch (Exception e) {
      return "Hello! I'm your AI assistant. I'm having some technical difficulties right now, but I'm here to help you. Please try your message again.";
    }
  }

  private void sendErrorMessage(long chatId) {
    try {
      SendMessage errorMessage = SendMessage
          .builder()
          .chatId(chatId)
          .text("I apologize, but I'm experiencing some technical difficulties. Please try again in a moment.")
          .build();
      telegramClient.execute(errorMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}