import SwiftUI
import Shared

struct ContentView: View {
    @State private var cartLines: [CartLine] = []
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
                    .badge(cartLines.isEmpty ? 0 : cartLines.reduce(0) { $0 + Int($1.quantity) })
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

            if selectedTab != 2 && selectedTab != 3 && !cartLines.isEmpty {
                FloatingCartView(cartLines: cartLines) {
                    selectedTab = 2
                }
            }
        }
        .onAppear {
            observeCart()
        }
        .onReceive(NotificationCenter.default.publisher(for: NSNotification.Name("GoHome"))) { _ in
            self.selectedTab = 0
        }
    }

    private func observeCart() {
        self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { _ in
            self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        }
    }
}

