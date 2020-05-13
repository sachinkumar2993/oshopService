package com.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VO.UserVO;
import com.modals.ApplicationResposne;
import com.modals.User;
import com.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ecart")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/authentictae")
	public ResponseEntity<ApplicationResposne<User>> authentictae(@Valid @RequestBody UserVO userVO) {
		String result = "failure";
		ApplicationResposne<User> appResp = new ApplicationResposne<>();
		Optional<User> userOp = userRepository.findByUsernameAndPassword(userVO.getUsername(), userVO.getPassword());
		if (userOp.isPresent()) {
			result = "Success";
			appResp.setDataObj(userOp.get());
			appResp.setStaus(result);
		}
		return new ResponseEntity<ApplicationResposne<User>>(appResp, HttpStatus.OK);

	}

	@PostMapping("/createUser")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserVO userVO) {
		User user = new User();
		BeanUtils.copyProperties(userVO, user);
		user = userRepository.save(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);

	}

	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getUsers() {
		List<User> userList = userRepository.findAll();
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);

	}

	@GetMapping("/getUserById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user= new User();
		Optional<User> userOp = userRepository.findById(id);
		if(userOp.isPresent()) {
			user=userOp.get();
			user.setPassword("");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

}
