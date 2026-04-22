# The Amazing Hunt / המרוץ למטמון

Native Android MVP scaffold for a local-first treasure hunt creation tool.

## Stack

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- ViewModel architecture
- Room local persistence
- Clean architecture-inspired `presentation`, `domain`, and `data` layers

Hebrew strings live in `app/src/main/res/values/strings.xml` as the default locale. English can be added later by creating `app/src/main/res/values-en/strings.xml` with the same keys.

## Build note

The project targets Android API 36.1 and JDK 17. Open it in Android Studio with the matching SDK installed, or run Gradle from a shell where Java and Gradle are available.
