import SwiftUI
import Shared

struct FloatingCartView: View {
    let cartLines: [CartLine]
    let onClick: () -> Void

    private var totalCount: Int {
        cartLines.reduce(0) { $0 + Int($1.quantity) }
    }

    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 8) {
                // Emojis stack logic
                let recentLines = Array(cartLines.reversed().prefix(3))
                if !recentLines.isEmpty {
                    ZStack(alignment: .leading) {
                        ForEach(Array(recentLines.enumerated()), id: \.offset) { index, line in
                            Text(line.product.emoji)
                                .font(.system(size: 16))
                                .frame(width: 32, height: 32)
                                .background(Color.white.opacity(0.2))
                                .clipShape(Circle())
                                .padding(.leading, CGFloat(index * 20))
                        }
                    }
                    .frame(width: CGFloat(32 + (recentLines.count - 1) * 20), height: 32)

                    Spacer().frame(width: 8)
                }

                // Text Column
                VStack(alignment: .leading, spacing: 0) {
                    Text("View cart")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(.white)
                    Text("\(totalCount) \(totalCount == 1 ? "item" : "items")")
                        .font(.system(size: 12))
                        .foregroundColor(.white.opacity(0.9))
                }

                Spacer().frame(width: 12)

                // Chevron Icon
                Image(systemName: "chevron.right")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(.white)
                    .frame(width: 28, height: 28)
            }
            .padding(.horizontal, 16)
            .frame(height: 56)
            .background(Color(hex: 0xFF0A6B19)) // Exact BrandColors.GreenDark
            .clipShape(Capsule())
            .shadow(color: Color.black.opacity(0.15), radius: 8, x: 0, y: 4)
        }
        .padding(16)
        .padding(.bottom, 60) // Position above tab bar
    }
}
