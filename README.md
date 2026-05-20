# 🌸 Sakura Planner - The Ultimate Japan Itinerary Builder

> Your perfect companion for planning unforgettable trips to Japan

---

## 🎯 Project Objective

Sakura Planner is a **Full-Stack web application** designed to simplify the planning of trips to Japan. From managing daily itineraries to tracking expenses in multiple currencies, this tool provides everything you need for seamless travel planning.

Developed as a personal portfolio project, this application demonstrates the implementation of a modern, scalable, and secure architecture using the **Spring Boot and Angular ecosystem**, complete with excellent documentation and best practices.

---

## 📊 Project Status & Current Phase

We are currently in **Phase 1: Architecture Setup & Core Data Modeling**.

- [x] Project architecture design and Tech Stack definition.
- [x] Docker environment configuration for PostgreSQL.
- [x] Database connection setup in Spring Boot.
- [•] Core Backend Entities definition (In progress: `User` entity created).
- [ ] Backend REST APIs and Service Layer implementation.
- [ ] Angular Standalone Frontend initialization and layout structure.
- [ ] External Third-Party APIs Integration.

---

## ✨ Core Features

- 🔐 **Secure Authentication**: User registration and login using Spring Security and JWT (JSON Web Tokens).

- 🗺️ **Interactive Itinerary Management**: Create trips, add specific days, and insert daily activities (temples, restaurants, transport) with specific time slots.

- 📍 **Map Integration**: Search and visualize locations directly on an interactive map using external geographic APIs.

- 💴 **Dynamic Budget Tracker**: Assign a cost in Yen (JPY) to each activity. The app automatically calculates the total budget and converts it to the user's local currency (e.g., EUR) using real-time exchange rates.

- 🌤️ **Weather Insights**: Get weather forecasts for the specific dates and locations of the trip.

---

## 🛠️ Tech Stack & Architecture

### Backend (Java / Spring Boot)
- **Framework**: Spring Boot (RESTful API architecture)
- **Data Access & ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT
- **Validation**: Hibernate Validator
- **Documentation**: Swagger / OpenAPI 3.0

### Frontend (TypeScript / Angular)
- **Framework**: Angular
- **UI Components**: Angular Material & TailwindCSS (or Bootstrap)
- **State Management / Async**: RxJS
- **Routing**: Angular Router with Route Guards (for protected pages)

### Database
- **RDBMS**: PostgreSQL
- **Schema Migration**: Flyway or Liquibase

---

## 🔌 External APIs Integrated

To provide a rich and dynamic user experience, Sakura Planner relies on the following third-party APIs (all proxied through the Spring Boot backend for security):

- **🗺️ Mapbox API / Google Places API**: For location autocomplete, fetching coordinates, and rendering interactive maps on the frontend.

- **💱 Frankfurter API (or ExchangeRate-API)**: To fetch daily, accurate currency exchange rates (EUR/USD to JPY) for the budget tracker.

- **🌡️ OpenWeatherMap API**: To display accurate weather data for selected Japanese cities during the planned travel dates.

- **📖 Wikipedia REST API** *(Bonus)*: To automatically fetch brief descriptions and thumbnails for famous landmarks added to the itinerary.

---

## 🗄️ Database Schema Overview

The relational database is structured to efficiently handle the itinerary hierarchy:

| Entity | Description | Relationship |
|--------|-------------|--------------|
| **User** | Manages credentials and profile data. | — |
| **Trip** | Contains metadata like Title (e.g., "Autumn in Kyoto"), Start Date, End Date, and Total Budget. | Many-to-One with User |
| **TripDay** | Represents a specific day within the trip (e.g., "Day 1 - Arrival"). | Many-to-One with Trip |
| **Activity** | The individual event or place to visit (e.g., "Senso-ji Temple"), including start time, end time, location data, and cost. | Many-to-One with TripDay |

---

## 🚀 Getting Started & How to Run

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
Make sure you have the following installed on your system:
- **Java JDK 17 or 21**
- **Node.js** (LTS version) & **Angular CLI**
- **Docker & Docker Compose**
- An IDE (e.g., **Eclipse**, IntelliJ, or VS Code)

### Step-by-Step Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YOUR_USERNAME/sakura-planner.git](https://github.com/YOUR_USERNAME/sakura-planner.git)
   cd sakura-planner
2. Environment Configuration:

- Copy the .env.example file and rename it to .env.
- The default configuration works out of the box with the Docker container setup.

3. Start the Infrastructure (Database):
Make sure Docker Desktop is running, then execute:
   ```bash
   docker-compose up -d

*This will download and spin up the PostgreSQL 15 container in the background.*

4. **Run the Backend (Spring Boot):**
   - Import the `backend` folder into your IDE (as an *Existing Maven Project*).
   - Locate the main application class (with `@SpringBootApplication`) and run it as a **Java Application**.
   - *Alternatively, via terminal from the backend folder:* `./mvnw spring-boot:run`

5. **Run the Frontend (Angular):**
   Open a new terminal window, navigate to the frontend folder, install dependencies, and start the development server:
   cd frontend
   npm install
   ng serve

---

## 🚀 Future Enhancements (Roadmap)

- ✅ Implement a drag-and-drop feature to easily reorder activities within a day.
- ✅ Add a "Collaborative Mode" to plan trips with friends (WebSocket integration).
- ✅ Generate a downloadable PDF summary of the entire itinerary.

---

<div align="center">

**Made with ❤️ for Japan travel enthusiasts**

</div>
