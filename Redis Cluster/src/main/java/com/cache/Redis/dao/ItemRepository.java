package com.cache.Redis.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cache.Redis.model.Item;

public interface ItemRepository {

	List<Item> getAllItems();
	Item getItemById(int itemId);
	
	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/
	void setItemAsString(String idKey, String Item);
	String getItemAsString(String idKey);
	TreeSet<String> getAllItemAsString();

	/*******************************************************************************************************************
	 * Flush All
	 *******************************************************************************************************************/

	void deleteString(String idKey);
	void deleteAllString();
	
	
	void addFakeItemSet();
}
