package org.example.botservice;

import org.example.botservice.repository.OrderRepository;
import org.example.botservice.dto.Order;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ButtonGenerator {
    private OrderRepository orderRepository;
    List<InlineKeyboardRow> buttons = new ArrayList<>();

    public ButtonGenerator(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public InlineKeyboardMarkup inlineKeyboardMarkup(List<Order> products) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup
                .builder()
                .keyboard(createButtons(products)).build();
        return keyboardMarkup;
    }

    private List<InlineKeyboardRow> createButtons(List<Order> products) {
        if (products.size() <= 10) {
            int size = 0;
            for (int range = 10; range >= products.size(); range++) {
                buttons.addAll(generateButtons(products, size));
                size++;
            }
            return buttons;

        }// else if (products.size() > 10) {}
        return buttons;
    }

    public List<InlineKeyboardRow> generateButtons(List<Order> products, int size) {
        List<InlineKeyboardRow> list = new ArrayList<>();
        if (orderRepository.findById(products.get(size).getOrderId()) != null
                && orderRepository.findCallbackByOrderId(products.get(size).getOrderId()) == false) {
            UUID uuid = UUID.randomUUID();
            Order order = Order.builder()
                    .orderId(products.get(size).getOrderId())
                    .product(products.get(size).getProduct())
                    .address(products.get(size).getAddress())
                    .customerName(products.get(size).getCustomerName())
                    .purchasesPrice(products.get(size).getPurchasesPrice())
                    .purchasesSell(products.get(size).getPurchasesSell())
                    .offsetDateTime(products.get(size).getOffsetDateTime())
                    .callback(uuid)
                    .soldStatus(products.get(size).getSoldStatus())
                    .build();
            list.add(new InlineKeyboardRow(InlineKeyboardButton.builder()
                    .text(products.get(size).getProduct())
                    .callbackData(uuid.toString()).build()));
            orderRepository.save(order);
        } else if (orderRepository.findById(products.get(size).getOrderId()) != null
                && orderRepository.findCallbackByOrderId(products.get(size).getOrderId()) == true) {

        }
        return list;
    }
}








