# Codex Master Spec - The Amazing Hunt / המרוץ למטמון

## Purpose of this document

This is a full product and technical specification for an Android MVP app called **The Amazing Hunt** / **המרוץ למטמון**.

The goal is to give Codex enough context to build the app step by step, while keeping the architecture clean, extendable, and ready for future features such as real AI generation, multiplayer mode, English localization, GPS validation, QR validation, and cloud sync.

---

# 1. Product overview

## App names

Hebrew name: **המרוץ למטמון**

English name: **The Amazing Hunt**

The app is a treasure hunt creation tool for parents, hosts, teachers, and activity organizers.

The MVP focuses on allowing an adult user to create a physical treasure hunt game for kids in a defined geographic area, such as a neighborhood, park, school area, or outdoor compound.

The user defines the play area on Google Maps, selects or confirms points of interest, chooses the number of stations and difficulty level, and the app generates a complete treasure hunt with clues, tasks, riddles, hints, host solutions, and an optional printable package.

---

# 2. MVP scope

## Main MVP capabilities

The MVP must support:

1. Creating a new treasure hunt game.
2. Defining game settings:

   * Game title.
   * Age range.
   * Number of participants.
   * Desired duration.
   * Number of stops.
   * Difficulty level.
   * Play area type.
   * Whether road crossing is allowed.
   * Whether to create a printable package.
3. Defining a play area on Google Maps using a polygon.
4. Discovering candidate points of interest inside the selected polygon.
5. Letting the user select, remove, rename, and manually add points.
6. Generating a route from the selected points.
7. Generating clues, tasks, riddles, hints, host solutions, and optional code pieces.
8. Allowing the user to manually edit all generated content.
9. Running the game on the same device.
10. Generating a printable PDF package.
11. Saving games locally.
12. Editing existing games.
13. Deleting games.
14. Keeping all visible UI text in resource files to support localization.

## MVP exclusions

Do not implement in the MVP:

1. User accounts.
2. Backend server.
3. Cloud sync.
4. Multiplayer with several devices.
5. Real-time group tracking.
6. Mandatory GPS validation for arrival at stops.
7. QR validation.
8. Online sharing of games.
9. Payments.
10. Push notifications.

These can be prepared for architecturally, but they should not be implemented unless explicitly requested later.

---

# 3. Core user flow

## Flow summary

1. User opens the app.
2. User selects **Create new game**.
3. User enters basic game details.
4. User defines the play area on a map.
5. App suggests points of interest inside the play area.
6. User selects and edits points.
7. App generates a route.
8. App generates clues and tasks.
9. User reviews and edits the generated game.
10. User saves the game.
11. User can either:

    * Play digitally on the same device.
    * Generate a printable PDF package.

---

# 4. Recommended technical stack

Build a native Android application using:

* Kotlin.
* Jetpack Compose.
* Material 3.
* Navigation Compose.
* ViewModel.
* Kotlin coroutines.
* Room for local persistence.
* Google Maps SDK for Android.
* Places SDK for Android, behind a service interface.
* Local PDF generation service.
* Clean architecture style.

The app should be structured into clear layers:

```text
presentation
  screens
  components
  navigation
  viewmodels

domain
  model
  repository
  usecase
  service

data
  local
  entity
  dao
  repository
  mapper
  service
```

---

# 5. Localization requirements

The default UI language is Hebrew.

Important:

1. Do not hardcode visible UI strings inside Compose components.
2. Place all visible UI strings in `strings.xml`.
3. Structure the app so English can be added easily later.
4. Use RTL-friendly layouts.
5. Avoid assumptions that break English localization.

Required resource structure:

```text
app/src/main/res/values/strings.xml
app/src/main/res/values-en/strings.xml
```

For MVP, `values-en/strings.xml` can be added later, but the code must be ready for it.

---

# 6. App screens

## 6.1 Home screen

Purpose:
Allow the user to start a new game, view saved games, or learn how the app works.

Content:

* App title: המרוץ למטמון
* English subtitle: The Amazing Hunt
* Primary button: Create new game
* Secondary button: My games
* Link/button: How it works

Actions:

