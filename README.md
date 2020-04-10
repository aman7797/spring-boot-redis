# Spring Boot Redis

## Install & Setup Redis
For Windows
1. Download [Redis](https://github.com/MicrosoftArchive/redis/releases)
2. Unzip the downloaded file
3. Start the Redis Server 
    
        redis-server.exe

    ![Redis Server](img/redis-server.png)
4. Now to use Redis start `Redis CLI`

        redis-cli.exe
    
    ![Redis Server](img/redis-cli.png)
5. To check Redis is setuped, try typing

        ping
    ![Redis Server](img/redis-cli-ping.png)
    
For Linux

1. Download [Redis](https://redis.io/download)
2. Unzip the downloaded file

        tar -xvf redis-*.tar.gz
3. Go to the directory

        cd redis-stable
4. Run make to create executable 

        make
5. To start the node server

        src/redis-server
6. To start the cli for the node

        src/redis-cli
## Redis Notes

https://github.com/aman7797/super-pancake/tree/master/Learn%20Redis%20And%20Utilize%20Jedis%20With%20Spring%20Data%20Redis

## Repository

1. [Redis Master Slave](https://github.com/aman7797/spring-boot-redis/tree/master/Redis%20Master%20Slave)
2. [Redis Sentinel](https://github.com/aman7797/spring-boot-redis/tree/master/Redis%20Sentinel)
3. [Redis Cluster](https://github.com/aman7797/spring-boot-redis/tree/master/Redis%20Cluster)
