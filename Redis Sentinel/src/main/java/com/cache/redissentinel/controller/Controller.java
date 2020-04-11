package com.cache.redissentinel.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cache.redissentinel.model.Item;
import com.cache.redissentinel.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {

	@Autowired
	ItemService itemService;

	private static Logger log = LoggerFactory.getLogger(Controller.class);

	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/

	@GetMapping("/item-string")
	@ResponseBody
	public void refreshItemString() throws JsonProcessingException {
		log.info("Refershing Cache");
		List<Item> items = itemService.getAllItems();
		for (Item item : items) {
			log.info("Pushing item {} ", item);
			addToItem(item);
		}
	}

	@PostMapping("/item-string")
	public void addToItem(@RequestBody Item item) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		itemService.setItemAsString(String.valueOf(item.getId()), mapper.writeValueAsString(item));
	}

	@GetMapping("/item-string/{itemId}")
	public String readItemString(@PathVariable String itemId) {
		return itemService.getItemAsString(itemId);
	}


	/*******************************************************************************************************************
	 * List Operations
	 *******************************************************************************************************************/
	@GetMapping("/item-list")
	@ResponseBody
	public void refreshItemList() throws JsonProcessingException {
		log.info("Refershing List Cache");
		List<Item> items = itemService.getAllItems();
		for (Item item : items) {
			log.info("Pushing item {} ", item);
			addToItemList(item);
		}
	}
	
	@PostMapping("/item-list")
	public void addToItemList(@RequestBody Item item) {
		itemService.addToItemList(item);
	}
	
	@GetMapping("/item-list/all")
	public List<Item> getItemListMembers() {
		return itemService.getItemListMembers();
	}
	
	@DeleteMapping("/item-list")
	public void deleteAllItemList() {
		itemService.deleteAllList();
	}
	

	/*******************************************************************************************************************
	 * Set Operations
	 *******************************************************************************************************************/


	@GetMapping("/item-set")
	@ResponseBody
	public void refreshItemSet() throws JsonProcessingException {
		log.info("Refershing List Cache");
		List<Item> items = itemService.getAllItems();
		for (Item item : items) {
			log.info("Pushing item {} ", item);
			addToItemSet(item);
		}
	}
	
	@PostMapping("/item-set")
	public String addToItemSet(@RequestBody Item item) {
		itemService.addToItemSet(item);
		return "ITEM ADDED";
	}
	
	@GetMapping("/item-set/fake")
	public String addFakeItemSet() {
		itemService.addFakeItemSet();
		return "ADDED";
	}
	
	@GetMapping("/item-set/all")
	public Set<Item> getItemSetMembers() {
		return itemService.getItemSetMembers();
	}
	
	@PostMapping("/item-set/isEmpty")
	public String isItemSetMember(@RequestBody Item item) {
		boolean result = itemService.isItemSetMember(item);
		if(result==true) {
			return "PRESENT";
		}
		return "NOT PRESENT";
	}
	
	@DeleteMapping("/item-set")
	public String deleteAllItemSet() {
		itemService.deleteAllSet();
		return "DELETED";
	}
}
