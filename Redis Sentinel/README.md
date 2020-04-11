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

## Setup Jedis Sentinel

## Operations