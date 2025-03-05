package org.example.botservice.emoji;

import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButtonPollType;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

public class Emoji implements LongPollingSingleThreadUpdateConsumer {
    private static final Logger log = LoggerFactory.getLogger(Emoji.class);
    private final String command = "/start";
   private final String buttonOrder = "Заказать";
   TelegramClient telegramClient = new OkHttpTelegramClient("7429114213:AAE2-zlkX3fzcYUMyqxyFU83cJKMTLLXXyc");
    @Override
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals(command) ) {
            long chatId = update.getMessage().getChatId();
            String answer = EmojiParser.parseToUnicode(":space_invader:");
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(answer)
                    .build();
            message.setReplyMarkup(ReplyKeyboardMarkup
                    .builder()
                    .keyboardRow(new KeyboardRow("Заказать"))
                    .keyboardRow(new KeyboardRow("Информация")).build());
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasMessage() && update.getMessage().getText().equals(buttonOrder) ) {
            long chatId = update.getMessage().getChatId();
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text("Выберите заказ")
                    .replyMarkup(InlineKeyboardMarkup
                            .builder()
                            .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                                    .builder()
                                    .text("Заказать")
                                    .callbackData("create_order")
                                    .build()))
                            .build())
                    .build();
            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        } else if (update.hasCallbackQuery()) {

             String callData = update.getCallbackQuery().getData();
             Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
             long chatId = update.getCallbackQuery().getMessage().getChatId();
             if (callData.equals("create_order")) {
                 String answer = "Марка нажата";
                 EditMessageText new_message = EditMessageText.builder()
                         .chatId(chatId)
                         .messageId(messageId)
                         .text(answer)
                         .build();
                 try {
                     telegramClient.execute(new_message);
                 } catch (TelegramApiException e) {
                     throw new RuntimeException(e);
                 }
             }

        }
    }
}
