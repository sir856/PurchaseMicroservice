package com.developing.shop.purchase.repository;

import com.developing.shop.purchase.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
