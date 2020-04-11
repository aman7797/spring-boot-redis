package com.cache.redissentinel.dao;

import java.util.List;
import java.util.Set;

import com.cache.redissentinel.model.Item;


public interface ItemRepository {

	List<Item> getAllItems();
	Item getItemById(int itemId);
	
	/*******************************************************************************************************************
	 * String Operations
	 *******************************************************************************************************************/
	void setItemAsString(String idKey, String Item);
	String getItemAsString(String idKey);

	/*******************************************************************************************************************
	 * List Operations
	 *******************************************************************************************************************/

	void addToItemList(Item Item);
	List<Item> getItemListMembers();
	Long getItemListCount();

	/*******************************************************************************************************************
	 * Set Operations
	 *******************************************************************************************************************/
	void addToItemSet(Item... Item);
	Set<Item> getItemSetMembers();
	boolean isItemSetMember(Item Item);

	/*******************************************************************************************************************
	 * Flush All
	 *******************************************************************************************************************/

	void deleteString(String idKey);
	void deleteAllList();
	void deleteAllSet();
	
	
	void addFakeItemSet();
}
