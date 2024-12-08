package com.grocery.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.service.GroceryService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private GroceryService service;

	@PostMapping("/add")
	public ResponseEntity<GroceryItem> addItem(@RequestBody GroceryItem item) {
		try {
			return new ResponseEntity<>(service.addGroceryItem(item), HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/items")
	public ResponseEntity<List<GroceryItem>> getAllItems() {
		try {
			return new ResponseEntity<>(service.getAllGroceryItems(), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable Long id) {
		try {
			service.deleteGroceryItem(id);
			return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<GroceryItem> updateItem(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
		try {
			return new ResponseEntity<>(service.updateGroceryItem(id, updatedItem), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/inventory/{id}")
	public ResponseEntity<String> updateInventory(@PathVariable Long id, @RequestParam Integer quantity) {
		try {
			service.updateInventory(id, quantity);
			return new ResponseEntity<>("Inventory updated successfully", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
