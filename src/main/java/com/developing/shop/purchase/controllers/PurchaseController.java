package com.developing.shop.purchase.controllers;

import com.developing.shop.purchase.model.Bill;
import com.developing.shop.purchase.model.CardStatus;
import com.developing.shop.purchase.model.Status;
import com.developing.shop.purchase.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/warehouse")
public class PurchaseController {
    private final BillService billService;

    @Autowired
    public PurchaseController(BillService orderService) {
        this.billService = orderService;
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
    public Bill purchase(@PathVariable("order_id") long id, @RequestParam(  name = "card") CardStatus cardStatus) {
        return billService.purchase(cardStatus, id);
    }

    @PutMapping("/orders/{order_id}/status/{status}")
    public Bill changeStatus(@PathVariable("order_id") long id, @PathVariable("status") Status status) {
        return billService.changeStatus(status, id);
    }

}
