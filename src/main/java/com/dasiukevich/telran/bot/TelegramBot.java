package com.dasiukevich.telran.bot;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.log4j.Log4j2;

@SuppressWarnings("deprecation")
@Log4j2
@Component
public class TelegramBot extends TelegramLongPollingBot {

	public final String messageHi = "Hi. Can I help you?";
	public final String maximUserId = "1146281309";
	
	@Override
	public void onUpdateReceived(Update update) {
//		String message = update.getMessage().getText();
		String chatId = update.getMessage().getChatId().toString();
		sendMsg(chatId, messageHi);
//		sendMsg(maximUserId, update.toString());
		log.log(Level.ALL, update.toString());
	}

	@Override
	public String getBotUsername() {
		return "CommonTestBot";
	}

	@Override
	public String getBotToken() {
		return "7084928382:AAH1nmJVum_7aruWMhh3xR98TW0nK8CyAIE";

	}

	private synchronized void sendMsg(String chatId, String message) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(message);
		setButtons(sendMessage);
		setInline(sendMessage);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			log.log(Level.TRACE, "Exception: ", e.toString());
		}
	}
	
	public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Hi"));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Help"));
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("Contact"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
	
	private void setInline(SendMessage sendMessage) {
		List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
		List<InlineKeyboardButton> buttons1 = new ArrayList<>();
		InlineKeyboardButton button_1 = new InlineKeyboardButton();
		button_1.setText("Press me");
		button_1.setCallbackData("17");
		buttons1.add(button_1);
		buttons.add(buttons1);
		
		InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
		markupKeyboard.setKeyboard(buttons);
		sendMessage.setReplyMarkup(markupKeyboard);
	}
}
