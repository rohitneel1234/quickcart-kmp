import SwiftUI
import Shared

struct ContentView: View {
    @ObservedObject private var cartManager = CartManager.shared
    @State private var selectedTab = 0

    var body: some View {
        ZStack(alignment: .bottom) {
            TabView(selection: $selectedTab) {
                HomeView()
                    .tabItem {
                        Label("Home", systemImage: "house.fill")
                    }
                    .tag(0)

                NavigationView {
                    CategoriesListView()
                }
                .tabItem {
                    Label("Categories", systemImage: "square.grid.2x2.fill")
                }
                .tag(1)

                CartView()
                    .tabItem {
                        Label("Cart", systemImage: "cart.fill")
                    }
                    .badge(cartManager.cartLines.isEmpty ? 0 : cartManager.cartLines.reduce(0) { $0 + Int($1.quantity) })
                    .tag(2)

                NavigationView {
                    OrdersView()
                }
                .tabItem {
                    Label("Orders", systemImage: "scroll.fill")
                }
                .tag(3)
            }
            .accentColor(.green)

            if selectedTab != 2 && selectedTab != 3 && !cartManager.cartLines.isEmpty {
                FloatingCartView(cartLines: cartManager.cartLines) {
                    selectedTab = 2
                }
            }
        }
        .onReceive(NotificationCenter.default.publisher(for: NSNotification.Name("GoHome"))) { _ in
            self.selectedTab = 0
        }
    }
}
