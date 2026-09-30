package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.ArtisanMember
import com.example.data.WageSlip
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.theme.AtelierColors

private val artisanSkillFilters = listOf(
    "Semua Keahlian (8)",
    "Master Tailor & Pola (5)",
    "Bordir & Payet (3)"
)

@Composable
fun AdminArtisanUpahScreen(
    artisans: List<ArtisanMember>,
    wageSlips: List<WageSlip>,
    selectedSkillFilter: String,
    showNewTaskDialog: Boolean,
    payrollModalArtisan: ArtisanMember?,
    onSelectSkillFilter: (String) -> Unit,
    onSetShowNewTaskDialog: (Boolean) -> Unit,
    onSetPayrollModalArtisan: (ArtisanMember?) -> Unit,
    onSettleArtisanWage: (ArtisanMember) -> Unit,
    onCreateWageSlip: (String, ArtisanMember, String, String, Int, String) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val filteredArtisans = remember(artisans, selectedSkillFilter) {
        when {
            selectedSkillFilter.startsWith("Master") ->
                artisans.filter { it.skillCategory.contains("Master", ignoreCase = true) }
            selectedSkillFilter.startsWith("Bordir") ->
                artisans.filter { it.skillCategory.contains("Bordir", ignoreCase = true) }
            else -> artisans
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("admin_artisan_upah_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Atelier Title & Quick Actions Header Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "MANAJEMEN OPERASIONAL",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.OnTertiaryContainer
                            )
                            Text(
                                text = "Karyawan & Rekap Upah",
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Surface(
                            shape = CircleShape,
                            color = AtelierColors.SurfaceContainer,
                            shadowElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Text(
                                    text = "Okt 2024",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.OnSurface
                                )
                                Icon(
                                    imageVector = Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = AtelierColors.Outline,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    // Quick Action Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AtelierColors.PrimaryContainer,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .clickable { onSetShowNewTaskDialog(true) }
                                .testTag("btn_tugas_baru")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    tint = AtelierColors.TertiaryFixed,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Tugas Baru",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.OnPrimary
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AtelierColors.SurfaceContainerLowest,
                            shadowElevation = 1.dp,
                            modifier = Modifier.clickable {
                                onShowSnackbar("Semua 8 Maestro Artisan aktif dalam daftar gilir kerja")
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "+ Artisan",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.Primary
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = AtelierColors.SurfaceContainer,
                            shadowElevation = 1.dp,
                            modifier = Modifier
                                .clickable {
                                    val pendingArtisan = artisans.firstOrNull { it.pendingWageValue > 0 } ?: artisans.firstOrNull()
                                    if (pendingArtisan != null) {
                                        onSetPayrollModalArtisan(pendingArtisan)
                                    }
                                }
                                .testTag("btn_bayar_massal")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Payments,
                                    contentDescription = null,
                                    tint = AtelierColors.Secondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Bayar Massal",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // 2. Metric Summary: Horizontal Swipe Carousel
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Card 1: Payroll Tertunda (Priority Deep Luxury Card)
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            shadowElevation = 4.dp,
                            modifier = Modifier.width(270.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                AtelierColors.Primary,
                                                AtelierColors.PrimaryContainer,
                                                AtelierColors.DeepBurgundyDark
                                            )
                                        )
                                    )
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "PAYROLL TERTUNDA",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.TertiaryFixedDim
                                        )
                                        Text(
                                            text = "Rp 3.850.000",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = AtelierColors.SurfaceBright
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.TertiaryFixed.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccountBalanceWallet,
                                            contentDescription = null,
                                            tint = AtelierColors.TertiaryFixed,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(AtelierColors.Primary.copy(alpha = 0.45f))
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Event,
                                            contentDescription = null,
                                            tint = AtelierColors.TertiaryFixedDim,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Text(
                                            text = "Jumat, 25 Okt 2024",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.TertiaryFixedDim
                                        )
                                    }
                                    Surface(
                                        shape = CircleShape,
                                        color = AtelierColors.PrimaryContainer
                                    ) {
                                        Text(
                                            text = "Segera",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.SecondaryFixed,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Card 2: Total Nilai Jasa
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLowest,
                            shadowElevation = 2.dp,
                            modifier = Modifier.width(250.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "TOTAL NILAI JASA",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "Rp 14.250.000",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = AtelierColors.Primary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PriceChange,
                                            contentDescription = null,
                                            tint = AtelierColors.PrimaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Tercairkan",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "Rp 10.400.000",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = AtelierColors.Primary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(6.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainerHigh)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.73f)
                                                .height(6.dp)
                                                .clip(CircleShape)
                                                .background(AtelierColors.Secondary)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Card 3: Kapasitas Ahli
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLowest,
                            shadowElevation = 2.dp,
                            modifier = Modifier.width(230.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "KAPASITAS AHLI",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "8 Artisan",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = AtelierColors.Primary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Group,
                                            contentDescription = null,
                                            tint = AtelierColors.TertiaryContainer,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "5 Master • 3 Bordir",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = AtelierColors.SurfaceContainerHigh
                                    ) {
                                        Text(
                                            text = "92% Sibuk",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.Primary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Card 4: Busana Dikerjakan
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = AtelierColors.SurfaceContainerLowest,
                            shadowElevation = 2.dp,
                            modifier = Modifier.width(230.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "BUSANA DIKERJAKAN",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "64 Potong",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = AtelierColors.Primary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Checkroom,
                                            contentDescription = null,
                                            tint = AtelierColors.Secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PriorityHigh,
                                        contentDescription = null,
                                        tint = AtelierColors.Error,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "11 Tenggat Pekan Ini",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.Secondary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 3. Filter Chips: Keahlian
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(artisanSkillFilters) { chip ->
                            val isSelected = selectedSkillFilter == chip
                            Surface(
                                shape = CircleShape,
                                color = if (isSelected) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainerLowest,
                                shadowElevation = 1.dp,
                                modifier = Modifier.clickable { onSelectSkillFilter(chip) }
                            ) {
                                Text(
                                    text = chip,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) AtelierColors.OnPrimary else AtelierColors.OnSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ARTISAN & ALOKASI UPAH",
                            style = MaterialTheme.typography.labelMedium,
                            color = AtelierColors.OnSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Geser untuk riwayat",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Outline
                        )
                    }
                }
            }

            // 4. Artisan Cards List
            items(filteredArtisans, key = { it.id }) { artisan ->
                ArtisanWageCard(
                    artisan = artisan,
                    onViewTasks = {
                        onShowSnackbar("Menampilkan ${artisan.completedCount + artisan.inProgressCount} penugasan ${artisan.name}")
                    },
                    onPrintSlip = {
                        onShowSnackbar("Slip gaji bulanan ${artisan.name} siap diunduh (PDF)")
                    },
                    onDisburseClick = {
                        onSetPayrollModalArtisan(artisan)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // 5. Riwayat Tugas Terakhir & Slips Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Riwayat Tugas Terakhir",
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Primary
                            )
                            Text(
                                text = "Catatan penyelesaian busana dan alokasi slip upah",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                onShowSnackbar("Menampilkan seluruh 64 riwayat penugasan Oktober 2024")
                            }
                        ) {
                            Text(
                                text = "Lihat Semua",
                                style = MaterialTheme.typography.labelMedium,
                                color = AtelierColors.Primary
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = AtelierColors.Primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    wageSlips.forEach { slip ->
                        WageSlipRowCard(slip = slip)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(68.dp))
            }
        }

        // Sticky Mobile Action Trigger Button for Tailoring Tasks (+ Tugas Jahit)
        Surface(
            shape = CircleShape,
            color = AtelierColors.PrimaryContainer,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .clickable { onSetShowNewTaskDialog(true) }
                .testTag("fab_tambah_tugas_jahit")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DesignServices,
                    contentDescription = null,
                    tint = AtelierColors.TertiaryFixed,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "+ Tugas Jahit",
                    style = MaterialTheme.typography.labelLarge,
                    color = AtelierColors.OnPrimary
                )
            }
        }
    }

    // Modal 1: Penugasan & Upah Baru (#modal-penugasan)
    if (showNewTaskDialog && artisans.isNotEmpty()) {
        NewTailoringTaskModal(
            artisans = artisans,
            onDismiss = { onSetShowNewTaskDialog(false) },
            onSubmit = onCreateWageSlip
        )
    }

    // Modal 2: Konfirmasi Payroll Upah (#modal-pembayaran-massal)
    if (payrollModalArtisan != null) {
        PayrollConfirmationModal(
            artisan = payrollModalArtisan,
            onDismiss = { onSetPayrollModalArtisan(null) },
            onConfirmSettle = { onSettleArtisanWage(payrollModalArtisan) }
        )
    }
}

@Composable
private fun ArtisanWageCard(
    artisan: ArtisanMember,
    onViewTasks: () -> Unit,
    onPrintSlip: () -> Unit,
    onDisburseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPaidOff = artisan.pendingWageValue <= 0
    val totalCount = (artisan.completedCount + artisan.inProgressCount).coerceAtLeast(1)
    val completedFraction = artisan.completedCount.toFloat() / totalCount.toFloat()

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .testTag("artisan_card_${artisan.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box {
                        AtelierNetworkImage(
                            imageUrl = artisan.avatarUrl,
                            contentDescription = artisan.name,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .align(Alignment.BottomEnd)
                                .clip(CircleShape)
                                .background(AtelierColors.EmeraldAvailable)
                                .border(2.dp, AtelierColors.SurfaceContainerLowest, CircleShape)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = artisan.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = AtelierColors.OnSurface,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (artisan.isVerified) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = AtelierColors.TertiaryFixedDim,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                        Text(
                            text = artisan.roleTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = artisan.phone,
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Outline
                        )
                    }
                }

                if (isPaidOff) {
                    Surface(
                        shape = CircleShape,
                        color = AtelierColors.PaidGreenBg
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = null,
                                tint = AtelierColors.PaidGreenText,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Lunas",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.PaidGreenText
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = CircleShape,
                        color = AtelierColors.SurfaceContainer
                    ) {
                        Text(
                            text = "On Duty",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Workload & Progress Breakdown
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = AtelierColors.SurfaceContainerLow,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = artisan.workloadLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = "${artisan.completedCount} Selesai / ${artisan.inProgressCount} Berjalan",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(AtelierColors.SurfaceContainerHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(completedFraction)
                                .height(6.dp)
                                .background(AtelierColors.PrimaryContainer)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .background(AtelierColors.TertiaryFixedDim)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Akumulasi: ${artisan.totalWageFormatted}",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = if (isPaidOff) "Tuntas Dicairkan" else "Dicairkan: ${artisan.paidWageFormatted}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isPaidOff) AtelierColors.PaidGreenText else AtelierColors.OnSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Wage Balance & Action Trigger
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Sisa Belum Cair",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Outline
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = artisan.pendingWageFormatted,
                            style = MaterialTheme.typography.titleLarge,
                            color = if (isPaidOff) AtelierColors.OnSurfaceVariant else AtelierColors.Secondary,
                            fontWeight = FontWeight.Bold
                        )
                        if (!isPaidOff) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(AtelierColors.SecondaryContainer)
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.SurfaceContainer,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(onClick = onViewTasks)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = "Rincian Tugas",
                                tint = AtelierColors.OnSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.SurfaceContainer,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(onClick = onPrintSlip)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = "Cetak Slip",
                                tint = AtelierColors.OnSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    if (isPaidOff) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AtelierColors.SurfaceContainer
                        ) {
                            Text(
                                text = "Hak Tuntas",
                                style = MaterialTheme.typography.labelMedium,
                                color = AtelierColors.Outline,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp)
                            )
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AtelierColors.PrimaryContainer,
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .clickable(onClick = onDisburseClick)
                                .testTag("cairkan_btn_${artisan.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Cairkan",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AtelierColors.OnPrimary
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = AtelierColors.OnPrimary,
                                    modifier = Modifier.size(14.dp)
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
private fun WageSlipRowCard(slip: WageSlip) {
    val isDone = slip.workStatus.equals("Selesai", ignoreCase = true)
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AtelierColors.SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when {
                            slip.artisanId == "ani" -> Icons.Default.AutoFixHigh
                            slip.artisanId == "joko" -> Icons.Default.Checkroom
                            else -> Icons.Default.ContentCut
                        },
                        contentDescription = null,
                        tint = AtelierColors.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = slip.slipCode,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace
                            ),
                            color = AtelierColors.Outline
                        )
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(CircleShape)
                                .background(AtelierColors.OutlineVariant)
                        )
                        Text(
                            text = slip.artisanName,
                            style = MaterialTheme.typography.labelMedium,
                            color = AtelierColors.OnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = slip.taskTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = slip.wageFormatted,
                    style = MaterialTheme.typography.titleMedium,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (isDone) AtelierColors.PaidGreenBg else AtelierColors.SurfaceContainer
                ) {
                    Text(
                        text = slip.workStatus,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                        color = if (isDone) AtelierColors.PaidGreenText else AtelierColors.Secondary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun NewTailoringTaskModal(
    artisans: List<ArtisanMember>,
    onDismiss: () -> Unit,
    onSubmit: (String, ArtisanMember, String, String, Int, String) -> Unit
) {
    var selectedArtisan by remember { mutableStateOf(artisans.first()) }
    var selectedJobType by remember { mutableStateOf("Pola & Potong") }
    var orderCode by remember { mutableStateOf("#NC-2024-089") }
    var wageText by remember { mutableStateOf("350000") }
    var notes by remember {
        mutableStateOf("Perhatikan motif tumpal kain jarik harus presisi di bagian sambungan pinggul belakang.")
    }

    val jobTypes = listOf("Pola & Potong", "Jahit Utama", "Payet & Bordir", "Alterasi / Fitting")

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AtelierColors.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PostAdd,
                                contentDescription = null,
                                tint = AtelierColors.OnPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Penugasan & Upah Baru",
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Primary
                            )
                            Text(
                                text = "Pencatatan tugas spesifik per potong",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.Outline
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                    }
                }

                // Artisan Picker Pills
                Text(
                    text = "TUKANG / ARTISAN PELAKSANA",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.Outline
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(artisans) { a ->
                        val selected = a.id == selectedArtisan.id
                        Surface(
                            shape = CircleShape,
                            color = if (selected) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainerLow,
                            modifier = Modifier.clickable { selectedArtisan = a }
                        ) {
                            Text(
                                text = a.name,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) AtelierColors.OnPrimary else AtelierColors.OnSurface,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Job Type Grid
                Text(
                    text = "JENIS PEKERJAAN SPESIFIK",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.Outline
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    jobTypes.take(2).forEach { jt ->
                        val sel = selectedJobType == jt
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (sel) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainerLow,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedJobType = jt }
                        ) {
                            Text(
                                text = jt,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (sel) AtelierColors.OnPrimary else AtelierColors.OnSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    jobTypes.drop(2).forEach { jt ->
                        val sel = selectedJobType == jt
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (sel) AtelierColors.PrimaryContainer else AtelierColors.SurfaceContainerLow,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedJobType = jt }
                        ) {
                            Text(
                                text = jt,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (sel) AtelierColors.OnPrimary else AtelierColors.OnSurface,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = orderCode,
                        onValueChange = { orderCode = it },
                        label = { Text("Kode Pesanan") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = wageText,
                        onValueChange = { wageText = it.filter { c -> c.isDigit() } },
                        label = { Text("Upah (Rp)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Instruksi Khusus & Catatan Artisan") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Button(
                    onClick = {
                        val wageVal = wageText.toIntOrNull() ?: 350000
                        val formatted = "Rp ${String.format(java.util.Locale.US, "%,d", wageVal).replace(',', '.')}"
                        onSubmit(orderCode, selectedArtisan, selectedJobType, formatted, wageVal, notes)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AtelierColors.PrimaryContainer,
                        contentColor = AtelierColors.OnPrimary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("submit_new_task_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Simpan & Terbitkan Tugas", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
private fun PayrollConfirmationModal(
    artisan: ArtisanMember,
    onDismiss: () -> Unit,
    onConfirmSettle: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AtelierColors.TertiaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryFixed,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Konfirmasi Payroll Upah",
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Primary
                            )
                            Text(
                                text = "Pelunasan upah karya melalui Rekening Butik",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.Outline
                            )
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AtelierColors.SurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Penerima Dana",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                            Text(
                                text = artisan.name,
                                style = MaterialTheme.typography.titleSmall,
                                color = AtelierColors.Primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Bank Tujuan",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                            Text(
                                text = artisan.bankInfo,
                                style = MaterialTheme.typography.titleSmall,
                                color = AtelierColors.OnSurface
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Nominal Dicairkan",
                                style = MaterialTheme.typography.titleSmall,
                                color = AtelierColors.OnSurface
                            )
                            Text(
                                text = artisan.pendingWageFormatted,
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

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
                        onClick = onConfirmSettle,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.Primary,
                            contentColor = AtelierColors.OnPrimary
                        ),
                        modifier = Modifier.testTag("confirm_payroll_settle_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Konfirmasi & Lunaskan Upah")
                    }
                }
            }
        }
    }
}
