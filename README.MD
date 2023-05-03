# Ecommerce cart application
Author: [Andree Anchi](https://www.linkedin.com/in/andree-anchi-dueñas/)
## Dependencies
- Java 11
- Maven 3.8.2
- H2 Database
- Spring Boot 2.5.5
- Spring Data JPA
- Spring Web
- Spring Validation
- Spring Test

## How to run
- Clone this repository
- Run `mvn clean install` to build the project
- Run `mvn spring-boot:run` to run the project
- The application will be available at `http://localhost:8080/v1`
- The H2 database will be available at `http://localhost:8080/v1/h2-console`
    - JDBC URL: `jdbc:h2:mem:testdb`
    - User Name: `sa`
    - Password: `sa`
- The Swagger documentation will be available at `http://localhost:8080/v1/swagger-ui.html`

## How to test
- Run `mvn test` to run the tests

## How to use

### Products
- `GET /product` - Get all products
- `POST /product` - Create a new product

### Carts
- `GET /cart` - Get all carts
- `GET /cart/{id}` - Get cart by id
- `POST /cart` - Create a new cart
- `DELETE /cart/{id}` - Delete cart by id
- `POST /cart/{id}/products` - Add product to cart
- `DELETE /cart/{id}/products` - Remove product from cart

## Workflow
- Get the list of products
- Create a new cart
- Add products to the cart
- Remove products from the cart if necessary
* Every minute the application will check if there are carts that have been inactive for more than 10 minutes and will delete them

## Worflow sample

### Get the list of products
```bash
curl --location --request GET 'http://localhost:8080/v1/product'
```
```json
{
  "products": [
    {
      "id": 1,
      "name": "iPhone",
      "description": "Apple iPhone 11",
      "price": 699,
      "stock": 2
    },
    {
      "id": 2,
      "name": "Galaxy",
      "description": "Samsung Galaxy S20",
      "price": 799,
      "stock": 3
    },
    {
      "id": 3,
      "name": "Pixel",
      "description": "Google Pixel 4",
      "price": 799,
      "stock": 2
    },
    {
      "id": 4,
      "name": "Lumia",
      "description": "Microsoft Lumia 950",
      "price": 399,
      "stock": 6
    }
  ]
}
```

### Create a new cart
```bash
curl --location --request POST 'http://localhost:8080/v1/cart'
```
```json
{
  "id": "ffaf35e5-1cb2-4c66-be0b-31c20c8a8784",
  "products": [],
  "total": 0,
  "createdAt": "2023-05-03T18:38:04.049+00:00",
  "updatedAt": "2023-05-03T18:38:04.049+00:00"
}
```

### Add products to the cart
```bash
curl -X 'POST' \
  'http://localhost:8080/v1/cart/ffaf35e5-1cb2-4c66-be0b-31c20c8a8784/product' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": 1,
  "quantity": 2
}'
```
```json
{
  "id": "ffaf35e5-1cb2-4c66-be0b-31c20c8a8784",
  "products": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 1398
    }
  ],
  "total": 1398,
  "createdAt": "2023-05-03T18:38:04.049+00:00",
  "updatedAt": "2023-05-03T18:39:10.360+00:00"
}
```

### Remove products from the cart
```bash
curl -X 'DELETE' \
  'http://localhost:8080/v1/cart/ffaf35e5-1cb2-4c66-be0b-31c20c8a8784/product' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "productId": 1,
  "quantity": 1
}'
```
```json
{
  "id": "ffaf35e5-1cb2-4c66-be0b-31c20c8a8784",
  "products": [
    {
      "productId": 1,
      "quantity": 1,
      "price": 699
    }
  ],
  "total": 699,
  "createdAt": "2023-05-03T18:38:04.049+00:00",
  "updatedAt": "2023-05-03T18:40:10.360+00:00"
}
```