* Create new game opens the game creation wizard.
* My games opens the saved games screen.
* How it works opens an explanation screen.

---

## 6.2 How it works screen

Purpose:
Explain the app in a simple way.

Suggested content:

1. Define your play area.
2. Choose interesting stops.
3. Generate clues and tasks.
4. Play on the same device or print a full activity package.

---

## 6.3 My games screen

Purpose:
Show saved games.

Content:

* List of saved games.
* Empty state if there are no games.
* For each game:

  * Title.
  * Status.
  * Number of stops.
  * Last updated date.
  * Actions: Edit, Play, Print package, Delete.

Game statuses:

* Draft.
* Ready.
* In progress.
* Completed.

---

## 6.4 Create game wizard

The create game wizard should be step-based.

Wizard steps:

1. Basic game details.
2. Play area setup.
3. Point selection.
4. Route preview.
5. Clues and tasks.
6. Summary.

The user should be able to move forward and backward between steps.

The wizard state should be held in a ViewModel.

The wizard state should eventually be persisted as a draft.

---

## 6.5 Step 1 - Basic game details

Fields:

* Game title.
* Age range.
* Number of participants.
* Desired duration in minutes.
* Number of stops.
* Difficulty level.
* Area type.
* Allow road crossing.
* Generate printable package.

Age ranges:

* 5-7.
* 8-10.
* 11-13.
* Mixed / family.

Difficulty levels:

* Easy.
* Medium.
* Hard.

Area types:

* Neighborhood.
* Park.
* Open area.
* Custom.

Validation:

* Game title is required.
* Number of stops must be at least 3.
* Desired duration must be positive.

---

## 6.6 Step 2 - Play area setup

Purpose:
Allow the user to define the physical game area.

Map capabilities:

1. Show Google Map.
2. Let the user tap the map to add polygon points.
3. Display polygon points.
4. Draw the polygon.
5. Clear polygon.
6. Confirm polygon.

Rules:

* A valid polygon requires at least 3 points.
* User cannot continue without confirming a valid polygon.
* Store polygon as a list of latitude/longitude points.

Future-ready requirements:

* Prepare for suggested neighborhood polygon selection.
* Prepare for current location based area suggestion.
* Prepare for editing existing polygon vertices.

---

## 6.7 Step 3 - Point selection

Purpose:
Suggest and select points of interest inside the polygon.

Point discovery:

Use a `PlacesService` interface.

Important logic:

Places Nearby Search is radius based. The app should:

1. Calculate polygon center.
2. Calculate a search radius that covers the polygon.
3. Request candidate places around the center.
4. Filter the returned places locally using a point-in-polygon function.
5. Display only places that are inside the polygon.

Candidate point types:

* Parks.
* Playgrounds.
* Schools.
* Public buildings.
* Local landmarks.
* Tourist attractions.
* Fountains.
* Statues.
* Squares.
* Sports courts.
* Transit stations.
* Small shops or kiosks.
* Benches or seating areas when available.
* Any other visually noticeable outdoor object.

User actions:

* Select suggested point.
* Remove point.
* Rename point.
* Add custom point manually on the map.
* Assign stop type.

If external Places results are not available yet, implement a mock service with sample points.

---

## 6.8 Step 4 - Route preview

Purpose:
Generate and preview the route between selected points.

Route generation rules:

1. Select the requested number of stops.
2. Avoid points that are too close to each other.
3. Order stops into a reasonable walking route.
4. Prefer a circular route if no custom end point exists.
5. Mark the last stop as the treasure/final stop.
6. Warn the user if there are not enough usable points.
7. Allow the user to reorder stops manually.

MVP algorithm:

Use a simple nearest-neighbor algorithm.

Do not use a paid routing API in the MVP.

Display:

* Numbered markers on map.
* Connecting polyline.
* Ordered stop list.
* Warnings if route is too long or too short.

---

## 6.9 Step 5 - Clues and tasks

Purpose:
Generate and edit game content.

The app should generate for each stop:

* Clue leading to the stop.
* Optional task.
* Optional riddle.
* Optional hint.
* Solution for host.
* Optional code piece.

Content generation approach:

Use an interface named `HuntContentGenerator`.

