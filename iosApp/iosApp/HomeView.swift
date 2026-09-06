import SwiftUI
import Shared

struct HomeView: View {
    @State private var trendingProducts: [Product] = []
    @State private var categories: [Shared.Category] = []

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
                                            .background(Color.green.opacity(0.1))
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
                                ProductCard(product: product)
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

struct ProductCard: View {
    let product: Product

    var body: some View {
        VStack(alignment: .leading) {
            Text(product.emoji)
                .font(.system(size: 60))
                .frame(maxWidth: .infinity)
                .frame(height: 120)
                .background(Color.gray.opacity(0.1))
                .cornerRadius(12)

            Text(product.name)
                .font(.headline)
                .lineLimit(1)

            Text(product.unit)
                .font(.caption)
                .foregroundColor(.secondary)

            HStack {
                Text("$\(String(format: "%.2f", product.price))")
                    .font(.subheadline)
                    .bold()
                Spacer()
                Button(action: {
                    CartRepository.shared.add(product: product)
                }) {
                    Image(systemName: "plus.circle.fill")
                        .foregroundColor(.green)
                        .font(.title3)
                }
            }
            .padding(.top, 4)
        }
        .padding()
        .background(Color(UIColor.systemBackground))
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 5, x: 0, y: 2)
    }
}
