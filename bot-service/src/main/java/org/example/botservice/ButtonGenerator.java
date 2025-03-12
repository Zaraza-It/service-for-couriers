package org.example.botservice;

import org.example.botservice.dto.ProductOnly;
import org.example.botservice.dto.TableProduct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonGenerator {


    List<InlineKeyboardRow> buttons = new ArrayList<>();

    public InlineKeyboardMarkup inlineKeyboardMarkup(List<TableProduct> products) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(createButtons(products)).build();
        return keyboardMarkup;
    }

    public List<InlineKeyboardRow> createButtons(List<TableProduct> products) {
        if (products.size() < 10) {
            int range = 0;
            while (range != products.size()) {
                buttons.addAll(generateButtons(products.get(range).toString()));
                range++;

            }

        }
        return buttons;
    }

public List<InlineKeyboardRow> generateButtons(String text) {
        List<InlineKeyboardRow> list = new ArrayList<>();
        list.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                .text(text)
                .callbackData(text).build()));

        return list;

}

}
