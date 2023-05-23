# Product, Order and Inventory

## Task
Assuming you have already implemented the "Product Service" and
"Order Service" as separate microservices, add an additional one
called “Inventory Service” which will check the availability of the
desired Product.

**1. Set up Eureka Server:** 
- Create a new Spring Boot application as the Eureka server
- Include the necessary Eureka dependencies in your project.
- Annotate the main class with @EnableEurekaServer.
- Configure the server's port and other properties in the
  application properties file.

**2. Configure Microservices to Register with Eureka:**
- Update the "Product Service", "Order Service" and “Inventory
  Service” to include Eureka client dependencies.
- In the configuration files of both services, specify the Eureka
  server URL and other required properties.
- Annotate the main classes of the three services with
  @EnableDiscoveryClient to enable service registration.

**3. Use Feign Client**

## Usage
These API endpoints for the project are accessible at http://localhost:8081/api/v1/products.

* GET /api/v1/products: Retrieves a list of all products.
* GET /api/v1/products/{id}: Retrieves a single product with the given ID.
* POST /api/v1/products: Creates a new product.
* PUT /api/v1/products/{id}: Updates an existing product with the given ID.
* DELETE /api/v1/products/{id}: Deletes a product with the given ID.