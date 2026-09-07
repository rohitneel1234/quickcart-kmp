import SwiftUI
import Shared

struct CartView: View {
    @ObservedObject private var cartManager = CartManager.shared

    private var subtotal: Double {
        CartRepository.shared.subtotal.value as! Double
    }

    private var deliveryFee: Double {
        (subtotal >= 199.0 || subtotal == 0.0) ? 0.0 : 25.0
    }

    private var handlingFee: Double {
        cartManager.cartLines.isEmpty ? 0.0 : 4.0
    }

    private var grandTotal: Double {
        subtotal + deliveryFee + handlingFee
    }

    var body: some View {
        NavigationView {
            VStack {
                if cartManager.cartLines.isEmpty {
                    VStack(spacing: 20) {
                        Text("🛒")
                            .font(.system(size: 80))
                        Text("Your cart is empty")
                            .font(.title2)
                            .foregroundColor(.secondary)
                    }
                    .frame(maxHeight: .infinity)
                } else {
                    List {
                        Section {
                            ForEach(cartManager.cartLines, id: \.product.id) { line in
                                HStack {
                                    Text(line.product.emoji)
                                        .font(.system(size: 40))
                                    VStack(alignment: .leading) {
                                        Text(line.product.name)
                                            .font(.headline)
                                        Text(line.product.unit)
                                            .font(.caption)
                                            .foregroundColor(.secondary)
                                    }
                                    Spacer()
                                    VStack(alignment: .trailing) {
                                        Text("₹\(Int(line.lineTotal))")
                                            .font(.subheadline)
                                            .bold()
                                        HStack {
                                            Button(action: { CartRepository.shared.remove(product: line.product) }) {
                                                Image(systemName: "minus.circle.fill")
                                                    .foregroundColor(.green)
                                            }
                                            Text("\(line.quantity)")
                                                .padding(.horizontal, 8)
                                            Button(action: { CartRepository.shared.add(product: line.product) }) {
                                                Image(systemName: "plus.circle.fill")
                                                    .foregroundColor(.green)
                                            }
                                        }
                                    }
                                }
                                .padding(.vertical, 4)
                            }
                        }

                        Section {
                            BillSummary(
                                subtotal: subtotal,
                                deliveryFee: deliveryFee,
                                handlingFee: handlingFee,
                                total: grandTotal
                            )
                            .listRowInsets(EdgeInsets())
                        }
                    }
                    .listStyle(InsetGroupedListStyle())

                    VStack(spacing: 16) {
                        HStack {
                            VStack(alignment: .leading) {
                                Text("To Pay")
                                    .font(.caption)
                                    .foregroundColor(.secondary)
                                Text("₹\(Int(grandTotal))")
                                    .font(.title3)
                                    .bold()
                            }
                            Spacer()
                        }
                        .padding(.horizontal)

                        NavigationLink(destination: CheckoutView(cartLines: cartManager.cartLines, subtotal: grandTotal)) {
                            Text("Proceed to Checkout")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.green)
                                .foregroundColor(.white)
                                .cornerRadius(12)
                        }
                        .padding(.horizontal)
                        .padding(.bottom, 8)
                    }
                    .background(Color(UIColor.systemBackground).shadow(radius: 4))
                }
            }
            .navigationTitle("My Cart")
        }
    }
}