For MVP, implement a mock/template-based generator that creates good Hebrew content.

The architecture must allow replacing this with a real AI API later.

User must be able to edit every generated text.

---

## 6.10 Step 6 - Summary screen

Purpose:
Review the complete game before saving or playing.

Show:

* Game title.
* Age range.
* Difficulty level.
* Number of stops.
* Estimated duration.
* Play area summary.
* Route summary.
* Stops list.
* Clues and tasks summary.

Actions:

* Save game.
* Edit game.
* Start game.
* Generate printable package.

---

# 7. Active game mode

Purpose:
Allow the game to be played on the same device.

Screen content:

* Current stop number.
* Total stops.
* Clue for the current stop.
* Task or riddle if relevant.
* Button: Show hint.
* Button: Show map.
* Button: We arrived / continue.
* Progress indicator.

Rules:

1. No GPS validation is required in the MVP.
2. The user manually advances between stops.
3. The game status changes to In Progress when started.
4. The game status changes to Completed after the final stop.
5. The final screen celebrates finding the treasure.

---

# 8. Printable PDF package

Purpose:
Generate a complete printable physical treasure hunt package.

The PDF package should include:

1. Cover page.
2. Host guide.
3. Game overview.
4. Route summary.
5. Clue cards.
6. Task sheets.
7. Riddle sheets.
8. Host solutions page.
9. Final treasure page.
10. Optional final code page if the game includes code pieces.

Design requirements:

* Kid-friendly layout.
* Clean, readable design.
* Hebrew RTL support.
* Each clue card visually separated.
* Host-only pages clearly marked.
* Stop number clearly visible.
* Print-ready format.

PDF generation requirements:

* Use a dedicated `PdfGenerationService`.
* Generate a local PDF file.
* Allow opening, sharing, or printing the PDF.
* Keep PDF generation logic separate from UI.

---

# 9. Domain models

Use these models as the conceptual domain layer.

```kotlin
data class HuntGame(
    val id: String,
    val title: String,
    val language: AppLanguage,
    val childAgeRange: ChildAgeRange,
    val participantsCount: Int?,
    val difficulty: DifficultyLevel,
    val desiredDurationMinutes: Int,
    val numberOfStops: Int,
    val areaType: PlayAreaType,
    val allowRoadCrossing: Boolean,
    val generatePrintablePackage: Boolean,
    val playAreaPolygon: List<MapPoint>,
    val startPoint: MapPoint?,
    val endPoint: MapPoint?,
    val stops: List<HuntStop>,
    val status: GameStatus,
    val createdAt: Long,
    val updatedAt: Long
)

data class HuntStop(
    val id: String,
    val title: String,
    val description: String?,
    val location: MapPoint,
    val orderIndex: Int,
    val type: StopType,
    val clueToThisStop: String,
    val taskText: String?,
    val riddleText: String?,
    val hintText: String?,
    val solutionText: String?,
    val codePiece: String?,
    val isFinalStop: Boolean
)

data class MapPoint(
    val latitude: Double,
    val longitude: Double
)

data class CandidatePointOfInterest(
    val id: String,
    val name: String,
    val type: PointOfInterestType,
    val location: MapPoint,
    val source: PointSource,
    val description: String? = null
)

data class PrintPackage(
    val id: String,
    val gameId: String,
    val includeHostGuide: Boolean,
    val includeClueCards: Boolean,
    val includeTaskSheets: Boolean,
    val includeSolutions: Boolean,
    val includeFinalCodePage: Boolean,
    val generatedPdfUri: String?
)

enum class AppLanguage {
    HEBREW,
    ENGLISH
}

enum class ChildAgeRange {
    AGE_5_7,
    AGE_8_10,
    AGE_11_13,
    FAMILY_MIXED
}

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD
}

enum class PlayAreaType {
    NEIGHBORHOOD,
    PARK,
    OPEN_AREA,
    CUSTOM
}

enum class GameStatus {
    DRAFT,
    READY,
    IN_PROGRESS,
    COMPLETED
}

enum class StopType {
    CLUE_ONLY,
    TASK,
    RIDDLE,
    PHOTO_TASK,
    CODE_PIECE,
    FINAL_TREASURE
}

enum class PointOfInterestType {
    PARK,
    PLAYGROUND,
    SCHOOL,
    PUBLIC_BUILDING,
    LANDMARK,
    FOUNTAIN,
    STATUE,
    SQUARE,
    SPORTS_COURT,
    TRANSIT_STATION,
    SHOP,
    SEATING_AREA,
    CUSTOM,
    OTHER
}

enum class PointSource {
    GOOGLE_PLACES,
    USER_CUSTOM,
    MOCK
}
```

