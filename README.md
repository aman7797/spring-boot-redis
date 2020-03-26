## Install & Setup Redis

1. We can download Redis from - https://github.com/MicrosoftArchive/redis/releases
2. Download the Zip file and Unzip it
3. Start the Redis Server 
    
        redis-server.exe

    ![Redis Server](/img/redis-server.png)
4. Now to use Redis start `Redis CLI`

        redis-cli.exe
    
    ![Redis Server](/img/redis-cli.png)
5. To check Redis is setuped, try typing

        ping
    ![Redis Server](/img/redis-cli-ping.png)
    
## Tutorial/Documentation

https://github.com/aman7797/super-pancake/tree/master/Learn%20Redis%20And%20Utilize%20Jedis%20With%20Spring%20Data%20Redis

## Endpoint

1. To add the programmer string

    ```curl
    curl --location --request POST 'localhost:8080/progrmmer-string/' \
    --header 'Content-Type: application/json' \
    --data-raw '{"id":1,"name":"Aman","company":"Xoriant"}'
    ```

    ![Programmer Output](/img/programmer_output.png)

2. To get the Programmer string 

    ```curl
    curl --location --request GET 'localhost:8080/programmer-string/1' \
    --header 'Content-Type: application/json'

    ```

    ![Get Programmer Output](/img/get_programmer_output.png)

3. To add Programmer List

    ```curl
    curl --location --request POST 'localhost:8080/programmer-list' \
    --header 'Content-Type: application/json' \
    --data-raw '{"id":2,"name":"Manan","company":"Null"}'
    ```
    ![Get Programmer Output](/img/add_programmer_list.png)

4. To get Programmer List

    ```curl
    curl --location --request GET 'localhost:8080/programmer-list' \
    --header 'Content-Type: application/json'
    ```
    ![Get Programmer Output](/img/get_programmer_list.png)

5. To get Programmer List

    ```curl
    curl --location --request GET 'localhost:8080/programmer-list/count' \
    --header 'Content-Type: application/json'
    ```
    ![Get Programmer Output](/img/get_programmer_list_range.png)

6. To add Programmer Set

    ```curl
    curl --location --request POST 'localhost:8080/programmer-set' \
    --header 'Content-Type: application/json' \
    --data-raw '{"id":3,"name":"Aman Lalpuria","company":"Juspay"}'
    ```
    ![Get Programmer Output](/img/add_programmer_set.png)

7. To get Programmer Set
    ```curl
    curl --location --request GET 'localhost:8080/programmer-set' \
    --header 'Content-Type: application/json'
    ```
    ![Get Programmer Output](/img/get_programmer_set.png)

8. To check if the Programmer isEmpty 
    ```curl
    curl --location --request POST 'localhost:8080/programmer-set/isEmpty' \
    --header 'Content-Type: application/json' \
    --data-raw '{"id":3,"name":"Aman Lalpuria","company":"Juspay"}'
    ```
    ![Get Programmer Output](/img/isEmpty_Set.png)