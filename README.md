# KBTU Green Ecosystem

A Kotlin Android app prototype for greener travel and campus life at KBTU. The current implementation focuses on the Carpool/Trip flow and uses local sample data; no backend service or API credentials are required.

## Implemented screens

- **Trip List** — the start screen, with 12 sample rides, destination/driver search, category filters, and a friendly empty state.
- **Trip Details** — opened with a trip ID through Navigation Compose; shows the route illustration, pickup and destination, driver, available seats, CO₂ savings, a favorite toggle, and a local demo join action.
- **Profile** — sample student profile, EcoCoins, ESG rating, recent activity, and navigation back to Trips.

The app uses a custom green Material 3 theme with light and dark color schemes. The design sketches used as references are in [`design/`](design/), especially `carpool.png`, `trip_details.png`, `profile.png`, and `app_navigation.png`.

## Project structure

```text
app/src/main/java/com/example/ecosystem/
├── MainActivity.kt
├── data/
│   ├── SampleTrips.kt
│   └── model/Trip.kt
└── ui/
    ├── components/
    │   ├── Icon.kt
    │   ├── SectionHeader.kt
    │   ├── TagChip.kt
    │   └── TripCard.kt
    ├── navigation/AppNavigation.kt
    ├── screens/
    │   ├── ProfileScreen.kt
    │   ├── TripDetailsScreen.kt
    │   └── TripListScreen.kt
    └── theme/
        ├── Color.kt
        ├── Spacing.kt
        ├── Theme.kt
        └── Type.kt

app/src/main/res/drawable/trip_route.xml
```

## Requirements

- Android Studio compatible with Android Gradle Plugin 9.3.2
- JDK 17 or newer
- Android SDK Platform 37
- Internet access for the first Gradle dependency sync

The project uses the Compose BOM declared in `gradle/libs.versions.toml`, Material 3, Material Icons Core/Extended, and Navigation Compose. Dependency versions are managed through the version catalog and BOM where applicable.

## Open and build

Clone the repository:

```bash
git clone https://github.com/Akedil02/Android_EcoSystem.git
cd Android_EcoSystem
```

1. Open the repository root folder in Android Studio (the folder containing `settings.gradle.kts`).
2. Select **File > Sync Project with Gradle Files** and wait for Gradle sync to finish. Install Android SDK Platform 37 if prompted.
3. Run the `app` configuration on an emulator or Android device.

Build a debug APK from the repository root:

```bash
# macOS / Linux
./gradlew :app:assembleDebug

# Windows PowerShell or Command Prompt
gradlew.bat :app:assembleDebug
```

Run local unit tests:

```bash
# macOS / Linux
./gradlew :app:testDebugUnitTest

# Windows PowerShell or Command Prompt
gradlew.bat :app:testDebugUnitTest
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.
