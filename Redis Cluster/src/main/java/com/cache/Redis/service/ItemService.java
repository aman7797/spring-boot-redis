package com.cache.Redis.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cache.Redis.model.Item;

public interface ItemService {

	
	List<Item> getAllItems();
	Item getItemById(int itemId);
	
	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/
	void setItemAsString(String itemId, String Item);
	String getItemAsString(String itemId);
	TreeSet<String> getAllItemAsString();


	/*******************************************************************************************************************
	 * Flush All
	 *******************************************************************************************************************/
	void deleteString(String itemId);
	void deleteAllString();
	
	void addFakeItemSet();
}
