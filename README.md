# Grama-Sanjeevni
Grama-Sanjeevini is an Android app that connects village medical stores into one digital network. Users can search for medicines and find nearby pharmacies with available stock. Pharmacists can update inventory and receive expiry alerts. The app improves healthcare access, reduces travel time, and minimizes medicine wastage in rural areas.
 Grama-Sanjeevini

Grama-Sanjeevini is an Android application developed to improve healthcare accessibility in rural areas by connecting multiple village medical stores into a single digital network. The app helps users search for medicines and locate nearby pharmacies where the required medicines are available.



Problem Statement

In remote villages, local medical stores are often the only source of medicines. When a medicine is unavailable, villagers may need to travel long distances without knowing where it is in stock. This causes delays in treatment and additional travel expenses.

Grama-Sanjeevini solves this problem by allowing users to search medicine availability across nearby pharmacies.

---

Features

Search medicines across multiple village pharmacies
View pharmacy name and available stock
Emergency stock section for life-saving medicines
Expiry alerts for pharmacists
Inventory management for medical stores
Location-based pharmacy search
Secure Firebase integration

---

Technologies Used

- Java
- Android Studio
- XML
- Firebase Firestore
- Firebase Authentication
- RecyclerView
- Material Design Components

---

Screens

- Splash Screen
- Login Screen
- Register Screen
- Home Dashboard
- Search Medicine Screen
- Add Medicine Screen
- Search Results Screen

---
Project Structure

```text
GramaSanjeevini/
├── app/
│   ├── java/com/example/gramasanjeevini/
│   │   ├── MainActivity.java
│   │   ├── AddMedicineActivity.java
│   │   ├── Medicine.java
│   │   ├── MedicineAdapter.java
│   │   └── FirebaseHelper.java
│   ├── res/layout/
│   │   ├── activity_main.xml
│   │   ├── activity_add_medicine.xml
│   │   └── item_medicine.xml
│   └── google-services.json
└── README.md
Future Enhancements
AI-based medicine recommendations
Voice search in local languages
Medicine home delivery
Doctor consultation integration
Offline mode for low-network areas
