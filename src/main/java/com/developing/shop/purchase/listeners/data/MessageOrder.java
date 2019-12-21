package com.developing.shop.purchase.listeners.data;

import java.io.Serializable;

public class MessageOrder implements Serializable {
    private long id;
    private long price;

    public MessageOrder(long id, long price) {
        this.id = id;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }
}
