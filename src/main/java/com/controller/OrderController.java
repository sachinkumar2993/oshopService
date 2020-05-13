package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.VO.ItemVO;
import com.VO.OrderVO;
import com.modals.Item;
import com.modals.Order;
import com.modals.Product;
import com.modals.Shipping;
import com.repository.OrderRepository;
import com.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ecart")
public class OrderController {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	@PostMapping("/createOrder")
	public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderVO orderVO) {
		Order order = new Order();
		order.setUser(userRepository.findByUsernameAndUserId(orderVO.getUsername(), orderVO.getUserId()).get());

		List<Item> itemList = new ArrayList<>();

		for (ItemVO i : orderVO.getItems()) {
			Product product = new Product();
			BeanUtils.copyProperties(i.getProduct(), product);
			Item item = new Item();
			item.setProduct(product);
			item.setQuantity(i.getQuantity());
			itemList.add(item);
		}
		order.setItems(itemList);

		Shipping shipping = new Shipping();
		BeanUtils.copyProperties(orderVO.getShipping(), shipping);
		order.setShipping(shipping);

		order.setDateCreated(new Date());

		order = orderRepository.save(order);
		return new ResponseEntity<Order>(HttpStatus.OK);

	}

	@GetMapping("/getOrdersByUser/{userId}")
	public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
		List<Order> orderList = orderRepository.findByUserUserId(userId);
		return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);

	}

	@GetMapping("/getOrders")
	public ResponseEntity<List<Order>> getOrders() {
		List<Order> orderList = orderRepository.findAll();
		return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);

	}

}
