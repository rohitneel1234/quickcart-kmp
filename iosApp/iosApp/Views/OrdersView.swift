import SwiftUI
import Shared

struct OrdersView: View {
    @State private var orders: [Order] = []

    var body: some View {
        NavigationView {
            VStack {
                if orders.isEmpty {
                    Text("No orders yet")
                        .foregroundColor(.secondary)
                } else {
                    List(orders, id: \.id) { order in
                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                Text("Order #\(order.id)")
                                    .fontWeight(.bold)
                                Spacer()
                                Text(statusLabel(status: order.status))
                                    .foregroundColor(.green)
                                    .font(.caption)
                                    .fontWeight(.bold)
                            }

                            Text(order.lines.map { "\($0.product.name) x\($0.quantity)" }.joined(separator: ", "))
                                .font(.caption)
                                .foregroundColor(.secondary)
                                .lineLimit(2)

                            HStack {
                                Text("Deliver to: \(order.deliveryAddress)")
                                    .font(.system(size: 10))
                                    .foregroundColor(.secondary)
                                Spacer()
                                Text("$\(String(format: "%.2f", order.total))")
                                    .fontWeight(.bold)
                            }
                        }
                        .padding(.vertical, 4)
                    }
                }
            }
            .navigationTitle("Order History")
            .onAppear {
                self.orders = OrderRepository.shared.orders.value as! [Order]
            }
        }
    }

    private func statusLabel(status: OrderStatus) -> String {
        switch status {
        case .placed: return "Order Placed"
        case .packed: return "Packed"
        case .outForDelivery: return "Out for Delivery"
        case .delivered: return "Delivered"
        default: return ""
        }
    }
}
