import SwiftUI
import Shared

class CartManager: ObservableObject {
    static let shared = CartManager()

    @Published var cartLines: [CartLine] = []

    private var timer: Timer?

    private init() {
        observeCart()
    }

    func qtyFor(productId: String) -> Int {
        return Int(cartLines.first(where: { $0.product.id == productId })?.quantity ?? 0)
    }

    private func observeCart() {
        // Initial fetch
        self.cartLines = CartRepository.shared.lines.value as! [CartLine]

        // Polling for updates (replaces the repeated logic in every view)
        timer = Timer.scheduledTimer(withTimeInterval: 0.5, repeats: true) { [weak self] _ in
            guard let self = self else { return }
            self.cartLines = CartRepository.shared.lines.value as! [CartLine]
        }
    }

    deinit {
        timer?.invalidate()
    }
}
