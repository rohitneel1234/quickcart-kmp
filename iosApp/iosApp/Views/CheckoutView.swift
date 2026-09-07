import SwiftUI
import Shared

struct CheckoutView: View {
    @State private var address: String = "221B, MG Road, Nashik"
    @State private var orderId: String? = nil
    @State private var navigateToSuccess = false

    let cartLines: [CartLine]
    let subtotal: Double

    var body: some View {
        VStack {
            Form {
                Section(header: Text("Delivery Address")) {
                    TextField("Home Address", text: $address)
                }

                Section(header: Text("Order Summary")) {
                    ForEach(cartLines, id: \.product.id) { line in
                        HStack {
                            Text("\(line.quantity)x \(line.product.name)")
                                .font(.body)
                            Spacer()
                            Text("₹\(Int(line.lineTotal))")
                                .font(.body)
                        }
                    }
                    HStack {
                        Text("Grand Total")
                            .fontWeight(.bold)
                        Spacer()
                        Text("₹\(Int(subtotal))")
                            .fontWeight(.bold)
                    }
                }
            }

            Button(action: {
                let order = OrderRepository.shared.placeOrder(lines: cartLines, total: subtotal, address: address)
                self.orderId = order.id
                self.navigateToSuccess = true
            }) {
                Text("Place Order - ₹\(Int(subtotal))")
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(12)
            }

            .padding()

            NavigationLink(destination: OrderSuccessView(orderId: orderId ?? ""), isActive: $navigateToSuccess) {
                EmptyView()
            }
        }
        .navigationTitle("Checkout")
    }
}
