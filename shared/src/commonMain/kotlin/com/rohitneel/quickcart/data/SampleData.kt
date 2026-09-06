package com.rohitneel.quickcart.data

import com.rohitneel.quickcart.model.Category
import com.rohitneel.quickcart.model.Product

/**
 * Static, in-memory catalog. Swap this out for a real backend/API repository
 * later without touching any UI code — screens only depend on [GroceryRepository].
 */
object SampleData {

    val categories = listOf(
        Category("fruits_veg", "Fruits & Vegetables", "🍎", 0xFFE6F4EA),
        Category("dairy_bread", "Dairy & Breakfast", "🥛", 0xFFFFF4E0),
        Category("atta_rice_dal", "Atta, Rice & Dal", "🌾", 0xFFF3E8D7),
        Category("bakery", "Bakery & Biscuits", "🍞", 0xFFFBE8E0),
        Category("snacks", "Snacks & Munchies", "🍟", 0xFFFFF0D9),
        Category("beverages", "Cold Drinks & Juices", "🥤", 0xFFE0F3FF),
        Category("tea_coffee", "Tea, Coffee & Health Drinks", "☕", 0xFFEFE2D0),
        Category("meat_fish", "Meat, Fish & Eggs", "🍗", 0xFFFDE2E2),
        Category("frozen", "Frozen Food & Ice Cream", "🍦", 0xFFE3EEFB),
        Category("masala_oil", "Masala, Oil & More", "🧂", 0xFFF6E9D9),
        Category("sweet_namkeen", "Sweets & Namkeen", "🍬", 0xFFFDEBF3),
        Category("home_care", "Cleaning & Household", "🧹", 0xFFE7F0FA),
        Category("personal_care", "Personal Care", "🧴", 0xFFEAE6FA),
        Category("baby_care", "Baby Care", "🍼", 0xFFFFF1F1),
        Category("pet_care", "Pet Care", "🐾", 0xFFEFF7E6),
        Category("stationery", "Stationery & Games", "✏️", 0xFFEAF2FF)
    )

