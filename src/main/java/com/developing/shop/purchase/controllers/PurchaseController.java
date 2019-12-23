package com.developing.shop.purchase.controllers;

import com.developing.shop.orders.messageListeners.data.StatusChange;
import com.developing.shop.purchase.model.Bill;
import com.developing.shop.purchase.model.CardStatus;
import com.developing.shop.purchase.model.Status;
import com.developing.shop.purchase.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/warehouse")
public class    PurchaseController {
    private Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final BillService billService;
    private final RabbitTemplate template;

    @Autowired
    public PurchaseController(BillService orderService, RabbitTemplate template) {
        this.billService = orderService;
        this.template = template;
    }


    @GetMapping("/orders")
    public List<Bill> getBills() {
        return billService.getBills();
    }

    @PostMapping(value = "/orders")
    public Bill createBill(@RequestBody Bill bill) {
        return billService.addBill(bill);
    }

    @PutMapping("/orders/{order_id}/payment")
    public Bill purchase(@PathVariable("order_id") long id, @RequestParam(name = "card") CardStatus cardStatus) {
        Bill bill = billService.purchase(cardStatus, id);
        setStatusChange(bill);
        if (bill.getStatus() == Status.FAILED) {
            template.setExchange("purchaseExchange");
            template.convertAndSend("cancelItem", bill.getOrderId());
            template.convertAndSend("cancelOrder", bill.getOrderId());
        }
        return bill;
    }

    @PutMapping("/orders/{order_id}/status/{status}")
    public Bill changeStatus(@PathVariable("order_id") long id, @PathVariable("status") Status status) {
        Bill bill = billService.changeStatus(status, id);
        if (bill.getStatus() == Status.CANCELLED) {
            template.setExchange("purchaseExchange");
            template.convertAndSend("cancelItem", bill.getOrderId());
            template.convertAndSend("cancelOrder", bill.getOrderId());
        }

        setStatusChange(bill);

        return bill;
    }

    private void setStatusChange(Bill bill) {
        template.setExchange("orderExchange");
        template.convertAndSend("status",
                new StatusChange(bill.getOrderId(), bill.getStatus().toString()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(Exception ex) {
        logger.error("Illegal argument exception", ex);

        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
