package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.AtelierOrder
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.AtelierColors

private data class AdminOrderFilterChip(val key: String, val label: String)

private val adminOrderTabs = listOf(
    AdminOrderFilterChip("all", "Semua (48)"),
    AdminOrderFilterChip("menunggu", "Menunggu DP (5)"),
    AdminOrderFilterChip("diproses", "Diproses (18)"),
    AdminOrderFilterChip("siap", "Siap Diambil (9)"),
    AdminOrderFilterChip("selesai", "Selesai (14)")
)

@Composable
fun AdminPesananScreen(
    orders: List<AtelierOrder>,
    searchQuery: String,
    selectedFilter: String,
    expandedOrderCode: String,
    showNewOrderDialog: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSelectFilter: (String) -> Unit,
    onToggleExpandOrder: (String) -> Unit,
    onAssignArtisan: (AtelierOrder, String) -> Unit,
    onAdvanceOrderStage: (AtelierOrder) -> Unit,
    onShowBarcode: (AtelierOrder) -> Unit,
    onSetShowNewOrderDialog: (Boolean) -> Unit,
    onCreateAdminOrder: (String, String, String, String, String) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val context = LocalContext.current

    val filteredOrders = remember(orders, searchQuery, selectedFilter) {
        orders.filter { order ->
            val matchesTab = selectedFilter == "all" ||
                order.statusKey.equals(selectedFilter, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                order.orderCode.contains(searchQuery, ignoreCase = true) ||
                order.clientName.contains(searchQuery, ignoreCase = true) ||
                order.garmentTitle.contains(searchQuery, ignoreCase = true)
            matchesTab && matchesSearch
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("admin_pesanan_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Atelier Title Banner & Context
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ATELIER LOG & PRODUKSI",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            shape = CircleShape,
                            color = AtelierColors.SurfaceContainerHigh
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AtelierColors.SecondaryContainer)
                                )
                                Text(
                                    text = "Live Sync",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }
                        }
                    }
                    Text(
                        text = "Manajemen Pesanan",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AtelierColors.Primary
                    )
                    Text(
                        text = "Pantau pesanan jahit bespoke & reservasi sewa kebaya secara presisi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }
            }

            // Metric Counters Bento / 2x2 Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Card 1: Total Aktif
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLow,
                            shadowElevation = 1.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "TOTAL AKTIF",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Checkroom,
                                        contentDescription = null,
                                        tint = AtelierColors.Primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "48",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Pesanan",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(CircleShape)
                                        .background(AtelierColors.SurfaceContainerHigh)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.78f)
                                            .height(4.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.Primary)
                                    )
                                }
                            }
                        }

                        // Card 2: Jahit & Pola
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLow,
                            shadowElevation = 1.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "JAHIT & POLA",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ContentCut,
                                        contentDescription = null,
                                        tint = AtelierColors.SurfaceTint,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "18",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = AtelierColors.PrimaryContainer,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Busana",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                                Text(
                                    text = "6 Dalam Bordir",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.Secondary
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Card 3: Fitting / Siap
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLow,
                            shadowElevation = 1.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "FITTING / SIAP",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Checkroom,
                                        contentDescription = null,
                                        tint = AtelierColors.OnTertiaryContainer,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = "9",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Busana",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                                Row(
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
                                        text = "3 Hari ini",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Card 4: Pendapatan
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.PrimaryContainer,
                            shadowElevation = 2.dp,
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "PENDAPATAN",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnPrimary.copy(alpha = 0.8f)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Payments,
                                        contentDescription = null,
                                        tint = AtelierColors.TertiaryFixedDim,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = "Rp 42,8M",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = AtelierColors.SurfaceBright,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "DP: Rp 28,5M",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.SurfaceContainerHighest.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }

            // Search & Filter Controls
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AtelierColors.SurfaceContainerLowest,
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 11.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = AtelierColors.OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Cari #NC, nama klien, WhatsApp...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = AtelierColors.OnSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                }
                                BasicTextField(
                                    value = searchQuery,
                                    onValueChange = onSearchQueryChange,
                                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                                        color = AtelierColors.OnSurface
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter Tingkat Lanjut",
                                tint = AtelierColors.OnSurfaceVariant,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(adminOrderTabs) { tab ->
                            val isSelected = selectedFilter == tab.key
                            Surface(
                                shape = CircleShape,
                                color = if (isSelected) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainer,
                                shadowElevation = if (isSelected) 2.dp else 0.dp,
                                modifier = Modifier
                                    .clickable { onSelectFilter(tab.key) }
                                    .testTag("admin_order_tab_${tab.key}")
                            ) {
                                Text(
                                    text = tab.label,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (isSelected) AtelierColors.OnPrimary else AtelierColors.OnSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Orders Stream
            items(filteredOrders, key = { it.orderCode }) { order ->
                AdminOrderStreamCard(
                    order = order,
                    isExpanded = expandedOrderCode == order.orderCode,
                    onToggleExpand = { onToggleExpandOrder(order.orderCode) },
                    onUpdateWaClick = {
                        openExternalUrl(
                            context,
                            "https://wa.me/6281238490000?text=Update%20Progres%20Pesanan%20${order.orderCode}"
                        )
                    },
                    onPrintWorksheet = {
                        onShowSnackbar("Lembar kerja produksi ${order.orderCode} siap dicetak")
                    },
                    onChangeFitting = {
                        onAdvanceOrderStage(order)
                    },
                    onScanHandover = {
                        onShowBarcode(order)
                    },
                    onRemindClient = {
                        onShowSnackbar("Notifikasi pengingat WhatsApp dikirim ke ${order.clientName}")
                    },
                    onAssignArtisan = {
                        onAssignArtisan(order, "Bu Siti (Master Tailor) & Mbak Ani (Bordir)")
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(68.dp))
            }
        }

        // Floating Sticky Action Button (+ Pesanan Baru)
        Surface(
            shape = CircleShape,
            color = AtelierColors.PrimaryContainer,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .clickable { onSetShowNewOrderDialog(true) }
                .testTag("admin_new_order_fab")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Buat Pesanan Baru",
                    tint = AtelierColors.TertiaryFixedDim,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "+ Pesanan Baru",
                    style = MaterialTheme.typography.labelLarge,
                    color = AtelierColors.OnPrimary
                )
            }
        }
    }

    if (showNewOrderDialog) {
        NewAdminOrderDialog(
            onDismiss = { onSetShowNewOrderDialog(false) },
            onSubmit = onCreateAdminOrder
        )
    }
}

