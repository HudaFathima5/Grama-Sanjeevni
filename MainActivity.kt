package com.example.gramasanjeevini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gramasanjeevini.ui.theme.GRAMASANJEEVINITheme
package com.huda.gramasanjeevini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// ============================================================================
// COLOR PALETTE - Medical Healthcare Theme
// ============================================================================
val MedicalGreen = Color(0xFF2E7D32)
val MedicalGreenLight = Color(0xFF66BB6A)
val MedicalGreenDark = Color(0xFF1B5E20)
val ClinicalBlue = Color(0xFF1976D2)
val ClinicalBlueLight = Color(0xFFE3F2FD)
val EmergencyRed = Color(0xFFD32F2F)
val EmergencyRedLight = Color(0xFFFFEBEE)
val WarningAmber = Color(0xFFFF9800)
val WarningAmberLight = Color(0xFFFFF3E0)
val SoftCream = Color(0xFFFAFAFA)
val PureWhite = Color(0xFFFFFFFF)
val TextDark = Color(0xFF212121)
val TextGray = Color(0xFF757575)
val SuccessGreen = Color(0xFF4CAF50)
val LowStockOrange = Color(0xFFFF6F00)

// ============================================================================
// DATA MODELS
// ============================================================================
data class Shop(
    val id: Int,
    val name: String,
    val village: String,
    val pharmacist: String,
    val distance: Double,
    val phone: String,
    val openHours: String
)

data class Medicine(
    val id: Int,
    val name: String,
    val genericName: String,
    val category: String,
    val price: Double,
    val isLifeSaving: Boolean,
    val description: String
)

data class StockEntry(
    val shopId: Int,
    val medicineId: Int,
    var stockCount: Int,
    val expiryDate: String,
    val daysToExpiry: Int
)

data class SearchResult(
    val shop: Shop,
    val medicine: Medicine,
    val stock: StockEntry
)

// ============================================================================
// PRE-LOADED MOCK DATA - Realistic Karnataka village pharmacy network
// ============================================================================
val mockShops = listOf(
    Shop(1, "Sanjivini Medicals", "Hosakote", "Mr. Ramesh K", 0.0, "+91 98450 12345", "8 AM - 9 PM"),
    Shop(2, "Sri Lakshmi Pharmacy", "Devanahalli", "Mrs. Geetha N", 3.2, "+91 98450 23456", "9 AM - 8 PM"),
    Shop(3, "Vinayaka Medical Store", "Anekal", "Mr. Suresh M", 5.5, "+91 98450 34567", "8 AM - 10 PM"),
    Shop(4, "Jeevan Drug House", "Hosur", "Dr. Priya S", 7.8, "+91 98450 45678", "24 Hours"),
    Shop(5, "Annapoorna Pharmacy", "Magadi", "Mr. Kiran R", 11.2, "+91 98450 56789", "8 AM - 9 PM"),
    Shop(6, "Aarogya Medicals", "Channapatna", "Mrs. Sunita P", 14.5, "+91 98450 67890", "9 AM - 9 PM")
)

