# Mobile Wallet With Merchant Payment Using Android

## 📱 Project Overview

A complete **QR-based merchant payment system** for secure, cashless transactions between consumers and merchants using Android applications with a Java Spring Boot backend.

**Current Date:** 2026-05-15  
**Author:** Full Stack Development Team  
**Status:** Production Ready

---

## 🎯 Project Objectives

1. **Secure Wallet Management** - Encrypted wallet systems for both consumers and merchants
2. **QR-Based Payments** - Dynamic QR code generation and scanning for transactions
3. **Transaction Security** - JWT authentication, AES encryption, and role-based authorization
4. **Real-time Notifications** - Firebase push notifications for payment updates
5. **Bank Integration** - Secure bank account management and transfers
6. **Admin Dashboard** - Comprehensive monitoring and analytics

---

## 🛠️ Technology Stack

### Frontend (Mobile)
- **Android Studio** with Java
- **Material Design 3**
- **MVVM Architecture**
- **Retrofit 2** - REST API client
- **LiveData & ViewModel** - Reactive programming
- **ZXing** - QR Code scanning
- **QRGen** - QR Code generation
- **Firebase** - Push notifications

### Backend
- **Spring Boot 3.x** - Java framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **Spring Data JPA** - Database access
- **Hibernate** - ORM
- **Maven** - Dependency management
- **Swagger/OpenAPI** - API documentation

### Database
- **MySQL 8.0+** or SQL Server
- **Liquibase/Flyway** - Database versioning

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container setup
- **Nginx** - Reverse proxy
- **Jenkins/GitHub Actions** - CI/CD

---

## 📁 Project Structure

```
mobile-wallet-merchant-payment/
├── backend/
│   ├── src/main/java/com/wallet/
│   │   ├── controller/          # REST API endpoints
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Database access
│   │   ├── entity/              # JPA entities
│   │   ├── dto/                 # Data transfer objects
│   │   ├── security/            # JWT, encryption
│   │   ├── config/              # Configuration classes
│   │   ├── exception/           # Exception handling
│   │   ├── util/                # Utility classes
│   │   └── WalletApplication.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   ├── application-prod.properties
│   │   ├── application-dev.properties
│   │   └── db/migration/        # SQL migration scripts
│   ├── src/test/java/           # Test cases
│   ├── pom.xml                  # Maven configuration
│   ├── Dockerfile               # Docker image
│   └── .dockerignore
├── consumer-app/                # Android Consumer App
│   ├── app/src/main/java/com/wallet/consumer/
│   │   ├── ui/
│   │   ├── viewmodel/
│   │   ├── api/
│   │   ├── repository/
│   │   ├── model/
│   │   └── utils/
│   ├── app/src/main/res/        # Resources, layouts, drawables
│   ├── build.gradle
│   └── settings.gradle
├── merchant-app/                # Android Merchant App
│   ├── app/src/main/java/com/wallet/merchant/
│   ├── app/src/main/res/
│   ├── build.gradle
│   └── settings.gradle
├── database/
│   ├── schema.sql               # Complete database schema
│   ├── sample_data.sql          # Sample data
│   └── migrations/              # Version control for DB
├── docs/
│   ├── API_DOCUMENTATION.md
│   ├── ARCHITECTURE.md
│   ├── SECURITY.md
│   ├── DEPLOYMENT.md
│   ├── USER_MANUAL.md
│   ├── diagrams/
│   │   ├── system_architecture.md
│   │   ├── erd.md
│   │   ├── uml_diagrams.md
│   │   ├── dfd.md
│   │   └── sequence_diagram.md
│   └── postman_collection.json
├── docker-compose.yml
├── nginx.conf
├── .github/
│   └── workflows/
│       ├── backend_ci.yml
│       ├── android_ci.yml
│       └── deploy.yml
├── .env.example
├── SETUP.md
└── CONTRIBUTING.md
```

---

## 🚀 Quick Start

### Prerequisites
- JDK 17+
- Maven 3.8+
- Android Studio 2024+
- MySQL 8.0+
- Docker & Docker Compose
- Node.js (optional, for frontend tools)

### Backend Setup

```bash
# Clone repository
git clone https://github.com/amitv28242/mobile-wallet-merchant-payment.git
cd mobile-wallet-merchant-payment/backend

# Create MySQL database
mysql -u root -p < ../database/schema.sql

# Configure application.properties
cp src/main/resources/application-example.properties src/main/resources/application.properties
# Edit application.properties with your database credentials

# Build project
mvn clean install

# Run application
mvn spring-boot:run

# API will be available at http://localhost:8080
# Swagger UI at http://localhost:8080/swagger-ui.html
```

### Consumer App Setup

```bash
# Open Android Studio
# File > Open > mobile-wallet-merchant-payment/consumer-app

# Configure API endpoint in strings.xml
# Update API_BASE_URL with your backend URL

# Build and run on emulator or device
```

### Merchant App Setup

```bash
# Open Android Studio
# File > Open > mobile-wallet-merchant-payment/merchant-app

# Configure API endpoint in strings.xml
# Update API_BASE_URL with your backend URL

# Build and run on emulator or device
```

---

## 🔐 Security Features

- **JWT Authentication** - Stateless token-based authentication
- **AES-256 Encryption** - Sensitive data encryption
- **Role-Based Access Control (RBAC)** - Permission management
- **SQL Injection Prevention** - Parameterized queries
- **CORS Protection** - Cross-origin request filtering
- **Rate Limiting** - API throttling
- **OTP Verification** - Two-factor authentication
- **Secure Password Hashing** - BCrypt algorithm
- **HTTPS/TLS** - Encrypted communication
- **Session Timeout** - Automatic session expiration

