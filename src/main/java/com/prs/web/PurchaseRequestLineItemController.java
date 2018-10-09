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
import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.utility.JsonResponse;

@Controller
@RequestMapping(path = "/PurchaseRequestLineItems")
public class PurchaseRequestLineItemController {
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewPurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jsr = savePurchaseRequestLineItem(prli);
		if (jsr.getMessage().equals(JsonResponse.SUCCESS)) {
			try {
				updatePurchaseRequestTotal(prli);
				return jsr;
			} catch (Exception ex) {
				return JsonResponse.getErrorInstance(ex.getMessage(), ex);
			}
		} else {
			return jsr;
		}
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

	@PostMapping(path = "/Remove")
	public @ResponseBody JsonResponse deletePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		try {
			purchaseRequestLineItemRepository.delete(prli);
			updatePurchaseRequestTotal(prli);
			return JsonResponse.getInstance(prli);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updatePurchaseRequestLineItem(@RequestBody PurchaseRequestLineItem prli) {
		JsonResponse jsr = savePurchaseRequestLineItem(prli);
		if (jsr.getMessage().equals(JsonResponse.SUCCESS)) {
			try {
				updatePurchaseRequestTotal(prli);
				return jsr;
			} catch (Exception ex) {
				return JsonResponse.getErrorInstance(ex.getMessage(), ex);
			}
		} else {
			return jsr;
		}
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

	private void updatePurchaseRequestTotal(PurchaseRequestLineItem prli) throws Exception{
		PurchaseRequest pr = purchaseRequestRepository.findById(prli.getPurchaseRequest().getId()).get();
		double total = 0;
		for (PurchaseRequestLineItem i : purchaseRequestLineItemRepository.findAllByPurchaseRequestId(pr.getId())) {
			total += i.getQuantity() * i.getProduct().getPrice();
		}
		pr.setTotal(total);
		purchaseRequestRepository.save(pr);
	}
}