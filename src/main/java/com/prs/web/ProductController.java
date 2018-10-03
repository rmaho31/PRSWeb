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
import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;

@Controller   
@RequestMapping(path="/Products") 
public class ProductController{
	@Autowired 
	private ProductRepository productRepository;

	@PostMapping(path="/Add") 
	public @ResponseBody Product addNewProduct (@RequestBody Product p) {
		return productRepository.save(p);
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody Product getProduct(@RequestParam int id) {
			return productRepository.findById(id).get();
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody String deleteProduct(@RequestParam int id) {
		try {
			productRepository.delete(productRepository.findById(id).get());			
		} catch(IllegalArgumentException | NoSuchElementException e) {
			e.printStackTrace();
			return "Error Deleting";
		}
		return "Deleted";
	}
	
	@PostMapping(path="/Change") 
	public @ResponseBody Product updateProduct (@RequestBody Product p) {
		return productRepository.save(p);
	}
}