val mockMedicines = listOf(
    // LIFE-SAVING DRUGS (with red badge)
    Medicine(1, "Insulin Regular", "Human Insulin", "Diabetes", 285.0, true, "Critical for Type 1 Diabetes"),
    Medicine(2, "Snake Antivenom", "Polyvalent ASV", "Emergency", 850.0, true, "Snake bite emergency"),
    Medicine(3, "Adrenaline Injection", "Epinephrine", "Emergency", 45.0, true, "Anaphylaxis, cardiac arrest"),
    Medicine(4, "Atropine Sulphate", "Atropine", "Emergency", 32.0, true, "Pesticide poisoning"),
    Medicine(5, "Aspirin 75mg", "Acetylsalicylic Acid", "Cardiac", 18.0, true, "Heart attack prevention"),
    Medicine(6, "Salbutamol Inhaler", "Albuterol", "Respiratory", 165.0, true, "Asthma attack rescue"),
    Medicine(7, "Diazepam 5mg", "Valium", "Neurological", 42.0, true, "Seizures, status epilepticus"),
    Medicine(8, "ORS Sachets", "Oral Rehydration", "Emergency", 12.0, true, "Severe dehydration"),
    Medicine(9, "Paracetamol IV", "Acetaminophen IV", "Emergency", 95.0, true, "High fever emergency"),
    Medicine(10, "Glucose 25%", "Dextrose IV", "Emergency", 35.0, true, "Hypoglycemia"),
    Medicine(11, "Frusemide 40mg", "Furosemide", "Cardiac", 22.0, true, "Heart failure, edema"),
    Medicine(12, "Hydrocortisone Inj", "Cortisol", "Emergency", 78.0, true, "Severe allergic reaction"),
    Medicine(13, "Tetanus Toxoid", "TT Vaccine", "Emergency", 55.0, true, "Wound infection prevention"),
    Medicine(14, "Anti-Rabies Vaccine", "ARV", "Emergency", 380.0, true, "Dog bite emergency"),
    Medicine(15, "Magnesium Sulphate", "MgSO4", "Emergency", 28.0, true, "Eclampsia, asthma"),
    Medicine(16, "Norepinephrine", "Noradrenaline", "Emergency", 145.0, true, "Septic shock, low BP"),
    Medicine(17, "Activated Charcoal", "Carbon", "Emergency", 65.0, true, "Poison absorption"),
    Medicine(18, "Lignocaine 2%", "Lidocaine", "Emergency", 38.0, true, "Cardiac arrhythmia"),

    // COMMON MEDICINES
    Medicine(19, "Paracetamol 500mg", "Acetaminophen", "Pain Relief", 8.0, false, "Fever, mild pain"),
    Medicine(20, "Amoxicillin 500mg", "Amoxil", "Antibiotic", 45.0, false, "Bacterial infections"),
    Medicine(21, "Cetirizine 10mg", "Zyrtec", "Allergy", 12.0, false, "Allergies, hay fever"),
    Medicine(22, "Pantoprazole 40mg", "Pantop", "Gastric", 28.0, false, "Acidity, ulcer"),
    Medicine(23, "Metformin 500mg", "Glucophage", "Diabetes", 18.0, false, "Type 2 diabetes"),
    Medicine(24, "Amlodipine 5mg", "Norvasc", "Cardiac", 25.0, false, "High BP"),
    Medicine(25, "Atorvastatin 10mg", "Lipitor", "Cardiac", 38.0, false, "Cholesterol"),
    Medicine(26, "Azithromycin 500mg", "Azithral", "Antibiotic", 65.0, false, "Respiratory infection"),
    Medicine(27, "Omeprazole 20mg", "Omez", "Gastric", 22.0, false, "Acid reflux"),
    Medicine(28, "Metronidazole 400mg", "Flagyl", "Antibiotic", 32.0, false, "Stomach infection"),
    Medicine(29, "Ibuprofen 400mg", "Brufen", "Pain Relief", 15.0, false, "Inflammation, pain"),
    Medicine(30, "Ranitidine 150mg", "Rantac", "Gastric", 18.0, false, "Heartburn"),
    Medicine(31, "Diclofenac Gel", "Voveran", "Pain Relief", 95.0, false, "Joint pain"),
    Medicine(32, "Vitamin B Complex", "Becosules", "Vitamin", 45.0, false, "Energy supplement"),
    Medicine(33, "Iron Tablets", "Fefol", "Vitamin", 35.0, false, "Anemia"),
    Medicine(34, "Calcium Tablets", "Shelcal", "Vitamin", 85.0, false, "Bone health"),
    Medicine(35, "Cough Syrup", "Benadryl", "Cold & Cough", 78.0, false, "Cough relief"),
    Medicine(36, "Antiseptic Cream", "Soframycin", "Topical", 35.0, false, "Wound care"),
    Medicine(37, "Eye Drops", "Refresh Tears", "Eye Care", 95.0, false, "Dry eyes"),
    Medicine(38, "Antifungal Cream", "Candid", "Topical", 65.0, false, "Skin fungal infection"),
    Medicine(39, "Bandages", "Gauze Roll", "First Aid", 25.0, false, "Wound dressing"),
    Medicine(40, "Thermometer", "Digital", "Equipment", 180.0, false, "Temperature check"),
    Medicine(41, "Glucose Strips", "Accu-Chek", "Diabetes", 450.0, false, "Blood sugar testing"),
    Medicine(42, "Hand Sanitizer", "Dettol", "Hygiene", 65.0, false, "Hand hygiene")
)

