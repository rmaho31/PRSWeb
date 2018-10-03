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
import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;

@Controller   
@RequestMapping(path="/Vendors") 
public class VendorController {
	@Autowired 
	private VendorRepository vendorRepository;

	@PostMapping(path="/Add") 
	public @ResponseBody Vendor addNewVendor (@RequestBody Vendor v) {		
		return vendorRepository.save(v);
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody Vendor getVendor(@RequestParam int id) {
		return vendorRepository.findById(id).get();
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Vendor> getAllVendors() {
		return vendorRepository.findAll();
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody String deleteVendor(@RequestParam int id) {
		try {
			vendorRepository.delete(vendorRepository.findById(id).get());			
		} catch(IllegalArgumentException | NoSuchElementException e) {
			e.printStackTrace();
			return "Error Deleting id not found";
		}
		return "Deleted";
	}
	
	@PostMapping(path="/Change") 
	public @ResponseBody Vendor updateVendor (@RequestBody Vendor v) {
		return vendorRepository.save(v);
	}
}