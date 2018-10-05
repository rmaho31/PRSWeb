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
import com.prs.business.purchaserequest.PurchaseRequestRepository;
import com.prs.utility.JsonResponse;

@Controller
@RequestMapping(path = "/PurchaseRequests")
public class PurchaseRequestController {
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewPurchaseRequest(@RequestBody PurchaseRequest pr) {
		return savePurchaseRequest(pr);
	}

	@GetMapping(path = "/Get/{id}")
	public @ResponseBody JsonResponse getPurchaseRequest(@PathVariable int id) {
		try {
			Optional<PurchaseRequest> purchasRequest = purchaseRequestRepository.findById(id);
			if (purchasRequest.isPresent())
				return JsonResponse.getInstance(purchasRequest.get());
			else
				return JsonResponse.getErrorInstance("PurchaseRequest not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting purchasRequest:  " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/List")
	public @ResponseBody JsonResponse getAllPurchaseRequests() {
		try {
			return JsonResponse.getInstance(purchaseRequestRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("PurchaseRequest list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping(path = "/Remove")
	public @ResponseBody JsonResponse deletePurchaseRequest(@RequestBody PurchaseRequest pr) {
		try {
			purchaseRequestRepository.delete(pr);
			return JsonResponse.getInstance(pr);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updatePurchaseRequest(@RequestBody PurchaseRequest pr) {
		return savePurchaseRequest(pr);
	}

	private @ResponseBody JsonResponse savePurchaseRequest(@RequestBody PurchaseRequest pr) {
		try {
			purchaseRequestRepository.save(pr);
			return JsonResponse.getInstance(pr);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}