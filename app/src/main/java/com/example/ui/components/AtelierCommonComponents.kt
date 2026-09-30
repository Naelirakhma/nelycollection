package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.AtelierAssets
import com.example.data.AtelierOrder
import com.example.data.KebayaCatalogItem
import com.example.ui.AdminTab
import com.example.ui.PatronTab
import com.example.ui.theme.AtelierColors
import com.example.ui.theme.PlayfairDisplayFontFamily

@Composable
fun AtelierNetworkImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                colors = listOf(
                    AtelierColors.PrimaryContainer,
                    AtelierColors.Primary,
                    AtelierColors.DeepBurgundyDark
                )
            )
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            error = painterResource(id = R.drawable.img_app_icon),
            placeholder = painterResource(id = R.drawable.img_app_icon),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AtelierTopHeader(
    isAdminMode: Boolean,
    patronTab: PatronTab,
    adminTab: AdminTab,
    onToggleAdminMode: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = AtelierColors.Surface.copy(alpha = 0.94f),
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(64.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Brand / Title section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (!isAdminMode && patronTab == PatronTab.JAHIT) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("header_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Kembali",
                            tint = AtelierColors.Primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Circular Gold & Burgundy Monogram Emblem
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(AtelierColors.Primary)
                        .border(1.5.dp, AtelierColors.TertiaryFixedDim, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N",
                        fontFamily = PlayfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = AtelierColors.TertiaryFixedDim
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                if (isAdminMode) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "NELY ATELIER",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.PrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(AtelierColors.TertiaryFixedDim)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Kebayoran Baru",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.OnSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        val adminTitle = when (adminTab) {
                            AdminTab.PESANAN -> "Pesanan"
                            AdminTab.ARTISAN_UPAH -> "Artisan Dan Upah"
                            AdminTab.KATALOG -> "Katalog Busana"
                            AdminTab.PENGATURAN -> "Butik & Sistem"
                        }
                        Text(
                            text = adminTitle,
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.OnSurface,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else if (patronTab == PatronTab.JAHIT) {
                    Text(
                        text = "Bespoke Measurement Dossier",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 17.sp),
                        color = AtelierColors.Primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else if (patronTab == PatronTab.AKUN) {
                    Column {
                        Text(
                            text = "Nely Collection",
                            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 17.sp),
                            color = AtelierColors.PrimaryContainer,
                            maxLines = 1
                        )
                        Text(
                            text = "HAUTE COUTURE",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.TertiaryContainer,
                            letterSpacing = 1.8.sp
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = "NELY",
                            fontFamily = PlayfairDisplayFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 2.5.sp,
                            color = AtelierColors.Primary
                        )
                        Text(
                            text = "ATELIER",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = AtelierColors.OnTertiaryContainer,
                            letterSpacing = 2.sp
                        )
                    }
                }
            }

            // Right Actions: Role Mode Switcher Pill + Search + Notifications + Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Role Switcher Pill (Klien vs Admin) so all HTML screens are 1-tap accessible
                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainerHigh,
                    modifier = Modifier
                        .height(30.dp)
                        .border(0.8.dp, AtelierColors.TertiaryFixedDim.copy(alpha = 0.6f), CircleShape)
                        .testTag("mode_switcher_pill")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (!isAdminMode) AtelierColors.PrimaryContainer else Color.Transparent
                                )
                                .clickable { onToggleAdminMode(false) }
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                .testTag("switch_mode_klien")
                        ) {
                            Text(
                                text = "Klien",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = if (!isAdminMode) AtelierColors.OnPrimary else AtelierColors.OnSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (isAdminMode) AtelierColors.PrimaryContainer else Color.Transparent
                                )
                                .clickable { onToggleAdminMode(true) }
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                                .testTag("switch_mode_admin")
                        ) {
                            Text(
                                text = "Admin",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = if (isAdminMode) AtelierColors.TertiaryFixed else AtelierColors.OnSurfaceVariant
                            )
                        }
                    }
                }

                if (!isAdminMode) {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("header_search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Pencarian",
                            tint = AtelierColors.PrimaryContainer,
                            modifier = Modifier.size(21.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("header_notification_button")
                ) {
                    Box {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Pemberitahuan",
                            tint = AtelierColors.PrimaryContainer,
                            modifier = Modifier.size(21.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape)
                                .background(
                                    if (isAdminMode) AtelierColors.SecondaryContainer else AtelierColors.TertiaryFixedDim
                                )
                                .border(1.5.dp, AtelierColors.Surface, CircleShape)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, AtelierColors.TertiaryFixedDim, CircleShape)
                        .clickable { onProfileClick() }
                        .testTag("header_profile_avatar")
                ) {
                    AtelierNetworkImage(
                        imageUrl = if (isAdminMode) AtelierAssets.ADMIN_AVATAR_URL else AtelierAssets.PROFILE_AVATAR_URL,
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun AtelierBottomNavBar(
    isAdminMode: Boolean,
    patronTab: PatronTab,
    adminTab: AdminTab,
    onSelectPatronTab: (PatronTab) -> Unit,
    onSelectAdminTab: (AdminTab) -> Unit
) {
    Surface(
        color = AtelierColors.Surface.copy(alpha = 0.96f),
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!isAdminMode) {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(72.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PatronNavItem(
                    label = "BERANDA",
                    icon = Icons.Default.Home,
                    selected = patronTab == PatronTab.BERANDA,
                    tag = "nav_beranda",
                    onClick = { onSelectPatronTab(PatronTab.BERANDA) }
                )
                PatronNavItem(
                    label = "JAHIT",
                    icon = Icons.Default.ContentCut,
                    selected = patronTab == PatronTab.JAHIT,
                    tag = "nav_jahit",
                    onClick = { onSelectPatronTab(PatronTab.JAHIT) }
                )
                PatronNavItem(
                    label = "SEWA",
                    icon = Icons.Default.Checkroom,
                    selected = patronTab == PatronTab.SEWA,
                    tag = "nav_sewa",
                    onClick = { onSelectPatronTab(PatronTab.SEWA) }
                )
                PatronNavItem(
                    label = "PESANAN",
                    icon = Icons.Default.Inventory2,
                    selected = patronTab == PatronTab.PESANAN,
                    tag = "nav_pesanan",
                    onClick = { onSelectPatronTab(PatronTab.PESANAN) }
                )
                PatronNavItem(
                    label = "AKUN",
                    icon = if (patronTab == PatronTab.AKUN) Icons.Default.Spa else Icons.Default.PersonOutline,
                    selected = patronTab == PatronTab.AKUN,
                    tag = "nav_akun",
                    onClick = { onSelectPatronTab(PatronTab.AKUN) }
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .height(68.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AdminNavItem(
                    label = "Pesanan",
                    icon = Icons.AutoMirrored.Outlined.ReceiptLong,
                    selected = adminTab == AdminTab.PESANAN,
                    tag = "nav_admin_pesanan",
                    onClick = { onSelectAdminTab(AdminTab.PESANAN) }
                )
                AdminNavItem(
                    label = "Artisan & Upah",
                    icon = Icons.Default.ContentCut,
                    selected = adminTab == AdminTab.ARTISAN_UPAH,
                    tag = "nav_admin_artisan",
                    onClick = { onSelectAdminTab(AdminTab.ARTISAN_UPAH) }
                )
                AdminNavItem(
                    label = "Katalog",
                    icon = Icons.Default.Checkroom,
                    selected = adminTab == AdminTab.KATALOG,
                    tag = "nav_admin_katalog",
                    onClick = { onSelectAdminTab(AdminTab.KATALOG) }
                )
                AdminNavItem(
                    label = "Pengaturan",
                    icon = Icons.Default.Settings,
                    selected = adminTab == AdminTab.PENGATURAN,
                    tag = "nav_admin_pengaturan",
                    onClick = { onSelectAdminTab(AdminTab.PENGATURAN) }
                )
            }
        }
    }
}

@Composable
private fun PatronNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    tag: String,
    onClick: () -> Unit
) {
    val color = if (selected) AtelierColors.PrimaryContainer else AtelierColors.OnSurfaceVariant
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag(tag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(23.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp
            ),
            color = color
        )
    }
}

@Composable
private fun AdminNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    tag: String,
    onClick: () -> Unit
) {
    val color = if (selected) AtelierColors.PrimaryContainer else AtelierColors.OnSurfaceVariant
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag(tag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(21.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp,
                letterSpacing = 0.4.sp
            ),
            color = color
        )
    }
}

@Composable
fun BarcodeVoucherModal(
    order: AtelierOrder,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VOUCHER PENGAMBILAN BUSANA",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnTertiaryContainer
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = AtelierColors.OnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = order.orderCode,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tunjukkan kode batang ini kepada staf butik Nely Collection Kebayoran.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Canvas Barcode matching the SVG in HTML
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AtelierColors.Surface)
                        .border(1.dp, AtelierColors.OutlineVariant.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val barWidths = remember {
                        listOf(3f, 2f, 5f, 2f, 4f, 1.5f, 6f, 2f, 4f, 2f, 5f, 2f, 3f, 6f, 2f, 3f, 5f, 2f, 4f, 2f, 5f, 2f, 4f, 2f, 6f, 3f, 2f, 5f, 2f, 4f, 1.5f, 5f, 2f, 4f, 3f)
                    }
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        val totalUnits = barWidths.sum() + (barWidths.size * 2.2f)
                        val unitPx = size.width / totalUnits
                        var currentX = 0f
                        barWidths.forEach { w ->
                            val barW = w * unitPx
                            drawRect(
                                color = AtelierColors.Primary,
                                topLeft = Offset(currentX, 0f),
                                size = Size(barW, size.height)
                            )
                            currentX += barW + (2.2f * unitPx)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = order.barcodeNumber,
                        style = MaterialTheme.typography.labelMedium.copy(
                            letterSpacing = 4.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = AtelierColors.Primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AtelierColors.SurfaceContainerLow)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Penyewa:",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = "Ibu Ratna Prawira",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Jaminan:",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = "KTP Asli + Deposit Rp 200.000",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AtelierColors.PrimaryContainer,
                        contentColor = AtelierColors.OnPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("close_barcode_modal_button")
                ) {
                    Text(
                        text = "TUTUP VOUCHER",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun CatalogDetailModal(
    item: KebayaCatalogItem,
    onDismiss: () -> Unit,
    onRentNow: (KebayaCatalogItem) -> Unit,
    onCustomizeBespoke: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    AtelierNetworkImage(
                        imageUrl = item.imageUrl,
                        contentDescription = item.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(AtelierColors.SurfaceContainerLowest.copy(alpha = 0.85f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = AtelierColors.Primary
                        )
                    }
                    Surface(
                        color = AtelierColors.Primary.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = item.badgeTag,
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = AtelierColors.Primary
                    )
                    Text(
                        text = "${item.colorPalette} • ${item.sizeInfo}",
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AtelierColors.OnSurface
                    )
                    HorizontalDivider(color = AtelierColors.OutlineVariant.copy(alpha = 0.4f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "TARIF SEWA 3 HARI",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                            Text(
                                text = item.priceFormatted,
                                style = MaterialTheme.typography.titleLarge,
                                color = AtelierColors.Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = if (item.isAvailable) "Tersedia di Salon" else "Daftar Tunggu",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isAvailable) AtelierColors.EmeraldAvailable else AtelierColors.Secondary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                                onCustomizeBespoke()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.SurfaceContainerHigh,
                                contentColor = AtelierColors.Primary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Ukur Dossier", style = MaterialTheme.typography.labelMedium)
                        }
                        Button(
                            onClick = { onRentNow(item) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.PrimaryContainer,
                                contentColor = AtelierColors.OnPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (item.isAvailable) "Sewa Sekarang" else "Daftar Tunggu",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderReviewModal(
    order: AtelierOrder,
    onDismiss: () -> Unit,
    onSubmitReview: (String) -> Unit
) {
    var reviewText by remember {
        mutableStateOf(
            order.userReview.ifBlank {
                "Jahitan stik halus sangat presisi dan nyaman dikenakan seharian saat acara."
            }
        )
    }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Ulasan Mahakarya Atelier",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary
                )
                Text(
                    text = "${order.orderCode} • ${order.garmentTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Kesan & Presisi Busana") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
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
                        onClick = { onSubmitReview(reviewText) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.PrimaryContainer,
                            contentColor = AtelierColors.OnPrimary
                        )
                    ) {
                        Text("Kirim Ulasan")
                    }
                }
            }
        }
    }
}

fun openExternalUrl(context: android.content.Context, url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
