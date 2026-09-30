package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.AtelierAssets
import com.example.data.MeasurementDossier
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.theme.AtelierColors

private val availableDates = listOf(
    "Sabtu, 28 Okt 2024",
    "Minggu, 29 Okt 2024",
    "Selasa, 31 Okt 2024",
    "Kamis, 2 Nov 2024"
)

private val availableTimes = listOf(
    "14:00 - 15:30 WIB",
    "10:30 - 12:00 WIB",
    "16:00 - 17:30 WIB"
)

@Composable
fun BespokeDossierScreen(
    dossier: MeasurementDossier,
    currentStep: Int,
    showVideoCallDialog: Boolean,
    onStepChange: (Int) -> Unit,
    onUpdateDossier: ((MeasurementDossier) -> MeasurementDossier) -> Unit,
    onConfirmOrder: () -> Unit,
    onSetShowVideoCallDialog: (Boolean) -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    var accordion1Expanded by remember { mutableStateOf(true) }
    var accordion2Expanded by remember { mutableStateOf(false) }

    val filledFieldsCount = remember(dossier) {
        listOf(
            dossier.lingkarDada,
            dossier.lingkarPinggang,
            dossier.lingkarPinggul,
            dossier.lebarBahu,
            dossier.panjangLengan,
            dossier.panjangKebaya,
            dossier.panjangRok,
            dossier.tinggiBadan
        ).count { it.isNotBlank() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("jahit_dossier_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 130.dp)
        ) {
            // 1. Stepper Header Section
            item {
                Surface(
                    color = AtelierColors.SurfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BespokeStepperNode(
                            stepNumber = 1,
                            label = "LAYANAN",
                            currentStep = currentStep,
                            onClick = { onStepChange(1) }
                        )
                        StepperConnector(isCompleted = currentStep > 1)
                        BespokeStepperNode(
                            stepNumber = 2,
                            label = "DATA DIRI",
                            currentStep = currentStep,
                            onClick = { onStepChange(2) }
                        )
                        StepperConnector(isCompleted = currentStep > 2)
                        BespokeStepperNode(
                            stepNumber = 3,
                            label = "BUSANA",
                            currentStep = currentStep,
                            onClick = { onStepChange(3) }
                        )
                        StepperConnector(isCompleted = currentStep > 3)
                        BespokeStepperNode(
                            stepNumber = 4,
                            label = "UKURAN",
                            currentStep = currentStep,
                            onClick = { onStepChange(4) }
                        )
                        StepperConnector(isCompleted = currentStep > 4)
                        BespokeStepperNode(
                            stepNumber = 5,
                            label = "KONFIRMASI",
                            currentStep = currentStep,
                            onClick = { onStepChange(5) }
                        )
                    }
                }
            }

            // Optional Step 5 Confirmation Summary Card when user advances to Step 5
            if (currentStep == 5) {
                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = AtelierColors.PrimaryContainer,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "LANGKAH 5 • RANGKUMAN KONFIRMASI BESPOKE",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.TertiaryFixedDim
                            )
                            Text(
                                text = dossier.selectedService,
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.OnPrimary
                            )
                            Text(
                                text = "Klien: ${dossier.clientName} • ${dossier.fabricChoice}",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.InverseOnSurface
                            )
                            Text(
                                text = if (dossier.method == "boutique") {
                                    "Metode: Fitting Langsung di Butik (${dossier.scheduleDate}, ${dossier.scheduleTime})"
                                } else {
                                    "Metode: Panduan Digital Mandiri (LD ${dossier.lingkarDada} / LP ${dossier.lingkarPinggang} / Pinggul ${dossier.lingkarPinggul} cm)"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.TertiaryFixed
                            )
                            Button(
                                onClick = onConfirmOrder,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.TertiaryFixed,
                                    contentColor = AtelierColors.Primary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp)
                                    .testTag("confirm_bespoke_order_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Konfirmasi & Buat Pesanan Bespoke",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Title Introduction
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "BESPOKE ATELIER",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer
                        )
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height(1.dp)
                                .background(AtelierColors.TertiaryFixedDim)
                        )
                    }
                    Text(
                        text = "Metode & Presisi Ukuran",
                        style = MaterialTheme.typography.headlineLarge,
                        color = AtelierColors.Primary
                    )
                    Text(
                        text = "Koleksi adibusana Nely dirancang mengikuti lekuk personal. Pilih kenyamanan Anda: konsultasi tatap muka atau pencatatan mandiri terbimbing.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }
            }

            // Section 1: Measurement Method Selector
            item {
                val isBoutique = dossier.method == "boutique"
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Option A: Datang ke Butik (Fitting Langsung)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isBoutique) AtelierColors.SurfaceContainerLowest else AtelierColors.SurfaceContainerLow,
                        shadowElevation = if (isBoutique) 3.dp else 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = if (isBoutique) 1.dp else 0.dp,
                                color = if (isBoutique) AtelierColors.TertiaryFixedDim.copy(alpha = 0.6f) else Color.Transparent,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { onUpdateDossier { it.copy(method = "boutique") } }
                            .testTag("method_boutique_card")
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
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Storefront,
                                            contentDescription = null,
                                            tint = AtelierColors.Primary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "OPSI PREMIUM",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnTertiaryContainer
                                        )
                                        Text(
                                            text = "Datang ke Butik (Fitting Langsung)",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = AtelierColors.Primary,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                                // Custom Concentric Radio
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isBoutique) AtelierColors.Primary else AtelierColors.OutlineVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (isBoutique) AtelierColors.TertiaryFixedDim else AtelierColors.Surface
                                            )
                                    )
                                }
                            }

                            Text(
                                text = "Diukur langsung oleh Master Tailor Nely Collection dengan fitting privat, eksplorasi jatuh siluet kain sutra, dan konsultasi proporsi kebaya.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AtelierColors.SurfaceContainerLow)
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PinDrop,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Jl. Tirtayasa No. 18, Kebayoran Baru, Jakarta Selatan",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            AnimatedVisibility(visible = isBoutique) {
                                Column(
                                    modifier = Modifier.padding(top = 4.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Reservasi Jadwal Master Tailor",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = AtelierColors.Primary
                                        )
                                        Text(
                                            text = "Slot Terbatas",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnTertiaryContainer
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        // Date selector box (cycles on tap)
                                        Row(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(AtelierColors.SurfaceContainerLow)
                                                .clickable {
                                                    val nextIdx = (availableDates.indexOf(dossier.scheduleDate) + 1) % availableDates.size
                                                    onUpdateDossier { it.copy(scheduleDate = availableDates[nextIdx]) }
                                                }
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.CalendarToday,
                                                contentDescription = null,
                                                tint = AtelierColors.Primary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = "Hari / Tanggal",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                                    color = AtelierColors.OnSurfaceVariant
                                                )
                                                Text(
                                                    text = dossier.scheduleDate,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = AtelierColors.Primary,
                                                    fontWeight = FontWeight.Medium,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }

                                        // Time selector box (cycles on tap)
                                        Row(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(AtelierColors.SurfaceContainerLow)
                                                .clickable {
                                                    val nextIdx = (availableTimes.indexOf(dossier.scheduleTime) + 1) % availableTimes.size
                                                    onUpdateDossier { it.copy(scheduleTime = availableTimes[nextIdx]) }
                                                }
                                                .padding(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Schedule,
                                                contentDescription = null,
                                                tint = AtelierColors.Primary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Column {
                                                Text(
                                                    text = "Waktu Salon",
                                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                                                    color = AtelierColors.OnSurfaceVariant
                                                )
                                                Text(
                                                    text = dossier.scheduleTime,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = AtelierColors.Primary,
                                                    fontWeight = FontWeight.Medium,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Option B: Ukur Sendiri (Panduan Digital)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (!isBoutique) AtelierColors.SurfaceContainerLowest else AtelierColors.SurfaceContainerLow,
                        shadowElevation = if (!isBoutique) 3.dp else 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = if (!isBoutique) 1.dp else 0.dp,
                                color = if (!isBoutique) AtelierColors.TertiaryFixedDim.copy(alpha = 0.6f) else Color.Transparent,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable { onUpdateDossier { it.copy(method = "self") } }
                            .testTag("method_self_card")
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(AtelierColors.SurfaceContainerHigh),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Straighten,
                                            contentDescription = null,
                                            tint = AtelierColors.Primary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                    Column {
                                        Text(
                                            text = "MANDIRI & FLEKSIBEL",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.OnSurfaceVariant
                                        )
                                        Text(
                                            text = "Ukur Sendiri (Panduan Digital)",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = AtelierColors.Primary,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (!isBoutique) AtelierColors.Primary else AtelierColors.OutlineVariant
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (!isBoutique) AtelierColors.TertiaryFixedDim else AtelierColors.Surface
                                            )
                                    )
                                }
                            }
                            Text(
                                text = "Ikuti petunjuk titik ukur centimeter (cm) secara mandiri. Tim kurasi pola kami akan mengulas proporsi agar hasil jatuh seimbang dan anggun.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Section 2: Formulir Ukuran Mandiri (Dossier Titik Ukuran 2x4 Grid)
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Dossier Titik Ukuran",
                                style = MaterialTheme.typography.headlineSmall,
                                color = AtelierColors.Primary
                            )
                            Text(
                                text = "Standar metrik sentimeter (cm) dengan toleransi drape jahitan.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(AtelierColors.SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Architecture,
                                contentDescription = null,
                                tint = AtelierColors.Primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Row 1: 1. Lingkar Dada & 2. Lingkar Pinggang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MeasurementSpecCell(
                            label = "1. Lingkar Dada",
                            subtitle = "Titik paling menonjol",
                            value = dossier.lingkarDada,
                            onValueChange = { v -> onUpdateDossier { it.copy(lingkarDada = v) } },
                            testTag = "input_lingkar_dada",
                            modifier = Modifier.weight(1f)
                        )
                        MeasurementSpecCell(
                            label = "2. Lingkar Pinggang",
                            subtitle = "Lekukan pinggang terkecil",
                            value = dossier.lingkarPinggang,
                            onValueChange = { v -> onUpdateDossier { it.copy(lingkarPinggang = v) } },
                            testTag = "input_lingkar_pinggang",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 2: 3. Lingkar Pinggul & 4. Lebar Bahu
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MeasurementSpecCell(
                            label = "3. Lingkar Pinggul",
                            subtitle = "Lekukan pinggul terbesar",
                            value = dossier.lingkarPinggul,
                            onValueChange = { v -> onUpdateDossier { it.copy(lingkarPinggul = v) } },
                            testTag = "input_lingkar_pinggul",
                            modifier = Modifier.weight(1f)
                        )
                        MeasurementSpecCell(
                            label = "4. Lebar Bahu",
                            subtitle = "Tulang bahu kiri ke kanan",
                            value = dossier.lebarBahu,
                            onValueChange = { v -> onUpdateDossier { it.copy(lebarBahu = v) } },
                            testTag = "input_lebar_bahu",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 3: 5. Panjang Lengan & 6. Panjang Kebaya
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MeasurementSpecCell(
                            label = "5. Panjang Lengan",
                            subtitle = "Pangkal bahu ke pergelangan",
                            value = dossier.panjangLengan,
                            onValueChange = { v -> onUpdateDossier { it.copy(panjangLengan = v) } },
                            testTag = "input_panjang_lengan",
                            modifier = Modifier.weight(1f)
                        )
                        MeasurementSpecCell(
                            label = "6. Panjang Kebaya",
                            subtitle = "Pangkal leher ke keliman",
                            value = dossier.panjangKebaya,
                            onValueChange = { v -> onUpdateDossier { it.copy(panjangKebaya = v) } },
                            testTag = "input_panjang_kebaya",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Row 4: 7. Panjang Rok/Jarik & 8. Tinggi Badan
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MeasurementSpecCell(
                            label = "7. Panjang Rok/Jarik",
                            subtitle = "Pinggang hingga mata kaki",
                            value = dossier.panjangRok,
                            onValueChange = { v -> onUpdateDossier { it.copy(panjangRok = v) } },
                            testTag = "input_panjang_rok",
                            modifier = Modifier.weight(1f)
                        )
                        MeasurementSpecCell(
                            label = "8. Tinggi Badan",
                            subtitle = "Posisi berdiri tegak wajar",
                            value = dossier.tinggiBadan,
                            onValueChange = { v -> onUpdateDossier { it.copy(tinggiBadan = v) } },
                            testTag = "input_tinggi_badan",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Section 3: Panduan Visual Anatomi & Accordions
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Panduan Visual Anatomi",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                        Text(
                            text = "ATELIER METHOD",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer
                        )
                    }

                    // Atelier Tailoring Photo Visual Card
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(195.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AtelierNetworkImage(
                                imageUrl = AtelierAssets.DOSSIER_GUIDE_URL,
                                contentDescription = "Panduan Visual Anatomi",
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                AtelierColors.Primary.copy(alpha = 0.4f),
                                                AtelierColors.Primary.copy(alpha = 0.92f)
                                            )
                                        )
                                    )
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = AtelierColors.TertiaryFixedDim,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "ETIKET PENGUKURAN PATEN",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.TertiaryFixed
                                    )
                                }
                                Text(
                                    text = "Gunakan pakaian pas badan atau undergarment/korset resmi yang akan Anda kenakan saat perhelatan. Biarkan pita ukur melingkar tanpa ditarik terlalu kencang.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnPrimary
                                )
                            }
                        }
                    }

                    // Accordion Item 1
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AtelierColors.SurfaceContainerLowest,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { accordion1Expanded = !accordion1Expanded }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Gesture,
                                        contentDescription = null,
                                        tint = AtelierColors.Primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Lekuk Pinggang & Pinggul Alami",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = AtelierColors.Primary
                                    )
                                }
                                Icon(
                                    imageVector = if (accordion1Expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary
                                )
                            }
                            AnimatedVisibility(visible = accordion1Expanded) {
                                Column(
                                    modifier = Modifier.padding(top = 10.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Letakkan pita ukur 2-3 cm di atas pusar pada lekuk tersempit torso. Untuk kebaya kutubaru maupun kartini, kelonggaran 1 jari telunjuk direkomendasikan untuk kenyamanan napas saat duduk.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = AtelierColors.Primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Rekomendasi kenyamanan drape kain: +1.5 cm",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AtelierColors.Primary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Accordion Item 2
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = AtelierColors.SurfaceContainerLowest,
                        shadowElevation = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { accordion2Expanded = !accordion2Expanded }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Straighten,
                                        contentDescription = null,
                                        tint = AtelierColors.Primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "Panjang Keliman Kebaya & Jarik",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = AtelierColors.Primary
                                    )
                                }
                                Icon(
                                    imageVector = if (accordion2Expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary
                                )
                            }
                            AnimatedVisibility(visible = accordion2Expanded) {
                                Text(
                                    text = "Jika berencana mengenakan sepatu berhak (heels), tambahkan estimasi tinggi hak sepatu pada panjang total rok jarik agar proporsi jatuh menjuntai anggun menyentuh lantai dengan rapi.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnSurfaceVariant,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                        }
                    }

                    // Concierge Assistance Banner
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = AtelierColors.SurfaceContainerHigh,
                        shadowElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
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
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(AtelierColors.PrimaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Videocam,
                                        contentDescription = null,
                                        tint = AtelierColors.OnPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Butuh Asistensi Langsung?",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = AtelierColors.Primary
                                    )
                                    Text(
                                        text = "Video call privat 15 menit bersama Asisten Master Tailor",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AtelierColors.SurfaceContainerLowest,
                                shadowElevation = 2.dp,
                                modifier = Modifier
                                    .clickable { onSetShowVideoCallDialog(true) }
                                    .testTag("jadwalkan_videocall_btn")
                            ) {
                                Text(
                                    text = "Jadwalkan",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.Primary,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Navigation Action Bar
        Surface(
            color = AtelierColors.Surface.copy(alpha = 0.96f),
            shadowElevation = 12.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = AtelierColors.OnTertiaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Garansi 1x Free Resizing Pasca Fitting",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                    }
                    Text(
                        text = "$filledFieldsCount/8 Terisi Lengkap",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onStepChange(3) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.SurfaceContainerHigh,
                            contentColor = AtelierColors.Primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Detail Busana", style = MaterialTheme.typography.labelLarge)
                    }

                    Button(
                        onClick = {
                            if (currentStep < 5) {
                                onStepChange(5)
                                onShowSnackbar("Menuju Langkah 5: Konfirmasi Pesanan & Rangkuman Bespoke")
                            } else {
                                onConfirmOrder()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.PrimaryContainer,
                            contentColor = AtelierColors.OnPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1.5f)
                            .height(48.dp)
                            .testTag("next_step_konfirmasi_btn")
                    ) {
                        Text(
                            text = if (currentStep < 5) "Ke Konfirmasi (Step 5)" else "Simpan & Buat Pesanan",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = AtelierColors.TertiaryFixedDim,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    if (showVideoCallDialog) {
        Dialog(onDismissRequest = { onSetShowVideoCallDialog(false) }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AtelierColors.SurfaceContainerLowest,
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Reservasi Video Call Privat",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AtelierColors.Primary
                    )
                    Text(
                        text = "Asisten Master Tailor Bu Siti Nurjanah akan memandu pengukuran anatomis Anda selama 15 menit.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                    HorizontalDivider(color = AtelierColors.OutlineVariant.copy(alpha = 0.4f))
                    Text(
                        text = "Jadwal Tersedia: Besok • 11:00 WIB (WhatsApp Video)",
                        style = MaterialTheme.typography.titleSmall,
                        color = AtelierColors.Primary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                onSetShowVideoCallDialog(false)
                                onShowSnackbar("Sesi Video Call Privat 15 menit berhasil dijadwalkan!")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.PrimaryContainer,
                                contentColor = AtelierColors.OnPrimary
                            )
                        ) {
                            Text("Konfirmasi Jadwal")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BespokeStepperNode(
    stepNumber: Int,
    label: String,
    currentStep: Int,
    onClick: () -> Unit
) {
    val isDone = stepNumber < currentStep
    val isActive = stepNumber == currentStep

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 2.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isDone -> AtelierColors.Primary
                            isActive -> AtelierColors.PrimaryContainer
                            else -> AtelierColors.SurfaceContainerHigh
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = AtelierColors.OnPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(
                        text = stepNumber.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isActive) AtelierColors.OnPrimary else AtelierColors.OnSurfaceVariant
                    )
                }
            }
            if (isActive) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(AtelierColors.TertiaryFixedDim)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
            ),
            color = when {
                isActive -> AtelierColors.PrimaryContainer
                isDone -> AtelierColors.Primary
                else -> AtelierColors.OnSurfaceVariant.copy(alpha = 0.6f)
            },
            maxLines = 1
        )
    }
}

@Composable
private fun StepperConnector(isCompleted: Boolean) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .width(16.dp)
            .height(2.dp)
            .background(if (isCompleted) AtelierColors.Primary else AtelierColors.OutlineVariant)
    )
}

@Composable
private fun MeasurementSpecCell(
    label: String,
    subtitle: String,
    value: String,
    onValueChange: (String) -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = AtelierColors.Primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = AtelierColors.TertiaryFixedDim,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = AtelierColors.OnSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(AtelierColors.SurfaceContainerLow)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { newVal ->
                        if (newVal.length <= 4 && newVal.all { it.isDigit() }) {
                            onValueChange(newVal)
                        }
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag(testTag)
                )
                Text(
                    text = "CM",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.OnSurfaceVariant
                )
            }
        }
    }
}
