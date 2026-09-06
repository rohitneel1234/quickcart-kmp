import SwiftUI
import Shared

struct FloatingCartView: View {
    let cartLines: [CartLine]
    let onClick: () -> Void

    var body: some View {
        if cartLines.isEmpty {
            EmptyView()
        } else {
            let totalCount = cartLines.reduce(0) { $0 + Int($1.quantity) }

            Button(action: onClick) {
                HStack(spacing: 12) {
                    // Emojis stack
                    HStack(spacing: -12) {
                        let recentLines = Array(cartLines.reversed().prefix(3))
                        ForEach(recentLines, id: \.product.id) { line in
                            Text(line.product.emoji)
                                .font(.system(size: 16))
                                .frame(width: 32, height: 32)
                                .background(Color.white.opacity(0.2))
                                .clipShape(Circle())
                        }
                    }

                    VStack(alignment: .leading, spacing: 0) {
                        Text("View cart")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(.white)
                        Text("\(totalCount) \(totalCount == 1 ? "item" : "items")")
                            .font(.system(size: 12))
                            .foregroundColor(.white.opacity(0.9))
                    }

                    Spacer()

                    Image(systemName: "chevron.right")
                        .font(.system(size: 20, weight: .bold))
                        .foregroundColor(.white)
                }
                .padding(.horizontal, 16)
                .frame(height: 56)
                .background(Color.green.opacity(0.9))
                .cornerRadius(28)
                .shadow(radius: 4)
            }
            .padding(.horizontal, 16)
            .padding(.bottom, 60) // Position above the TabBar
        }
    }
}