@Composable
private fun AdminOrderStreamCard(
    order: AtelierOrder,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onUpdateWaClick: () -> Unit,
    onPrintWorksheet: () -> Unit,
    onChangeFitting: () -> Unit,
    onScanHandover: () -> Unit,
    onRemindClient: () -> Unit,
    onAssignArtisan: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_order_card_${order.orderCode}")
    ) {
        Column {
            // Card Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AtelierColors.SurfaceContainerLow)
                    .clickable { onToggleExpand() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = order.orderCode,
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Surface(
                            shape = CircleShape,
                            color = when (order.statusKey) {
                                "siap" -> AtelierColors.TertiaryFixed
                                "menunggu" -> AtelierColors.ErrorContainer
                                else -> AtelierColors.SecondaryContainer.copy(alpha = 0.25f)
                            }
                        ) {
                            Text(
                                text = if (order.statusKey == "diproses") "DIPROSES (${order.currentStep}/5)" else order.statusBadge.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = when (order.statusKey) {
                                    "siap" -> AtelierColors.OnTertiaryFixed
                                    "menunggu" -> AtelierColors.OnErrorContainer
                                    else -> AtelierColors.OnSecondaryContainer
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Text(
                        text = "${order.serviceType} • ${order.subNote}",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onToggleExpand,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(AtelierColors.Surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu Tindakan",
                        tint = AtelierColors.OnSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Client & Garment Info
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    AtelierNetworkImage(
                        imageUrl = order.imageUrl,
                        contentDescription = order.garmentTitle,
                        modifier = Modifier
                            .width(48.dp)
                            .height(58.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = order.clientName,
                                style = MaterialTheme.typography.titleMedium,
                                color = AtelierColors.OnSurface,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "WhatsApp",
                                tint = AtelierColors.OnTertiaryContainer,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable { onUpdateWaClick() }
                            )
                        }
                        Text(
                            text = order.garmentTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = order.clientPhone,
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant.copy(alpha = 0.75f)
                        )
                    }
                }

                // Artisan & Production Progress Strip
                if (order.statusKey == "menunggu") {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.SurfaceContainerHigh,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = AtelierColors.Error,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = order.artisanAssigned,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.Error,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = order.fittingDateNote,
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.SurfaceContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (order.statusKey == "siap") Icons.Default.Person else Icons.Default.Group,
                                    contentDescription = null,
                                    tint = AtelierColors.SurfaceTint,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (order.statusKey == "siap") {
                                        "Staff: ${order.artisanAssigned}"
                                    } else {
                                        "Artisan: ${order.artisanAssigned}"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnSurface
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (order.statusKey == "siap") Icons.Default.Storefront else Icons.Default.Event,
                                    contentDescription = null,
                                    tint = if (order.statusKey == "siap") AtelierColors.OnTertiaryContainer else AtelierColors.Secondary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = order.fittingDateNote,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (order.statusKey == "siap") AtelierColors.OnTertiaryContainer else AtelierColors.Secondary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                // Financial Summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = when (order.statusKey) {
                                "siap" -> "STATUS SEWA"
                                "menunggu" -> "NILAI PESANAN"
                                else -> "BIAYA JAHIT"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = order.priceFormatted,
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Surface(
                            shape = CircleShape,
                            color = AtelierColors.SurfaceContainerHigh
                        ) {
                            Text(
                                text = if (order.statusKey == "diproses") "DP Masuk: ${order.dpPaidFormatted}" else order.dpPaidFormatted,
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.OnSurface,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                            )
                        }
                        if (order.remainingFormatted != "Rp 0") {
                            Text(
                                text = if (order.statusKey == "diproses") "Sisa: ${order.remainingFormatted}" else order.remainingFormatted,
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.Error
                            )
                        }
                    }
                }

                // Expandable Dossier Accordion (Active Order #NC-2024-089 or any tapped order)
                AnimatedVisibility(visible = isExpanded) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AtelierColors.SurfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Straighten,
                                        contentDescription = null,
                                        tint = AtelierColors.OnTertiaryContainer,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "DOSSIER UKURAN ANATOMIS",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "Satuan: cm",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }

                            // 4 Anatomical Metric Pills
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AdminAnatomyPill("LD", order.ldCm.toString(), Modifier.weight(1f))
                                AdminAnatomyPill("LP", order.lpCm.toString(), Modifier.weight(1f))
                                AdminAnatomyPill("Pinggul", order.pinggulCm.toString(), Modifier.weight(1f))
                                AdminAnatomyPill("Panjang", order.panjangCm.toString(), Modifier.weight(1f))
                            }

                            // Artisan Wage Ledger Breakdown
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AtelierColors.SurfaceContainerLowest,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "ALOKASI UPAH ARTISAN",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.OnSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Bu Siti (Potong Pola & Pasang)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "Rp 150.000",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.OnSurface,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Mbak Ani (Bordir Kerancang Halus)",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "Rp 50.000",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.OnSurface,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Dossier CTAs
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = onUpdateWaClick,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AtelierColors.PrimaryContainer,
                                        contentColor = AtelierColors.OnPrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Chat,
                                        contentDescription = null,
                                        tint = AtelierColors.TertiaryFixedDim,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Update WA Klien", style = MaterialTheme.typography.labelMedium)
                                }

                                Button(
                                    onClick = onPrintWorksheet,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AtelierColors.SurfaceContainerHigh,
                                        contentColor = AtelierColors.OnSurface
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Print,
                                        contentDescription = null,
                                        tint = AtelierColors.OnSurfaceVariant,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Lembar Kerja", style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }
                }

                // Bottom Action Row based on status
                when (order.statusKey) {
                    "siap" -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onScanHandover,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainer,
                                    contentColor = AtelierColors.OnSurface
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCodeScanner,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Scan Handover", style = MaterialTheme.typography.labelMedium)
                            }
                            Button(
                                onClick = onRemindClient,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.PrimaryContainer,
                                    contentColor = AtelierColors.OnPrimary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ingatkan Klien", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    "menunggu" -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onRemindClient,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainer,
                                    contentColor = AtelierColors.OnSurface
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Kirim Reminder DP", style = MaterialTheme.typography.labelMedium)
                            }
                            Button(
                                onClick = onAssignArtisan,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.Primary,
                                    contentColor = AtelierColors.OnPrimary
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Tugaskan", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }

                    else -> {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .clickable { onChangeFitting() }
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EditCalendar,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Ubah Jadwal Fitting",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.Primary
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .clickable { onToggleExpand() }
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "Riwayat Log",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = AtelierColors.OnSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminAnatomyPill(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = AtelierColors.OnSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = AtelierColors.Primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NewAdminOrderDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, String, String) -> Unit
) {
    var clientName by remember { mutableStateOf("Ibu Raden Ayu Sekar") }
    var clientPhone by remember { mutableStateOf("+62 811-2345-6789") }
    var garmentTitle by remember { mutableStateOf("Kebaya Kutubaru Brokat Perancis & Batik Sogan") }
    var serviceType by remember { mutableStateOf("Jasa Jahit Bespoke") }
    var priceFormatted by remember { mutableStateOf("Rp 1.650.000") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Buat Pesanan Atelier Baru",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary
                )
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Nama Klien / Patron") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = clientPhone,
                    onValueChange = { clientPhone = it },
                    label = { Text("Nomor WhatsApp") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = garmentTitle,
                    onValueChange = { garmentTitle = it },
                    label = { Text("Rincian Busana & Kain") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = priceFormatted,
                    onValueChange = { priceFormatted = it },
                    label = { Text("Estimasi Biaya") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.SurfaceContainerLow,
                            contentColor = AtelierColors.OnSurface
                        )
                    ) {
                        Text("Batal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (clientName.isNotBlank() && garmentTitle.isNotBlank()) {
                                onSubmit(clientName, clientPhone, garmentTitle, serviceType, priceFormatted)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.PrimaryContainer,
                            contentColor = AtelierColors.OnPrimary
                        )
                    ) {
                        Text("Simpan Pesanan")
                    }
                }
            }
        }
    }
}
