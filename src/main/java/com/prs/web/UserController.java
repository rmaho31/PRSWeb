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
import com.prs.business.user.User;
import com.prs.business.user.UserRepository;

@Controller   
@RequestMapping(path="/Users") 
public class UserController {
	@Autowired 
	private UserRepository userRepository;

	@PostMapping(path="/Add") 
	public @ResponseBody User addNewUser (@RequestBody User u) {				
		return userRepository.save(u);
	}
	
	@GetMapping(path="/Get")
	public @ResponseBody User getUser(@RequestParam int id) {
		return userRepository.findById(id).get();
	}

	@GetMapping(path="/List")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/Remove")
	public @ResponseBody String deleteUser(@RequestParam int id) {
		try {
			userRepository.delete(userRepository.findById(id).get());			
		} catch(IllegalArgumentException | NoSuchElementException e) {
			e.printStackTrace();
			return "Error Deleting";
		}
		return "Deleted";
	}
	
	@PostMapping(path="/Change") 
	public @ResponseBody User updateUser(@RequestBody User u) {
			return userRepository.save(u);
	}
	
	@PostMapping(path="/Authenticate") 
	public @ResponseBody User authenticate(@RequestBody User u) {

		return userRepository.findByUserNameAndPassword(u.getUserName(), u.getPassword()).get();
	}
}