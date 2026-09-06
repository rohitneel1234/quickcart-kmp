package com.rohitneel.quickcart

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

/**
 * Entry point used from Swift/Xcode:
 *
 *   let controller = MainViewControllerKt.MainViewController()
 *
 * Wire this into an iosApp Xcode project's SwiftUI `UIViewControllerRepresentable`
 * (see README.md for the standard KMP + Compose Multiplatform iOS wiring).
 */
fun MainViewController(): UIViewController = ComposeUIViewController { App() }