// Initial stock distributed across shops with varying availability
fun initialStock(): MutableList<StockEntry> = mutableListOf(
    // Shop 1 - Sanjivini Medicals (Hosakote) - well-stocked
    StockEntry(1, 1, 8, "30 Aug 2026", 124),
    StockEntry(1, 2, 2, "15 Dec 2026", 231),
    StockEntry(1, 3, 12, "20 Sep 2026", 145),
    StockEntry(1, 5, 45, "10 Nov 2026", 196),
    StockEntry(1, 6, 5, "25 Jul 2026", 88),
    StockEntry(1, 8, 80, "15 May 2027", 382),
    StockEntry(1, 19, 120, "12 Jun 2027", 410),
    StockEntry(1, 20, 35, "20 Aug 2026", 114),
    StockEntry(1, 21, 60, "30 Sep 2026", 155),
    StockEntry(1, 23, 85, "15 Dec 2026", 231),
    StockEntry(1, 35, 18, "10 May 2026", 12), // EXPIRING SOON
    StockEntry(1, 39, 25, "20 Mar 2027", 326),

    // Shop 2 - Sri Lakshmi Pharmacy (Devanahalli)
    StockEntry(2, 1, 5, "25 Sep 2026", 150),
    StockEntry(2, 3, 3, "18 Aug 2026", 112),
    StockEntry(2, 4, 8, "10 Oct 2026", 165),
    StockEntry(2, 7, 4, "15 Nov 2026", 201),
    StockEntry(2, 8, 100, "20 Apr 2027", 357),
    StockEntry(2, 13, 22, "30 Jun 2026", 63),
    StockEntry(2, 19, 95, "08 Jul 2027", 436),
    StockEntry(2, 22, 42, "15 Sep 2026", 142),
    StockEntry(2, 24, 28, "20 Nov 2026", 206),
    StockEntry(2, 33, 50, "25 Aug 2026", 119),
    StockEntry(2, 36, 12, "05 May 2026", 7), // EXPIRING VERY SOON
    StockEntry(2, 42, 40, "30 Dec 2026", 246),

    // Shop 3 - Vinayaka Medical Store (Anekal)
    StockEntry(3, 1, 0, "-", 0), // OUT OF STOCK
    StockEntry(3, 2, 1, "20 Nov 2026", 206),
    StockEntry(3, 5, 38, "28 Aug 2026", 122),
    StockEntry(3, 9, 15, "12 Oct 2026", 167),
    StockEntry(3, 10, 25, "18 Sep 2026", 143),
    StockEntry(3, 14, 6, "30 Mar 2027", 336),
    StockEntry(3, 19, 78, "20 Jun 2027", 418),
    StockEntry(3, 25, 30, "10 Oct 2026", 165),
    StockEntry(3, 26, 18, "15 Aug 2026", 109),
    StockEntry(3, 29, 55, "22 Sep 2026", 147),
    StockEntry(3, 31, 8, "18 May 2026", 20), // EXPIRING SOON
    StockEntry(3, 40, 5, "01 Jan 2028", 613),

    // Shop 4 - Jeevan Drug House (Hosur) - 24-hour, comprehensive stock
    StockEntry(4, 1, 15, "10 Oct 2026", 165),
    StockEntry(4, 2, 4, "30 Nov 2026", 216),
    StockEntry(4, 3, 25, "20 Aug 2026", 114),
    StockEntry(4, 4, 18, "15 Sep 2026", 140),
    StockEntry(4, 6, 12, "30 Jul 2026", 93),
    StockEntry(4, 7, 8, "10 Dec 2026", 226),
    StockEntry(4, 11, 32, "22 Aug 2026", 116),
    StockEntry(4, 12, 14, "18 Oct 2026", 173),
    StockEntry(4, 14, 10, "25 Apr 2027", 362),
    StockEntry(4, 15, 22, "10 Sep 2026", 135),
    StockEntry(4, 16, 9, "30 Jul 2026", 93),
    StockEntry(4, 17, 28, "15 Nov 2026", 201),
    StockEntry(4, 18, 35, "20 Oct 2026", 175),
    StockEntry(4, 19, 200, "10 Aug 2027", 469),
    StockEntry(4, 27, 65, "25 Sep 2026", 150),
    StockEntry(4, 30, 48, "10 Nov 2026", 196),
    StockEntry(4, 41, 22, "01 Dec 2026", 217),

    // Shop 5 - Annapoorna Pharmacy (Magadi) - moderate stock
    StockEntry(5, 5, 28, "12 Sep 2026", 137),
    StockEntry(5, 8, 60, "18 Mar 2027", 324),
    StockEntry(5, 13, 18, "25 May 2026", 27), // EXPIRING SOON
    StockEntry(5, 19, 88, "15 Jul 2027", 443),
    StockEntry(5, 20, 24, "30 Aug 2026", 124),
    StockEntry(5, 23, 55, "10 Oct 2026", 165),
    StockEntry(5, 28, 32, "20 Sep 2026", 145),
    StockEntry(5, 32, 70, "12 Dec 2026", 228),
    StockEntry(5, 34, 45, "08 Nov 2026", 194),
    StockEntry(5, 37, 18, "15 Oct 2026", 170),
    StockEntry(5, 38, 22, "20 Aug 2026", 114),

    // Shop 6 - Aarogya Medicals (Channapatna) - smaller shop
    StockEntry(6, 5, 20, "28 Aug 2026", 122),
    StockEntry(6, 8, 35, "10 May 2027", 377),
    StockEntry(6, 19, 65, "22 Jun 2027", 420),
    StockEntry(6, 20, 18, "15 Sep 2026", 140),
    StockEntry(6, 21, 38, "20 Aug 2026", 114),
    StockEntry(6, 22, 28, "30 Oct 2026", 185),
    StockEntry(6, 29, 45, "12 Sep 2026", 137),
    StockEntry(6, 33, 32, "18 Aug 2026", 112),
    StockEntry(6, 35, 14, "08 May 2026", 10), // EXPIRING VERY SOON
    StockEntry(6, 42, 25, "20 Nov 2026", 206)
)

