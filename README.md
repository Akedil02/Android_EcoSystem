# Module1：EcoDrop: Smart Eco Container Tracker

EcoDrop is a smart urban sustainability Android application that connects users with nearby eco-containers to encourage proper waste sorting and recycling[cite: 1]. Users can locate containers, log waste deposits, earn reward points, and access integrated eco-taxi and points shop features[cite: 1].

## Core Functionality List

* User can view a map/list of nearby Eco Containers with real-time fill level indicators.
* User can record a waste deposit event to earn eco-points.
* User can create, edit, and delete personal recycling history log entries.
* App persists container status, accumulated user points, and deposit history locally.
* App displays a specific visual alert state (e.g., warning badge) when a container reaches 100% full capacity.
* User can filter containers by accepted waste categories (Plastic, Paper, Glass, E-waste).
* User can view leaderboard rankings and browse the in-app Points Shop.

## Project Folder Structure

```text
EcoDrop/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── java/com/example/ecodrop/
│       │   │   └── MainActivity.kt
│       │   ├── res/
│       │   │   ├── layout/
│       │   │   │   └── activity_main.xml
│       │   │   ├── values/
│       │   │   │   ├── colors.xml
│       │   │   │   ├── strings.xml
│       │   │   │   └── themes.xml
│       │   │   └── mipmap/
│       │   └── AndroidManifest.xml
│       ├── androidTest/
│       └── test/
├── gradle/
│   └── wrapper/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── settings.gradle.kts
└── README.md


***************************************************************************************
Build and Run Instructions
Prerequisites
Android Studio (Ladybug, Jellyfish, or newer)

JDK 17 or higher

Android SDK API Level 24+

Steps
Clone the Repository: git@github.com:Akedil02/Android_EcoSystem.git

Bash
git clone git@github.com:Akedil02/Android_EcoSystem.git
Open Project: Android_EcoSystem

Launch Android Studio.

Click Open and select the cloned EcoDrop project root directory.

Gradle Sync:

Wait for Android Studio to automatically download dependencies and build the project indexing.

If prompted, click Sync Project with Gradle Files.

Run Application:

Set up an Android Virtual Device (Emulator) or connect a physical Android device with USB Debugging enabled.

Click the green Run 'app' button in the top toolbar (or press Shift + F10).
