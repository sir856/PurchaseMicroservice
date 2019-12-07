package com.developing.shop.purchase.service;

import com.developing.shop.purchase.model.Bill;
import com.developing.shop.purchase.model.CardStatus;
import com.developing.shop.purchase.model.Status;

import java.util.List;

public interface BillService {
    Bill addBill(Bill bill);

    Bill getBillById(long id);

    List<Bill> getBills();

    Bill purchase(CardStatus status, long id);

    Bill changeStatus(Status status, long id);
}
