package com.prs.business.purchaserequest;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called PurchaseRequestLineItemRepository
//CRUD refers Create, Read, Update, Delete

public interface PurchaseRequestLineItemRepository extends CrudRepository<PurchaseRequestLineItem, Integer>{
	List<PurchaseRequestLineItem> findAllByPurchaseRequestId(int purchaseRequestId);
}
