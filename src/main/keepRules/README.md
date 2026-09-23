# Smart Pantry Manager

## Application Description

Smart Pantry Manager is a Java Android application designed to help users reduce food waste by keeping track of ingredients they already have at home.

The application allows users to add, view, edit and delete pantry ingredients. It then compares the user's pantry against a collection of pre-loaded recipes and displays only recipes that can be prepared using the ingredients currently available.

The application is designed around a strict recipe-matching rule. A recipe is only suggested when every required ingredient is available in the pantry in at least the required quantity.

## Main Features

- Add pantry ingredients
- View pantry ingredients
- Edit pantry ingredients
- Delete pantry ingredients
- Persistent local data storage
- Pre-loaded recipe collection
- Strict recipe matching
- Quantity checking
- Basic ingredient name normalization
- Suggested Recipes screen
- Recipe Detail screen
- Settings screen
- Bottom navigation
- Input validation
- No GPS or location services

## Database

The application uses SQLite for local data storage.

SQLite was selected because it provides persistent on-device storage without requiring an external server or internet connection. It is suitable for the application's pantry and recipe data and is consistent with the database concepts covered in Android development.

The database stores pantry ingredients and recipe information so that data remains available when the application is closed and reopened.

## Requirements

- Android Studio
- Java
- Android SDK
- Android emulator or compatible Android device

## How to Run

1. Open the project in Android Studio.
2. Allow Gradle to finish syncing.
3. Connect an Android device or start an Android emulator.
4. Select the application configuration.
5. Press Run.
6. The Smart Pantry Manager application will be installed and launched.

## Application Screens

The application contains:

- Main/Home screen
- Pantry screen
- Add/Edit Ingredient screen
- Suggested Recipes screen
- Recipe Detail screen
- Settings screen

## Recipe Matching

The application uses strict matching when generating recipe suggestions.

A recipe is only displayed when:

1. Every required ingredient exists in the pantry.
2. The available quantity is at least the required quantity.
3. Basic ingredient-name differences such as singular and plural forms are handled.

Recipes that are missing required ingredients or do not have sufficient quantities are excluded from the main Suggested Recipes list.

## Project Structure

The project is written entirely in Java and uses Android Studio.

Main components include:

- Activities for application screens
- SQLite database helper
- Java model classes
- RecyclerView adapters
- Recipe matching logic
- XML layouts
- Bottom navigation

## Version Control

The project is maintained using Git and hosted on GitHub. Development milestones are committed throughout the project to document the development process.