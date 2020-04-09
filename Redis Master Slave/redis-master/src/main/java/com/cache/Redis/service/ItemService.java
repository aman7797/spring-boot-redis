package com.cache.Redis.service;

import java.util.List;
import java.util.Set;

import com.cache.Redis.model.Item;

public interface ItemService {

	
	List<Item> getAllItems();
	Item getItemById(int itemId);
	
	//String
	void setItemAsString(String idKey, String Item);
	String getItemAsString(String idKey);

	// List
	void addToItemList(Item Item);
	List<Item> getItemListMembers();
	Long getItemListCount();

	// Set

	void addToItemSet(Item... Item);
	Set<Item> getItemSetMembers();
	boolean isItemSetMember(Item Item);
	
	void deleteString(String idKey);
	void deleteAllList();
	void deleteAllSet();
	void addFakeItemSet();
}