---

# 10. Services and interfaces

Create service interfaces in the domain layer.

## 10.1 Places service

```kotlin
interface PlacesService {
    suspend fun findCandidatePoints(
        polygon: List<MapPoint>,
        preferredTypes: List<PointOfInterestType>
    ): List<CandidatePointOfInterest>
}
```

## 10.2 Route generation service

```kotlin
interface RouteGenerationService {
    fun generateRoute(
        selectedPoints: List<CandidatePointOfInterest>,
        settings: RouteGenerationSettings
    ): RouteGenerationResult
}
```

## 10.3 Content generation service

```kotlin
interface HuntContentGenerator {
    suspend fun generateContentForRoute(
        request: HuntContentGenerationRequest
    ): HuntContentGenerationResult
}
```

## 10.4 PDF generation service

```kotlin
interface PdfGenerationService {
    suspend fun generatePrintPackage(game: HuntGame): PrintPackageResult
}
```

---

# 11. Local persistence

Use Room for local persistence.

Store:

1. Games.
2. Stops.
3. Polygon points.
4. Candidate/selected points if needed.
5. Generated content.
6. PDF package metadata.

Recommended Room tables:

* `hunt_games`
* `hunt_stops`
* `hunt_polygon_points`
* `print_packages`

Use mappers between:

* Room entities.
* Domain models.

Do not use Room entities directly in UI.

---

# 12. Validation and safety rules

## Game setup validation

* Title cannot be blank.
* Number of stops must be at least 3.
* Polygon must include at least 3 points.
* Selected points must be equal to or greater than number of stops.
* Warn if the route may be too long for the selected age range.

## Child safety considerations

The app must include non-legal safety guidance:

* Adult supervision is recommended.
* Avoid unsafe roads.
* Avoid private property.
* Check the area before starting the game.
* Adjust difficulty and distance to the children's age.

This guidance should appear in the host guide and possibly in the setup summary.

---

# 13. AI content generation guidelines

For MVP, use template/mock content generation.

Generated content should be:

* Age appropriate.
* Friendly.
* Clear.
* Easy to edit.
* Suitable for Hebrew.
* Avoiding unsafe instructions.
* Not instructing children to enter private property, roads, construction areas, water sources, or restricted spaces.

The generator should consider:

* Stop title.
* Stop type.
* Age range.
* Difficulty.
* Stop order.
* Whether this is the final stop.

Example output for a playground stop:

Clue:

```text
חפשו מקום שבו מטפסים, מתגלשים וצוחקים. שם מחכה לכם התחנה הבאה.
```

Task:

```text
כל חבר בקבוצה צריך למצוא משהו אחד בצבע כחול באזור התחנה ולספר איפה מצא אותו.
```

Hint:

```text
הסתכלו ליד המגלשה והנדנדות.
```

Host solution:

```text
התחנה היא גן השעשועים שבמרכז האזור.
```

---

# 14. Required navigation routes

Suggested navigation routes:

```kotlin
sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object HowItWorks : AppRoute("how_it_works")
    object MyGames : AppRoute("my_games")
    object CreateGame : AppRoute("create_game")
    object ActiveGame : AppRoute("active_game/{gameId}")
    object GameSummary : AppRoute("game_summary/{gameId}")
}
```

The exact implementation may differ, but navigation should be clean and type-safe where possible.

---

# 15. UI style direction

The UI should feel:

* Friendly.
* Family-oriented.
* Simple.
* Playful but not childish.
* Easy for parents to understand.

Visual direction:

