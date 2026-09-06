import SwiftUI
import Shared

struct HomeView: View {
    @State private var trendingProducts: [Product] = []
    @State private var categories: [Shared.Category] = []
    @State private var cartLines: [CartLine] = []

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    func qtyFor(productId: String) -> Int {
        return Int(cartLines.first(where: { $0.product.id == productId })?.quantity ?? 0)
    }

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
                                ProductCard(product: product, quantity: qtyFor(productId: product.id))
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
                observeCart()
            }
        }
    }

    private func observeCart() {
        self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { _ in
            self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        }
    }
}

struct ProductCard: View {
    let product: Product
    let quantity: Int

    var body: some View {
        VStack(alignment: .leading) {
            ZStack(alignment: .topLeading) {
                Text(product.emoji)
                    .font(.system(size: 60))
                    .frame(maxWidth: .infinity)
                    .frame(height: 120)
                    .background(Color.gray.opacity(0.1))
                    .cornerRadius(12)

                if product.discountPercent > 0 {
                    Text("\(product.discountPercent)% OFF")
                        .font(.system(size: 10, weight: .bold))
                        .foregroundColor(.white)
                        .padding(.horizontal, 6)
                        .padding(.vertical, 2)
                        .background(Color.red)
                        .cornerRadius(4, corners: [.topLeft, .bottomRight])
                }
            }

            Text(product.name)
                .font(.headline)
                .lineLimit(1)
                .foregroundColor(.primary)

            Text(product.unit)
                .font(.caption)
                .foregroundColor(.secondary)

            HStack {
                VStack(alignment: .leading) {
                    Text("₹\(Int(product.price))")
                        .font(.subheadline)
                        .bold()
                        .foregroundColor(.primary)
                    if product.mrp > product.price {
                        Text("₹\(Int(product.mrp))")
                            .font(.caption2)
                            .strikethrough()
                            .foregroundColor(.secondary)
                    }
                }

                Spacer()

                if quantity == 0 {
                    Button(action: {
                        CartRepository.shared.add(product: product)
                    }) {
                        Text("ADD")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.green)
                            .padding(.horizontal, 12)
                            .padding(.vertical, 6)
                            .overlay(
                                RoundedRectangle(cornerRadius: 6)
                                    .stroke(Color.green, lineWidth: 1)
                            )
                    }
                } else {
                    HStack(spacing: 8) {
                        Button(action: { CartRepository.shared.remove(product: product) }) {
                            Image(systemName: "minus")
                                .font(.system(size: 12, weight: .bold))
                                .foregroundColor(.white)
                        }
                        Text("\(quantity)")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.white)
                        Button(action: { CartRepository.shared.add(product: product) }) {
                            Image(systemName: "plus")
                                .font(.system(size: 12, weight: .bold))
                                .foregroundColor(.white)
                        }
                    }
                    .padding(.horizontal, 8)
                    .padding(.vertical, 6)
                    .background(Color.green)
                    .cornerRadius(6)
                }
            }
            .padding(.top, 4)
        }
        .padding(10)
        .background(Color(UIColor.systemBackground))
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.05), radius: 5, x: 0, y: 2)
    }
}
