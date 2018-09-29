package com.prs.web;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.prs.business.purchaserequest.PurchaseRequest;
import com.prs.business.purchaserequest.PurchaseRequestRepository;

@Controller   
@RequestMapping(path="/PurchaseRequests") 
public class PurchaseRequestController {
	@Autowired 
	private PurchaseRequestRepository purchaseRequestRepository;

	@PostMapping(path="/Add") 
	public @ResponseBody String addNewPurchaseRequest (@RequestBody PurchaseRequest pr) {
				purchaseRequestRepository.save(pr);
		return "Saved";
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody PurchaseRequest getPurchaseRequest(@RequestParam int id) {
			return purchaseRequestRepository.findById(id).get();
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequest> getAllPurchaseRequests() {
		return purchaseRequestRepository.findAll();
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody String getPurchaseRequestByID(@RequestParam int id) {
		try {
			purchaseRequestRepository.delete(purchaseRequestRepository.findById(id).get());			
		} catch(IllegalArgumentException | NoSuchElementException e) {
			e.printStackTrace();
			return "Error Deleting";
		}
		return "Deleted";
	}
	
	@PostMapping(path="/Change") 
	public @ResponseBody String updatePurchaseRequest (@RequestBody PurchaseRequest pr) {
			purchaseRequestRepository.save(pr);
			return "Updated";
	}
}