// ============================================================================
// MAIN ACTIVITY
// ============================================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = MedicalGreen,
                    onPrimary = PureWhite,
                    secondary = ClinicalBlue,
                    background = SoftCream,
                    surface = PureWhite
                )
            ) {
                GramaSanjeeviniApp()
            }
        }
    }
}

@Composable
fun GramaSanjeeviniApp() {
    var showSplash by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1800)
        showSplash = false
    }

    AnimatedContent(
        targetState = showSplash,
        transitionSpec = { fadeIn(tween(500)) togetherWith fadeOut(tween(500)) },
        label = "splash"
    ) { splash ->
        if (splash) SplashScreen() else MainScreen()
    }
}

// ============================================================================
// SPLASH SCREEN
// ============================================================================
@Composable
fun SplashScreen() {
    val infinite = rememberInfiniteTransition(label = "splash")
    val scale by infinite.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(MedicalGreen, MedicalGreenDark))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .scale(scale)
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(PureWhite),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.LocalPharmacy,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = MedicalGreen
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                "Grama-Sanjeevini",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = PureWhite
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Rural Pharmacy Network",
                fontSize = 16.sp,
                color = PureWhite.copy(alpha = 0.9f)
            )
            Spacer(Modifier.height(48.dp))
            CircularProgressIndicator(
                color = PureWhite,
                modifier = Modifier.size(32.dp),
                strokeWidth = 3.dp
            )
        }
    }
}

// ============================================================================
// MAIN SCREEN with Bottom Navigation
// ============================================================================
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val stockList = remember { mutableStateListOf<StockEntry>().apply { addAll(initialStock()) } }
    var pharmacistMode by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = PureWhite) {
                listOf(
                    Triple("Home", Icons.Default.Home, 0),
                    Triple("Search", Icons.Default.Search, 1),
                    Triple("Life Saving", Icons.Default.Favorite, 2),
                    Triple("Expiry", Icons.Default.AccessTime, 3),
                    Triple("More", Icons.Default.Menu, 4)
                ).forEach { (label, icon, idx) ->
                    NavigationBarItem(
                        selected = selectedTab == idx,
                        onClick = { selectedTab = idx },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MedicalGreen,
                            selectedTextColor = MedicalGreen,
                            indicatorColor = ClinicalBlueLight
                        )
                    )
                }
            }
        }
    ) { padding ->
        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
            label = "tab",
            modifier = Modifier.padding(padding)
        ) { tab ->
            when (tab) {
                0 -> HomeDashboard(stockList) { selectedTab = it }
                1 -> SearchScreen(stockList)
                2 -> LifeSavingScreen(stockList)
                3 -> ExpiryWatchScreen(stockList)
                4 -> MoreScreen(stockList, pharmacistMode) { pharmacistMode = it }
            }
        }
    }
}

