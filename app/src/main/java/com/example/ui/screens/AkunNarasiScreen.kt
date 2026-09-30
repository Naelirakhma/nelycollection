package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AtelierAssets
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.AtelierColors

@Composable
fun AkunNarasiScreen(
    onSwitchToAdminMode: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("akun_narasi_screen"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // SECTION 1: EDITORIAL HEADER & INTRO
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AtelierColors.SurfaceContainerHigh,
                        shadowElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.HistoryEdu,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryContainer,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "WARISAN ADIBUSANA NUSANTARA",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.Primary
                            )
                        }
                    }
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Outline
                    )
                    Text(
                        text = "Sejak 2008",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }

                Text(
                    text = "Dedikasi, Kemahiran, & Narasi Nely Collection",
                    style = MaterialTheme.typography.headlineLarge,
                    color = AtelierColors.Primary
                )
                Text(
                    text = "Menghidupkan keanggunan siluet tradisi Jawa ke panggung haute couture modern dengan ketelitian stik tangan dan presisi personal.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AtelierColors.OnSurfaceVariant
                )

                // Quick Admin Atelier Portal Banner for Ibu Nely
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AtelierColors.SurfaceContainerLow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .clickable { onSwitchToAdminMode() }
                        .testTag("akun_switch_admin_banner")
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AtelierColors.PrimaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = AtelierColors.TertiaryFixed,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Konsol Operasional Admin (Ibu Nely)",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = AtelierColors.Primary
                                )
                                Text(
                                    text = "Buka Manajemen Pesanan, Alur Produksi & Payroll Upah Artisan",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = AtelierColors.Primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Chalk Accent Line
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    AtelierColors.PrimaryContainer.copy(alpha = 0.2f),
                                    AtelierColors.TertiaryFixedDim,
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
        }

        // SECTION 2: HERO STORY & QUOTE
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AtelierNetworkImage(
                            imageUrl = AtelierAssets.NARASI_QUOTE_URL,
                            contentDescription = "Atelier Nely Collection",
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            AtelierColors.Primary.copy(alpha = 0.45f),
                                            AtelierColors.Primary
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    tint = AtelierColors.TertiaryFixed,
                                    modifier = Modifier.size(28.dp)
                                )
                                Text(
                                    text = "“Busana bukan sekadar sandang, melainkan helai doa, tata krama, dan ekspresi martabat pemakainya.”",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontStyle = FontStyle.Italic
                                    ),
                                    color = AtelierColors.InverseOnSurface
                                )
                            }
                            Text(
                                text = "— IBU NELY, PENDIRI & MAESTRO DESAINER",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.TertiaryFixed,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // SECTION 3: LINIMASA PERJALANAN KAMI
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "KRONIK SARTORIAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Secondary
                        )
                        Text(
                            text = "Linimasa Perjalanan Kami",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = null,
                        tint = AtelierColors.TertiaryContainer,
                        modifier = Modifier.size(26.dp)
                    )
                }

                TimelineMilestoneCard(
                    year = "2008",
                    badge = "Solo, Jawa Tengah",
                    isGoldenBadge = false,
                    title = "Awal Mula Studio Rumahan di Solo",
                    description = "Berawal dari 1 mesin jahit antik dan kecintaan merawat pakem kebaya klasik keraton untuk kerabat terdekat."
                )
                TimelineMilestoneCard(
                    year = "2014",
                    badge = "Kebayoran Baru, Jakarta",
                    isGoldenBadge = false,
                    title = "Ekspansi Butik & Workshop Kebayoran Baru",
                    description = "Menghadirkan layanan bespoke fitting privat untuk keluarga terhormat, figur publik, serta perhelatan pernikahan adat nusantara."
                )
                TimelineMilestoneCard(
                    year = "2019",
                    badge = "Koleksi Adibusana Siap Pakai",
                    isGoldenBadge = false,
                    title = "Layanan Sewa Adibusana Siap Pakai",
                    description = "Meluncurkan lini sewa kebaya wisuda & lamaran berstandar adibusana agar kemewahan dapat diakses secara fleksibel dan berkelanjutan."
                )
                TimelineMilestoneCard(
                    year = "2024",
                    badge = "Era Baru Digital Dossier",
                    isGoldenBadge = true,
                    title = "Integrasi Atelier Digital & Dossier Presisi",
                    description = "Mengadopsi pencatatan ukuran presisi (digital dossier) tanpa mengurangi kehangatan sentuhan tangan manual dan keakraban silaturahmi atelier."
                )
            }
        }

        // SECTION 4: FILOSOFI & NILAI MAHAKARYA (3 PILAR)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "LANDASAN BUDAYA",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.Secondary
                )
                Text(
                    text = "Filosofi & Nilai Mahakarya",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary
                )
                Text(
                    text = "Tiga komitmen abadi yang dirajut dalam setiap helai busana yang meninggalkan atelier kami.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )

                PhilosophyPillarCard(
                    icon = Icons.Default.AutoFixHigh,
                    title = "Pakem Berpadu Inovasi",
                    description = "Menghormati lekuk filosofis kebaya kutubaru dan kartini sembari menyesuaikan ergonomi kenyamanan gerak wanita aktif masa kini."
                )
                PhilosophyPillarCard(
                    icon = Icons.Default.Diversity1,
                    title = "Pemberdayaan Maestro Artisan",
                    description = "Menjunjung kesejahteraan penjahit, pembatik tulis pesisir, dan perajin payet dengan skema upah karya yang adil, apresiatif, dan bermartabat."
                )
                PhilosophyPillarCard(
                    icon = Icons.Default.Diamond,
                    title = "Material Tanpa Kompromi",
                    description = "Sutra ATBM tenun tangan murni, brokat French chantilly asli, serta kristal Swarovski bersertifikat resmi demi kilau ningrat yang berkelas."
                )
            }
        }

        // SECTION 5: MITRA & SANG MAESTRO JAHIT
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TANGAN-TANGAN TERAMPIL",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Secondary
                        )
                        Text(
                            text = "Mitra & Sang Maestro Jahit",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = AtelierColors.PrimaryContainer
                    ) {
                        Text(
                            text = "8 Maestro Tetap",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.InverseOnSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
                Text(
                    text = "Setiap potongan pola dipahat oleh tangan-tangan berpengalaman belasan tahun dalam seni rancang busana tradisional.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )

                MaestroProfileCard(
                    name = "Bu Siti Nurjanah",
                    badge = "16 Th Mengabdi",
                    isGoldenBadge = false,
                    role = "Senior Pattern Master & Kebaya Draper",
                    bio = "Spesialis perancang siluet torso langsing proporsional yang mengunci lekuk tubuh anggun tanpa membatasi pernapasan.",
                    avatarUrl = AtelierAssets.ARTISAN_SITI_URL
                )
                MaestroProfileCard(
                    name = "Pak Joko Prasetyo",
                    badge = "12 Th Pengalaman",
                    isGoldenBadge = false,
                    role = "Master Tailor Beskap & Kerah Adat",
                    bio = "Pakar struktur jas beskap Jawi jangkep, atela, dan sikepan dengan fitting dada kokoh serta jahitan kancing perak manual.",
                    avatarUrl = AtelierAssets.ARTISAN_JOKO_URL
                )
                MaestroProfileCard(
                    name = "Mbak Ani Rahmawati",
                    badge = "9 Th Berkarya",
                    isGoldenBadge = false,
                    role = "Lead Artisan Payet & Bordir Kerancang",
                    bio = "Mengarahkan ketelitian sulam tangan kristal, payet pasir emas, dan taburan mutiara air tawar asli dari perairan Lombok.",
                    avatarUrl = AtelierAssets.ARTISAN_ANI_URL
                )
                MaestroProfileCard(
                    name = "Komunitas Giriloyo",
                    badge = "15 Perajin Binaan",
                    isGoldenBadge = true,
                    role = "Mitra Pembatik Sogan Tulis Alami",
                    bio = "Penyedia kain jarik sogan tulis malam dingin Yogyakarta bersertifikasi pewarna alami ramah lingkungan.",
                    avatarUrl = AtelierAssets.ARTISAN_GIRILOYO_URL
                )
            }
        }

        // SECTION 6: KEMITRAAN & KEPERCAYAAN INSTANSI
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AtelierColors.SurfaceContainerLow.copy(alpha = 0.55f))
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = AtelierColors.Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "PORTOFOLIO & REKAM JEJAK",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Secondary
                    )
                }
                Text(
                    text = "Kemitraan & Kepercayaan Instansi",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary
                )
                Text(
                    text = "Dipercaya dalam perhelatan kenegaraan, wisuda agung universitas ternama, dan seragam korporasi bergengsi.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )

                // Stats Counter Bar
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.SurfaceContainerLowest,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCounterColumn("35+", "Kemitraan\nInstansi", AtelierColors.Primary)
                        StatCounterColumn("2.400+", "Pasang\nKebaya", AtelierColors.Secondary)
                        StatCounterColumn("100%", "Presisi\nUkuran", AtelierColors.Primary)
                    }
                }

                InstitutionPartnerCard(
                    title = "Kementerian Luar Negeri RI",
                    icon = Icons.Default.Public,
                    description = "Perancang busana delegasi resmi resepsi diplomatik internasional, selendang songket duta bangsa, dan kebaya seremonial kenegaraan.",
                    tag1 = "Diplomasi Budaya",
                    tag2 = "Protokol Resmi"
                )
                InstitutionPartnerCard(
                    title = "Universitas Indonesia & ITB",
                    icon = Icons.Default.School,
                    description = "Penyedia busana wisudawati terbaik, selempang kehormatan senat akademis, serta lini sewa kebaya wisuda mahasiswa program sarjana & pascasarjana.",
                    tag1 = "Wisuda Akbar",
                    tag2 = "Adibusana Mahasiswa"
                )
                InstitutionPartnerCard(
                    title = "Bank Mandiri & BCA Prioritas",
                    icon = Icons.Default.AccountBalance,
                    description = "Rancang bangun seragam bespoke representatif staf VIP perbankan berkonsep kebaya modern santun dengan sentuhan kain jumputan sutra eksklusif.",
                    tag1 = "Bespoke Seragam VIP",
                    tag2 = "Corporate Identity"
                )
                InstitutionPartnerCard(
                    title = "KBRI Tokyo & KBRI Paris",
                    icon = Icons.Default.FlightTakeoff,
                    description = "Eksibisi pameran warisan kebaya dan busana ningrat Nusantara dalam festival pertukaran budaya Indonesia di Jepang dan Perancis.",
                    tag1 = "Exhibition Tokyo 2022",
                    tag2 = "Paris Couture Week 2023"
                )

                // Testimonial Quote Card
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.PrimaryContainer,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryFixed,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "KESAKSIAN KLIEN RESMI",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.TertiaryFixed
                            )
                        }
                        Text(
                            text = "“Ketepatan waktu dan presisi ukuran atelier Nely Collection luar biasa. 40 pasang kebaya panitia resepsi kenegaraan selesai rapi dengan fitting tanpa revisi mayor sama sekali.”",
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                            color = AtelierColors.InverseOnSurface
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Ibu Dyah Kusumaningrum",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AtelierColors.OnPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Koordinator Protokol & Jamuan Kenegaraan",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnPrimaryContainer
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Gavel,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryFixed.copy(alpha = 0.4f),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }

        // SECTION 7: CALL TO ACTION & CONTACT
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = AtelierColors.SurfaceContainerHighest,
                    shadowElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(AtelierColors.Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Handshake,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryFixed,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        Text(
                            text = "Wujudkan Narasi Agung Bersama Kami",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Konsultasikan kebutuhan kebaya eksklusif keluarga, wisuda kehormatan, atau kolaborasi seragam korporasi berskala besar dengan atelier kami.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        Button(
                            onClick = {
                                onShowSnackbar("Permintaan konsultasi B2B / Instansi telah diteruskan ke Tim Protokol Nely Atelier")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.Primary,
                                contentColor = AtelierColors.OnPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CorporateFare,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Konsultasi Kolaborasi Instansi / B2B",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Button(
                            onClick = {
                                openExternalUrl(context, "https://wa.me/6281234567890")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AtelierColors.SurfaceContainerLowest,
                                contentColor = AtelierColors.Primary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Kunjungi Studio & Hubungi WhatsApp",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "ATELIER NELY COLLECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Jl. Suryo No. 42, Senopati - Kebayoran Baru, Jakarta Selatan",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtelierColors.OnSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Buka Selasa - Minggu | Melayani Fitting Privat",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.Outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineMilestoneCard(
    year: String,
    badge: String,
    isGoldenBadge: Boolean,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(AtelierColors.Primary)
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(95.dp)
                    .background(AtelierColors.TertiaryFixedDim.copy(alpha = 0.6f))
            )
        }

        Surface(
            shape = RoundedCornerShape(14.dp),
            color = AtelierColors.SurfaceContainerLowest,
            shadowElevation = 2.dp,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.headlineSmall,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.Bold
                    )
                    Surface(
                        shape = CircleShape,
                        color = if (isGoldenBadge) AtelierColors.TertiaryFixed else AtelierColors.SurfaceContainerHigh
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isGoldenBadge) AtelierColors.OnTertiaryFixed else AtelierColors.OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = AtelierColors.OnSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PhilosophyPillarCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AtelierColors.SurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AtelierColors.Primary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MaestroProfileCard(
    name: String,
    badge: String,
    isGoldenBadge: Boolean,
    role: String,
    bio: String,
    avatarUrl: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AtelierNetworkImage(
                imageUrl = avatarUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = if (isGoldenBadge) AtelierColors.TertiaryFixed else AtelierColors.SurfaceContainer
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = if (isGoldenBadge) AtelierColors.OnTertiaryFixed else AtelierColors.OnSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = role,
                    style = MaterialTheme.typography.labelMedium,
                    color = AtelierColors.Secondary
                )
                Text(
                    text = bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun StatCounterColumn(
    number: String,
    label: String,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = AtelierColors.OnSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun InstitutionPartnerCard(
    title: String,
    icon: ImageVector,
    description: String,
    tag1: String,
    tag2: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AtelierColors.Secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = AtelierColors.OnSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainerHigh
                ) {
                    Text(
                        text = tag1,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainerHigh
                ) {
                    Text(
                        text = tag2,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
