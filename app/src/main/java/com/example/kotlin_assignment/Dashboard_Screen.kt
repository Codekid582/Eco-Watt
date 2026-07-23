package com.example.kotlin_assignment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val DashboardGreen = Color(0xFF3A985E)
private val DarkGreen = Color(0xFF168A4B)
private val LightGreen = Color(0xFF7BD8A7)
private val ScreenBackground = Color(0xFFF7F8F8)
private val BottomBarBackground = Color(0xFFF0F3F5)


@Composable
fun DashboardScreen() {

    var selectedBottomItem by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ScreenBackground,

        topBar = {
            DashboardTopBar()
        },

        bottomBar = {
            DashboardBottomBar(
                selectedItem = selectedBottomItem,
                onItemSelected = {
                    selectedBottomItem = it
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 10.dp
            ),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                PowerConsumptionCard()
            }

            item {
                UsageBreakdownCard()
            }
        }
    }
}


// =====================================================
// Top bar
// =====================================================

@Composable
private fun DashboardTopBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DashboardGreen)
            .statusBarsPadding()
            .height(72.dp)
            .padding(horizontal = 24.dp)
    ) {

        Text(
            text = "Dashboard",
            modifier = Modifier.align(Alignment.CenterStart),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {

            IconButton(
                onClick = {
                    // 之后打开 Notification 页面
                }
            ) {

                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(29.dp),
                    tint = Color(0xFFB8D7C3)
                )
            }

            Box(
                modifier = Modifier
                    .size(11.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}


// =====================================================
// Power consumption card
// =====================================================

@Composable
private fun PowerConsumptionCard() {

    var selectedPeriod by rememberSaveable {
        mutableStateOf("Daily")
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFC5C5C5)
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 18.dp
            ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total Power Consumption",
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                PeriodButton(
                    text = "Daily",
                    selected = selectedPeriod == "Daily",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedPeriod = "Daily"
                    }
                )

                PeriodButton(
                    text = "Monthly",
                    selected = selectedPeriod == "Monthly",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        selectedPeriod = "Monthly"
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            PowerProgressCircle(
                progress = 0.83f,
                percentageText = "83%",
                powerText = "25.7"
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "You’ve used 83% of your average monthly budget.",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun PeriodButton(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(22.dp),

        color = if (selected) {
            DashboardGreen
        } else {
            Color.Transparent
        }
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = text,
                color = if (selected) {
                    Color.White
                } else {
                    Color.Black
                },
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


// =====================================================
// Circular progress
// =====================================================

@Composable
private fun PowerProgressCircle(
    progress: Float,
    percentageText: String,
    powerText: String
) {

    Box(
        modifier = Modifier.size(240.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val strokeWidth = 6.dp.toPx()
            val padding = strokeWidth / 2

            val circleSize = Size(
                width = size.width - strokeWidth,
                height = size.height - strokeWidth
            )

            drawArc(
                color = Color(0xFFD8D8D8),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(
                    padding,
                    padding
                ),
                size = circleSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = Color(0xFF2DBA43),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(
                    padding,
                    padding
                ),
                size = circleSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = percentageText,
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = powerText,
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "KILOWATT PER HOURS",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}


// =====================================================
// Usage breakdown
// =====================================================

@Composable
private fun UsageBreakdownCard() {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = DashboardGreen,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF8EC59F)
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 24.dp
            )
        ) {

            Text(
                text = "Usage Breakdown",
                color = Color.White.copy(alpha = 0.40f),
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "On Track",
                    color = Color.White.copy(alpha = 0.55f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(
                            Color.White.copy(alpha = 0.45f)
                        )
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            UsageItem(
                title = "HVAC",
                subtitle = "Heating and cooling systems",
                value = "24.5 KW",
                progress = 0.52f,
                icon = Icons.Filled.AcUnit,
                iconBackground = Color(0xFFFFB5B5),
                iconTint = Color(0xFFAD3A32)
            )

            Spacer(
                modifier = Modifier.height(17.dp)
            )

            UsageItem(
                title = "Appliances",
                subtitle = "Kitchen and laundry hardware",
                value = "0 KW",
                progress = 0.10f,
                icon = Icons.Filled.Kitchen,
                iconBackground = Color(0xFFB8C8FF),
                iconTint = Color(0xFF283B8E)
            )

            Spacer(
                modifier = Modifier.height(17.dp)
            )

            UsageItem(
                title = "Lighting",
                subtitle = "Indoor and exterior luminaires",
                value = "10.1 KW",
                progress = 0.27f,
                icon = Icons.Filled.Lightbulb,
                iconBackground = Color(0xFFFFF59B),
                iconTint = Color(0xFF9B8F1A)
            )
        }
    }
}


@Composable
private fun UsageItem(
    title: String,
    subtitle: String,
    value: String,
    progress: Float,
    icon: ImageVector,
    iconBackground: Color,
    iconTint: Color
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(iconBackground),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(25.dp),
                tint = iconTint
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    color = Color.White.copy(alpha = 0.38f),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = value,
                    color = Color.White.copy(alpha = 0.50f),
                    fontSize = 12.sp
                )
            }

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.30f),
                fontSize = 12.sp
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            UsageProgressBar(
                progress = progress
            )
        }
    }
}


@Composable
private fun UsageProgressBar(
    progress: Float
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(DarkGreen)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(LightGreen)
        )
    }
}


// =====================================================
// Bottom navigation bar
// =====================================================

private data class DashboardBottomItem(
    val label: String,
    val icon: ImageVector
)


@Composable
private fun DashboardBottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {

    val bottomItems = listOf(
        DashboardBottomItem(
            label = "Dashboard",
            icon = Icons.Filled.Dashboard
        ),
        DashboardBottomItem(
            label = "Appliance",
            icon = Icons.Filled.Bolt
        ),
        DashboardBottomItem(
            label = "Stats",
            icon = Icons.Filled.BarChart
        ),
        DashboardBottomItem(
            label = "Setting",
            icon = Icons.Filled.Settings
        )
    )

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = BottomBarBackground,
        tonalElevation = 0.dp
    ) {

        bottomItems.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItem == index,

                onClick = {
                    onItemSelected(index)
                },

                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(23.dp)
                    )
                },

                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = DashboardGreen,
                    indicatorColor = DashboardGreen,

                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black
                )
            )
        }
    }
}