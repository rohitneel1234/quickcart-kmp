import SwiftUI
import Shared

struct SearchView: View {
    @State private var query: String = ""
    @State private var results: [Product] = []
    @ObservedObject private var cartManager = CartManager.shared

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        VStack {
            // Search Bar
            HStack {
                Image(systemName: "magnifyingglass")
                    .foregroundColor(.gray)
                TextField("Search products...", text: $query)
                    .onChange(of: query) { newValue in
                        self.results = GroceryRepository.shared.search(query: newValue)
                    }
                if !query.isEmpty {
                    Button(action: { query = "" }) {
                        Image(systemName: "xmark.circle.fill")
                            .foregroundColor(.gray)
                    }
                }
            }
            .padding()
            .background(Color(.systemGray6))
            .cornerRadius(10)
            .padding(.horizontal)

            if query.isEmpty {
                Spacer()
                Text("Search for items")
                    .foregroundColor(.secondary)
                Spacer()
            } else {
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 15) {
                        ForEach(results, id: \.id) { product in
                            NavigationLink(destination: ProductDetailView(productId: product.id)) {
                                ProductCard(product: product, quantity: cartManager.qtyFor(productId: product.id))
                            }
                            .buttonStyle(PlainButtonStyle())
                        }
                    }
                    .padding()
                }
            }
        }
        .navigationTitle("Search")
    }
}
