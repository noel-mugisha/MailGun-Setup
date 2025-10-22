# 🔐 OTP Service | Mailgun HTTP API Integration

> A modern Spring Boot 3.x microservice for user registration with OTP delivery via Mailgun's HTTP API. Built for performance, reliability, and developer experience.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Mailgun](https://img.shields.io/badge/Mailgun-HTTP%20API-red.svg)](https://www.mailgun.com/)

---

## 🚀 Overview

This service demonstrates production-grade integration with third-party APIs using Spring's reactive WebClient. It handles user registration and delivers one-time passwords via email, bypassing traditional SMTP in favor of Mailgun's faster, more reliable HTTP endpoint.

### ✨ Key Features

- **🔄 Non-Blocking Architecture** - Asynchronous HTTP calls with Spring WebClient keep threads available
- **⚡ Mailgun HTTP API** - Faster, more secure alternative to SMTP with rich feature set
- **🛡️ Robust Error Handling** - Custom error translation from Mailgun's 4xx/5xx responses
- **🔒 Externalized Configuration** - Secure credential management via `.env` and Spring properties
- **🧪 Developer Tools** - H2 console for easy database inspection during development

---

## 🛠️ Tech Stack

| Technology | Version/Component | Purpose |
|------------|-------------------|---------|
| **Backend** | Java 21 + Spring Boot 3.x | Core framework |
| **HTTP Client** | Spring WebFlux / WebClient | Non-blocking API calls |
| **Database** | H2 (In-Memory) | Development persistence |
| **ORM** | Spring Data JPA / Hibernate | Data layer management |
| **Utilities** | Lombok, spring-dotenv | Boilerplate reduction, config loading |

---

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- Mailgun account with API credentials
- Git

---

## ⚙️ Setup & Configuration

### 1. Clone the Repository

```bash
git clone <your-repo-url>
cd otp-service
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```bash
# .env
MAILGUN_API_KEY=your-api-key-here
MAILGUN_BASE_URL=https://api.mailgun.net/v3/
MAILGUN_SENDING_DOMAIN=your-mailgun-domain.com
MAILGUN_FROM_EMAIL=OTP Service <noreply@your-domain.com>
```

> **⚠️ Sandbox Domain Users:** Remember to authorize recipient emails in the Mailgun dashboard. For production, use a verified custom domain.

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The service starts at: `http://localhost:8081/api/v1`

---

## 🧪 Testing the API

### Register User & Send OTP

**Endpoint:** `POST /api/v1/users/register`

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com"
}
```

**Success Response (200 OK):**
```
"User registered and OTP email sent successfully to: john.doe@example.com"
```

### cURL Example

```bash
curl -X POST http://localhost:8081/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"John Doe","email":"john.doe@example.com"}'
```

---

## 🔍 Developer Tools

### H2 Database Console

Access the in-memory database console for debugging:

**URL:** `http://localhost:8081/h2-console`

**Connection Settings:**
- **JDBC URL:** `jdbc:h2:mem:otpdb`
- **Username:** `sa`
- **Password:** `password`

**Useful Queries:**
```sql
-- View all registered users and their OTPs
SELECT * FROM USERS;

-- Check latest registrations
SELECT * FROM USERS ORDER BY id DESC LIMIT 10;
```

---

## 📁 Project Structure

```
src/main/java/
├── config/          # Configuration classes (WebClient, properties)
├── controller/      # REST endpoints
├── entity/          # JPA entities
├── repository/      # Data access layer
├── service/         # Business logic & Mailgun integration
└── exception/       # Custom exception handling
```

---

## 🔐 Security Notes

- Never commit `.env` files to version control
- Add `.env` to your `.gitignore`
- Use environment-specific configuration for production
- Rotate API keys regularly
- Validate and sanitize all user inputs

---

## 🐛 Troubleshooting

### Common Issues

**Email not received:**
- Check Mailgun logs in the dashboard
- Verify recipient is authorized (sandbox domains)
- Confirm API key has send permissions

**Connection errors:**
- Verify `MAILGUN_BASE_URL` format
- Check firewall/proxy settings
- Ensure internet connectivity

**Database issues:**
- Access H2 console to verify data persistence
- Check application logs for JPA errors

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

## 📧 Support

For issues and questions:
- Open an issue on GitHub
- Check Mailgun documentation: [https://documentation.mailgun.com](https://documentation.mailgun.com)
- Review Spring Boot guides: [https://spring.io/guides](https://spring.io/guides)

---

<div align="center">
  <strong>Built with ❤️ using Spring Boot & Mailgun</strong>
</div>