import SwiftUI
import Shared

struct ProductDetailView: View {
    let productId: String
    @Environment(\.presentationMode) var presentationMode
    @State private var product: Product? = nil
    @State private var quantity: Int = 0

    var body: some View {
        VStack(spacing: 0) {
            if let product = product {
                ScrollView {
                    VStack(alignment: .leading) {
                        // Product Image Placeholder
                        ZStack {
                            Color(UIColor.systemGray6)
                            Text(product.emoji)
                                .font(.system(size: 120))
                        }
                        .frame(height: 300)

                        VStack(alignment: .leading, spacing: 16) {
                            VStack(alignment: .leading, spacing: 4) {
                                Text(product.brand ?? "Fresh")
                                    .font(.subheadline)
                                    .foregroundColor(.green)
                                    .fontWeight(.bold)
                                Text(product.name)
                                    .font(.largeTitle)
                                    .fontWeight(.bold)
                                Text(product.unit)
                                    .font(.body)
                                    .foregroundColor(.secondary)
                            }

                            HStack {
                                Image(systemName: "star.fill")
                                    .foregroundColor(.yellow)
                                Text(String(format: "%.1f", product.rating))
                                    .fontWeight(.bold)
                            }
                            .padding(.horizontal, 8)
                            .padding(.vertical, 4)
                            .background(Color(UIColor.systemGray6))
                            .cornerRadius(8)

                            VStack(alignment: .leading, spacing: 8) {
                                Text("Product Details")
                                    .font(.headline)
                                Text(product.description.isEmpty ? "No description available." : product.description)
                                    .font(.body)
                                    .foregroundColor(.primary)
                                    .lineSpacing(4)
                            }

                            Spacer(minLength: 100)
                        }
                        .padding()
                    }
                }

                // Bottom Bar
                VStack {
                    Divider()
                    HStack {
                        VStack(alignment: .leading) {
                            Text("$\(String(format: "%.2f", product.price))")
                                .font(.title2)
                                .fontWeight(.bold)
                            if product.mrp > product.price {
                                Text("$\(String(format: "%.2f", product.mrp))")
                                    .font(.caption)
                                    .strikethrough()
                                    .foregroundColor(.secondary)
                            }
                        }

                        Spacer()

                        if quantity == 0 {
                            Button(action: {
                                CartRepository.shared.add(product: product)
                                updateQuantity()
                            }) {
                                Text("Add to Cart")
                                    .fontWeight(.bold)
                                    .padding(.horizontal, 30)
                                    .padding(.vertical, 12)
                                    .background(Color.green)
                                    .foregroundColor(.white)
                                    .cornerRadius(10)
                            }
                        } else {
                            HStack(spacing: 20) {
                                HStack {
                                    Button(action: {
                                        CartRepository.shared.remove(product: product)
                                        updateQuantity()
                                    }) {
                                        Image(systemName: "minus.circle.fill")
                                            .foregroundColor(.green)
                                            .font(.title2)
                                    }

                                    Text("\(quantity)")
                                        .font(.headline)
                                        .frame(minWidth: 30)

                                    Button(action: {
                                        CartRepository.shared.add(product: product)
                                        updateQuantity()
                                    }) {
                                        Image(systemName: "plus.circle.fill")
                                            .foregroundColor(.green)
                                            .font(.title2)
                                    }
                                }
                                .padding(8)
                                .background(Color(UIColor.systemGray6))
                                .cornerRadius(10)
                            }
                        }
                    }
                    .padding()
                    .background(Color(UIColor.systemBackground))
                }
            } else {
                ProgressView()
            }
        }
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            self.product = GroceryRepository.shared.getProduct(id: productId)
            updateQuantity()
        }
    }

    private func updateQuantity() {
        self.quantity = Int(CartRepository.shared.quantityOf(productId: productId))
    }
}
