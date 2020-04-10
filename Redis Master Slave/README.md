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
1. Slaves cannot to master
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

## Operations
