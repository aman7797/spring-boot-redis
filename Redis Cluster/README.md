# Redis Cluster

Redis Cluster is a **high availability** and **horizontal scalability** solution for Redis. The cluster is **self-managed**. It abstracts configuration away from clients and `removes the single point of failure` introduced by proxy servers. Redis Cluster maintains the `performance and strong data model`.

**Advantage**
- **High Performance**
Redis Cluster promises the same level of performance as standalone Redis deployments.

- **High Availability**
Redis Cluster supports the standard Redis master-replica configuration to ensure high availability and durability. It also implements a Raft-like consensus approach to ensure availability of the entire cluster.

- **Horizontal & Vertical Scalability**
Adding or removing new Redis nodes from the Cluster can occur transparently and without any downtime. This makes adding and removing shards, retiring, or scaling individual nodes easy.

- **Native Solution**
Deploying Redis Clusters requires no external proxies or tools, so there are no new tools you need to learn or worry about. It also provides almost complete compatibility with standalone Redis deployments.

- Deployment that consist of at least 6 nodes (3 masters and 3 slaves). where data is sharded between the masters. It is highly available since in case of master failure, data can be fetched from the slave. It is scalable since we can add more nodes and reshard the data so that the new nodes will take some of the load.

- Redis Cluster over Sentinel is scalable

**Disadvantage**
- Redis Cluster deployment clients should have network access to all nodes (and node only Master1). This is because data is sharded between the masters. In case client try to write data to Master1 but Master2 is the owner of the data, Master1 will return a MOVE message to the client, guiding it to send the request to Master2. You cannot have a single HAProxy in front of all Redis nodes.

- **Limited Multi-Key Operation Support**
Multi-key operations are supported only when all the keys in a single operation belong to the same slot. This is something to be careful about when designing your data structures.

**Project Overview**

![Cluster Implementation](/img/cluster_architecture.png)
## Setup Redis Cluster

1. Create six different node file with the configuration

        port 7001
        cluster-enabled yes
        cluster-config-file nodes.conf
        cluster-node-timeout 5000
        appendonly yes
    
    Need to change port number for all the node configuration file, please find the sample file for reference `Redis Cluster\config file`

2. Start 6 Redis Nodes 7001 7002 7003 7004 7005 7006

    in windows

        redis-server 7001.config
    
    in linux

        src/redis-server 7001.config

    Similarly for the rest node

    ![Nodes Started](/img/nodes.png)
3. Configure to allow 3 master nodes to meet

    in windows

        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7002
        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7003
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7002
        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7003


    In this step we are interlinking the 3 master to talk to each other.

    ![Configure Master](/img/configure_master.png)

