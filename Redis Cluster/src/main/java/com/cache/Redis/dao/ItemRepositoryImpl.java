package com.cache.Redis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cache.Redis.model.Item;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Repository
public class ItemRepositoryImpl extends JdbcDaoSupport implements ItemRepository {

	
	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	private static Logger log = LoggerFactory.getLogger(ItemRepositoryImpl.class);

	private static final String REDIS_LIST_KEY = "ItemList";

	private static final String REDIS_SET_KEY = "ItemSet";

	@Override
	public List<Item> getAllItems() {
		log.info("Inside the dao");
		String sql = "SELECT * FROM Item";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

		List<Item> result = new ArrayList<Item>();
		for (Map<String, Object> row : rows) {
			Item item = new Item();
			item.setId((int) row.get("id"));
			item.setName((String) row.get("name"));
			item.setCategory((String) row.get("category"));
			result.add(item);
		}

		return result;
	}

	@Override
	public Item getItemById(int itemId) {
		String sql = "SELECT * FROM Item WHERE id = ?";
		return (Item) getJdbcTemplate().queryForObject(sql, new Object[] { itemId }, new RowMapper<Item>() {
			@Override
			public Item mapRow(ResultSet row, int rwNumber) throws SQLException {
				Item item = new Item();
				item.setId(row.getInt("id"));
				item.setName(row.getString("name"));
				item.setCategory(row.getString("category"));
				return item;
			}
		});
	}

	@Override
	public void setItemAsString(String itemId, String item) {
		jedisCluster.set(itemId, item);
	}

	@Override
	public String getItemAsString(String itemId) {
		return (String) jedisCluster.get(itemId);
	}

	@Override
	public TreeSet<String> getAllItemAsString() {
		TreeSet<String> keys = new TreeSet<>();
		TreeSet<String> output = new TreeSet<>();
		for (String key : jedisCluster.getClusterNodes().keySet()) {
			log.info("Getting keys from: {}", key);
			JedisPool jedisPool = jedisCluster.getClusterNodes().get(key);
			Jedis jedisConn = jedisPool.getResource();
			try {
				
				keys.addAll(jedisConn.keys("*"));
				for(String opertaionKey: keys) {
					output.add(getItemAsString(opertaionKey));
				}
				
            }catch (Exception e) {
                logger.error("Getting keys error: {}", e);
            }
		}
		return output;
	}
	
	@Override
	public void addFakeItemSet() {
		Item item = new Item();
		for(int i =0 ; i<100; i++) {
			item.setId(i);
			item.setName("Aman" + i);
			item.setCategory("Developer");
			jedisCluster.set(String.valueOf(i), item.toString());
		}
	}
	
	@Override
	public void deleteString(String itemId) {
		 jedisCluster.del(itemId);
		
	}

	@Override
	public void deleteAllString() {
		TreeSet<String> keys = new TreeSet<>();
		for (String key : jedisCluster.getClusterNodes().keySet()) {
			log.info("Getting keys from: {}", key);
			JedisPool jedisPool = jedisCluster.getClusterNodes().get(key);
			Jedis jedisConn = jedisPool.getResource();
			try {
				keys.addAll(jedisConn.keys("*"));
				for(String keyDelete: keys) {
					deleteString(keyDelete);
					log.info("Deleted: {}",keyDelete);
				}
            }catch (Exception e) {
                logger.error("Getting keys error: {}", e);
            }
		}
	}

}
