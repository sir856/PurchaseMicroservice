package com.developing.shop.purchase.listeners;

import com.developing.shop.orders.model.Order;
import com.developing.shop.purchase.model.Bill;
import com.developing.shop.purchase.repository.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private BillRepository repository;

    @Autowired
    public RabbitMqListener(BillRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "addOrder")
    public void addOrder(Order order) {
        repository.save(new Bill(order.getId(), order.getTotalCost()));
    }
}