// ============================================================================
// HOME DASHBOARD
// ============================================================================
@Composable
fun HomeDashboard(stockList: List<StockEntry>, onNavigate: (Int) -> Unit) {
    val shopsConnected = mockShops.size
    val medicinesTracked = mockMedicines.size
    val lifeSavingInStock = stockList.count { stock ->
        stock.stockCount > 0 && mockMedicines.find { it.id == stock.medicineId }?.isLifeSaving == true
    }
    val expiryAlerts = stockList.count { it.daysToExpiry in 1..30 && it.stockCount > 0 }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftCream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Greeting Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MedicalGreen),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Namaskara! 🙏", fontSize = 14.sp, color = PureWhite.copy(alpha = 0.9f))
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Grama-Sanjeevini",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    Text(
                        "6 village pharmacies, one network",
                        fontSize = 13.sp,
                        color = PureWhite.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Stat Cards 2x2 grid
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    "Shops Connected",
                    shopsConnected.toString(),
                    Icons.Default.Store,
                    ClinicalBlue,
                    Modifier.weight(1f)
                )
                StatCard(
                    "Medicines Tracked",
                    medicinesTracked.toString(),
                    Icons.Default.MedicalServices,
                    MedicalGreen,
                    Modifier.weight(1f)
                )
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(
                    "Life Saving Stock",
                    lifeSavingInStock.toString(),
                    Icons.Default.Favorite,
                    EmergencyRed,
                    Modifier.weight(1f)
                )
                StatCard(
                    "Expiry Alerts",
                    expiryAlerts.toString(),
                    Icons.Default.Warning,
                    WarningAmber,
                    Modifier.weight(1f)
                )
            }
        }

        // Quick Actions Title
        item {
            Text(
                "Quick Actions",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }

        // Quick action cards
        item {
            QuickActionCard(
                "Search Medicine",
                "Find availability across all shops",
                Icons.Default.Search,
                ClinicalBlue
            ) { onNavigate(1) }
        }
        item {
            QuickActionCard(
                "Life-Saving Drugs",
                "Emergency stock at a glance",
                Icons.Default.Favorite,
                EmergencyRed
            ) { onNavigate(2) }
        }
        item {
            QuickActionCard(
                "Expiry Watch",
                "$expiryAlerts items expiring soon",
                Icons.Default.AccessTime,
                WarningAmber
            ) { onNavigate(3) }
        }

        // Nearest shop
        item {
            Text(
                "Nearest Shop",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        item {
            val nearest = mockShops.minByOrNull { it.distance }!!
            ShopCard(nearest, isNearest = true)
        }

        // Recent searches feed
        item {
            Text(
                "Common Searches",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        item {
            ActivityRow("💊", "Paracetamol", "Available in 5 shops", "Just now")
        }
        item {
            ActivityRow("❤️", "Insulin Regular", "Available in 4 shops", "2 hr ago")
        }
        item {
            ActivityRow("🚨", "Snake Antivenom", "Available in 3 shops", "1 day ago")
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.height(8.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(label, fontSize = 11.sp, color = TextGray)
        }
    }
}

@Composable
fun QuickActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(subtitle, fontSize = 13.sp, color = TextGray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextGray)
        }
    }
}

@Composable
fun ActivityRow(emoji: String, name: String, status: String, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Text(status, fontSize = 12.sp, color = TextGray)
            }
            Text(time, fontSize = 11.sp, color = TextGray)
        }
    }
}

