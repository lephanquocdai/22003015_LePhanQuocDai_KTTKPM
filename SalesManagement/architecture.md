# System Architecture

Here is the diagram illustrating the communication between the services in the Sales Management System.

```mermaid
graph TD
    %% Define components
    Client((Client))
    APIGateway[API Gateway\n:3000]
    
    subgraph Microservices
        ProductService[Product Service\n:3001]
        OrderService[Order Service\n:3002]
        CustomerService[Customer Service\n:3003]
    end

    subgraph Message Broker
        RabbitMQ((RabbitMQ))
    end

    subgraph Databases
        DB_Product[(Product DB\nMongoDB)]
        DB_Order[(Order DB\nMongoDB)]
        DB_Customer[(Customer DB\nMongoDB)]
    end

    %% Client requests
    Client -->|HTTP Requests| APIGateway
    
    %% Gateway Routing
    APIGateway -->|GET/POST/PUT/DELETE \n /products| ProductService
    APIGateway -->|GET/POST/PUT/DELETE \n /orders| OrderService
    APIGateway -->|GET/POST/PUT/DELETE \n /customers| CustomerService
    
    %% Synchronous Communication (REST/gRPC)
    OrderService -.->|REST API Check Product| ProductService
    OrderService -.->|REST API Check Customer| CustomerService
    
    %% Asynchronous Communication (Message Broker)
    OrderService ==>|Publish 'order.created' event| RabbitMQ
    RabbitMQ ==>|Consume 'order.created' event| ProductService
    
    %% Database connections
    ProductService --- DB_Product
    OrderService --- DB_Order
    CustomerService --- DB_Customer

    %% Styling
    classDef gateway fill:#f9f,stroke:#333,stroke-width:2px;
    classDef service fill:#bbf,stroke:#333,stroke-width:2px;
    classDef broker fill:#fbb,stroke:#333,stroke-width:2px;
    classDef db fill:#bfb,stroke:#333,stroke-width:2px;

    class APIGateway gateway;
    class ProductService,OrderService,CustomerService service;
    class RabbitMQ broker;
    class DB_Product,DB_Order,DB_Customer db;
```

## Description
1. **API Gateway**: Acts as the single entry point. It proxies incoming requests to the appropriate Microservice.
2. **Synchronous Communication**: The `Order Service` uses REST APIs to communicate synchronously with the `Product Service` and `Customer Service` to validate whether a product and a customer exist before creating an order.
3. **Asynchronous Communication**: When an order is successfully created, the `Order Service` publishes an event (`order.created`) to the Message Broker (`RabbitMQ`). The `Product Service` consumes this event to asynchronously reduce the product inventory.
4. **Database per Service**: Each microservice maintains its own MongoDB database, satisfying the "Database per Service" pattern.
