# 🌸 Sakura Planner - The Ultimate Japan Itinerary Builder

> Your perfect companion for planning unforgettable trips to Japan

---

## 🎯 Project Objective

Sakura Planner is a **Full-Stack web application** designed to simplify the planning of trips to Japan. From managing daily itineraries to tracking expenses in multiple currencies, this tool provides everything you need for seamless travel planning.

Developed as a personal portfolio project, this application demonstrates the implementation of a modern, scalable, and secure architecture using the **Spring Boot and Angular ecosystem**, complete with excellent documentation and best practices.

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

## 🚀 Future Enhancements (Roadmap)

- ✅ Implement a drag-and-drop feature to easily reorder activities within a day.
- ✅ Add a "Collaborative Mode" to plan trips with friends (WebSocket integration).
- ✅ Generate a downloadable PDF summary of the entire itinerary.

---

<div align="center">

**Made with ❤️ for Japan travel enthusiasts**

</div>
