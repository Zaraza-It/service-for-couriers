package org.example.serviceforcouriers.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.serviceforcouriers.controller.ReceivingOrderController;
import org.example.serviceforcouriers.controller.dto.OrderRequest;
import org.example.serviceforcouriers.entity.Order;
import org.example.serviceforcouriers.exceptions.ProductNotFoundException;
import org.example.serviceforcouriers.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    public Order getOrderById (Long productId) {
        return orderRepository
                .findById(productId)
                .orElseThrow(() ->  new ProductNotFoundException("Товар не был найден"));
    }

    public List<Order> getAllOrders () {
        return orderRepository.findAll();
    }

    public Order saveSoldStatus(Long productId, boolean status) {
      try {
          Order order = getOrderById(productId);
          order.setSoldStatus(status);
          return orderRepository.save(order);
      } catch (Exception e) {
          logger.error(e);
      }
        logger.info("Успешно сохранён заказ");
        return null;
    }

    public Order newOrder (String product, String customerName, String executorName, String address, BigDecimal purchasesPrice, BigDecimal purchasesSell) {
        return orderRepository.save(new Order(product, customerName, executorName, address, purchasesPrice, purchasesSell));
    }

    public void createOrder(OrderRequest request) {
        try {
            if (orderRepository.findOrderByProduct(request.getProduct()) == null) {
               Order order =  new Order()
                order.setCustomerName(request.getCustomerName());
                order.setExecutorName(request.getExecutorName());
                order.setAddress(request.getAddress());
                order.setPurchasesPrice(request.getPurchasesPrice());
                order.setPurchasesSell(request.getPurchasesSell());
                order.setProduct(request.getProduct());
                orderRepository.save(order);
            }
        }catch (Exception e) {
            logger.error(e);
        }
    }

}
