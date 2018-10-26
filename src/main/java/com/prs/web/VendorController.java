package com.prs.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.prs.business.vendor.Vendor;
import com.prs.business.vendor.VendorRepository;
import com.prs.utility.JsonResponse;

@CrossOrigin
@Controller
@RequestMapping(path = "/Vendors")
public class VendorController {
	@Autowired
	private VendorRepository vendorRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewVendor(@RequestBody Vendor v) {
		return saveVendor(v);
	}

	@GetMapping(path = "/Get/{id}")
	public @ResponseBody JsonResponse getVendor(@PathVariable int id) {
		try {
			Optional<Vendor> vendor = vendorRepository.findById(id);
			if (vendor.isPresent())
				return JsonResponse.getInstance(vendor.get());
			else
				return JsonResponse.getErrorInstance("Vendor not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting vendor:  " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/List")
	public @ResponseBody JsonResponse getAllVendors() {
		try {
			return JsonResponse.getInstance(vendorRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Vendor list failure:" + e.getMessage(), e);
		}
	}

	@PostMapping(path = "/Remove")
	public @ResponseBody JsonResponse deleteVendor(@RequestBody Vendor v) {
		try {
			vendorRepository.delete(v);
			return JsonResponse.getInstance(v);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updateVendor(@RequestBody Vendor v) {
		return saveVendor(v);
	}

	private @ResponseBody JsonResponse saveVendor(@RequestBody Vendor v) {
		try {
			vendorRepository.save(v);
			return JsonResponse.getInstance(v);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}