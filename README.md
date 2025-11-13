# 📝 Spring Boot Blog REST API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive RESTful API for a blogging platform built with Spring Boot. This application provides complete blog post and comment management with JWT authentication, role-based access control, category organization, and full CRUD operations.

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [Request/Response Examples](#-requestresponse-examples)
- [Security](#-security)
- [Database Schema](#-database-schema)
- [Docker Deployment](#-docker-deployment)
- [API Documentation](#-api-documentation)
- [Configuration](#-configuration)
- [Contributing](#-contributing)

## ✨ Features

### 🔐 Authentication & Security
- JWT (JSON Web Token) based authentication
- Role-based access control (ADMIN, USER)
- BCrypt password encryption
- Stateless session management
- Token expiration handling

### 📰 Post Management
- **CRUD Operations**
    - Create, read, update, and delete blog posts
    - Full update (PUT) and partial update (PATCH) support
    - Unique title constraint

- **Advanced Features**
    - Pagination support for listing posts
    - Sorting by multiple fields (ascending/descending)
    - Filter posts by category
    - Rich content support with long text

### 💬 Comment Management
- Add comments to specific posts
- Retrieve comments by post with pagination
- Update and delete comments
- Full and partial updates (PUT/PATCH)
- Comment-to-post relationship validation
- Nested comments display

### 🏷️ Category Management
- Create and manage blog categories
- Organize posts by categories
- Category-based post filtering
- CRUD operations for categories

### 🛠️ Technical Features
- RESTful API design with proper HTTP methods
- Comprehensive input validation with Bean Validation
- Global exception handling
- Standardized error responses
- Swagger/OpenAPI documentation
- Docker support for easy deployment
- Profile-based configuration (dev, docker)

## 🏗️ Technology Stack

### Core Technologies
- **Framework**: Spring Boot 3.5.6
- **Java Version**: Java 21
- **Security**: Spring Security + JWT (JJWT 0.13.0)
- **ORM**: Spring Data JPA with Hibernate
- **Database**: MySQL 8.0+
- **Build Tool**: Maven

### Additional Libraries

| Library | Version | Purpose |
|---------|---------|---------|
| Lombok | 1.18.42 | Reduce boilerplate code |
| ModelMapper | 3.2.5 | Object mapping (Entity ↔ DTO) |
| JJWT | 0.13.0 | JWT token generation and validation |
| SpringDoc OpenAPI | 2.8.13 | API documentation (Swagger UI) |
| Jackson | (Spring Boot) | JSON parsing and PATCH operations |
| Spring Validation | 3.5.6 | Input validation |
| Spring DevTools | 3.5.6 | Development hot reload |

### Design Patterns & Principles
- **Layered Architecture**: Controller → Service → Repository
- **Dependency Injection**: Constructor-based injection
- **DTO Pattern**: Separate request/response objects
- **Repository Pattern**: Spring Data JPA repositories
- **Builder Pattern**: Lombok @Builder annotation
- **RESTful Principles**: Proper HTTP methods and status codes

## 🎨 Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Client Layer                             │
│              (Web/Mobile/Postman/Swagger)                    │
└────────────────────┬────────────────────────────────────────┘
                     │ HTTP Requests (JSON)
                     ↓
┌─────────────────────────────────────────────────────────────┐
│                  Security Layer                              │
│        JWT Filter → Authentication → Authorization           │
└────────────────────┬────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────────────┐
│                 Controller Layer                             │
│  AuthController | PostController | CommentController        │
│              CategoryController                              │
└────────────────────┬────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────────────┐
│                  Service Layer                               │
│      AuthService | PostService | CommentService             │
│                CategoryService                               │
└────────────────────┬────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────────────┐
│                Repository Layer                              │
│    UserRepository | PostRepository | CommentRepository      │
│    RoleRepository | CategoryRepository                      │
└────────────────────┬────────────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────────────┐
│                  Database Layer                              │
│                    MySQL Database                            │
│     (users, posts, comments, categories, roles)              │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 Getting Started

### Prerequisites

- **JDK 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git**
- **Docker** (optional, for containerized deployment)

### Installation Steps

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/springboot-blog-rest-api.git
cd springboot-blog-rest-api
```

2. **Create MySQL Database**
```sql
CREATE DATABASE myblog CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Configure Database Connection**

Update `src/main/resources/application-dev.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/myblog
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
app.jwt-secret=your_secret_key_here_min_256_bits
app-jwt-expiration-millisecond=86400000
```

4. **Build the Project**
```bash
mvn clean install
```

5. **Run the Application**
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080`

6. **Access API Documentation**

Swagger UI: `http://localhost:8080/swagger-ui.html`

## 📌 API Endpoints

### 🔑 Authentication Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/v1/auth/register` | Register new user | Public |
| POST | `/api/v1/auth/login` | Login and get JWT token | Public |

### 📰 Post Endpoints

| Method | Endpoint | Description | Access | Parameters |
|--------|----------|-------------|--------|------------|
| GET | `/api/v1/posts` | Get all posts (paginated) | Public | `pageNo`, `pageSize`, `sortBy`, `sortDir` |
| GET | `/api/v1/posts/{id}` | Get post by ID | Public | - |
| GET | `/api/v1/posts/category/{id}` | Get posts by category | Public | - |
| POST | `/api/v1/posts` | Create new post | ADMIN | - |
| PUT | `/api/v1/posts/{id}` | Update post (full) | ADMIN | - |
| PATCH | `/api/v1/posts/{id}` | Update post (partial) | ADMIN | - |
| DELETE | `/api/v1/posts/{id}` | Delete post | ADMIN | - |

### 💬 Comment Endpoints

| Method | Endpoint | Description | Access | Parameters |
|--------|----------|-------------|--------|------------|
| GET | `/api/v1/posts/{postId}/comments` | Get all comments for post | Public | `pageNo`, `pageSize`, `sortBy`, `sortDir` |
| GET | `/api/v1/posts/{postId}/comments/{commentId}` | Get specific comment | Public | - |
| POST | `/api/v1/posts/{postId}/comments` | Create comment | Public | - |
| PUT | `/api/v1/posts/{postId}/comments/{commentId}` | Update comment | Public | - |
| PATCH | `/api/v1/posts/{postId}/comments/{commentId}` | Partial update comment | ADMIN | - |
| DELETE | `/api/v1/posts/{postId}/comments/{commentId}` | Delete comment | Public | - |

### 🏷️ Category Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/v1/categories` | Get all categories | Public |
| GET | `/api/v1/categories/{id}` | Get category by ID | Public |
| POST | `/api/v1/categories` | Create new category | ADMIN |
| PUT | `/api/v1/categories/{id}` | Update category | ADMIN |
| DELETE | `/api/v1/categories/{id}` | Delete category | ADMIN |

## 📝 Request/Response Examples

### Register User

**Request:**
```json
POST /api/v1/auth/register
Content-Type: application/json

{
  "name": "Omar Ellafy",
  "username": "omar_ellafy",
  "email": "omar.ellafy@example.com",
  "password": "SecurePass123!"
}
```

**Response:**
```json
"User registered successfully"
```

### Login

**Request:**
```json
POST /api/v1/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "omar_ellafy",
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvbWFyLmVsbGFmeUBleGFtcGxlLmNvbSIsImlhdCI6MTczMTUwNjQwMCwiZXhwIjoxNzMxNTkyODAwfQ...",
  "tokenType": "Bearer"
}
```

### Create Post

**Request:**
```json
POST /api/v1/posts
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Getting Started with Spring Boot",
  "description": "A comprehensive guide to building REST APIs with Spring Boot",
  "content": "Spring Boot is a powerful framework that simplifies Java application development. In this post, we'll explore how to build a RESTful API from scratch...",
  "categoryId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Getting Started with Spring Boot",
  "description": "A comprehensive guide to building REST APIs with Spring Boot",
  "content": "Spring Boot is a powerful framework that simplifies Java application development...",
  "categoryId": 1,
  "comments": []
}
```

### Get All Posts (Paginated)

**Request:**
```
GET /api/v1/posts?pageNo=0&pageSize=10&sortBy=id&sortDir=desc
```

**Response:**
```json
{
  "content": [
    {
      "id": 2,
      "title": "Advanced Java Concepts",
      "description": "Deep dive into advanced Java programming",
      "content": "Let's explore some advanced concepts...",
      "categoryId": 1,
      "comments": []
    },
    {
      "id": 1,
      "title": "Getting Started with Spring Boot",
      "description": "A comprehensive guide to building REST APIs",
      "content": "Spring Boot is a powerful framework...",
      "categoryId": 1,
      "comments": []
    }
  ],
  "pageNo": 0,
  "pageSize": 10,
  "totalElements": 2,
  "totalPages": 1,
  "last": true
}
```

### Create Comment

**Request:**
```json
POST /api/v1/posts/1/comments
Content-Type: application/json

{
  "name": "Ahmed Hassan",
  "email": "ahmed@example.com",
  "body": "Great article! Very informative and well-written. Looking forward to more content like this."
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Ahmed Hassan",
  "email": "ahmed@example.com",
  "body": "Great article! Very informative and well-written. Looking forward to more content like this."
}
```

### Partial Update (PATCH)

**Request:**
```json
PATCH /api/v1/posts/1
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "title": "Getting Started with Spring Boot 3"
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Getting Started with Spring Boot 3",
  "description": "A comprehensive guide to building REST APIs with Spring Boot",
  "content": "Spring Boot is a powerful framework...",
  "categoryId": 1,
  "comments": []
}
```

### Create Category

**Request:**
```json
POST /api/v1/categories
Authorization: Bearer {admin_token}
Content-Type: application/json

{
  "name": "Technology",
  "description": "Posts related to technology, software development, and programming"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Technology",
  "description": "Posts related to technology, software development, and programming"
}
```

### Validation Error Response

**Response:**
```json
{
  "title": "post title must be more than 2 characters",
  "body": "Body must be more than 10 characters",
  "email": "must be a well-formed email address"
}
```

### Not Found Error Response

**Response:**
```json
{
  "timestamp": "2025-11-13T12:00:00.000+00:00",
  "message": "Post not found with id: '999'",
  "details": "uri=/api/v1/posts/999"
}
```

## 🔒 Security

### JWT Authentication Flow

```
1. User registers/logs in with credentials
2. Server validates and generates JWT token (valid for 24 hours)
3. Client stores token
4. Client includes token in Authorization header: "Bearer {token}"
5. Server validates token for each protected request
6. Server grants/denies access based on role (ADMIN/USER)
```

### Password Security
- All passwords encrypted using BCrypt
- Plain text passwords never stored in database
- Secure password hashing algorithm

### Role-Based Authorization
- **ADMIN Role**: Full access to create, update, and delete posts and categories
- **USER Role**: Can create comments and view content
- Method-level security using `@PreAuthorize` annotation
- Public endpoints accessible without authentication (GET requests)

### Token Configuration
- Default expiration: 24 hours (86400000 milliseconds)
- Configurable in `application.properties`
- Automatic token validation on each request
- Expired tokens rejected with appropriate error message

## 💾 Database Schema

### Entity Relationships

```
User
├── roles: Set<Role> (Many-to-Many)
└── User can have multiple roles (ADMIN, USER)

Post
├── category: Category (Many-to-One)
├── comments: Set<Comment> (One-to-Many)
└── Posts belong to one category, have multiple comments

Comment
└── post: Post (Many-to-One)
    Comments belong to one post

Category
└── posts: List<Post> (One-to-Many)
    Categories contain multiple posts

Role
└── Used for authorization (ROLE_ADMIN, ROLE_USER)
```

### Database Tables

**users**
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  username VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);
```

**roles**
```sql
CREATE TABLE roles (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);
```

**users_roles** (Join Table)
```sql
CREATE TABLE users_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

**categories**
```sql
CREATE TABLE categories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  description VARCHAR(500)
);
```

**posts**
```sql
CREATE TABLE posts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL UNIQUE,
  description VARCHAR(500) NOT NULL,
  content TEXT NOT NULL,
  category_id BIGINT,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

**comments**
```sql
CREATE TABLE comments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  body TEXT NOT NULL,
  post_id BIGINT NOT NULL,
  FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);
```

## 🐳 Docker Deployment

### Docker Compose Setup

1. **Build and Run with Docker Compose**
```bash
docker-compose up -d
```

2. **Application Configuration**

The application includes `application-docker.properties`:

```properties
spring.datasource.url=jdbc:mysql://mysqldb:3306/myblog
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

3. **Access the Application**
- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`

4. **Stop the Application**
```bash
docker-compose down
```

### Environment Profiles

- **dev**: Local development with local MySQL
- **docker**: Containerized deployment

Activate profile in `application.properties`:
```properties
spring.profiles.active=dev
```

## 📚 API Documentation

The API is fully documented using Swagger/OpenAPI 3.0.

### Access Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Documentation Features

- Interactive API testing
- Request/response schemas
- Authentication support (JWT Bearer token)
- Detailed endpoint descriptions
- Example requests and responses
- Error response codes

### Using Swagger UI

1. Navigate to Swagger UI
2. Click "Authorize" button
3. Enter JWT token: `Bearer {your_token}`
4. Test endpoints interactively

## 📦 Project Structure

```
springboot-blog-rest-api/
├── src/
│   ├── main/
│   │   ├── java/com/springboot/blog/
│   │   │   ├── SpringbootBlogRestApiApplication.java
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── PostController.java
│   │   │   │   ├── CommentController.java
│   │   │   │   └── CategoryController.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── Post.java
│   │   │   │   ├── Comment.java
│   │   │   │   └── Category.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   ├── PostRepository.java
│   │   │   │   ├── CommentRepository.java
│   │   │   │   └── CategoryRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── PostService.java
│   │   │   │   ├── CommentService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   └── impl/
│   │   │   │       ├── AuthServiceImpl.java
│   │   │   │       ├── PostServiceImpl.java
│   │   │   │       ├── CommentServiceImpl.java
│   │   │   │       └── CategoryServiceImpl.java
│   │   │   │
│   │   │   ├── payload/
│   │   │   │   ├── LoginDto.java
│   │   │   │   ├── RegisterDto.java
│   │   │   │   ├── JwtAuthResponse.java
│   │   │   │   ├── PostDto.java
│   │   │   │   ├── PostResponse.java
│   │   │   │   ├── CommentDto.java
│   │   │   │   ├── CommentResponse.java
│   │   │   │   ├── CategoryDto.java
│   │   │   │   └── ErrorDetails.java
│   │   │   │
│   │   │   ├── security/
│   │   │   │   ├── JwtAuthenticationEntryPoint.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   └── CustomUserDetailsService.java
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── BlogAPIException.java
│   │   │   │
│   │   │   └── utils/
│   │   │       ├── AppConstants.java
│   │   │       └── PasswordGeneratorEncoder.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-docker.properties
│   │
│   └── test/
│       └── java/com/springboot/blog/
│           └── SpringbootBlogRestApiApplicationTests.java
│
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## ⚙️ Configuration

### Application Properties

**application.properties** (Main)
```properties
spring.application.name=springboot-blog-rest-api

# JWT Configuration
app.jwt-secret=your_secret_key_minimum_256_bits
app-jwt-expiration-millisecond=86400000

# Active Profile
spring.profiles.active=dev
```

**application-dev.properties** (Development)
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/myblog
spring.datasource.username=root
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

**application-docker.properties** (Docker)
```properties
# Database Configuration (Docker)
spring.datasource.url=jdbc:mysql://mysqldb:3306/myblog
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
```

### Environment Variables (Production)

```bash
export DB_URL=jdbc:mysql://your-host:3306/myblog
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_production_secret_key
export JWT_EXPIRATION=86400000
```

## 🧪 Testing

Run all tests:
```bash
mvn test
```

Run specific test:
```bash
mvn test -Dtest=PostServiceTest
```

## 🔧 Key Implementation Details

### One-to-Many Relationships
- Posts can have multiple comments
- Categories can contain multiple posts
- Cascade operations for related entities
- Orphan removal for comments when post is deleted

### Lazy Loading
- Comments loaded lazily to optimize performance
- Category loaded lazily in posts
- Prevents N+1 query problems

### ModelMapper Configuration
- Automatic entity-DTO conversion
- Skip null values for PATCH operations
- Custom field mappings where needed

### Global Exception Handling
- `@ControllerAdvice` for centralized error handling
- Custom exceptions with meaningful messages
- Standardized error response format
- Validation error mapping

### Pagination & Sorting
- Configurable page size and page number
- Multi-field sorting support
- Sort direction control (ASC/DESC)
- Default values via AppConstants

### PATCH Operation
- Jackson ObjectMapper for partial updates
- Merge patch strategy
- Null-value handling
- Field-level updates

## 🚧 Common Issues & Solutions

### Issue: JWT Token Expired
**Solution**: Login again to get a new token. Token validity: 24 hours.

### Issue: Access Denied (403)
**Solution**: Ensure you're using ADMIN role token for protected endpoints.

### Issue: Validation Errors
**Solution**: Check request body format. Title must be 2+ characters, comment body 10+ characters.

### Issue: Post Not Found
**Solution**: Verify post ID exists. Use GET /api/v1/posts to see all posts.

### Issue: Comment Does Not Belong to Post
**Solution**: Ensure comment ID and post ID match. Comments are tied to specific posts.

## 📈 Future Enhancements

- [ ] User profile management
- [ ] Post like/dislike functionality
- [ ] Comment replies (nested comments)
- [ ] Post tags and tagging system
- [ ] Full-text search functionality
- [ ] File upload for post images
- [ ] Email notifications
- [ ] Social media sharing
- [ ] Rate limiting for API endpoints
- [ ] Caching with Redis
- [ ] Comprehensive integration tests
- [ ] CI/CD pipeline
- [ ] Admin dashboard
- [ ] Analytics and reporting

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Contribution Guidelines
- Follow Java naming conventions
- Write meaningful commit messages
- Add unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR
- Follow existing code style

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Developer

**Omar Hamdi Ellafy**

- GitHub: [@OmarHamdi11](https://github.com/OmarHamdi11)
- LinkedIn: [Omar Ellafy](https://linkedin.com/in/omar-ellafy)
- Email: dev.omar.ellafy@gmail.com

## 🙏 Acknowledgments

- Spring Boot Documentation
- Spring Security Documentation
- JWT.io
- Hibernate ORM
- MySQL Documentation
- Swagger/OpenAPI
- Stack Overflow Community

---

**Built with ❤️ using Spring Boot, Spring Security, JWT, and MySQL**

⭐ If you found this project helpful, please give it a star!

## 📞 Support

For support, email dev.omar.ellafy@gmail.com or open an issue in the repository.