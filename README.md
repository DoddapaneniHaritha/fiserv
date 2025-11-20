This is a Spring Boot application that demonstrates order management integrated with Apache Kafka for messaging.
It provides REST endpoints to create orders and publish them to a Kafka topic (order-topics). The application uses KafkaProducer and KafkaConsumer classes for message handling.
Sample REST API:
POST /orders/{customerId}
Content-Type: application/json

{
   "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"
}
Response:
Order sent successfully!

Flow:

The request is sent to the Kafka Producer with the topic name order-topics.
Orders with the same customerId are sent to the same partition.
Consumers read and log data from the same topic. There are three consumers in different groups; if one consumer goes down, another takes over.
Finally, the data is stored in the database.

Git Link: git clone https://github.com/DoddapaneniHaritha/fiserv.git

Run zookeeper:
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties


Run Kafka server
.\bin\windows\kafka-server-start.bat .\config\server.properties


Create a topic:
.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092   


Curl: curl --location 'http://localhost:8081/orders/Customer6' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"

}'
