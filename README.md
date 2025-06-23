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
<img width="341" alt="Screenshot 2025-06-21 at 6 05 26 PM" src="https://github.com/user-attachments/assets/c2f5e42d-963b-4162-98d9-7ce521a474ca" />
<img width="341" alt="Screenshot 2025-06-21 at 6 05 07 PM" src="https://github.com/user-attachments/assets/77cb4590-2cb8-4a37-972c-7811336a75c0" />
<img width="341" alt="Screenshot 2025-06-21 at 6 04 19 PM" src="https://github.com/user-attachments/assets/eb84b6bc-d720-4549-bad8-ab69c49f387b" />
<img width="341" alt="Screenshot 2025-06-21 at 6 06 49 PM" src="https://github.com/user-attachments/assets/140110a0-16a9-4e10-beb7-76418c056202" />



