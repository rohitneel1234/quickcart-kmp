import SwiftUI
import Shared

struct CartView: View {
    @State private var cartLines: [CartLine] = []

    private var subtotal: Double {
        CartRepository.shared.subtotal.value as! Double
    }

    private var deliveryFee: Double {
        (subtotal >= 199.0 || subtotal == 0.0) ? 0.0 : 25.0
    }

    private var handlingFee: Double {
        cartLines.isEmpty ? 0.0 : 4.0
    }

    private var grandTotal: Double {
        subtotal + deliveryFee + handlingFee
    }

    var body: some View {
        NavigationView {
            VStack {
                if cartLines.isEmpty {
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
                            ForEach(cartLines, id: \.product.id) { line in
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

                        NavigationLink(destination: CheckoutView(cartLines: cartLines, subtotal: grandTotal)) {
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
            .onAppear {
                observeCart()
            }
        }
    }

    private func observeCart() {
        // In a real app, use a proper Flow wrapper or Combine
        self.cartLines = CartRepository.shared.lines.value as! [CartLine]

        // Simple polling/fake observation for demo purposes if not using a library like KMP-NativeCoroutines
        Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { _ in
            self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        }
    }
}

struct BillRow: View {
    let label: String
    let value: String
    var isBold: Bool = false

    var body: some View {
        HStack {
            Text(label)
                .font(.system(size: 14, weight: isBold ? .bold : .regular))
            Spacer()
            Text(value)
                .font(.system(size: 14, weight: isBold ? .bold : .regular))
        }
        .padding(.vertical, 2)
    }
}

struct BillSummary: View {
    let subtotal: Double
    let deliveryFee: Double
    let handlingFee: Double
    let total: Double

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("Bill Details")
                .font(.headline)

            BillRow(label: "Items total", value: "₹\(Int(subtotal))")
            BillRow(label: "Delivery fee", value: deliveryFee == 0 ? "FREE" : "₹\(Int(deliveryFee))")
            BillRow(label: "Handling fee", value: "₹\(Int(handlingFee))")

            Divider()

            BillRow(label: "Grand total", value: "₹\(Int(total))", isBold: true)
        }
        .padding()
    }
}