---

## 📊 Main Modules

### 1. User Authentication
- Register with email/phone verification
- Login with JWT token generation
- Password reset with OTP
- Role-based authorization

### 2. Consumer Features
- Wallet balance management
- Add money to wallet
- Debit/Credit card management
- Generate dynamic QR codes
- View transaction history
- Profile management

### 3. Merchant Features
- Scan QR codes
- Receive payments
- Transfer wallet to bank account
- Bank account management
- Transaction history
- Merchant profile

### 4. Admin Features
- User management
- Merchant management
- Transaction monitoring
- Report generation
- Analytics dashboard
- Block/Unblock users

### 5. QR Code System
- Dynamic QR generation with encrypted data
- QR code verification and validation
- Transaction token management
- Timestamp-based verification

### 6. Payment Processing
- Secure payment transfer
- Transaction recording
- Balance updates
- Rollback on failure

### 7. Notifications
- Push notifications via Firebase
- In-app notifications
- Email notifications
- SMS notifications (optional)

---

## 📱 QR Code Flow

```
1. Consumer App:
   - User opens QR Generator
   - App creates transaction token
   - Backend encrypts: { consumerId, walletId, amount, timestamp, token }
   - QR code generated with encrypted data
   - QR displayed on screen

2. Merchant App:
   - Merchant opens QR Scanner
   - Scans consumer's QR code
   - App sends QR data to backend
   
3. Backend:
   - Decrypts QR data
   - Validates timestamp (< 5 minutes)
   - Verifies transaction token
   - Validates amount and balance
   - Debits consumer wallet
   - Credits merchant wallet
   - Records transaction
   - Sends notifications

4. Response:
   - Success/Failure response to Merchant App
   - Real-time notification to Consumer
   - Transaction recorded in history
```

---

## 🗄️ Database Schema

### Core Tables
- **users** - Consumer user accounts
- **merchants** - Merchant business accounts
- **wallets** - User and merchant wallets
- **cards** - Saved debit/credit cards
- **bank_accounts** - Bank account details
- **transactions** - Payment transactions
- **qr_codes** - QR code records
- **notifications** - Notification logs
- **admin** - Admin users
- **audit_logs** - Activity audit trail

---

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/verify-otp` - OTP verification
- `POST /api/auth/refresh-token` - Refresh JWT token
- `POST /api/auth/logout` - User logout

### Wallet
- `GET /api/wallet/balance` - Get wallet balance
- `POST /api/wallet/add-money` - Add money to wallet
- `GET /api/wallet/transactions` - Get transaction history

### QR Code
- `POST /api/qr/generate` - Generate QR code
- `POST /api/qr/verify` - Verify QR code
- `POST /api/qr/scan` - Process scanned QR

### Payment
- `POST /api/payment/transfer` - Transfer money
- `GET /api/payment/history` - Payment history
- `POST /api/payment/confirm` - Confirm payment

### Cards
- `POST /api/cards/add` - Add card
- `GET /api/cards/list` - List cards
- `DELETE /api/cards/{id}` - Delete card

### Bank Transfer
- `POST /api/bank/transfer` - Transfer to bank
- `POST /api/bank/accounts/add` - Add bank account
- `GET /api/bank/accounts` - List bank accounts

### Admin
- `GET /api/admin/users` - List users
- `GET /api/admin/merchants` - List merchants
- `POST /api/admin/block-user/{id}` - Block user
- `GET /api/admin/transactions` - View transactions
- `GET /api/admin/analytics` - Get analytics

---

## 🧪 Testing

### Backend Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn test jacoco:report
```

### Android Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

### API Testing
- **Postman Collection** provided in `docs/postman_collection.json`
- Import and run complete API test suite

---

## 🐳 Docker Deployment

```bash
# Build and run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down
```

---

## 📚 Documentation

- **[API Documentation](docs/API_DOCUMENTATION.md)** - Complete REST API reference
- **[Architecture](docs/ARCHITECTURE.md)** - System design and architecture
- **[Security](docs/SECURITY.md)** - Security implementation details
- **[Deployment](docs/DEPLOYMENT.md)** - Production deployment guide
- **[User Manual](docs/USER_MANUAL.md)** - End-user guide
- **[Setup Instructions](SETUP.md)** - Detailed setup guide

---

## 📈 Key Features

✅ **End-to-End Encryption** - All sensitive data encrypted  
✅ **Real-time Updates** - WebSocket/Firebase for live notifications  
✅ **Scalable Architecture** - Microservices ready  
✅ **Production Ready** - Error handling, logging, monitoring  
✅ **Comprehensive Testing** - Unit, integration, and UI tests  
✅ **Complete Documentation** - API docs, architecture, deployment  
✅ **DevOps Ready** - Docker, CI/CD pipelines included  
✅ **Security Best Practices** - OWASP compliance  

---

## 🔄 Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2026-05-15 | Initial release - Production ready |

---

## 🤝 Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on code style and contribution process.

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 📞 Support

For issues and questions, please create an issue on GitHub or contact the development team.

---

## 🎉 Acknowledgments

Built with ❤️ by the Full Stack Development Team
- Senior Full Stack Java Architect
- Android Developer
- DevOps Engineer
- Security Expert
- Technical Documentation Writer

---

**Last Updated:** 2026-05-15  
**Status:** ✅ Production Ready  
**Next Phase:** Mobile app enhancement and AI integration

