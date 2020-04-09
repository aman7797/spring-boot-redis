# Redis Cluster



## How to setup Redis Cluster

1. Create six different node file with the configuration

        port 7001
        cluster-enabled yes
        cluster-config-file nodes.conf
        cluster-node-timeout 5000
        appendonly yes
    
    Need to change port number for all the node configuration file

2. Start 6 Redis Nodes 7001 7002 7003 7004 7005 7006

    in windows

        redis-server 7001.config
    
    in linux

        src/redis-server 7001.config

    Similarly for the rest node

3. Configure to allow 3 master nodes to meet

    in windows

        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7002
        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7003
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7002
        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7003
4. Assign 3 master nodes with slots.  Redis has 16384 slots for hashing, we need to distribute these slots to different  master nodes.

    in window's

        FOR /L %i IN (0,1,5461) DO ( redis-cli.exe -h node1ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (5462,1,10923) DO ( redis-cli.exe -h node2ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (10924,1,16383) DO ( redis-cli.exe -h node3ip -p 6379 CLUSTER ADDSLOTS %i )
    
    in linux

        for i in {0..5400}; do src/redis-cli -p 7001 CLUSTER ADDSLOTS $i; done
        for i in {5401..10800}; do src/redis-cli -p 7002 CLUSTER ADDSLOTS $i; done
        for i in {10801..16383}; do src/redis-cli -p 7003 CLUSTER ADDSLOTS $i; done

5. Configure to allow 3 slave nodes to meet with master nodes

    in window's

        redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7004
        redis-cli -c -h 127.0.0.1 -p 7002 cluster meet 127.0.0.1 7005
        redis-cli -c -h 127.0.0.1 -p 7003 cluster meet 127.0.0.1 7006
    
    in linux
    
        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster meet 127.0.0.1 7004
        src/redis-cli -c -h 127.0.0.1 -p 7002 cluster meet 127.0.0.1 7005
        src/redis-cli -c -h 127.0.0.1 -p 7003 cluster meet 127.0.0.1 7006

6. Find master node id

    in window's

        FOR /L %i IN (0,1,5461) DO ( redis-cli.exe -h node1ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (5462,1,10923) DO ( redis-cli.exe -h node2ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (10924,1,16383) DO ( redis-cli.exe -h node3ip -p 6379 CLUSTER ADDSLOTS %i )
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7001 cluster nodes
        src/redis-cli -c -h 127.0.0.1 -p 7002 cluster nodes
        src/redis-cli -c -h 127.0.0.1 -p 7003 cluster nodes

7. Map slave node to master node

    in window's

        FOR /L %i IN (0,1,5461) DO ( redis-cli.exe -h node1ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (5462,1,10923) DO ( redis-cli.exe -h node2ip -p 6379 CLUSTER ADDSLOTS %i )
        FOR /L %i IN (10924,1,16383) DO ( redis-cli.exe -h node3ip -p 6379 CLUSTER ADDSLOTS %i )
    
    in linux

        src/redis-cli -c -h 127.0.0.1 -p 7004 cluster replicate ef7c3cbe7b90d1559897ea633d566c52fc697776
        src/redis-cli -c -h 127.0.0.1 -p 7005 cluster replicate dafa6fd681171f93a2444dee83f5d88a82cb223b
        src/redis-cli -c -h 127.0.0.1 -p 7006 cluster replicate c6b850c98745db9deb6fd8c879c138c1f7a35286