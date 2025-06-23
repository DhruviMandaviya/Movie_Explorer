# 🎬 Movie Explorer

A modern Android application that allows users to search and discover movies by title or genre using The Movie Database (TMDb) API.

## 📱 Features

- 🔍 Search for movies by title
- 🎭 Browse and filter movies by genre (Action, Drama, Sci-Fi, etc.)
- 📄 View movie details such as title, release year, rating, and description
- 📶 Offline-aware UI with no-internet detection dialog
- 🔄 Pagination to load more results as the user scrolls
- ✅ Clear genre selection when typing, and reset search on genre selection
- 🧠 Clean architecture with ViewModel + Repository pattern

## 🏗️ Architecture Overview
This app follows modern Android development best practices:

- **MVVM (Model-View-ViewModel)**
- **Single Activity / Jetpack Compose UI**
- **StateFlow + ViewModel** for reactive state handling
- **Repository pattern** for API/data interaction
- **Modular code** to enhance readability and scalability

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- StateFlow / Coroutine Flow
- Retrofit for network calls
- Material 3
- The Movie Database (TMDb) API

## 🔌 API Integration

- TMDb Search API for querying movies by text
- TMDb Discover API for filtering movies by genre

## 🧪 Edge Cases Handled

- No internet connection: Shows alert dialog
- Empty search: Displays helpful error message
- No results found: Error message for user
- Genre deselected when typing a new query, and vice versa
- Keyboard dismiss handled on action/search button

# 📸 Screenshots
![Screenshot 2025-06-22 at 9 46 53 PM](https://github.com/user-attachments/assets/9dc14cca-c71f-4ef8-97cf-d40f63073829)
![Screenshot 2025-06-22 at 9 46 38 PM](https://github.com/user-attachments/assets/54670ad8-9646-4c06-a9e9-99d82cecbec1)
![Screenshot 2025-06-22 at 9 45 55 PM](https://github.com/user-attachments/assets/ed245e17-7021-423d-b3c2-2ef8447dd487)
![Screenshot 2025-06-22 at 9 45 42 PM](https://github.com/user-attachments/assets/2f78da92-a036-4db1-adaf-207d3b22e32e)




