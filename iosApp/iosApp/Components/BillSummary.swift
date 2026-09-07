import SwiftUI

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
