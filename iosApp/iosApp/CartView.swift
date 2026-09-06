import SwiftUI
import Shared

struct CartView: View {
    @State private var cartLines: [CartLine] = []

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
                                    Text("$\(String(format: "%.2f", line.lineTotal))")
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

                    VStack(spacing: 16) {
                        HStack {
                            Text("Total")
                                .font(.headline)
                            Spacer()
                            Text("$\(String(format: "%.2f", CartRepository.shared.subtotal.value as! Double))")
                                .font(.title3)
                                .bold()
                        }
                        .padding(.horizontal)

                        NavigationLink(destination: CheckoutView(cartLines: cartLines, subtotal: CartRepository.shared.subtotal.value as! Double)) {
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
