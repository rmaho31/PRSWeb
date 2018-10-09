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
import com.prs.business.product.Product;
import com.prs.business.product.ProductRepository;
import com.prs.utility.JsonResponse;

@Controller
@RequestMapping(path = "/Products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewProduct(@RequestBody Product p) {
		return saveProduct(p);
	}

	@GetMapping(path = "/Get/{id}")
	public @ResponseBody JsonResponse getProduct(@PathVariable int id) {
		try {
			Optional<Product> product = productRepository.findById(id);
			if (product.isPresent())
				return JsonResponse.getInstance(product.get());
			else
				return JsonResponse.getErrorInstance("Product not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting product:  " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/List")
	public @ResponseBody JsonResponse getAllProducts() {
		try {
			return JsonResponse.getInstance(productRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Product list failure:" + e.getMessage(), e);
		}
	}

	@PostMapping(path = "/Remove")
	public @ResponseBody JsonResponse deleteProduct(@RequestBody Product product) {
		try {
			productRepository.delete(product);
			return JsonResponse.getInstance(product);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updateProduct(@RequestBody Product p) {
		return saveProduct(p);
	}

	private @ResponseBody JsonResponse saveProduct(@RequestBody Product p) {
		try {
			productRepository.save(p);
			return JsonResponse.getInstance(p);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}