* Material 3.
* Rounded cards.
* Clear step indicators.
* Large buttons.
* Simple icons.
* Map-heavy screens should be clean and not cluttered.
* Hebrew RTL layout should feel natural.

---

# 16. Acceptance criteria for MVP foundation

The MVP foundation is acceptable when:

1. Project builds successfully using `assembleDebug`.
2. App launches on emulator.
3. Home screen is shown.
4. User can navigate to Create Game.
5. User can move through wizard steps.
6. User can enter basic game details.
7. Polygon editor screen exists.
8. Point selection screen exists.
9. Route preview screen exists.
10. Clue/task screen exists.
11. Summary screen exists.
12. Active game screen exists.
13. My Games screen exists.
14. Local Room structure exists.
15. Repository interfaces exist.
16. Mock services exist for Places, route generation, content generation, and PDF generation.
17. UI strings are not hardcoded.
18. Hebrew is the default language.
19. Code is separated into presentation, domain, and data layers.
20. No backend is required.

---

# 17. Implementation plan for Codex

Build the app in phases.

## Phase 1 - Project foundation

Create:

* Android project structure.
* MainActivity.
* Theme.
* Navigation graph.
* Home screen.
* My Games placeholder.
* How It Works screen.
* Create Game wizard placeholders.
* Domain models.
* Room entity and DAO stubs.
* Repository interfaces.
* Mock service interfaces.

## Phase 2 - Full game creation wizard

Implement:

* Wizard state.
* Basic details form.
* Validation.
* Step navigation.
* Summary of entered data.

## Phase 3 - Map polygon editor

Implement:

* Google Map screen.
* Tap to add polygon point.
* Draw polygon.
* Clear polygon.
* Confirm polygon.
* Save polygon in draft state.

## Phase 4 - Points of interest

Implement:

* Places service interface.
* Mock Places service first.
* Point-in-polygon filter.
* Select/remove/rename/add custom points.

## Phase 5 - Route generation

Implement:

* Nearest-neighbor route ordering.
* Stop creation.
* Manual reorder.
* Numbered markers and route preview.

## Phase 6 - Clues and tasks

Implement:

* Mock/template Hebrew content generator.
* Editable generated content.
* Hint and host solution fields.
* Optional code piece support.

## Phase 7 - Active game mode

Implement:

* Play on same device.
* Progress through stops.
* Show hint.
* Show map.
* Complete game.

## Phase 8 - Printable package

Implement:

* PDF generation service.
* Host guide.
* Clue cards.
* Task/riddle sheets.
* Solutions.
* Final treasure page.
* Open/share/print PDF.

---

# 18. Master prompt for Codex

Use this prompt when asking Codex to build or continue the app.

