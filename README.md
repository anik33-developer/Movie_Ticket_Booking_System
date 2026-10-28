# Movie Ticket Booking System

A BookMyShow-style full stack web app. Users can browse movies, pick a show, choose seats on a seat map, and book or cancel tickets. An admin panel manages cities, theaters, screens, seats, movies and shows.

Built with **Spring Boot, Spring Data JPA (Hibernate), MySQL** on the backend and **HTML, CSS, JavaScript** on the frontend.

## Features

**User**
- Register and log in
- Browse movies, search by title, filter by genre and language
- View shows for a movie with theater, screen, date, time and price
- Interactive seat map (available, selected, booked; Regular, Premium and VIP seats)
- Book multiple seats in one booking, with the total price calculated on the server
- View booking history and cancel a booking (cancelled seats become available again)

**Admin**
- Add and manage cities, movies, theaters, screens, seats and shows

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 4, Spring Web MVC, Spring Data JPA, Hibernate |
| Database | MySQL |
| Frontend | HTML, CSS, vanilla JavaScript (`fetch` API) |
| Build | Maven |
| Other | Lombok |

## Architecture

```
Browser (HTML/JS)  ->  Controller  ->  Service  ->  Repository  ->  MySQL
```

- **Controller**: REST endpoints under `/api/...`, returns JSON with `ResponseEntity`
- **Service**: business logic (seat availability check, price calculation, transactions)
- **Repository**: Spring Data JPA interfaces, derived queries plus a custom JPQL query
- **DTOs**: request objects (`BookingRequest`, `MovieRequest`, ...) so clients send only the fields they are allowed to
- **GlobalExceptionHandler**: converts exceptions into JSON error responses

### Data model

```
City -> Theater -> Screen -> Seat
Movie + Screen -> Show
User + Show + Seats -> Booking   (many-to-many through booking_seats)
```

## API Overview

| Resource | Endpoints |
|---|---|
| Users | `POST /api/users/register`, `POST /api/users/login`, `GET /api/users/{id}` |
| Movies | `GET /api/movies`, `GET /api/movies/{id}`, `GET /api/movies/search?title=`, `GET /api/movies/genre/{genre}`, `GET /api/movies/language/{language}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| Cities | `GET /api/cities`, `POST /api/cities` |
| Theaters | `GET /api/theaters`, `GET /api/theaters/city/{cityId}`, `POST` |
| Screens | `GET /api/screens`, `GET /api/screens/theater/{theaterId}`, `POST` |
| Seats | `GET /api/seats/screen/{screenId}`, `POST` |
| Shows | `GET /api/shows`, `GET /api/shows/movie/{movieId}`, `GET /api/shows/movie/{movieId}/date?date=`, `POST` |
| Bookings | `POST /api/bookings`, `GET /api/bookings/user/{userId}`, `PUT /api/bookings/{id}/cancel`, `GET /api/bookings/show/{showId}/available-seats` |

## How Booking Works

1. The user selects a show; the frontend loads all seats of the screen and the seats still available for that show.
2. On confirm, the frontend sends `POST /api/bookings` with `userId`, `showId` and `seatIds`.
3. `BookingService.createBooking` runs in a single transaction. It locks the show row, rejects seats that are already booked, validates the seat IDs, calculates the total price and saves the booking.
4. Cancelling sets the status to `CANCELLED`. Availability is computed from `CONFIRMED` bookings only, so those seats are free again.

Concurrent bookings for the same show are serialized with a pessimistic lock (`SELECT ... FOR UPDATE`) on the show row, which prevents two users from booking the same seat.

## Running Locally

**Prerequisites:** Java 21, Maven (or the included `mvnw`), MySQL running on `localhost:3306`.

1. Clone the repo
   ```bash
   git clone https://github.com/anik33-developer/Movie_Ticket_Booking_System.git
   cd Movie_Ticket_Booking_System
   ```
2. Check the database credentials in `src/main/resources/application.properties` (default user `root`, password `root`). The `BMS` database is created automatically.
3. Start the backend
   ```bash
   ./mvnw spring-boot:run
   ```
   The API runs at `http://localhost:8080`. Tables are created on first start.
4. (Optional) Load sample data: run `src/main/resources/BMS.sql` in MySQL after the first start.
5. Open `UI/UI/index.html` in a browser (or serve the `UI/UI` folder with a live server).

## Known Limitations and Future Work

This is a learning project focused on the booking flow. Planned improvements:

- Hash passwords with BCrypt and add JWT authentication with Spring Security (passwords are currently stored in plain text)
- Return response DTOs instead of entities, so sensitive fields are never exposed
- Custom exceptions with proper status codes (404 not found, 409 conflict) instead of a generic 400
- Use `BigDecimal` for money and add seat-type based pricing
- Input validation with `@Valid`, pagination, and a booking ownership check on cancel
- Temporary seat holds with expiry during checkout
- Unit and integration tests

## Author

**Aniket Kokate** - [GitHub](https://github.com/anik33-developer)
