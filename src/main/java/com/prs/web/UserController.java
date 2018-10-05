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
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.utility.JsonResponse;

@Controller
@RequestMapping(path = "/Users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping(path = "/Add")
	public @ResponseBody JsonResponse addNewUser(@RequestBody User u) {
		return saveUser(u);
	}

	@GetMapping(path = "/Get/{id}")
	public @ResponseBody JsonResponse getUser(@PathVariable int id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent())
				return JsonResponse.getInstance(user.get());
			else
				return JsonResponse.getErrorInstance("User not found for id: " + id, null);
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error getting user:  " + e.getMessage(), null);
		}
	}

	@GetMapping(path = "/List")
	public @ResponseBody JsonResponse getAllUsers() {
		try {
			return JsonResponse.getInstance(userRepository.findAll());
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("User list failure:" + e.getMessage(), e);
		}
	}

	@GetMapping(path = "/Remove")
	public @ResponseBody JsonResponse deleteUser(@RequestBody User u) {
		try {
			userRepository.delete(u);
			return JsonResponse.getInstance(u);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}

	@PostMapping(path = "/Change")
	public @ResponseBody JsonResponse updateUser(@RequestBody User u) {
		return saveUser(u);
	}

	@PostMapping(path = "/Authenticate")
	public @ResponseBody JsonResponse authenticate(@RequestBody User u) {
		try {
			Optional<User> user = userRepository.findByUserNameAndPassword(u.getUserName(), u.getPassword());
			if (user.isPresent()) {
				return JsonResponse.getInstance(user.get());
			} else {
				return JsonResponse.getErrorInstance("Invalid Login", null);
			}
		} catch (Exception e) {
			return JsonResponse.getErrorInstance("Error logging in:   " + e.getMessage(), null);

		}
	}

	private @ResponseBody JsonResponse saveUser(@RequestBody User u) {
		try {
			userRepository.save(u);
			return JsonResponse.getInstance(u);
		} catch (DataIntegrityViolationException ex) {
			return JsonResponse.getErrorInstance(ex.getRootCause().toString(), ex);
		} catch (Exception ex) {
			return JsonResponse.getErrorInstance(ex.getMessage(), ex);
		}
	}
}