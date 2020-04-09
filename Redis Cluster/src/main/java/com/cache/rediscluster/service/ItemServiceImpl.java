package com.cache.rediscluster.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.rediscluster.dao.ItemRepository;
import com.cache.rediscluster.model.Item;

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
	public void setItemAsString(String idKey, String Item) {
		itemRepository.setItemAsString(idKey, Item);
	}

	@Override
	public String getItemAsString(String idKey) {
		return itemRepository.getItemAsString(idKey);
	}

	@Override
	public void addToItemList(Item Item) {
		itemRepository.addToItemList(Item);
	}

	@Override
	public List<Item> getItemListMembers() {
		return itemRepository.getItemListMembers();
	}

	@Override
	public Long getItemListCount() {
		return itemRepository.getItemListCount();
	}

	@Override
	public void addToItemSet(Item... Item) {
		itemRepository.addToItemSet(Item);
	}

	@Override
	public Set<Item> getItemSetMembers() {
		return itemRepository.getItemSetMembers();
	}

	@Override
	public boolean isItemSetMember(Item Item) {
		return itemRepository.isItemSetMember(Item);
	}

	@Override
	public void deleteString(String idKey) {
		itemRepository.deleteString(idKey);
	}

	@Override
	public void deleteAllList() {
		itemRepository.deleteAllList();
	}

	@Override
	public void deleteAllSet() {
		itemRepository.deleteAllSet();
	}

	@Override
	public void addFakeItemSet() {
		itemRepository.addFakeItemSet();
	}

}
