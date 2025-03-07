package org.example.botservice;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import org.example.botservice.dao.OrderRepository;
import org.example.botservice.dto.Order;
import org.example.botservice.service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;
@Component
public class MBot implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {
    private final String command = "/start";
    @Autowired
    private final OrderRepository orderRepository;
    private final TelegramClient telegramClient;
   private final String buttonOrder = "Заказать";

   public MBot(OrderRepository orderRepository) {
       this.orderRepository = orderRepository;
       telegramClient = new OkHttpTelegramClient(getBotToken());
   }

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
                    .keyboardRow(new KeyboardRow("Список заказов"))
                    .keyboardRow(new KeyboardRow("Информация")).build());
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasMessage() && update.getMessage().getText().equals(buttonOrder) ) {
            long chatId = update.getMessage().getChatId();
            List<Order> allOrders = orderRepository.findAll();
            if (allOrders.size() > 0) {
                try {
                    int range = 0;
                    while (allOrders.size() > 0) {
                        if (range != allOrders.size()) {
                            SendMessage sendMessage = SendMessage.builder()
                                    .chatId(chatId)
                                    .text("Выберите заказ")
                                    .replyMarkup(InlineKeyboardMarkup
                                            .builder()
                                            .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton
                                                    .builder()
                                                    .text(allOrders.get(range).toString())
                                                    .callbackData(allOrders.get(range).getProduct())
                                                    .build()))
                                            .build())
                                    .build();
                            telegramClient.execute(sendMessage);
                        }
                    range++;
                    }

                    } catch(TelegramApiException e){
                        throw new RuntimeException(e);

                    }




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

    @Override
    public String getBotToken() {
        return "7429114213:AAE2-zlkX3fzcYUMyqxyFU83cJKMTLLXXyc";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
