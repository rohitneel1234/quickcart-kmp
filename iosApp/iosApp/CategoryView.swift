import SwiftUI
import Shared

struct CategoryView: View {
    let categoryId: String
    @State private var category: Shared.Category? = nil
    @State private var products: [Product] = []

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        ScrollView {
            VStack {
                if let category = category {
                    LazyVGrid(columns: columns, spacing: 15) {
                        ForEach(products, id: \.id) { product in
                            NavigationLink(destination: ProductDetailView(productId: product.id)) {
                                ProductCard(product: product)
                            }
                            .buttonStyle(PlainButtonStyle())
                        }
                    }
                    .padding()
                } else {
                    ProgressView()
                }
            }
        }
        .navigationTitle(category?.name ?? "Category")
        .onAppear {
            self.category = GroceryRepository.shared.getCategory(id: categoryId)
            self.products = GroceryRepository.shared.getProductsForCategory(categoryId: categoryId)
        }
    }
}
