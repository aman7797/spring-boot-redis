package com.cache.Redis.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.Redis.dao.ItemRepository;
import com.cache.Redis.model.Item;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Override
	public List<Item> getAllItems() {
		return itemRepository.getAllItems();
	}

	@Override
	public Item getItemById(int itemId) {
		return itemRepository.getItemById(itemId);
	}

	@Override
	public void setItemAsString(String itemId, String Item) {
		itemRepository.setItemAsString(itemId, Item);
	}

	@Override
	public String getItemAsString(String itemId) {
		return itemRepository.getItemAsString(itemId);
	}

	@Override
	public TreeSet<String> getAllItemAsString() {
		return itemRepository.getAllItemAsString();
	}
	
	@Override
	public void deleteString(String itemId) {
		itemRepository.deleteString(itemId);
	}


	@Override
	public void addFakeItemSet() {
		itemRepository.addFakeItemSet();
	}

	@Override
	public void deleteAllString() {
		itemRepository.deleteAllString();
		
	}

	

}
