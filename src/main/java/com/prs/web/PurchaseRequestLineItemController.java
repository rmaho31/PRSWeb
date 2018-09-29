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
import com.prs.business.purchaserequest.PurchaseRequestLineItem;
import com.prs.business.purchaserequest.PurchaseRequestLineItemRepository;

@Controller   
@RequestMapping(path="/PurchaseRequestLineItems") 
public class PurchaseRequestLineItemController {
	@Autowired 
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepository;

	@PostMapping(path="/Add") 
	public @ResponseBody String addNewPurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
				purchaseRequestLineItemRepository.save(prli);
		return "Saved";
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody PurchaseRequestLineItem getPurchaseRequestLineItem(@RequestParam int id) {
			return purchaseRequestLineItemRepository.findById(id).get();
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<PurchaseRequestLineItem> getAllPurchaseRequestLineItems() {
		return purchaseRequestLineItemRepository.findAll();
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody String getPurchaseRequestLineItemByID(@RequestParam int id) {
		try {
			purchaseRequestLineItemRepository.delete(purchaseRequestLineItemRepository.findById(id).get());			
		} catch(IllegalArgumentException | NoSuchElementException e) {
			e.printStackTrace();
			return "Error Deleting";
		}
		return "Deleted";
	}
	
	@PostMapping(path="/Change") 
	public @ResponseBody String updatePurchaseRequestLineItem (@RequestBody PurchaseRequestLineItem prli) {
			purchaseRequestLineItemRepository.save(prli);
			return "Updated";
	}
}