package com.grocery.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grocery.booking.entity.GroceryItem;
import com.grocery.booking.entity.Order;
import com.grocery.booking.repository.GroceryItemRepository;
import com.grocery.booking.repository.OrderRepository;

@Service
public class GroceryService {

	@Autowired
	private GroceryItemRepository repository;

	@Autowired
	private OrderRepository orderRepository;

	public GroceryItem addGroceryItem(GroceryItem item) {
		try {
			return repository.save(item);
		} catch (Exception e) {
			throw new RuntimeException("Error while adding grocery item: " + e.getMessage());
		}
	}

	public List<GroceryItem> getAllGroceryItems() {
		try {
			return repository.findAll();
		} catch (Exception e) {
			throw new RuntimeException("Error while fetching grocery items: " + e.getMessage());
		}
	}

	public void deleteGroceryItem(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new RuntimeException("Item not found with ID: " + id);
			}
			repository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Error while deleting grocery item: " + e.getMessage());
		}
	}

	public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
		try {
			GroceryItem item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
			item.setName(updatedItem.getName());
			item.setPrice(updatedItem.getPrice());
			item.setQuantity(updatedItem.getQuantity());
			return repository.save(item);
		} catch (Exception e) {
			throw new RuntimeException("Error while updating grocery item: " + e.getMessage());
		}
	}

	public void updateInventory(Long id, Integer newQuantity) {
		try {
			GroceryItem item = repository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
			item.setQuantity(newQuantity);
			repository.save(item);
		} catch (Exception e) {
			throw new RuntimeException("Error while updating inventory: " + e.getMessage());
		}
	}

	public Order createOrder(List<Long> itemIds) {
		try {
			List<GroceryItem> items = repository.findAllById(itemIds);
			double total = 0;

			for (GroceryItem item : items) {
				if (item.getQuantity() <= 0) {
					throw new RuntimeException("Item out of stock: " + item.getName());
				}
				item.setQuantity(item.getQuantity() - 1); // Decrement inventory
				total += item.getPrice();
			}
			repository.saveAll(items);

			Order order = new Order();
			order.setItems(items);
			order.setTotal(total);

			return orderRepository.save(order);
		} catch (Exception e) {
			throw new RuntimeException("Error while creating order: " + e.getMessage());
		}
	}

}