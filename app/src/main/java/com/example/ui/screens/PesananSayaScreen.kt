package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AtelierOrder
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.AtelierColors

private data class OrderFilterTabItem(
    val key: String,
    val label: String
)

private val orderFilterTabs = listOf(
    OrderFilterTabItem("all", "SEMUA"),
    OrderFilterTabItem("menunggu", "MENUNGGU"),
    OrderFilterTabItem("diproses", "DIPROSES"),
    OrderFilterTabItem("siap", "SIAP DIAMBIL"),
    OrderFilterTabItem("selesai", "SELESAI"),
    OrderFilterTabItem("dibatalkan", "DIBATALKAN")
)

@Composable
fun PesananSayaScreen(
    orders: List<AtelierOrder>,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    onShowBarcode: (AtelierOrder) -> Unit,
    onAdvanceStage: (AtelierOrder) -> Unit,
    onOpenReview: (AtelierOrder) -> Unit,
    onReorder: (AtelierOrder) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val context = LocalContext.current
    val filteredOrders = remember(orders, selectedFilter) {
        if (selectedFilter == "all") {
            // Show the 3 customer orders first (#NC-2024-089, #NC-2024-072, #NC-2024-055) + any newly created ones
            orders
        } else {
            orders.filter { it.statusKey.equals(selectedFilter, ignoreCase = true) }
        }
    }

    val diprosesCount = orders.count { it.statusKey == "diproses" }
    val siapCount = orders.count { it.statusKey == "siap" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("pesanan_saya_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Editorial Section Intro
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(1.5.dp)
                            .background(AtelierColors.TertiaryFixedDim)
                    )
                    Text(
                        text = "PELACAKAN ATELIER",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnTertiaryContainer,
                        letterSpacing = 2.sp
                    )
                }
                Text(
                    text = "Pesanan Saya",
                    style = MaterialTheme.typography.headlineLarge,
                    color = AtelierColors.Primary
                )
                Text(
                    text = "Pantau progres jahitan bespoke dan status sewa kebaya Anda secara real-time dengan presisi artisan.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AtelierColors.OnSurfaceVariant
                )
            }
        }

        // Interactive Filter Tabs
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(orderFilterTabs) { tab ->
                    val isSelected = selectedFilter == tab.key
                    val countBadge = when (tab.key) {
                        "diproses" -> diprosesCount
                        "siap" -> siapCount
                        else -> 0
                    }
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainerLow,
                        shadowElevation = if (isSelected) 2.dp else 0.dp,
                        modifier = Modifier
                            .clickable { onSelectFilter(tab.key) }
                            .testTag("order_filter_tab_${tab.key}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = tab.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) AtelierColors.TertiaryFixed else AtelierColors.OnSurfaceVariant
                            )
                            if (countBadge > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (tab.key == "diproses") AtelierColors.SecondaryFixed else AtelierColors.TertiaryFixed
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = countBadge.toString(),
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Order Cards or Empty State
        if (filteredOrders.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.SurfaceContainerLow.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(AtelierColors.SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null,
                                tint = AtelierColors.Primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Text(
                            text = "Tidak Ada Pesanan",
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.Primary
                        )
                        Text(
                            text = "Belum ada catatan pesanan atelier pada kategori ini saat ini.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { onSelectFilter("all") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.PrimaryContainer,
                                contentColor = AtelierColors.TertiaryFixed
                            ),
                            shape = CircleShape
                        ) {
                            Text("LIHAT SEMUA PESANAN", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        } else {
            items(filteredOrders, key = { it.orderCode }) { order ->
                PatronOrderCard(
                    order = order,
                    onCopyCode = {
                        copyToClipboard(context, order.orderCode)
                        onShowSnackbar("Kode pesanan ${order.orderCode} disalin")
                    },
                    onShowBarcode = { onShowBarcode(order) },
                    onAdvanceStage = { onAdvanceStage(order) },
                    onOpenReview = { onOpenReview(order) },
                    onReorder = { onReorder(order) }
                )
            }
        }

        // Garansi Pas Sempurna (14 Hari) Reassurance Banner
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = AtelierColors.SurfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AtelierColors.PrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = AtelierColors.TertiaryFixed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Garansi Pas Sempurna (14 Hari)",
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Setiap busana bespoke mencakup penyesuaian gratis hingga jatuh anggun sesuai proporsi tubuh Anda.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PatronOrderCard(
    order: AtelierOrder,
    onCopyCode: () -> Unit,
    onShowBarcode: () -> Unit,
    onAdvanceStage: () -> Unit,
    onOpenReview: () -> Unit,
    onReorder: () -> Unit
) {
    val context = LocalContext.current
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("patron_order_card_${order.orderCode}")
    ) {
        Column {
            // Fine Hairline Golden Top Thread
            if (order.statusKey != "selesai") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    AtelierColors.TertiaryFixedDim.copy(alpha = 0.2f),
                                    AtelierColors.TertiaryFixedDim,
                                    AtelierColors.TertiaryFixedDim.copy(alpha = 0.2f)
                                )
                            )
                        )
                )
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Meta
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = order.serviceType.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = order.orderCode,
                                style = MaterialTheme.typography.titleMedium,
                                color = AtelierColors.Primary,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = onCopyCode,
                                modifier = Modifier.size(26.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Salin Kode Pesanan",
                                    tint = AtelierColors.OnTertiaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    // Status Badge
                    OrderStatusPill(statusKey = order.statusKey, statusBadge = order.statusBadge)
                }

                // Garment Visual & Synopsis
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AtelierColors.SurfaceContainerLow.copy(alpha = 0.65f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (order.statusKey == "selesai") 0.9f else 1f)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(96.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            AtelierNetworkImage(
                                imageUrl = order.imageUrl,
                                contentDescription = order.garmentTitle,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (order.statusKey != "selesai") {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (order.statusKey == "siap") {
                                        AtelierColors.TertiaryContainer.copy(alpha = 0.9f)
                                    } else {
                                        AtelierColors.Primary.copy(alpha = 0.85f)
                                    },
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        text = if (order.statusKey == "siap") "Sewa 3 Hari" else "Bespoke",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                                        color = if (order.statusKey == "siap") AtelierColors.TertiaryFixed else AtelierColors.OnPrimary,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = order.garmentTitle,
                                style = MaterialTheme.typography.titleMedium,
                                color = AtelierColors.OnSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (order.statusKey == "diproses") {
                                    Icon(
                                        imageVector = Icons.Default.Straighten,
                                        contentDescription = null,
                                        tint = AtelierColors.OnTertiaryContainer,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Text(
                                    text = order.subNote,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = order.priceFormatted,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AtelierColors.Primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = order.paymentNote,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = if (order.statusKey == "diproses") AtelierColors.Secondary else AtelierColors.OnSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // Conditional Mid-Section: Progress Stepper OR Pickup Callout
                when (order.statusKey) {
                    "diproses", "menunggu" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "PROGRES PENGERJAAN",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.Primary
                                )
                                Text(
                                    text = "Tahap ${order.currentStep} dari ${order.totalSteps}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnTertiaryContainer
                                )
                            }

                            // 5-Step Horizontal Stepper
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OrderStageStep(
                                    label = "Dibuat",
                                    icon = Icons.Default.Check,
                                    stepIndex = 1,
                                    currentStep = order.currentStep
                                )
                                OrderStageStep(
                                    label = "Konfirmasi",
                                    icon = Icons.Default.Check,
                                    stepIndex = 2,
                                    currentStep = order.currentStep
                                )
                                OrderStageStep(
                                    label = "Pola & Jahit",
                                    icon = Icons.Default.ContentCut,
                                    stepIndex = 3,
                                    currentStep = order.currentStep
                                )
                                OrderStageStep(
                                    label = "Fitting/Siap",
                                    icon = Icons.Default.Inventory2,
                                    stepIndex = 4,
                                    currentStep = order.currentStep
                                )
                                OrderStageStep(
                                    label = "Selesai",
                                    icon = Icons.Default.Verified,
                                    stepIndex = 5,
                                    currentStep = order.currentStep
                                )
                            }

                            // Artisan Note Pill
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = AtelierColors.SurfaceContainerLow,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EditNote,
                                        contentDescription = null,
                                        tint = AtelierColors.OnTertiaryContainer,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = "Catatan Kepala Penjahit:",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.Primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "\"${order.tailorNote}\"",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontStyle = FontStyle.Italic
                                            ),
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }

                    "siap" -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AtelierColors.SurfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = "LOKASI PENGAMBILAN:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = order.pickupLocation,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.OnSurface
                                    )
                                    Text(
                                        text = order.pickupHours,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                if (order.userReview.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.SurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Ulasan Anda: \"${order.userReview}\"",
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                            color = AtelierColors.Primary,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                // Action CTAs
                when (order.statusKey) {
                    "siap" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onShowBarcode,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.PrimaryContainer,
                                    contentColor = AtelierColors.TertiaryFixed
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("barcode_btn_${order.orderCode}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCode2,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("KODE & BARCODE SEWA", style = MaterialTheme.typography.labelMedium)
                            }

                            Button(
                                onClick = {
                                    openExternalUrl(context, "https://maps.google.com/?q=Nely+Collection+Jakarta")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainerHigh,
                                    contentColor = AtelierColors.Primary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Directions,
                                    contentDescription = null,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("PETUNJUK ARAH", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    "selesai" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onOpenReview,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainerHigh,
                                    contentColor = AtelierColors.Primary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("review_btn_${order.orderCode}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RateReview,
                                    contentDescription = null,
                                    tint = AtelierColors.OnTertiaryContainer,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("BERI ULASAN HASIL JAHIT", style = MaterialTheme.typography.labelMedium)
                            }

                            Button(
                                onClick = onReorder,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.PrimaryContainer,
                                    contentColor = AtelierColors.OnPrimary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("reorder_btn_${order.orderCode}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Replay,
                                    contentDescription = null,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("JAHIT ULANG MODEL INI", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    else -> {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onAdvanceStage,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.PrimaryContainer,
                                    contentColor = AtelierColors.OnPrimary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .testTag("detail_pelacakan_btn_${order.orderCode}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timeline,
                                    contentDescription = null,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("DETAIL PELACAKAN", style = MaterialTheme.typography.labelMedium)
                            }

                            Button(
                                onClick = {
                                    openExternalUrl(
                                        context,
                                        "https://wa.me/6281234567890?text=Halo%20Nely%20Collection,%20saya%20patron%20pesanan%20${order.orderCode}"
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainerHigh,
                                    contentColor = AtelierColors.Primary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    tint = AtelierColors.Secondary,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("WHATSAPP BUTIK", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderStatusPill(statusKey: String, statusBadge: String) {
    when (statusKey) {
        "diproses" -> {
            Surface(
                shape = CircleShape,
                color = AtelierColors.PrimaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(AtelierColors.TertiaryFixedDim)
                    )
                    Text(
                        text = statusBadge.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.TertiaryFixed
                    )
                }
            }
        }

        "siap" -> {
            Surface(
                shape = CircleShape,
                color = AtelierColors.TertiaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = AtelierColors.TertiaryFixed,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = statusBadge.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.TertiaryFixed
                    )
                }
            }
        }

        else -> {
            Surface(
                shape = CircleShape,
                color = AtelierColors.SurfaceContainerHigh
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = AtelierColors.OnTertiaryContainer,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = statusBadge.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderStageStep(
    label: String,
    icon: ImageVector,
    stepIndex: Int,
    currentStep: Int
) {
    val isDone = stepIndex < currentStep
    val isCurrent = stepIndex == currentStep

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (isCurrent) 32.dp else 28.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCurrent -> AtelierColors.TertiaryFixed
                        isDone -> AtelierColors.PrimaryContainer
                        else -> AtelierColors.SurfaceContainer
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isDone) Icons.Default.Check else icon,
                contentDescription = label,
                tint = when {
                    isCurrent -> AtelierColors.Primary
                    isDone -> AtelierColors.OnPrimary
                    else -> AtelierColors.OnSurfaceVariant
                },
                modifier = Modifier.size(if (isCurrent) 17.dp else 15.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isDone || isCurrent) AtelierColors.Primary else AtelierColors.OnSurfaceVariant
        )
    }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    clipboard?.setPrimaryClip(ClipData.newPlainText("Order Code", text))
}
