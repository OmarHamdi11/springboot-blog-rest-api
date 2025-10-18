# Spring Boot Blog REST API

> ⚠️ **Project Status**: This project is currently under development and is a work in progress.

## 📋 Overview

A RESTful API for a blogging platform built with Spring Boot. This application provides endpoints for managing blog posts and comments with full CRUD operations, pagination, and sorting capabilities.

## 🚀 Features

- **Post Management**
  - Create, read, update, and delete blog posts
  - Pagination support for listing posts
  - Sorting by different fields (ascending/descending)
  - Unique title constraint

- **Comment Management**
  - Add comments to specific posts
  - Retrieve comments by post
  - Update and delete comments
  - Comment-to-post relationship validation

- **Data Validation**
  - Custom exception handling
  - Resource not found exceptions
  - Business logic validation

## 🛠️ Technology Stack

- **Framework**: Spring Boot
- **Java Version**: Java 17+
- **ORM**: Spring Data JPA with Hibernate
- **Database**: MySQL/PostgreSQL (configurable)
- **Build Tool**: Maven
- **Object Mapping**: ModelMapper
- **Annotations**: Lombok

## 📦 Project Structure

```
springboot-blog-rest-api/
├── src/main/java/com/springboot/blog/
│   ├── controller/          # REST Controllers
│   │   ├── PostController.java
│   │   └── CommentController.java
│   ├── entity/              # JPA Entities
│   │   ├── Post.java
│   │   └── Comment.java
│   ├── repository/          # Data Access Layer
│   │   ├── PostRepository.java
│   │   └── CommentRepository.java
│   ├── service/             # Business Logic
│   │   ├── PostService.java
│   │   ├── CommentService.java
│   │   └── impl/
│   ├── payload/             # DTOs
│   │   ├── PostDto.java
│   │   ├── CommentDto.java
│   │   └── PostResponse.java
│   ├── exception/           # Custom Exceptions
│   │   ├── ResourceNotFoundException.java
│   │   └── BlogAPIException.java
│   └── utils/               # Utility Classes
│       └── AppConstants.java
```

## 🔌 API Endpoints

### Posts

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/posts` | Create a new post |
| GET | `/api/posts` | Get all posts (with pagination & sorting) |
| GET | `/api/posts/{id}` | Get post by ID |
| PUT | `/api/posts/{id}` | Update post |
| DELETE | `/api/posts/{id}` | Delete post |

**Query Parameters for GET /api/posts:**
- `pageNo` (default: 0)
- `pageSize` (default: 10)
- `sortBy` (default: id)
- `sortDir` (default: asc)

### Comments

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/posts/{postId}/comments` | Create a comment for a post |
| GET | `/api/posts/{postId}/comments` | Get all comments for a post |
| GET | `/api/posts/{postId}/comments/{commentId}` | Get specific comment |
| PUT | `/api/posts/{postId}/comments/{commentId}` | Update comment |
| DELETE | `/api/posts/{postId}/comments/{commentId}` | Delete comment |

## 📊 Data Models

### Post
```json
{
  "id": 1,
  "title": "Sample Post Title",
  "description": "Short description",
  "content": "Full post content",
  "comments": []
}
```

### Comment
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "body": "Comment text"
}
```

### Paginated Response
```json
{
  "content": [],
  "pageNo": 0,
  "pageSize": 10,
  "totalElements": 50,
  "totalPages": 5,
  "last": false
}
```

## ⚙️ Configuration

Update `application.properties` with your database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## 🚦 Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.6+
- MySQL/PostgreSQL database

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd springboot-blog-rest-api
```

2. Configure the database in `application.properties`

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 🧪 Testing

Run tests using:
```bash
mvn test
```

## 📝 Key Implementation Details

- **One-to-Many Relationship**: Posts can have multiple comments
- **Lazy Loading**: Comments are loaded lazily to optimize performance
- **Orphan Removal**: Deleting a post automatically removes associated comments
- **ModelMapper**: Automatic entity-DTO conversion
- **Custom Exceptions**: Proper error handling with meaningful messages
- **Repository Pattern**: Clean separation of data access logic

## 🔜 Future Enhancements

- [ ] User authentication and authorization (Spring Security)
- [ ] JWT token-based authentication
- [ ] Category/Tag management for posts
- [ ] File upload for post images
- [ ] Search functionality
- [ ] API documentation with Swagger/OpenAPI
- [ ] Input validation with Bean Validation
- [ ] Global exception handler
- [ ] Integration tests

## 👨‍💻 Author

Omar Hamdi Ellafy

## 📄 License

This project is licensed under the MIT License.

---

**Note**: This is an educational project and is currently under active development.
