# Redis Master Slave

Master-Replica replication. Use replicaof to make a Redis instance a copy of
another Redis server. 

 1) Redis replication is asynchronous, but we can configure a master to
    stop accepting writes if it appears to be not connected with at least
    a given number of replicas.
 2) Redis replicas are able to perform a partial resynchronization with the
    master if the replication link is lost for a relatively small amount of
    time it can connect back.
 3) Replication is automatic and does not need user intervention. After a
    network partition replicas automatically try to reconnect to masters
    and resynchronize with them.

**Disadvantage**
1. Slaves cannot be master
2. Cannot write on slaves

## Setup Master Slave

1. Start the Master Node - 6379

    in windows

        redis-server
    
    in linux

        src/redis-server
    
2. Create a slave config file `6380.config` or any name with `.conf` extension
    - Copy the 6379 config file and change the tag **port** to 6380

            port 6380

    - And uncomment the 
        
            replicaof 127.0.0.1 6379
        `6379` master node port
        `127.0.0.1` if the master is on the local network

## Configure Master Slave

1. Pom Dependency

    ```xml
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>2.9.0</version>
        <type>jar</type>
        <scope>compile</scope>
    </dependency>
    ```

2. Configure the properties in application.properties

    ```properties
    redis.host=localhost
    redis.port=7001
    redis.password=
    redis.jedis.pool.max-total=16
    redis.jedis.pool.max-idle=8
    redis.jedis.pool.min-idle=4
    ```

    - `localhost` - if the redis is on the same system
    - `6379` - master node port number, in the slave program change it to slaves port
    - `password` - is null as no authentication required for my setup

3. Create JedisClientConfiguration Bean
    ```java
    @Bean
    public JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder JedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        GenericObjectPoolConfig GenericObjectPoolConfig = new GenericObjectPoolConfig();
        GenericObjectPoolConfig.setMaxTotal(maxTotal);
        GenericObjectPoolConfig.setMaxIdle(maxIdle);
        GenericObjectPoolConfig.setMinIdle(minIdle);
        return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build();
    }
    ```

4. Create JedisConnection

    ```java
    @Bean
    public JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        if(!StringUtils.isEmpty(password)) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        }
        redisStandaloneConfiguration.setPort(port);
        return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
    }
    ```

5. Define RedisTemplate

    ```java
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        return redisTemplate;
    }
    ```

## String Impl: Building APIs

```java
@Override
public void setProgrammerAsString(String idKey, String programmer) {
    redisTemplate.opsForValue().set(idKey, programmer);
    redisTemplate.expire(idKey, 10, TimeUnit.SECONDS);
}

@Override
public String getProgrammerAsString(String idKey) {
    return (String) redisTemplate.opsForValue().get(idKey);
}
```

## String Impl: Calling APIs

```java
@PostMapping("/progrmmer-string")
public void addToTopic(@RequestBody Programmer programmer) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    programmerService.setProgrammerAsString(String.valueOf(programmer.getId()), mapper.writeValueAsString(programmer));
}
```
![img](/img/programmer_output.png)
```java	
@GetMapping("/programmer-string/{idKey}")
public String readString(@PathVariable String idKey) {
    return programmerService.getProgrammerAsString(idKey);
}
```
![img](/img/get_programmer_output.png)

## List Impl: Building and Calling API

1. Configuration
    ```java
    @Bean
    @Qualifier("listOperations")
    public ListOperations<String, Programmer> listOperations(RedisTemplate<String, Programmer> redisTemplate){
        return redisTemplate.opsForList();
    }
    ```
2. Build API
    ```java
    @Override
    public void addToProgrammerList(Programmer programmer) {
        ListOps.leftPush(REDIS_LIST_KEY, programmer);
        
    }

    @Override
    public List<Programmer> getProgrammerListMembers() {
        return (List<Programmer>) ListOps.range(REDIS_LIST_KEY, 0, -1);
    }

    @Override
    public Long getProgrmmerListCount() {
        return ListOps.size(REDIS_LIST_KEY);
    }
    ```

3. Calling API
    ```java
    @PostMapping("/programmer-list")
    public void addToProgrammerList(@RequestBody Programmer programmer){
        programmerService.addToProgrammerList(programmer);
    }
    ```
    ![img](/img/add_programmer_list.png)
    ```java
    @GetMapping("/programmer-list")
    public List<Programmer> getProgrammerListMembers() {
        return programmerService.getProgrammerListMembers();
    }
    ```
    ![img](/img/get_programmer_list.png)
    ```java
    @GetMapping("/programmer-list/count")
    public Long getProgrmmerListCount() {
        return programmerService.getProgrmmerListCount();
    }
    ```
## Set Impl: Building and Calling API

1. Configuration
    ```java
    @Bean
    @Qualifier("setOperations")
    public SetOperations<String, Programmer> setOperations(RedisTemplate<String, Programmer> redisTemplate){
        return redisTemplate.opsForSet();
    }
    ```
2. Build API
    ```java
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
    ```

3. Calling API
    ```java
    @PostMapping("/programmer-set")
    public void addToProgrammerSet(@RequestBody Programmer programmer){
        programmerService.addToProgrammerSet(programmer);
    }
    ```
    ![img](/img/add_programmer_set.png)
    ```java
    @GetMapping("/programmer-set")
    public Set<Programmer> getProgrammerSetMembers() {
        return programmerService.getProgrammerSetMembers();
    }
    ```
    ![img](/img/get_programmer_set.png)
    ```java
    @PostMapping("/programmer-set/isEmpty")
    public boolean isSerMember(@RequestBody Programmer programmer) {
        return programmerService.isSerMember(programmer);
    }
    ```
    ![img](/img/isEmpty_Set.png)
