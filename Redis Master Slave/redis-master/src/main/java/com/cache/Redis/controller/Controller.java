package com.cache.Redis.controller;

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

import com.cache.Redis.model.Item;
import com.cache.Redis.model.Programmer;
import com.cache.Redis.service.ItemService;
import com.cache.Redis.service.ProgrammerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {

	@Autowired
	ProgrammerService programmerService;

	@Autowired
	ItemService itemService;

	private static Logger log = LoggerFactory.getLogger(Controller.class);

	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/
	@PostMapping("/progrmmer-string")
	public void addToProgrammer(@RequestBody Programmer programmer) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		programmerService.setProgrammerAsString(String.valueOf(programmer.getId()),
				mapper.writeValueAsString(programmer));
	}

	@GetMapping("/programmer-string/{idKey}")
	public String readString(@PathVariable String idKey) {
		return programmerService.getProgrammerAsString(idKey);
	}

	@DeleteMapping("/programmer-string/{idKey}")
	public void deleteString(@PathVariable String idKey) {
		programmerService.deleteString(idKey);
	}

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

	@DeleteMapping("/item-string/{idKey}")
	public void deleteItem(@PathVariable String idKey) {
		programmerService.deleteString(idKey);
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
	
	@PostMapping("/programmer-list")
	public void addToProgrammerList(@RequestBody Programmer programmer) {
		programmerService.addToProgrammerList(programmer);
	}

	@GetMapping("/programmer-list")
	public List<Programmer> getProgrammerListMembers() {
		return programmerService.getProgrammerListMembers();
	}

	@GetMapping("/programmer-list/count")
	public Long getProgrmmerListCount() {
		return programmerService.getProgrmmerListCount();
	}

	@DeleteMapping("/programmer-list")
	public void deleteAllList() {
		programmerService.deleteAllList();
	}

	/*******************************************************************************************************************
	 * Set Operations
	 *******************************************************************************************************************/
	@PostMapping("/programmer-set")
	public void addToProgrammerSet(@RequestBody Programmer programmer) {
		programmerService.addToProgrammerSet(programmer);
	}

	@GetMapping("/programmer-set")
	public Set<Programmer> getProgrammerSetMembers() {
		return programmerService.getProgrammerSetMembers();
	}

	@PostMapping("/programmer-set/isEmpty")
	public boolean isSerMember(@RequestBody Programmer programmer) {
		return programmerService.isSerMember(programmer);
	}

	@DeleteMapping("/programmer-set")
	public void deleteAllSet() {
		programmerService.deleteAllSet();
	}

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
