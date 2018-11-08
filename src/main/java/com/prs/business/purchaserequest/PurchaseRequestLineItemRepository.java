package com.prs.business.purchaserequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called PurchaseRequestLineItemRepository
//CRUD refers Create, Read, Update, Delete

public interface PurchaseRequestLineItemRepository extends JpaRepository<PurchaseRequestLineItem, Integer> {
	List<PurchaseRequestLineItem> findAllByPurchaseRequestId(int purchaseRequestId);
}
