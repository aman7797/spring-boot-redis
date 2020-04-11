# Redis Sentinel

Redis Sentinel provides high availability for Redis. In practical terms this means that using Sentinel you can create a Redis deployment that resists without human intervention certain kinds of failures.

Redis Sentinel also provides other collateral tasks such as monitoring, notifications and acts as a configuration provider for clients.



**Advantage**
- **Monitoring**. Sentinel constantly checks if your master and replica instances are working as expected.
Notification. Sentinel can notify the system administrator, or other computer programs, via an API, that something is wrong with one of the monitored Redis instances.
- **Automatic failover**. If a master is not working as expected, Sentinel can start a failover process where a replica is promoted to master, the other additional replicas are reconfigured to use the new master, and the applications using the Redis server are informed about the new address to use when connecting.
- **Configuration provider**. Sentinel acts as a source of authority for clients service discovery: clients connect to Sentinels in order to ask for the address of the current Redis master responsible for a given service. If a failover occurs, Sentinels will report the new address.
- **Hardware** - We can setup fully working Sentinel deployment with three nodes. Redis Cluster requires at least six nodes.
- **Simplicity** - usually it is easier to maintain and configure.
- Deployment that consist of multiple nodes where one is elected as master and the rest are slaves. It adds `high availability` since in case of master failure one of the slaves will be automatically promoted to master. 


**Disadvantage**

- Sentinel manages the failover, it doesn't configure Redis for HA
- The exception is that adding slaves can improve your read performance (in case you direct read requests to the slaves).
- It is `not scalable since the master node is the only node that can write data`. You can configure the clients to direct read requests to the slaves, which will take some of the load from the master. However, in this case slaves might return stale data since they replicate the master asynchronously.

**Project Overview**

![Sentinel Architecture](/img/sentinel_architecture.png)

## Setup Redis Sentinel

1. Start the master and slave node

    start the master

        redis-server

    start the slave

        redis-server 6380.conf

    ![Master Slave](/img/master_slave_server.png)

2. Create 2 different config file sentinel.conf with different port and different name

        port 26379
        sentinel monitor master 127.0.0.1 6379 2
        sentinel down-after-milliseconds master 5000
        sentinel failover-timeout master 60000
        sentinel config-epoch master 0

    - `26379` is the sentinel port
    - `master` is the name for the master declared
    - `127.0.0.1` is the host for the master
    - `6379` is the port for the master
    - `1` is the quorum size
3. Start the Sentinel

        redis-server sentinel.conf --sentinel

    ![Sentinel Start](/img/sentinel_cli.png)

## Setup Jedis Sentinel

Creating connection
```java
@Bean
public RedisConnectionFactory jedisConnectionFactory() {
    RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("master")
            .sentinel(host, node1).sentinel(host, node2);
    return new JedisConnectionFactory(sentinelConfig);
}
```

Created Redis Template
```java
@Bean
public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
}
```
