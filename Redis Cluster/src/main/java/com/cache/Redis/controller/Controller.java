package com.cache.Redis.controller;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cache.Redis.model.Item;
import com.cache.Redis.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {

	@Autowired
	ItemService itemService;

	private static Logger log = LoggerFactory.getLogger(Controller.class);

	/*******************************************************************************************************************
	 * Refersh From DB
	 *******************************************************************************************************************/
	@GetMapping("/item-string")
	@ResponseBody
	public void refreshItemString() throws JsonProcessingException {
		log.info("Refershing Cache");
		List<Item> items = itemService.getAllItems();
		for (Item item : items) {
			log.info("Pushing item {} ", item);
			addToItemString(item);
		}
	}

	/*******************************************************************************************************************
	 * Set Data
	 *******************************************************************************************************************/
	@PostMapping("/item-string")
	public void addToItemString(@RequestBody Item item) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		itemService.setItemAsString(String.valueOf(item.getId()), mapper.writeValueAsString(item));
	}

	/*******************************************************************************************************************
	 * Get Data
	 *******************************************************************************************************************/

	@GetMapping("/item-string/{itemId}")
	public String readItemString(@PathVariable String itemId) {
		return itemService.getItemAsString(itemId);
	}
	
	@GetMapping("/item-string/all")
	public TreeSet<String> readAllItemString() {
		return itemService.getAllItemAsString();
	}

	/*******************************************************************************************************************
	 * Set Fake Data
	 *******************************************************************************************************************/

	@GetMapping("/fake-data")
	public void fake() {
		itemService.addFakeItemSet();
	}

	/*******************************************************************************************************************
	 * Delete Operation
	 *******************************************************************************************************************/
	
	
	@DeleteMapping("/item-string/{itemId}")
	public void deleteItemString(@PathVariable String itemId) {
		itemService.deleteString(itemId);
	}
	
	@DeleteMapping(value="/item-string/all")
	public String deleteAllString() {
		itemService.deleteAllString();
		return "DELETED";
	}
}
