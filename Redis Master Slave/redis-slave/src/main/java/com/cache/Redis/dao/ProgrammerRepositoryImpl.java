package com.cache.Redis.dao;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.cache.Redis.model.Programmer;

@Repository
public class ProgrammerRepositoryImpl implements ProgrammerRepository {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/*@Autowired
	@Qualifier("listOperations")
	private ListOperations<String, Programmer> ListOps;*/
	
	private static final String REDIS_LIST_KEY="ProgrammerList";

	private static final String REDIS_SET_KEY = "ProgrammerSet";
	
	@Override
	public void setProgrammerAsString(String idKey, String programmer) {
		// TODO Auto-generated method stub
		redisTemplate.opsForValue().set(idKey, programmer);
		//expire.refresh.delete
		redisTemplate.expire(idKey, 10, TimeUnit.SECONDS);
	}

	@Override
	public String getProgrammerAsString(String idKey) {
		return (String) redisTemplate.opsForValue().get(idKey);
	}

	//list
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

	@Override
	public void addToProgrammerSet(Programmer... programmer) {
		redisTemplate.opsForSet().add(REDIS_SET_KEY, programmer);
	}

	@Override
	public Set<Programmer> getProgrammerSetMembers() {
		// TODO Auto-generated method stub
		return (Set)redisTemplate.opsForSet().members(REDIS_SET_KEY);
	}

	@Override
	public boolean isSerMember(Programmer programmer) {
		// TODO Auto-generated method stub
		return redisTemplate.opsForSet().isMember(REDIS_SET_KEY, programmer);
	}

	
}
