# Book My Stay App

## 📖 Overview
The **Book My Stay App** is a Hotel Booking Management System designed to illustrate the practical application of **Core Java** and fundamental **data structures** in real-world scenarios.  
The project is developed incrementally, with each **Use Case (UC)** introducing a specific concept that addresses common software engineering challenges such as fair request handling, inventory consistency, thread safety, and persistence.

By focusing on **core logic and system behavior** rather than user interface concerns, the app helps learners understand not only *how* data structures are used, but *why* they are essential in scalable and maintainable software systems.

---

## 🚀 Features by Use Case

| Use Case | Concept Introduced | Key Data Structure / Technique |
|----------|--------------------|--------------------------------|
| UC1 | Welcome Message | Basic Java program structure |
| UC2 | Room Types & Availability | Abstract classes, inheritance, static variables |
| UC3 | Centralized Inventory | `HashMap<String, Integer>` |
| UC4 | Room Search | Read-only access, filtering |
| UC5 | Booking Request Queue | `Queue<Reservation>` (FIFO) |
| UC6 | Reservation Confirmation | `Set<String>` for unique room IDs |
| UC7 | Add-On Services | `Map<String, List<Service>>` |
| UC8 | Booking History & Reporting | `List<Reservation>` |
| UC9 | Error Handling & Validation | Custom exceptions, fail-fast validation |
| UC10 | Booking Cancellation | `Stack<String>` rollback |
| UC11 | Concurrent Booking Simulation | Thread safety, `synchronized` methods |
| UC12 | Data Persistence & Recovery | Serialization, file-based persistence |

---

## 🛠️ Tech Stack
- **Language:** Java (Core Java, JDK 17+ recommended)
- **IDE:** IntelliJ IDEA
- **Version Control:** Git & GitHub
- **Persistence:** File-based serialization (UC12)

---

## 📂 Project Structure

