import SwiftUI
import Shared

struct OrderSuccessView: View {
    let orderId: String
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        VStack(spacing: 24) {
            Spacer()
            Text("🎉")
                .font(.system(size: 80))
            Text("Order Placed Successfully!")
                .font(.title)
                .fontWeight(.bold)
                .multilineTextAlignment(.center)
            Text("Order ID: #\(orderId)")
                .font(.headline)
                .foregroundColor(.secondary)

            Spacer()

            Button(action: {
                CartRepository.shared.clear()
                NotificationCenter.default.post(name: NSNotification.Name("GoHome"), object: nil)
                presentationMode.wrappedValue.dismiss()
            }) {
                Text("Continue Shopping")
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.green)
                    .foregroundColor(.white)
                    .cornerRadius(12)
            }
            .padding(.horizontal)
            .padding(.bottom, 40)
        }
        .navigationBarBackButtonHidden(true)
        .padding()
    }
}
