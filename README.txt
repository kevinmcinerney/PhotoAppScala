sudo service rabbitmq-server start
sudo service rabbitmq-server stop

sudo service mysql start
sudo service mysql stop

mysql -u kevin -p

keytool -genkey -alias apiEncryptionKey -keyalg RSA -dname "CN=Kevin Mcinerney,OU=API Development,O=appsdeveloperblog.com,L=Ottawa,S=ON,C=CA" -keypass 1q2w3e4r -keystore apiEncryption.jks -storepass 1q2w3e4r


kevin@Babage-Machine:~$ java -jar zipkin.jar


kevin@Babage-Machine:/usr/share/logstash$ bin/logstash -f simple-config.conf 


kevin@Babage-Machine:/usr/share/elasticsearch$ ./bin/elasticsearch


http://localhost:9200/albums-ws-2020.08.10/_search?q=*&format&pretty
http://localhost:9200/users-ws-2020.08.10/_search?q=*&format&pretty

kevin@Babage-Machine:~/kibana-4.1.1-linux-x64$ bin/kibana



# To run in chosen environment (dev prod...)
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=production



## Building Docker Containers

docker login --username=kevbot55

- PhotoAppApiConfigServer
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=config-server --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <CONTAINER_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>


