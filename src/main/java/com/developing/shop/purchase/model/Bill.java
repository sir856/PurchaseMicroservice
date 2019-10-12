package com.developing.shop.purchase.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long orderId;

    @Column
    @Enumerated(EnumType.STRING)
    private CardStatus cardAuthorizationInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonCreator
    public Bill(long orderId) {
        this.orderId = orderId;
        this.status = Status.COLLECTING;
        this.cardAuthorizationInfo = CardStatus.UNAUTHORIZED;
    }

    public Bill(){}


    public long getId() {
        return id;
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