```text
You are building an Android MVP app called "The Amazing Hunt" / "המרוץ למטמון".

The app is a treasure hunt creation tool for parents, hosts, teachers, and activity organizers. The MVP lets a user create a kids' treasure hunt in a defined physical area, select points of interest, generate a route, generate clues/tasks/riddles, play the game on the same device, and generate a printable PDF package.

Use:
- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- Kotlin coroutines
- Room for local persistence
- Google Maps SDK for Android
- Places SDK for Android behind an interface
- Local PDF generation behind an interface
- Clean architecture with presentation, domain, and data layers

Default UI language is Hebrew. All visible UI strings must be in strings.xml. Do not hardcode visible UI text in Compose. The project must be ready for English localization later.

MVP features:
1. Home screen with app title, create new game, my games, and how it works.
2. Create game wizard with these steps:
   - Basic game details
   - Play area setup
   - Point selection
   - Route preview
   - Clues and tasks
   - Summary
3. Basic game settings:
   - Game title
   - Child age range
   - Participants count
   - Desired duration
   - Number of stops
   - Difficulty level
   - Area type
   - Allow road crossing
   - Generate printable package
4. Google Maps polygon editor:
   - Show map
   - Tap to add polygon points
   - Draw polygon
   - Clear polygon
   - Confirm polygon
   - Require at least 3 points
5. Point of interest discovery:
   - Use PlacesService interface
   - Calculate polygon center and search radius
   - Fetch candidate places
   - Filter locally using point-in-polygon
   - Allow selecting, removing, renaming, and adding custom points
6. Route generation:
   - Use simple nearest-neighbor algorithm for MVP
   - Avoid points too close to each other
   - Prefer circular route when no custom endpoint exists
   - Allow manual reorder
   - Mark final stop as FINAL_TREASURE
7. AI/content generation:
   - Use HuntContentGenerator interface
   - For MVP implement MockHuntContentGenerator with high-quality Hebrew templates
   - Generate clues, tasks, riddles, hints, host solutions, and optional code pieces
   - All generated content must be editable
8. Active game mode:
   - Played on the same device
   - Show current stop, clue, task/riddle, hint button, map button, continue button, progress indicator
   - No GPS arrival validation required in MVP
   - Update game status to IN_PROGRESS and COMPLETED
9. Printable package:
   - Use PdfGenerationService interface
   - Generate a local PDF package including host guide, game overview, clue cards, task sheets, riddle sheets, host solutions, final treasure page, and optional final code page
   - Allow opening, sharing, or printing the PDF
10. Local persistence:
   - Save games locally using Room
   - Support draft, ready, in progress, and completed statuses
   - Support edit, play, print, and delete actions

Important architecture requirements:
- Separate presentation, domain, and data layers.
- Do not put business logic inside Compose screens.
- Use ViewModels for screen state.
- Use repositories for data access.
- Use service interfaces for Maps, Places, route generation, content generation, and PDF generation.
- Implement mock services first where real integrations are not yet ready.
- Keep code readable, maintainable, and production-oriented.
- Add comments only where helpful.

Domain models should include:
- HuntGame
- HuntStop
- MapPoint
- CandidatePointOfInterest
- PrintPackage
- AppLanguage
- ChildAgeRange
- DifficultyLevel
- PlayAreaType
- GameStatus
- StopType
- PointOfInterestType
- PointSource

MVP exclusions:
- No backend
- No user accounts
- No cloud sync
- No multiplayer
- No push notifications
- No mandatory GPS arrival validation
- No QR validation
- No payments

Start by reviewing the current codebase if one exists. Then implement the next missing phase while preserving the architecture and ensuring the project builds successfully with assembleDebug.
```

---

# 19. Suggested next prompt after project foundation

If the project foundation already exists, use this next prompt:

```text
Review the current Android project for "The Amazing Hunt" / "המרוץ למטמון" and continue with the full Create Game wizard implementation.

Implement the wizard steps:
1. Basic game details
2. Play area setup placeholder or existing map step
3. Point selection placeholder
4. Route preview placeholder
5. Clues and tasks placeholder
6. Summary

Requirements:
- Use the existing architecture.
- Use ViewModel state management.
- Keep all visible strings in strings.xml.
- Default language is Hebrew.
- Validate basic details.
- Allow next/back navigation.
- Preserve state while moving between steps.
- Save the wizard state as a draft if Room infrastructure already exists.
- Do not implement full Google Maps logic yet unless it already exists.
- Ensure the app builds successfully with assembleDebug.
```

---

# 20. Checklist for reviewing Codex output

After Codex finishes each phase, verify:

```text
[ ] Project builds with assembleDebug.
[ ] App runs on emulator.
[ ] No visible hardcoded UI strings.
[ ] Hebrew UI works.
[ ] Navigation works.
[ ] ViewModels hold screen state.
[ ] Domain models are not mixed with Room entities.
[ ] Repositories are used for data access.
[ ] Service interfaces exist for external or future integrations.
[ ] Mock implementations are used where real integrations are not ready.
[ ] No backend was added.
[ ] Code is organized into presentation/domain/data layers.
[ ] User can continue through the current implemented flow.
```

---

# 21. Product notes for future versions

Future versions may add:

1. Real AI API integration.
2. English UI.
3. Sharing a game with multiple devices.
4. Team mode.
5. QR code validation at stations.
6. GPS arrival validation.
7. Cloud saving.
8. Public templates.
9. Ready-made games by city/park.
10. Monetization and premium game packs.
11. Printable design themes.
12. Photo tasks.
13. Parent dashboard.
14. Game analytics.

Do not implement these in the MVP unless explicitly requested.
