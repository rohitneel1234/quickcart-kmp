import SwiftUI
import Shared

struct HomeView: View {
    @State private var trendingProducts: [Product] = []
    @State private var categories: [Shared.Category] = []
    @ObservedObject private var cartManager = CartManager.shared

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 20) {
                    // Search Bar Placeholder
                    NavigationLink(destination: SearchView()) {
                        HStack {
                            Image(systemName: "magnifyingglass")
                                .foregroundColor(.gray)
                            Text("Search products...")
                                .foregroundColor(.gray)
                            Spacer()
                        }
                        .padding()
                        .background(Color(.systemGray6))
                        .cornerRadius(10)
                        .padding(.horizontal)
                    }
                    .buttonStyle(PlainButtonStyle())

                    // Categories
                    Text("Categories")
                        .font(.title2)
                        .bold()
                        .padding(.horizontal)

                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 15) {
                            ForEach(categories, id: \.id) { category in
                                NavigationLink(destination: CategoryView(categoryId: category.id)) {
                                    VStack {
                                        Text(category.emoji)
                                            .font(.system(size: 30))
                                            .frame(width: 60, height: 60)
                                            .background(Color(hex: category.colorHex))
                                            .cornerRadius(30)
                                        Text(category.name)
                                            .font(.caption)
                                            .foregroundColor(.primary)
                                    }
                                }
                            }
                        }
                        .padding(.horizontal)
                    }

                    // Trending
                    Text("Trending Products")
                        .font(.title2)
                        .bold()
                        .padding(.horizontal)

                    LazyVGrid(columns: columns, spacing: 15) {
                        ForEach(trendingProducts, id: \.id) { product in
                            NavigationLink(destination: ProductDetailView(productId: product.id)) {
                                ProductCard(product: product, quantity: cartManager.qtyFor(productId: product.id))
                            }
                            .buttonStyle(PlainButtonStyle())
                        }
                    }
                    .padding(.horizontal)
                }
            }
            .navigationTitle("QuickCart")
            .onAppear {
                self.categories = GroceryRepository.shared.getCategories()
                self.trendingProducts = GroceryRepository.shared.getTrendingProducts()
            }
        }
    }
}