// ============================================================================
// SEARCH SCREEN
// ============================================================================
@Composable
fun SearchScreen(stockList: List<StockEntry>) {
    var searchQuery by remember { mutableStateOf("") }

    val results = remember(searchQuery, stockList.toList()) {
        if (searchQuery.length < 2) emptyList()
        else mockMedicines
            .filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.genericName.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true)
            }
            .flatMap { medicine ->
                stockList
                    .filter { it.medicineId == medicine.id && it.stockCount > 0 }
                    .mapNotNull { stock ->
                        mockShops.find { it.id == stock.shopId }?.let { shop ->
                            SearchResult(shop, medicine, stock)
                        }
                    }
            }
            .sortedBy { it.shop.distance }
    }

    Column(modifier = Modifier.fillMaxSize().background(SoftCream)) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MedicalGreen)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    "Find Medicine",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
                Text(
                    "Search across all 6 connected shops",
                    fontSize = 12.sp,
                    color = PureWhite.copy(alpha = 0.85f)
                )
            }
        }

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Type medicine name...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // Quick search chips
        if (searchQuery.isEmpty()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Try searching:",
                    fontSize = 13.sp,
                    color = TextGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf("Paracetamol", "Insulin", "Aspirin", "ORS", "Antibiotic")) { suggestion ->
                        AssistChip(
                            onClick = { searchQuery = suggestion },
                            label = { Text(suggestion) },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        )
                    }
                }
            }
        }

        // Results
        if (searchQuery.length >= 2 && results.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = TextGray
                )
                Spacer(Modifier.height(16.dp))
                Text("No medicine found", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Try searching with a different name",
                    fontSize = 13.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center
                )
            }
        } else if (results.isNotEmpty()) {
            Text(
                "Found in ${results.size} shop(s)",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextGray,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(results) { result ->
                    SearchResultCard(result)
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun SearchResultCard(result: SearchResult) {
    val stockLevel = when {
        result.stock.stockCount >= 20 -> Triple("In Stock", SuccessGreen, Icons.Default.CheckCircle)
        result.stock.stockCount in 5..19 -> Triple("Limited", WarningAmber, Icons.Default.Warning)
        else -> Triple("Low Stock", LowStockOrange, Icons.Default.PriorityHigh)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top row: medicine + life-saving badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        result.medicine.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        "${result.medicine.genericName} - ${result.medicine.category}",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
                if (result.medicine.isLifeSaving) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(EmergencyRed)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "🚨 LIFE SAVING",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 10.dp), color = SoftCream)

            // Shop details
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Store,
                    contentDescription = null,
                    tint = ClinicalBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        result.shop.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                    Text(
                        "${result.shop.village} - ${result.shop.distance}km away",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(stockLevel.second.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            stockLevel.third,
                            contentDescription = null,
                            tint = stockLevel.second,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            stockLevel.first,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = stockLevel.second
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Bottom: price, stock count, hours
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoPill("₹${result.medicine.price.toInt()}", Icons.Default.CurrencyRupee, MedicalGreen)
                InfoPill("${result.stock.stockCount} units", Icons.Default.Inventory, ClinicalBlue)
                InfoPill(result.shop.openHours, Icons.Default.AccessTime, TextGray)
            }
        }
    }
}

@Composable
fun InfoPill(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold)
    }
}

