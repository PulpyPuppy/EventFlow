# EventFlow — Community-Moderated Event Platform

**Project Overview:** EventFlow is a decentralized, community-moderated platform for publishing event announcements. The system prevents spam through a mandatory contribution fee (nominally $5) and a collective voting mechanism that automatically hides unwanted content.

---

## 1. Stack

* **Backend:** Java 17, Spring Boot 4.0.3, Spring Data JPA, Spring Security
* **Database:** H2
* **Frontend:** React.js (Functional Components, Hooks), Axios for API requests, Tailwind CSS
* **Authentication:** Stateless JWT (JSON Web Tokens) for session security

---

## 2. Architecture

The project is built on an **N-Layered Architecture**:

1. **Controller Layer:** REST endpoints (JSON API).
2. **Service Layer:** Encapsulation of business logic (payment validation, rating calculation).
3. **Repository Layer:** Database abstraction (Spring Data JPA).
4. **Security Layer:** Request filtering and JWT token verification.

---

## 3. Entity-Relationship

### `User` Entity
* `id` (UUID)
* `username` (String, unique)
* `password` (String, BCrypt encoded)
* `reputation` (Integer) — sum of all votes cast by the user.
* `isBanned` (Boolean)

### `Event` Entity
* `id` (Long)
* `title`, `description` (Text)
* `author_id` (ManyToOne -> User)
* `status` (Enum: PENDING_PAYMENT, ACTIVE, SOFT_BANNED)
* `votesCount` (Integer) — current vote balance (Upvotes - Downvotes).
* `createdAt` (Timestamp)

### `Vote` Entity
* `user_id`, `event_id` (Composite Key) — enforces the "1 vote per event" rule.
* `type` (UPVOTE / DOWNVOTE)

---

## 4. Core Logic

### 4.1. Economic Barrier (Proof of Stake Lite)
Publishing an event is only possible after a successful call to `PaymentMockService`. The event's status in the database changes from `PENDING_PAYMENT` to `ACTIVE`.

### 4.2. "People's Veto" System (Democratic Ban)
* Users vote on events in the feed.
* **Business rule:** If `Event.votesCount <= N`, the event automatically receives a `SOFT_BANNED` status.
* **Scheduled Task:** Every 15 minutes, the system scans the database (`@Scheduled`) and archives community-suppressed events, notifying their authors.

### 4.3. Ranking Algorithm
Events in the feed are sorted in descending order by `votesCount`, pushing high-quality content to the top (similar to Reddit Hot).

---

## 5. API Endpoints (Examples)

* `POST /api/auth/register` — user registration.
* `POST /api/events/create` — create a draft (requires payment).
* `GET /api/events/feed` — retrieve list of active events.
* `POST /api/events/{id}/vote` — submit a vote (Up/Down).
* `GET /api/admin/banned` — view banned events (Admin only).

---

## 6. Development Plan (Milestones)

1. **Phase 1:** Set up Spring Boot, create JPA entities and DB migrations.
2. **Phase 2:** Implement Security (JWT) and basic CRUD for events.
3. **Phase 3:** Develop voting logic and `@Scheduled` task for automated bans.
4. **Phase 4:** Build the React frontend: feed, user dashboard, and voting interface.
