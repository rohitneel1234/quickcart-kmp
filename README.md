# QuickCart — Blinkit-style Grocery App (Kotlin Multiplatform)

A Compose Multiplatform grocery-delivery app scaffold, modeled on Blinkit's UX:
category browsing, search, product detail, cart, checkout, and order history —
sharing 100% of the UI and business logic across **Android**, **iOS**, and
**Desktop** from a single `commonMain` source set.

## What's included

- **16 grocery categories, 60+ products** — Fruits & Vegetables, Dairy &
  Breakfast, Atta/Rice/Dal, Bakery, Snacks, Beverages, Tea/Coffee, Meat/Fish/Eggs,
  Frozen Food, Masala/Oil, Sweets & Namkeen, Household Cleaning, Personal Care,
  Baby Care, Pet Care, Stationery — see `data/SampleData.kt`.
- **Home screen** — delivery ETA header, search bar, promo banner, category
  grid, "Popular Right Now" carousel.
- **Category & Search screens** — product grids with live add/remove steppers.
- **Product detail screen** — price, discount, rating, description, add-to-cart.
- **Cart screen** — line items, quantity steppers, bill breakdown (items total,
  delivery fee, handling fee, grand total), free-delivery threshold logic.
- **Checkout screen** — address selection, order placement.
- **Order success + Order history screens.**
- Reactive state via `StateFlow` (`CartRepository`, `OrderRepository`), no
  external state-management library required.
- Blinkit-inspired theme (green primary / yellow accent) in `ui/theme/Theme.kt`.

## Project structure

```
QuickCart/
├── composeApp/
│   ├── build.gradle.kts          # KMP targets: android, iOS x3, desktop(jvm)
│   └── src/
│       ├── commonMain/kotlin/com/blinkclone/app/
│       │   ├── model/            # Category, Product, CartLine, Order, Address
│       │   ├── data/             # SampleData, GroceryRepository, CartRepository, OrderRepository
│       │   ├── navigation/       # Sealed-class Screen + bottom-tab mapping
│       │   ├── ui/theme/         # Colors, typography
│       │   ├── ui/components/    # ProductCard, CategoryTile, QuantityStepper, etc.
│       │   ├── ui/screens/       # Home, Category, Search, ProductDetail, Cart, Checkout, Orders
│       │   └── App.kt            # Root composable: nav host + bottom nav bar
│       ├── androidMain/          # MainActivity, AndroidManifest
│       ├── iosMain/               # MainViewController() entry for Xcode/SwiftUI
│       └── desktopMain/          # main() for `./gradlew :composeApp:run`
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/libs.versions.toml
```

## Running it

### Prerequisites
- Android Studio (Koala+) with the Kotlin Multiplatform plugin, or IntelliJ IDEA.
- JDK 17+.
- For iOS: a Mac with Xcode 15+.

### Android
Open the project root in Android Studio and run the `composeApp` configuration
on an emulator/device (min SDK 24).

### Desktop (fastest way to preview on this machine)
```bash
./gradlew :composeApp:run
```

### iOS
This scaffold includes the shared Kotlin/Native entry point
(`iosMain/MainViewController.kt`) but not a generated Xcode project (that's
normally created by Android Studio's "New Kotlin Multiplatform App" wizard or
via `kdoctor`/Fleet). To wire it up:
1. In Android Studio, use **File → New → New Module → Kotlin Multiplatform**
   only to generate an `iosApp/` Xcode project shell (or create one manually
   in Xcode), then delete its default shared module — keep this repo's
   `composeApp` as the shared module.
2. In `iosApp`'s SwiftUI `App`, call into Kotlin:
   ```swift
   import UIKit
   import SwiftUI
   import ComposeApp

   struct ComposeView: UIViewControllerRepresentable {
       func makeUIViewController(context: Context) -> UIViewController {
           MainViewControllerKt.MainViewController()
       }
       func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
   }
   ```
3. Build & run from Xcode.

## Extending toward a real product

This is a fully wired **UI + local state** scaffold, not a backend. To take it
further:
- Replace `GroceryRepository`'s static list with real network calls (Ktor
  client is the standard KMP choice) — no screen code needs to change since
  they only depend on the repository's function signatures.
- Add persistence (SQLDelight) so the cart survives process death.
- Add real user auth, payments, and live order tracking (websocket/polling)
  in place of the mocked `OrderStatus`.
- Add product images (Coil3/Kamel for KMP) instead of the emoji placeholders
  used here to keep the scaffold dependency-free and instantly runnable.