// ============================================================================
// LIFE SAVING SCREEN - Critical drugs with red badge
// ============================================================================
@Composable
fun LifeSavingScreen(stockList: List<StockEntry>) {
    val lifeSavingMeds = mockMedicines.filter { it.isLifeSaving }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(SoftCream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = EmergencyRed),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "Life-Saving Drugs",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            "Emergency stock visibility across network",
                            fontSize = 12.sp,
                            color = PureWhite.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        item {
            Text(
                "${lifeSavingMeds.size} critical medicines tracked",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextGray
            )
        }

        items(lifeSavingMeds) { medicine ->
            LifeSavingCard(medicine, stockList)
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun LifeSavingCard(medicine: Medicine, stockList: List<StockEntry>) {
    val availableShops = stockList
        .filter { it.medicineId == medicine.id && it.stockCount > 0 }
        .mapNotNull { stock ->
            mockShops.find { it.id == stock.shopId }?.let { it to stock.stockCount }
        }
        .sortedBy { it.first.distance }

    val nearestShop = availableShops.firstOrNull()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(EmergencyRedLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.MedicalServices,
                        contentDescription = null,
                        tint = EmergencyRed,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        medicine.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        medicine.description,
                        fontSize = 12.sp,
                        color = TextGray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(EmergencyRed)
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text(
                        "🚨",
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            if (availableShops.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Available in ${availableShops.size} shop(s) - Nearest: ${nearestShop?.first?.village} (${nearestShop?.first?.distance}km)",
                        fontSize = 12.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = EmergencyRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "OUT OF STOCK across all shops",
                        fontSize = 12.sp,
                        color = EmergencyRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoPill("₹${medicine.price.toInt()}", Icons.Default.CurrencyRupee, MedicalGreen)
                InfoPill(medicine.category, Icons.Default.Category, ClinicalBlue)
            }
        }
    }
}

// ============================================================================
// EXPIRY WATCH SCREEN
// ============================================================================
@Composable
fun ExpiryWatchScreen(stockList: List<StockEntry>) {
    val expiringStock = stockList
        .filter { it.daysToExpiry in 1..60 && it.stockCount > 0 }
        .sortedBy { it.daysToExpiry }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(SoftCream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = WarningAmber),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "Expiry Watch",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            "Sell near-expiry stock at discount to reduce wastage",
                            fontSize = 12.sp,
                            color = PureWhite.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        if (expiringStock.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = SuccessGreen
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("All clear!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "No medicines expiring within 60 days",
                            fontSize = 13.sp,
                            color = TextGray
                        )
                    }
                }
            }
        } else {
            item {
                Text(
                    "${expiringStock.size} item(s) expiring soon",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextGray
                )
            }

            items(expiringStock) { stock ->
                val medicine = mockMedicines.find { it.id == stock.medicineId }
                val shop = mockShops.find { it.id == stock.shopId }
                if (medicine != null && shop != null) {
                    ExpiryCard(stock, medicine, shop)
                }
            }
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun ExpiryCard(stock: StockEntry, medicine: Medicine, shop: Shop) {
    val urgency = when {
        stock.daysToExpiry <= 15 -> Triple("URGENT", EmergencyRed, 40)
        stock.daysToExpiry <= 30 -> Triple("WARNING", WarningAmber, 25)
        else -> Triple("WATCH", ClinicalBlue, 15)
    }
    val discountedPrice = medicine.price * (100 - urgency.third) / 100

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        medicine.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        "${shop.name}, ${shop.village}",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(urgency.second)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        urgency.first,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoPill("Expires: ${stock.expiryDate}", Icons.Default.CalendarToday, urgency.second)
                InfoPill("${stock.daysToExpiry} days left", Icons.Default.Timer, urgency.second)
            }

            Spacer(Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SoftCream)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocalOffer,
                        contentDescription = null,
                        tint = MedicalGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Suggested: ${urgency.third}% discount",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MedicalGreen
                        )
                        Text(
                            "Original ₹${medicine.price.toInt()} → Sell at ₹${discountedPrice.toInt()}",
                            fontSize = 11.sp,
                            color = TextGray
                        )
                    }
                    Text(
                        "${stock.stockCount} units",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ClinicalBlue
                    )
                }
            }
        }
    }
}

// ============================================================================
// MORE SCREEN - Pharmacist mode toggle, shop list, about
// ============================================================================
@Composable
fun MoreScreen(stockList: List<StockEntry>, pharmacistMode: Boolean, onToggleMode: (Boolean) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(SoftCream),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }

        // Pharmacist mode toggle
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MedicalGreen,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Pharmacist Mode", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "Update stock levels for your shop",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                    Switch(
                        checked = pharmacistMode,
                        onCheckedChange = onToggleMode,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = PureWhite,
                            checkedTrackColor = MedicalGreen
                        )
                    )
                }
            }
        }

        item {
            Text(
                "Connected Shops",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(mockShops) { shop ->
            ShopCard(shop, isNearest = false)
        }

        item {
            Text(
                "About",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Grama-Sanjeevini",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MedicalGreen
                    )
                    Text("v1.0 - Rural Pharmacy Network", fontSize = 12.sp, color = TextGray)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "A unified pharmacy network connecting village medical stores. Built to ensure critical medicines are found within minimum travel distance, while helping pharmacists reduce expiry wastage through smart alerts.",
                        fontSize = 13.sp,
                        color = TextDark,
                        lineHeight = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Built by Huda Fathima | MindMatrix Internship 2026",
                        fontSize = 11.sp,
                        color = TextGray,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun ShopCard(shop: Shop, isNearest: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isNearest) ClinicalBlueLight else PureWhite
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MedicalGreen.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Store,
                        contentDescription = null,
                        tint = MedicalGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            shop.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        if (isNearest) {
                            Spacer(Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ClinicalBlue)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    "NEAREST",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PureWhite
                                )
                            }
                        }
                    }
                    Text("${shop.village} - ${shop.distance}km", fontSize = 12.sp, color = TextGray)
                    Text(shop.pharmacist, fontSize = 11.sp, color = TextGray)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoPill(shop.openHours, Icons.Default.AccessTime, ClinicalBlue)
                InfoPill(shop.phone, Icons.Default.Phone, MedicalGreen)
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GRAMASANJEEVINITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GRAMASANJEEVINITheme {
        Greeting("Android")
    }
}