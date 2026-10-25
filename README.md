# 🍔 Yeb To‘yma — Food Delivery & Restaurant Mobile Application

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android" />
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/Architecture-MVC%20%2F%20Fragments-blue?style=for-the-badge" alt="Architecture" />
  <img src="https://img.shields.io/badge/Gradle-Build%20System-02303A?style=for-the-badge&logo=gradle&logoColor=white" alt="Gradle" />
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License" />
</p>

---

## 📌 Overview / Loyiha haqida

### English
**Yeb To'yma** is a native Android food ordering and restaurant delivery application built with Java and modern Android SDK components. It offers an intuitive mobile user experience allowing customers to browse dynamic restaurant menus, manage cart orders, track order history, and manage customer user profiles.

### O‘zbekcha
**Yeb To'yma** — bu Java va Android SDK asosida yaratilgan zamonaviy restoran va taom yetkazib berish mobil ilovasi. Ilova foydalanuvchilarga qulay interfeys orqali turli xil taomlar menyusini ko'rish, savatga qo'shish, buyurtmalar tarixini kuzatish va shaxsiy profilni boshqarish imkoniyatini taqdim etadi.

---

## ✨ Features / Asosiy imkoniyatlar

- 🚀 **Smooth Onboarding & Splash Screen:** Polished transition into the main application.
- 📋 **Interactive Food Catalog:** Categorized food items with responsive `RecyclerView` and custom adapters (`FoodAdapter`, `MenusAdapter`).
- 🛒 **Order & Cart Flow:** Real-time order calculation and checkout handling (`Order_food_adapter`).
- 📱 **Fragment-Based Navigation:**
  - `MenuFragment`: Explore food categories and featured meals.
  - `StoreFragment`: Partner restaurant outlets and branches.
  - `HistoryFragment`: Previous user orders and receipts.
  - `UserFragment`: Account profile details and settings.
- 🎨 **Modern Material UI:** Clean XML layouts, card designs, and smooth ripple animations.

---

## 📁 Project Architecture / Loyiha tuzilishi

```text
app/src/main/java/com/...
├── Dashboard.java                 # Main navigation hub & bottom bar
├── SplashScreen.java              # Animated splash entry
├── FoodAdapter.java               # RecyclerView adapter for food cards
├── MenusAdapter.java              # Category list adapter
├── Order_food_adapter.java        # Shopping cart & order items adapter
├── HistoryFragment.java           # Order history view
├── MenuFragment.java              # Food catalog view
├── StoreFragment.java             # Restaurant branches view
├── UserFragment.java              # Profile management view
├── FoodItemModel.java             # Food item data model
├── Order_food.java                # Order model
└── User.java                      # User entity model
```

---

## 🛠️ Build & Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/HumoyunSaydullayev/Yeb-Toyma-Android-App.git
   ```
2. Open the project in **Android Studio** (Flamingo or newer recommended).
3. Allow Gradle to sync dependencies.
4. Select an Android emulator (API level 24+) or connect a physical Android device with USB debugging enabled.
5. Click **Run (`Shift + F10`)** to build and launch the app.

---

## 👨‍💻 Author
**Humoyun Saydullayev**  
Software Engineering Final Project - KIUT  
GitHub: [@HumoyunSaydullayev](https://github.com/HumoyunSaydullayev)
