package com.grocery.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;
import com.grocery.booking.service.GroceryService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private GroceryService service;

	@GetMapping("/items")
	public ResponseEntity<List<GroceryItem>> getAvailableItems() {
		try {
			return new ResponseEntity<>(service.getAllGroceryItems(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody List<Long> itemIds) {
	    try {
	        return new ResponseEntity<>(service.createOrder(itemIds), HttpStatus.CREATED);
	    } catch (RuntimeException e) {
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}