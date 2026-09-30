package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.DryCleaning
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AtelierAssets
import com.example.data.KebayaCatalogItem
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.AtelierColors

@Composable
fun BerandaScreen(
    catalogItems: List<KebayaCatalogItem>,
    onNavigateToJahit: () -> Unit,
    onNavigateToSewa: () -> Unit,
    onToggleFavorite: (KebayaCatalogItem) -> Unit,
    onOpenDetail: (KebayaCatalogItem) -> Unit,
    onQuickRent: (KebayaCatalogItem) -> Unit,
    onSwitchToAdmin: () -> Unit
) {
    val context = LocalContext.current
    val featuredItems = catalogItems.filter { it.isFeaturedCarousel }.ifEmpty { catalogItems.take(3) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("beranda_screen"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // 1. Hero Showcase
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.PrimaryContainer,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(440.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AtelierNetworkImage(
                            imageUrl = AtelierAssets.HERO_BESPOKE_URL,
                            contentDescription = "Stunning bespoke royal Indonesian burgundy and gold bridal kebaya",
                            modifier = Modifier.fillMaxSize()
                        )
                        // Gradient Scrim
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            AtelierColors.Primary.copy(alpha = 0.55f),
                                            AtelierColors.Primary
                                        )
                                    )
                                )
                        )

                        // Top Left Badge: ATELIER BESPOKE
                        Surface(
                            shape = CircleShape,
                            color = AtelierColors.SurfaceContainerLowest.copy(alpha = 0.92f),
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AtelierColors.OnTertiaryContainer)
                                )
                                Text(
                                    text = "ATELIER BESPOKE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.Primary
                                )
                            }
                        }

                        // Bottom Overlay Typography & CTAs
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "HAUTE COUTURE NUSANTARA",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtelierColors.TertiaryFixedDim,
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = "Jahit dengan Sentuhan Tradisi, Tampil dengan Elegansi",
                                style = MaterialTheme.typography.headlineLarge,
                                color = AtelierColors.OnPrimary
                            )
                            Text(
                                text = "Jasa jahit bespoke & sewa kebaya eksklusif dengan detail payet hand-embroidered, presisi tinggi, dan keanggunan busana Nusantara.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.PrimaryFixedDim.copy(alpha = 0.92f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = onNavigateToJahit,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AtelierColors.SurfaceContainerLowest,
                                        contentColor = AtelierColors.Primary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .testTag("hero_pesan_busana_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Straighten,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Pesan Busana",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }

                                Button(
                                    onClick = onNavigateToSewa,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AtelierColors.PrimaryContainer.copy(alpha = 0.85f),
                                        contentColor = AtelierColors.TertiaryFixed
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .border(
                                            0.8.dp,
                                            AtelierColors.TertiaryFixedDim.copy(alpha = 0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .testTag("hero_lihat_koleksi_btn")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Lihat Koleksi",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. Layanan Utama (Solusi Busana Anda)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LAYANAN ISTIMEWA",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer
                        )
                        Text(
                            text = "Solusi Busana Anda",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = AtelierColors.OutlineVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Card 1: Jasa Jahit Bespoke
                ServiceShowcaseCard(
                    icon = Icons.Default.ContentCut,
                    pricePill = "MULAI RP350.000",
                    title = "Jasa Jahit Bespoke",
                    description = "Kebaya modern, gamis pesta, bridal couture & seragam keluarga. Pola presisi mengikuti postur tubuh secara sempurna.",
                    subFeatureIcon = Icons.Default.DesignServices,
                    subFeatureText = "Termasuk Konsultasi Siluet",
                    ctaText = "Rancang Gaun",
                    testTag = "service_card_bespoke",
                    onClick = onNavigateToJahit
                )

                // Card 2: Sewa Kebaya Eksklusif
                ServiceShowcaseCard(
                    icon = Icons.Default.Checkroom,
                    pricePill = "MULAI RP250.000",
                    title = "Sewa Kebaya Eksklusif",
                    description = "Koleksi ready-to-wear premium untuk wisuda, lamaran, akad nikah & resepsi. Bersih, terawat, dan bebas fitting minor.",
                    subFeatureIcon = Icons.Default.DryCleaning,
                    subFeatureText = "Termasuk Dry Clean & Alterasi",
                    ctaText = "Pilih Ukuran",
                    testTag = "service_card_sewa",
                    onClick = onNavigateToSewa
                )
            }
        }

        // 3. Koleksi Pilihan (Horizontal Carousel)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "EDISI SALON",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer
                        )
                        Text(
                            text = "Koleksi Pilihan",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                    }
                    Text(
                        text = "SEMUA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = AtelierColors.PrimaryContainer,
                        modifier = Modifier
                            .clickable { onNavigateToSewa() }
                            .padding(4.dp)
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(featuredItems, key = { it.id }) { item ->
                        FeaturedCarouselCard(
                            item = item,
                            onFavoriteClick = { onToggleFavorite(item) },
                            onCardClick = { onOpenDetail(item) },
                            onBookClick = { onQuickRent(item) }
                        )
                    }
                }
            }
        }

        // 4. Keunggulan Nely Collection (Why Choose Us 2x2 Grid)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "DEDIKASI MAHAKARYA",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnTertiaryContainer
                    )
                    Text(
                        text = "Keunggulan Nely Collection",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AtelierColors.Primary,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdvantageGridCard(
                        icon = Icons.Default.Architecture,
                        title = "Detail & Presisi",
                        description = "Jahitan stik halus standar haute couture bergaransi pas di badan.",
                        modifier = Modifier.weight(1f)
                    )
                    AdvantageGridCard(
                        icon = Icons.Default.Brush,
                        title = "Custom Bebas",
                        description = "Konsultasi siluet desain pribadi & pemilihan kain impian tanpa batas.",
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdvantageGridCard(
                        icon = Icons.Default.Diamond,
                        title = "Bahan Prima",
                        description = "Seleksi French chantilly lace, sutra ATBM & batik tulis nusantara.",
                        modifier = Modifier.weight(1f)
                    )
                    AdvantageGridCard(
                        icon = Icons.Default.HowToReg,
                        title = "Layanan Personal",
                        description = "Fitting eksklusif salon privat dengan pendampingan langsung desainer.",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 5. Galeri Hasil Jahitan (Editorial Staggered Grid)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "PORTOFOLIO PATRONS",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer
                        )
                        Text(
                            text = "Galeri Jahitan",
                            style = MaterialTheme.typography.headlineSmall,
                            color = AtelierColors.Primary
                        )
                    }
                    Text(
                        text = "Karya Terkini",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }

                // Wide Featured Piece
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        AtelierNetworkImage(
                            imageUrl = AtelierAssets.GALERI_WISUDA_URL,
                            contentDescription = "Wisuda Magister UI • Ibu Alverna",
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            AtelierColors.Primary.copy(alpha = 0.82f)
                                        )
                                    )
                                )
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column {
                                Surface(
                                    shape = CircleShape,
                                    color = AtelierColors.SurfaceContainerLowest.copy(alpha = 0.92f)
                                ) {
                                    Text(
                                        text = "KEBAYA WISUDA",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtelierColors.Primary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Wisuda Magister UI • Ibu Alverna",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AtelierColors.OnPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = AtelierColors.TertiaryFixedDim,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Dual Half-Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GalleryHalfCard(
                        imageUrl = AtelierAssets.GALERI_LAMARAN_URL,
                        badge = "GAUN LAMARAN",
                        title = "Akad & Sangjit Nadine",
                        modifier = Modifier.weight(1f)
                    )
                    GalleryHalfCard(
                        imageUrl = AtelierAssets.GALERI_BORDIR_URL,
                        badge = "BORDIR MANUAL",
                        title = "Payet Sulam 40 Jam",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 6. Studio & Butik Pusat + Quick Admin Access Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.SurfaceContainerLowest,
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(
                                    text = "KUNJUNGI ATELIER",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtelierColors.OnTertiaryContainer
                                )
                                Text(
                                    text = "Studio & Butik Pusat",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = AtelierColors.Primary
                                )
                            }
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
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Map Preview Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AtelierColors.SurfaceContainer)
                        ) {
                            AtelierNetworkImage(
                                imageUrl = AtelierAssets.MAP_PREVIEW_URL,
                                contentDescription = "Lokasi Butik Nely Collection",
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(AtelierColors.Primary.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = AtelierColors.SurfaceContainerLowest,
                                    shadowElevation = 6.dp,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "Pin",
                                            tint = AtelierColors.Primary,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PinDrop,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .padding(top = 2.dp)
                                )
                                Column {
                                    Text(
                                        text = "Nely Collection Atelier",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.Primary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Jl. Tirtayasa No. 18, Kebayoran Baru, Jakarta Selatan, 12160",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtelierColors.OnSurfaceVariant
                                    )
                                }
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = AtelierColors.Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Selasa – Minggu • 10:00 – 18:00 WIB (Senin Tutup)",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    openExternalUrl(
                                        context,
                                        "https://maps.google.com/?q=Jl.+Tirtayasa+No.+18,+Kebayoran+Baru,+Jakarta+Selatan"
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.SurfaceContainer,
                                    contentColor = AtelierColors.Primary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Directions,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Petunjuk Arah", style = MaterialTheme.typography.labelMedium)
                            }

                            Button(
                                onClick = {
                                    openExternalUrl(context, "https://wa.me/6281234567890")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierColors.PrimaryContainer,
                                    contentColor = AtelierColors.OnPrimary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Chat,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Konsultasi WA", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceShowcaseCard(
    icon: ImageVector,
    pricePill: String,
    title: String,
    description: String,
    subFeatureIcon: ImageVector,
    subFeatureText: String,
    ctaText: String,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AtelierColors.SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = AtelierColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainer
                ) {
                    Text(
                        text = pricePill,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnTertiaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = AtelierColors.Primary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = subFeatureIcon,
                        contentDescription = null,
                        tint = AtelierColors.OnTertiaryContainer,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = subFeatureText,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.OnTertiaryContainer
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = ctaText,
                        style = MaterialTheme.typography.labelMedium,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = AtelierColors.Primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedCarouselCard(
    item: KebayaCatalogItem,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit,
    onBookClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 4.dp,
        modifier = Modifier
            .width(256.dp)
            .clickable(onClick = onCardClick)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(288.dp)
            ) {
                AtelierNetworkImage(
                    imageUrl = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainerLowest.copy(alpha = 0.92f),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "TERSEDIA",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(AtelierColors.SurfaceContainerLowest.copy(alpha = 0.85f))
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorit",
                        tint = if (item.isFavorite) AtelierColors.Secondary else AtelierColors.Primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.badgeTag.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.OnTertiaryContainer
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.colorPalette,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Sewa 3 Hari",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnSurfaceVariant
                        )
                        Text(
                            text = item.priceFormatted,
                            style = MaterialTheme.typography.titleMedium,
                            color = AtelierColors.Primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = AtelierColors.PrimaryContainer,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable(onClick = onBookClick)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Sewa",
                                tint = AtelierColors.OnPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdvantageGridCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AtelierColors.SurfaceContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AtelierColors.Primary,
                    modifier = Modifier.size(22.dp)
                )
            }
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

@Composable
private fun GalleryHalfCard(
    imageUrl: String,
    badge: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 4.dp,
        modifier = modifier.height(240.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AtelierNetworkImage(
                imageUrl = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                AtelierColors.Primary.copy(alpha = 0.82f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = AtelierColors.SurfaceContainerLowest.copy(alpha = 0.92f)
                ) {
                    Text(
                        text = badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtelierColors.Primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = AtelierColors.OnPrimary,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
