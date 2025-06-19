# 🎬 Movie Explorer

A modern Android app built with **Jetpack Compose**, implementing clean architecture principles, pagination, and offline-aware UI using the [OMDb API](http://www.omdbapi.com/).

---

## 🌟 Features

- 🔍 **Search** for movies by name  
- 🖼️ View **movie posters, titles, and release years**  
- 🔄 **Pagination** with infinite scrolling support  
- 📶 **Offline handling**: shows dialog when mobile data or Wi-Fi is turned off  
- 🎨 Smooth **UI**, overflow text handling, and responsive layout  
- 🎹 **Keyboard-aware input** and automatic dismiss behavior

---

## 🏗 Architecture

This project uses **MVVM + Clean Architecture**:

- **UI Layer** – Jetpack Compose, StateFlow, Dialogs
- **ViewModel Layer** – Business logic, pagination, connectivity detection
- **Repository Layer** – Network abstraction using Retrofit
- **Network Layer** – OMDb API integration via Retrofit + Moshi

---

## 🛠 Tech Stack

| Tool                 | Purpose                                |
|----------------------|----------------------------------------|
| **Kotlin**           | Primary programming language           |
| **Jetpack Compose**  | Declarative UI toolkit                 |
| **Retrofit**         | HTTP client for networking             |
| **Gson**             | JSON parsing                           |
| **Coil**             | Image loading from URL                 |
| **StateFlow**        | Reactive state management              |
| **ViewModel**        | Lifecycle-aware UI logic               |
| **AndroidViewModel** | Application context support (for connectivity)
