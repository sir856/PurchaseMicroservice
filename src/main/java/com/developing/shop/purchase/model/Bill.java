package com.developing.shop.purchase.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @Column
    private long orderId;

    @Column
    @Enumerated(EnumType.STRING)
    private CardStatus cardAuthorizationInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private long price;

    @JsonCreator
    public Bill(long orderId, long price) {
        this.orderId = orderId;
        this.status = Status.COLLECTED;
        this.cardAuthorizationInfo = CardStatus.UNAUTHORIZED;
        this.price = price;
    }

    public Bill() {
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public CardStatus getCardAuthorizationInfo() {
        return cardAuthorizationInfo;
    }

    public long getPrice() {
        return price;
    }

    public void setCardAuthorizationInfo(CardStatus cardAuthorizationInfo) {
        this.cardAuthorizationInfo = cardAuthorizationInfo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
