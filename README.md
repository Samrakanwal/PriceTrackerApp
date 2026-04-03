# Price Tracker App

A real-time stock price tracking Android application built using Jetpack Compose.  
The app simulates live market updates using a WebSocket echo server and demonstrates modern Android architecture with reactive UI.

---

## Overview

This project showcases how to build a responsive, real-time UI using Kotlin Flow and WebSockets.  
It tracks multiple stock symbols and updates their prices continuously, reflecting changes instantly across multiple screens.

---

## Features

- Live price updates for 25 stock symbols
- Real-time UI updates using WebSocket echo
- Price change indicators (↑ increase / ↓ decrease)
- Visual feedback:
    - Price flashes green on increase
    - Price flashes red on decrease
- Sorted feed (highest price first)
- Start / Stop streaming control
- Connection status indicator (green / red)
- Symbol details screen with live updates
- Deep link support:
  stocks://symbol/{symbol}
- Light and Dark theme support

---

##  Screens

### Feed Screen
- Displays all tracked symbols in a scrollable list
- Shows:
- symbol name
- current price
- direction indicator
- Tap any row to open details
- Top bar:
- connection status
- start/stop toggle

### Details Screen
- Displays selected symbol
- Real-time price updates
- Direction indicator
- Uses `SavedStateHandle` for navigation argument

---

##  Architecture

The app follows a simple **MVVM + Repository pattern**:
UI (Compose)
↓
ViewModel
↓
Repository (single source of truth)
↓
WebSocket client



### Key decisions:
- A **single WebSocket connection** is shared across the app
- State is exposed via `StateFlow`
- UI observes immutable state only
- No duplicate data streams across screens

---

##  Data Flow

1. Every 2 seconds:
    - A random price is generated for each symbol
2. Data is sent to:
wss://ws.postman-echo.com/raw
3. The server echoes the same message back
4. The app parses the response and updates state
5. UI recomposes automatically via Flow
##  Project Structure

com.example.pricetrackerapp
data/
model/
StockPrice.kt
repository/
StockRepository.kt
websocket/
PriceWebSocketClient.kt
ui/
feed/
FeedViewModel.kt
details/
DetailsViewModel.kt
navigation/
AppNavGraph.kt
screens/
FeedScreen.kt
SymbolDetailsScreen.kt
theme/
Color.kt
Theme.kt
Type.kt  


---

##  Navigation

Navigation is implemented using **Navigation Compose**.

Routes:
- `feed` → main list
- `details/{symbol}` → details screen

Deep link supported:
stocks://symbol/AAPL



---

##  UI & Design

- Built entirely with **Jetpack Compose**
- Material 3 components
- Supports **light and dark themes**
- Smooth UI updates via state-driven rendering

---

##  Testing

Includes:
- Unit tests for core logic (`StockPrice`)
- Compose UI tests for screen rendering

---

##  Tech Stack

- Kotlin
- Jetpack Compose
- Navigation Compose
- Kotlin Coroutines + Flow
- OkHttp (WebSocket)
- Material 3

---

##  How to Run

1. Clone the project
2. Open in Android Studio
3. Build and run on emulator/device
4. Tap **Start** to begin live updates

---

##  Notes

- Uses a public echo WebSocket server (not a real stock API)
- Prices are simulated for demonstration purposes
- Requires internet permission

---

##  What This Project Demonstrates

- Reactive UI with Flow + Compose
- Real-time data handling
- Clean separation of concerns
- Navigation with arguments and deep links
- Managing shared state across multiple screens

---

##  License

This project is for learning and demonstration purposes.






