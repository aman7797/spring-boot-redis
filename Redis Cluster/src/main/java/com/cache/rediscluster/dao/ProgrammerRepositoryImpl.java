package com.cache.rediscluster.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cache.rediscluster.model.Programmer;


@Repository
public class ProgrammerRepositoryImpl implements ProgrammerRepository {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
		
	private static final String REDIS_LIST_KEY="ProgrammerList";

	private static final String REDIS_SET_KEY = "ProgrammerSet";
	
	/*******************************************************************************************************************
	 * 												String Operations
	 *******************************************************************************************************************/
	@Override
	public void setProgrammerAsString(String idKey, String programmer) {
		// TODO Auto-generated method stub
		redisTemplate.opsForValue().set(idKey, programmer);
		//expire.refresh.delete
//		redisTemplate.expire(idKey, 10, TimeUnit.SECONDS);
	}

	@Override
	public String getProgrammerAsString(String idKey) {
		return (String) redisTemplate.opsForValue().get(idKey);
	}

	/*******************************************************************************************************************
	 * 												List Operations
	 *******************************************************************************************************************/
	@Override
	public void addToProgrammerList(Programmer programmer) {
		redisTemplate.opsForList().leftPush(REDIS_LIST_KEY, programmer);
		
	}

	@Override
	public List<Programmer> getProgrammerListMembers() {
		return (List)redisTemplate.opsForList().range(REDIS_LIST_KEY, 0, -1);
	}

	@Override
	public Long getProgrmmerListCount() {
		return redisTemplate.opsForList().size(REDIS_LIST_KEY);
	}

	/*******************************************************************************************************************
	 * 												Set Operations
	 *******************************************************************************************************************/
	@Override
	public void addToProgrammerSet(Programmer... programmer) {
		redisTemplate.opsForSet().add(REDIS_SET_KEY, programmer);
	}

	@Override
	public Set<Programmer> getProgrammerSetMembers() {
		return (Set)redisTemplate.opsForSet().members(REDIS_SET_KEY);
	}

	@Override
	public boolean isSerMember(Programmer programmer) {
		return redisTemplate.opsForSet().isMember(REDIS_SET_KEY, programmer);
	}

	/*******************************************************************************************************************
	 * 												Flush All
	 *******************************************************************************************************************/
	@Override
	public void deleteString(String idKey) {
		System.out.println("Delete String");
		redisTemplate.delete(idKey);
	}
	
	@Override
	public void deleteAllList() {
		Set<String> keys = redisTemplate.keys(REDIS_LIST_KEY);
		for (String key : keys) {
			System.out.println("Deleteing ::" + key);
			redisTemplate.delete(key);
		} 
	}

	@Override
	public void deleteAllSet() {
		Set<String> keys = redisTemplate.keys(REDIS_SET_KEY);
		for (String key : keys) {
			System.out.println("Deleteing ::" + key);
			redisTemplate.delete(key);
		}
		
	}
}
