import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Label("Home", systemImage: "house.fill")
                }

            CategoriesListView()
                .tabItem {
                    Label("Categories", systemImage: "square.grid.2x2.fill")
                }

            CartView()
                .tabItem {
                    Label("Cart", systemImage: "cart.fill")
                }

            OrdersView()
                .tabItem {
                    Label("Orders", systemImage: "scroll.fill")
                }
        }
        .accentColor(.green)
    }
}
