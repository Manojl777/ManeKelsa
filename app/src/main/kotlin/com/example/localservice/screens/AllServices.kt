package com.example.localservice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Wash
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
private val ColorPrimary = Color(0xFF006D67)
private val ColorBackground = Color(0xFFF8F6EF)

data class ServiceModel(
    val englishName: String,
    val kannadaName: String,
    val icon: ImageVector,
    val iconBackground: Color
)

@Composable
fun AllServices(

    language: String = "en",

    onLanguageChange: (String) -> Unit = {},

    onBack: () -> Unit = {},

    onServiceClick: (String) -> Unit = {}

) {

    var searchQuery by remember {
        mutableStateOf("")
    }

    val services = remember {

        mutableStateListOf(

            ServiceModel(
                "AC Service",
                "ಎಸಿ ಸೇವೆ",
                Icons.Default.Power,
                Color(0xFFE7EEFF)
            ),

            ServiceModel(
                "Appliance Repair",
                "ಉಪಕರಣ ದುರಸ್ತಿ",
                Icons.Default.Build,
                Color(0xFFFFEDD9)
            ),

            ServiceModel(
                "Carpentry",
                "ಕಾರ್ಪೆಂಟ್ರಿ",
                Icons.Default.HomeRepairService,
                Color(0xFFF8E9B7)
            ),

            ServiceModel(
                "Child Care",
                "ಮಕ್ಕಳ ಆರೈಕೆ",
                Icons.Default.ThumbUp,
                Color(0xFFFFE7D8)
            ),

            ServiceModel(
                "Cooking",
                "ಅಡುಗೆ",
                Icons.Default.CleaningServices,
                Color(0xFFF9E8B2)
            ),

            ServiceModel(
                "Deep Cleaning",
                "ಡೀಪ್ ಕ್ಲೀನಿಂಗ್",
                Icons.Default.CleaningServices,
                Color(0xFFE7EEFF)
            ),

            ServiceModel(
                "Elderly Care",
                "ಹಿರಿಯರ ಆರೈಕೆ",
                Icons.Default.ThumbUp,
                Color(0xFFFFE3EA)
            ),

            ServiceModel(
                "Electric Installation",
                "ಎಲೆಕ್ಟ್ರಿಕ್ ಇನ್‌ಸ್ಟಾಲೇಶನ್",
                Icons.Default.Power,
                Color(0xFFE7E9FF)
            ),

            ServiceModel(
                "Electrical Repair",
                "ಎಲೆಕ್ಟ್ರಿಕಲ್ ರಿಪೇರ್",
                Icons.Default.ElectricalServices,
                Color(0xFFF7EDB7)
            ),

            ServiceModel(
                "Gardening",
                "ತೋಟಗಾರಿಕೆ",
                Icons.Default.LocationOn,
                Color(0xFFDDF7E8)
            ),

            ServiceModel(
                "House Cleaning",
                "ಮನೆ ಕ್ಲೀನಿಂಗ್",
                Icons.Default.CleaningServices,
                Color(0xFFE4EEFF)
            ),

            ServiceModel(
                "Painting",
                "ಪೇಂಟಿಂಗ್",
                Icons.Default.Brush,
                Color(0xFFF2E4FF)
            ),

            ServiceModel(
                "Pet Care",
                "ಪೆಟ್ ಕೇರ್",
                Icons.Default.Pets,
                Color(0xFFFFE5EA)
            ),

            ServiceModel(
                "Plumbing",
                "ಪ್ಲಂಬಿಂಗ್",
                Icons.Default.Plumbing,
                Color(0xFFDDF7FF)
            ),

            ServiceModel(
                "Vehicle Wash",
                "ವಾಹನ ವಾಶ್",
                Icons.Default.Wash,
                Color(0xFFE4E9FF)
            )
        )
    }

    val filteredServices = services.filter {

        if (searchQuery.length < 2) {

            true

        } else {

            it.englishName.contains(
                searchQuery,
                ignoreCase = true
            ) ||

                    it.kannadaName.contains(
                        searchQuery,
                        ignoreCase = true
                    )
        }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                ),

            verticalAlignment = Alignment.CenterVertically

        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Text(

                text = if (language == "en")
                    "All Services"
                else
                    "ಎಲ್ಲಾ ಸೇವೆಗಳು",

                color = ColorPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )

            Surface(
                shape = CircleShape,
                color = Color(0xFFEAF2EE),
                modifier = Modifier.size(46.dp)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Outlined.DarkMode,
                        contentDescription = null,
                        tint = ColorPrimary
                    )
                }
            }

            androidx.compose.foundation.layout.Spacer(
                modifier = Modifier.size(10.dp)
            )

            Surface(

                shape = RoundedCornerShape(30.dp),

                color = Color(0xFFEFF3F0),

                modifier = Modifier.clickable {

                    onLanguageChange(

                        if (language == "en")
                            "kn"
                        else
                            "en"
                    )
                }
            ) {

                Text(

                    text = if (language == "en")
                        "ಕನ್ನಡ"
                    else
                        "ENGLISH",

                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 10.dp
                    ),

                    color = ColorPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 18.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(

                value = searchQuery,

                onValueChange = {
                    searchQuery = it
                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },

                placeholder = {

                    Text(

                        text = if (language == "en")
                            "Search for a service..."
                        else
                            "ಸೇವೆಗೆ ಹುಡುಕಿ..."
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),

                shape = RoundedCornerShape(18.dp),

                singleLine = true,

                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = ColorBackground,
                    unfocusedContainerColor = ColorBackground,

                    focusedBorderColor = Color(0xFFAACCC3),
                    unfocusedBorderColor = Color(0xFFE5E5E5)
                )
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 10.dp
            ),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            items(filteredServices) { service ->

                ServiceListCard(

                    title = if (language == "en")
                        service.englishName
                    else
                        service.kannadaName,

                    icon = service.icon,

                    iconBackground = service.iconBackground,

                    onClick = {

                        onServiceClick(service.englishName)
                    }
                )
            }
        }
    }
}

@Composable
fun ServiceListCard(

    title: String,

    icon: ImageVector,

    iconBackground: Color,

    onClick: () -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Surface(

                shape = RoundedCornerShape(18.dp),

                color = iconBackground,

                modifier = Modifier.size(54.dp)

            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        icon,
                        contentDescription = null,
                        tint = ColorPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Text(

                text = title,

                fontSize = 22.sp,

                fontWeight = FontWeight.SemiBold,

                color = Color(0xFF454545),

                modifier = Modifier.weight(1f)
            )

            Text(
                text = ">",
                color = Color.LightGray,
                fontSize = 26.sp
            )
        }
    }
}