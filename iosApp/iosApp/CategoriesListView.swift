import SwiftUI
import Shared

struct CategoriesListView: View {
    @State private var categories: [Category] = []

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible()),
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var sections: [(String, [Category])] {
        let groceryAndKitchenIds = ["fruits_veg", "atta_rice_dal", "masala_oil", "dairy_bread", "bakery", "meat_fish"]
        let snacksAndDrinksIds = ["snacks", "beverages", "tea_coffee", "sweet_namkeen", "frozen"]

        return [
            ("Grocery & Kitchen", categories.filter { groceryAndKitchenIds.contains($0.id) }),
            ("Snacks & Drinks", categories.filter { snacksAndDrinksIds.contains($0.id) }),
            ("Household & More", categories.filter { !groceryAndKitchenIds.contains($0.id) && !snacksAndDrinksIds.contains($0.id) })
        ]
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                ForEach(sections, id: \.0) { title, sectionCategories in
                    if !sectionCategories.isEmpty {
                        VStack(alignment: .leading, spacing: 12) {
                            Text(title)
                                .font(.headline)
                                .padding(.horizontal)

                            LazyVGrid(columns: columns, spacing: 20) {
                                ForEach(sectionCategories, id: \.id) { category in
                                    NavigationLink(destination: CategoryView(categoryId: category.id)) {
                                        VStack {
                                            Text(category.emoji)
                                                .font(.system(size: 30))
                                                .frame(width: 60, height: 60)
                                                .background(Color.green.opacity(0.1))
                                                .cornerRadius(12)
                                            Text(category.name)
                                                .font(.caption)
                                                .multilineTextAlignment(.center)
                                                .foregroundColor(.primary)
                                        }
                                    }
                                }
                            }
                            .padding(.horizontal)
                        }
                    }
                }
            }
            .padding(.vertical)
        }
        .navigationTitle("Categories")
        .onAppear {
            self.categories = GroceryRepository.shared.getCategories()
        }
    }
}
