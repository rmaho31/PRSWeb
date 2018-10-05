package com.prs.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.utility.JsonResponse;

@Controller
@RequestMapping(path = "/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		return savePurchaseRequestLineItem(prli);
	}

	@GetMapping(path = "/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequestLineItem(@PathVariable int id) {
		try {
			Optional<PurchaseRequestLineItem> purchasRequest = purchaseRequestLineItemRepository.findById(id);
			if (purchasRequest.isPresent())
				return JsonResponse.getInstance(purchasRequest.get());
			else
				return JsonResponse.getErrorInstance("PurchaseRequestLineItem not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting purchasRequest:  " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/List")
	public @ResponseBody JsonResponse getAllPurchaseRequestLineItems() {
		try {
			return JsonResponse.getInstance(purchaseRequestLineItemRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("PurchaseRequestLineItem list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping(path = "/Remove")
	public @ResponseBody JsonResponse deletePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			purchaseRequestLineItemRepository.delete(prli);
			return JsonResponse.getInstance(prli);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		return savePurchaseRequestLineItem(prli);
	}

	private @ResponseBody JsonResponse savePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			purchaseRequestLineItemRepository.save(prli);
			return JsonResponse.getInstance(prli);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}