4. Assign 3 master nodes with slots.  Redis has 16384 slots for hashing, we need to distribute these slots to different  master nodes.

    in window's

        FOR /L %i IN (0,1,5461) DO ( redis-cli.exe -h 127.0.0.1 -p 7001 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (5462,1,10923) DO ( redis-cli.exe -h 127.0.0.1 -p 7002 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (10924,1,16383) DO ( redis-cli.exe -h 127.0.0.1 -p 7003 CLUSTER ADDSLOTS %i )
    
    in linux

        for i in {0..5400}; do src/redis-cli -p 7001 CLUSTER ADDSLOTS $i; done
        for i in {5401..10800}; do src/redis-cli -p 7002 CLUSTER ADDSLOTS $i; done
        for i in {10801..16383}; do src/redis-cli -p 7003 CLUSTER ADDSLOTS $i; done

    ![Slots Assign](/img/slots_assign.png)

5. Configure to allow 3 slave nodes to meet with master nodes

    in window's

        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7004
        redis-cli -c -h 127.0.0.1 -p 7002 cluster meet 127.0.0.1 7005
        redis-cli -c -h 127.0.0.1 -p 7003 cluster meet 127.0.0.1 7006
    
    in linux
    
        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7004
        src/redis-cli -c -h 127.0.0.1 -p 7002 cluster meet 127.0.0.1 7005
        src/redis-cli -c -h 127.0.0.1 -p 7003 cluster meet 127.0.0.1 7006

    ![Slave Master Config](/img/slave_master_config.png)

6. Find master node id

    in window's

        redis-cli -c -h 127.0.0.1 -p 7001 cluster nodes
        redis-cli -c -h 127.0.0.1 -p 7002 cluster nodes
        redis-cli -c -h 127.0.0.1 -p 7003 cluster nodes
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster nodes
        src/redis-cli -c -h 127.0.0.1 -p 7002 cluster nodes
        src/redis-cli -c -h 127.0.0.1 -p 7003 cluster nodes

    ![Master Clusterr Details](/img/master_cluster_details.png)

7. Map slave node to master node, in this step we will be replicating the master to the slave to cover the failure case 
    
    in window's

        redis-cli -c -h 127.0.0.1 -p 7004 cluster replicate a83f79aaf27c9f83b082bf443d4cdb0fb7a06305
        redis-cli -c -h 127.0.0.1 -p 7005 cluster replicate 8314270e075978c6b74202dcb4674299e84e06e4
        redis-cli -c -h 127.0.0.1 -p 7006 cluster replicate fadf7ff3713e32c0c209092b9eb91da4eaafde60
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7004 cluster replicate ef7c3cbe7b90d1559897ea633d566c52fc697776
        src/redis-cli -c -h 127.0.0.1 -p 7005 cluster replicate dafa6fd681171f93a2444dee83f5d88a82cb223b
        src/redis-cli -c -h 127.0.0.1 -p 7006 cluster replicate c6b850c98745db9deb6fd8c879c138c1f7a35286
    
    ![Slave Mapping](/img/slave_mapping.png)


**Test**

To test the cluster connection -start the cli

    redis-cli -c -p 7001

![Cluster Test1](/img/cluster_test.png)

## Setup Jedis Cluster

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
    - `7001` - master node port number
    - `password` - is null as no authentication required for my setup

3. Create a bean for Jedis Cluster 
    ```java
    @Bean
    public JedisCluster getRedisCluster(){
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort(host, port));
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(maxTotal);
        cfg.setMaxIdle(maxIdle);
        cfg.setMaxWaitMillis(10000);
        cfg.setTestOnBorrow(true); 
        JedisCluster jc = new JedisCluster(jedisClusterNode, 10000, 1, cfg);
        return jc;
    }
    ```

4. Using JedisCluster Bean we can do the basic operations

    ```java
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void setItemAsString(String itemId, String item) {
        jedisCluster.set(itemId, item);
    }

    @Override
    public String getItemAsString(String itemId) {
        return (String) jedisCluster.get(itemId);
    }

    ```

## Operations

1. Set in Cluster

    ```curl
    curl --location --request POST 'localhost:9001/item-string' \
    --header 'Content-Type: application/json' \
    --header 'Content-Type: text/plain' \
    --data-raw '{"id":2,"name":"Manan","company":"Fla"}'
    ```

2. Get from cluster

    ```curl
    curl --location --request GET 'localhost:9001/item-string/2' \
    --header 'Content-Type: application/json'
    ``` 
    ![Get String](/img/get_string.png)

3. Get All 

    ```curl
    curl --location --request GET 'localhost:9001/item-string/all' \
    --header 'Content-Type: application/json'
    ```
    ![Get All String](/img/get_all.png)

4. Delete a Key 

    ```curl
    curl --location --request DELETE 'localhost:9001/item-string/2' \
    --header 'Content-Type: application/json'
    ```

5. Delete All key from Cluster

    ```curl
    curl --location --request DELETE 'localhost:9001/item-string/all' \
    --header 'Content-Type: application/json'
    ```

    ![Delete All String](/img/delete_all.png)

6. Refresh from DB

    ```curl
    curl --location --request GET 'localhost:9001/item-string'
    ```
    ![Refresh DB](/img/refresh_db.png)