    val allProducts: List<Product> = buildList {
        // Fruits & Vegetables
        add(p("fv1", "fruits_veg", "Fresh Banana", unit = "6 pcs", price = 39.0, mrp = 45.0, emoji = "🍌"))
        add(p("fv2", "fruits_veg", "Alphonso Mango", unit = "1 kg", price = 399.0, mrp = 450.0, emoji = "🥭"))
        add(p("fv3", "fruits_veg", "Tomato", unit = "1 kg", price = 28.0, mrp = 35.0, emoji = "🍅"))
        add(p("fv4", "fruits_veg", "Onion", unit = "1 kg", price = 32.0, mrp = 38.0, emoji = "🧅"))
        add(p("fv5", "fruits_veg", "Potato", unit = "1 kg", price = 26.0, mrp = 30.0, emoji = "🥔"))
        add(p("fv6", "fruits_veg", "Green Capsicum", unit = "500 g", price = 34.0, mrp = 40.0, emoji = "🫑"))
        add(p("fv7", "fruits_veg", "Spinach (Palak)", unit = "250 g", price = 18.0, mrp = 20.0, emoji = "🥬"))
        add(p("fv8", "fruits_veg", "Fresh Apple (Shimla)", unit = "4 pcs", price = 149.0, mrp = 179.0, emoji = "🍎"))

        // Dairy & Breakfast
        add(p("db1", "dairy_bread", "Full Cream Milk", "Amul", "500 ml", 33.0, 33.0, "🥛"))
        add(p("db2", "dairy_bread", "Fresh Paneer", "Mother Dairy", "200 g", 89.0, 99.0, "🧀"))
        add(p("db3", "dairy_bread", "Curd", "Amul", "400 g", 40.0, 45.0, "🥣"))
        add(p("db4", "dairy_bread", "Butter", "Amul", "100 g", 55.0, 58.0, "🧈"))
        add(p("db5", "dairy_bread", "Brown Eggs", "Farm Fresh", "6 pcs", 72.0, 80.0, "🥚"))
        add(p("db6", "dairy_bread", "Cornflakes", "Kellogg's", "475 g", 235.0, 260.0, "🥣"))

        // Atta, Rice & Dal
        add(p("ar1", "atta_rice_dal", "Whole Wheat Atta", "Aashirvaad", "5 kg", 249.0, 275.0, "🌾"))
        add(p("ar2", "atta_rice_dal", "Basmati Rice", "India Gate", "1 kg", 129.0, 145.0, "🍚"))
        add(p("ar3", "atta_rice_dal", "Toor Dal", "Tata Sampann", "1 kg", 159.0, 175.0, "🫘"))
        add(p("ar4", "atta_rice_dal", "Moong Dal", "Tata Sampann", "1 kg", 149.0, 165.0, "🫘"))
        add(p("ar5", "atta_rice_dal", "Besan", "Fortune", "1 kg", 99.0, 110.0, "🌾"))

        // Bakery & Biscuits
        add(p("bk1", "bakery", "Brown Bread", "Britannia", "400 g", 45.0, 50.0, "🍞"))
        add(p("bk2", "bakery", "Marie Gold Biscuits", "Britannia", "250 g", 35.0, 40.0, "🍪"))
        add(p("bk3", "bakery", "Chocolate Muffin", "Bakers", "4 pcs", 89.0, 99.0, "🧁"))
        add(p("bk4", "bakery", "Rusk", "Britannia", "300 g", 45.0, 50.0, "🥖"))

        // Snacks & Munchies
        add(p("sn1", "snacks", "Potato Chips (Classic Salted)", "Lay's", "52 g", 20.0, 20.0, "🍟"))
        add(p("sn2", "snacks", "Cheese Balls", "Kurkure", "80 g", 30.0, 35.0, "🧀"))
        add(p("sn3", "snacks", "Peanut Butter Crunchy", "Sundrop", "1 kg", 349.0, 399.0, "🥜"))
        add(p("sn4", "snacks", "Instant Noodles", "Maggi", "560 g (4 pk)", 56.0, 60.0, "🍜"))
        add(p("sn5", "snacks", "Popcorn (Butter)", "Act II", "40 g", 30.0, 35.0, "🍿"))

        // Cold Drinks & Juices
        add(p("bv1", "beverages", "Cola Soft Drink", "Coca-Cola", "750 ml", 40.0, 45.0, "🥤"))
        add(p("bv2", "beverages", "Orange Juice", "Real", "1 L", 110.0, 125.0, "🧃"))
        add(p("bv3", "beverages", "Packaged Drinking Water", "Bisleri", "1 L", 20.0, 20.0, "💧"))
        add(p("bv4", "beverages", "Lemonade", "Sprite", "750 ml", 40.0, 45.0, "🥤"))

        // Tea, Coffee & Health Drinks
        add(p("tc1", "tea_coffee", "Tea Powder", "Tata Tea Gold", "500 g", 249.0, 270.0, "🍵"))
        add(p("tc2", "tea_coffee", "Instant Coffee", "Nescafé", "100 g", 285.0, 320.0, "☕"))
        add(p("tc3", "tea_coffee", "Health Drink (Chocolate)", "Bournvita", "500 g", 210.0, 230.0, "🍫"))

        // Meat, Fish & Eggs
        add(p("mf1", "meat_fish", "Chicken Curry Cut", "Licious", "500 g", 179.0, 210.0, "🍗"))
        add(p("mf2", "meat_fish", "Rohu Fish Curry Cut", "FreshToHome", "500 g", 199.0, 230.0, "🐟"))
        add(p("mf3", "meat_fish", "Mutton Curry Cut", "Licious", "500 g", 449.0, 499.0, "🍖"))

        // Frozen Food & Ice Cream
        add(p("fz1", "frozen", "Veg Momos", "ITC", "375 g", 130.0, 145.0, "🥟"))
        add(p("fz2", "frozen", "French Fries", "McCain", "425 g", 99.0, 110.0, "🍟"))
        add(p("fz3", "frozen", "Vanilla Ice Cream Tub", "Amul", "1 L", 199.0, 220.0, "🍦"))
        add(p("fz4", "frozen", "Green Peas (Frozen)", "Safal", "500 g", 55.0, 60.0, "🟢"))

        // Masala, Oil & More
        add(p("mo1", "masala_oil", "Sunflower Oil", "Fortune", "1 L", 139.0, 155.0, "🛢️"))
        add(p("mo2", "masala_oil", "Turmeric Powder", "Everest", "200 g", 45.0, 50.0, "🧂"))
        add(p("mo3", "masala_oil", "Red Chilli Powder", "Everest", "200 g", 55.0, 60.0, "🌶️"))
        add(p("mo4", "masala_oil", "Garam Masala", "MDH", "100 g", 65.0, 72.0, "🧂"))
        add(p("mo5", "masala_oil", "Iodized Salt", "Tata", "1 kg", 25.0, 28.0, "🧂"))

        // Sweets & Namkeen
        add(p("sw1", "sweet_namkeen", "Kaju Katli", "Haldiram's", "250 g", 249.0, 280.0, "🍬"))
        add(p("sw2", "sweet_namkeen", "Aloo Bhujia", "Haldiram's", "200 g", 45.0, 50.0, "🥨"))
        add(p("sw3", "sweet_namkeen", "Gulab Jamun Tin", "Haldiram's", "1 kg", 199.0, 220.0, "🍡"))

        // Cleaning & Household
        add(p("hc1", "home_care", "Dishwash Liquid", "Vim", "500 ml", 99.0, 110.0, "🧴"))
        add(p("hc2", "home_care", "Surface Cleaner", "Lizol", "500 ml", 149.0, 165.0, "🧽"))
        add(p("hc3", "home_care", "Laundry Detergent", "Surf Excel", "1 kg", 135.0, 150.0, "🧺"))
        add(p("hc4", "home_care", "Toilet Cleaner", "Harpic", "500 ml", 89.0, 99.0, "🚽"))
        add(p("hc5", "home_care", "Garbage Bags", "Novino", "30 pcs", 99.0, 110.0, "🗑️"))

        // Personal Care
        add(p("pc1", "personal_care", "Shampoo", "Head & Shoulders", "340 ml", 299.0, 330.0, "🧴"))
        add(p("pc2", "personal_care", "Body Wash", "Dove", "250 ml", 189.0, 210.0, "🧴"))
        add(p("pc3", "personal_care", "Toothpaste", "Colgate", "150 g", 89.0, 99.0, "🪥"))
        add(p("pc4", "personal_care", "Face Wash", "Himalaya", "100 ml", 99.0, 110.0, "🧴"))
        add(p("pc5", "personal_care", "Sanitary Pads", "Whisper", "30 pcs", 249.0, 280.0, "🩹"))
        add(p("pc6", "personal_care", "Razor", "Gillette", "1 pc", 99.0, 110.0, "🪒"))

        // Baby Care
        add(p("bc1", "baby_care", "Baby Diapers (M)", "Pampers", "44 pcs", 599.0, 650.0, "👶"))
        add(p("bc2", "baby_care", "Baby Wipes", "Himalaya", "72 pcs", 149.0, 165.0, "🧻"))
        add(p("bc3", "baby_care", "Baby Lotion", "Johnson's", "200 ml", 165.0, 180.0, "🍼"))

        // Pet Care
        add(p("pt1", "pet_care", "Dog Food (Adult)", "Pedigree", "1.2 kg", 349.0, 390.0, "🐶"))
        add(p("pt2", "pet_care", "Cat Litter", "Whiskas", "5 L", 299.0, 330.0, "🐱"))
        add(p("pt3", "pet_care", "Pet Shampoo", "Himalaya", "200 ml", 189.0, 210.0, "🧴"))

        // Stationery & Games
        add(p("st1", "stationery", "Notebook (Ruled)", "Classmate", "1 pc", 45.0, 50.0, "📓"))
        add(p("st2", "stationery", "Ball Pen Set", "Reynolds", "5 pcs", 30.0, 35.0, "🖊️"))
        add(p("st3", "stationery", "Playing Cards", "generic", "1 pack", 25.0, 30.0, "🃏"))
    }

    private fun p(
        id: String,
        categoryId: String,
        name: String,
        brand: String? = null,
        unit: String,
        price: Double,
        mrp: Double,
        emoji: String
    ) = Product(
        id = id,
        categoryId = categoryId,
        name = name,
        brand = brand,
        unit = unit,
        price = price,
        mrp = mrp,
        emoji = emoji,
        rating = 3.8 + (id.hashCode().mod(12)) / 10.0,
        productDescription = "$name${brand?.let { " by $it" } ?: ""}, $unit pack. Fresh quality, handpicked and delivered fast.",
        deliveryEta = listOf("8 mins", "10 mins", "12 mins", "15 mins")[id.hashCode().mod(4).let { if (it < 0) it + 4 else it }]
    )

    /** A curated set for the "Best Sellers" / "Popular Right Now" home carousel. */
    val trendingProductIds = listOf(
        "fv1", "db1", "sn1", "bv1", "bk1", "fz3", "sw1", "hc1", "pc1", "ar2"
    )
}
