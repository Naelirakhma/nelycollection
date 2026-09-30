package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KebayaCatalogItem
import com.example.ui.components.AtelierNetworkImage
import com.example.ui.components.openExternalUrl
import com.example.ui.theme.AtelierColors

private val sewaCategories = listOf(
    "Semua",
    "Kebaya Kutubaru",
    "Kebaya Kartini",
    "Kebaya Modern",
    "Bridal/Akad",
    "Kebaya Encim"
)

private val sewaSortOptions = listOf("Terpopuler", "Terbaru", "Harga Terendah")

@Composable
fun SewaKebayaScreen(
    catalogItems: List<KebayaCatalogItem>,
    searchQuery: String,
    selectedCategory: String,
    selectedSort: String,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onSortSelect: (String) -> Unit,
    onToggleFavorite: (KebayaCatalogItem) -> Unit,
    onOpenDetail: (KebayaCatalogItem) -> Unit,
    onRentClick: (KebayaCatalogItem) -> Unit,
    onNavigateToMeasureConsult: () -> Unit
) {
    val context = LocalContext.current
    var sortMenuExpanded by remember { mutableStateOf(false) }

    val filteredCatalog = remember(catalogItems, searchQuery, selectedCategory, selectedSort) {
        val baseList = if (selectedCategory == "Semua" && searchQuery.isBlank()) {
            // Show the 4 primary rental items first, or all if filtered
            catalogItems.filter { !it.isFeaturedCarousel }.ifEmpty { catalogItems }
        } else {
            catalogItems.filter { item ->
                val matchesCat = selectedCategory == "Semua" ||
                    item.categoryFilter.equals(selectedCategory, ignoreCase = true)
                val matchesQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.colorPalette.contains(searchQuery, ignoreCase = true) ||
                    item.badgeTag.contains(searchQuery, ignoreCase = true)
                matchesCat && matchesQuery
            }
        }
        when (selectedSort) {
            "Harga Terendah" -> baseList.sortedBy { it.priceValue }
            "Terbaru" -> baseList.sortedByDescending { it.popularityRank }
            else -> baseList.sortedBy { it.popularityRank }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierColors.Surface)
            .testTag("sewa_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Editorial Header
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(1.dp)
                                .background(AtelierColors.TertiaryFixedDim)
                        )
                        Text(
                            text = "ADIBUSANA RENTAL ATELIER",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtelierColors.OnTertiaryContainer,
                            letterSpacing = 2.sp
                        )
                    }
                    Text(
                        text = "Sewa Kebaya Eksklusif",
                        style = MaterialTheme.typography.headlineLarge,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Koleksi kebaya ready-to-wear berstandar adibusana untuk wisuda, lamaran, dan resepsi.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }
            }

            // Search & Filter Bar
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Search Input
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AtelierColors.SurfaceContainerLowest,
                        shadowElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = AtelierColors.OnTertiaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Cari warna, model, atau acara...",
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("sewa_search_input")
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Filter",
                                tint = AtelierColors.OnSurfaceVariant.copy(alpha = 0.7f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Filter Pills + Sort Dropdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(sewaCategories) { category ->
                                val isSelected = selectedCategory == category
                                Surface(
                                    shape = CircleShape,
                                    color = if (isSelected) AtelierColors.Primary else AtelierColors.SurfaceContainer,
                                    shadowElevation = if (isSelected) 2.dp else 0.dp,
                                    modifier = Modifier
                                        .clickable { onCategorySelect(category) }
                                        .testTag("filter_pill_$category")
                                ) {
                                    Text(
                                        text = category,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = if (isSelected) AtelierColors.OnPrimary else AtelierColors.OnSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        // Sort Pill
                        Box {
                            Surface(
                                shape = CircleShape,
                                color = AtelierColors.SurfaceContainerLow,
                                modifier = Modifier.clickable { sortMenuExpanded = true }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = selectedSort,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = AtelierColors.Primary
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ExpandMore,
                                        contentDescription = null,
                                        tint = AtelierColors.Primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            DropdownMenu(
                                expanded = sortMenuExpanded,
                                onDismissRequest = { sortMenuExpanded = false }
                            ) {
                                sewaSortOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            onSortSelect(option)
                                            sortMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Garansi Layanan Paripurna Banner
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.SurfaceContainerLow,
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(AtelierColors.Surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = AtelierColors.Primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Garansi Layanan Paripurna",
                                style = MaterialTheme.typography.titleMedium,
                                color = AtelierColors.Primary,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Sudah Termasuk: Dry clean premium, kain jarik wiru siap pakai, dan kemben/manset senada.",
                                style = MaterialTheme.typography.bodySmall,
                                color = AtelierColors.OnSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Catalog Cards
            items(filteredCatalog, key = { it.id }) { item ->
                RentalKebayaCard(
                    item = item,
                    onFavoriteClick = { onToggleFavorite(item) },
                    onDetailClick = { onOpenDetail(item) },
                    onRentClick = { onRentClick(item) }
                )
            }

            // Bingung dengan Ukuran? Consultation Banner
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = AtelierColors.SurfaceContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateToMeasureConsult() }
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
                                    .background(AtelierColors.Primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Straighten,
                                    contentDescription = null,
                                    tint = AtelierColors.TertiaryFixed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Bingung dengan Ukuran?",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AtelierColors.Primary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Konsultasikan lingkar dada & pinggang ke stylist kami.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtelierColors.OnSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = AtelierColors.Primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }

        // Floating Tanya Stylist WhatsApp Button
        Surface(
            shape = CircleShape,
            color = AtelierColors.WhatsAppGreen,
            shadowElevation = 8.dp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp)
                .clickable { openExternalUrl(context, "https://wa.me/6281234567890") }
                .testTag("tanya_stylist_fab")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Tanya Stylist via WhatsApp",
                    tint = AtelierColors.OnPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Tanya Stylist",
                    style = MaterialTheme.typography.labelMedium,
                    color = AtelierColors.OnPrimary
                )
            }
        }
    }
}

@Composable
private fun RentalKebayaCard(
    item: KebayaCatalogItem,
    onFavoriteClick: () -> Unit,
    onDetailClick: () -> Unit,
    onRentClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = AtelierColors.SurfaceContainerLowest,
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (item.isAvailable) 1f else 0.9f)
            .testTag("rental_card_${item.id}")
    ) {
        Column {
            // 4:5 Portrait Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
            ) {
                AtelierNetworkImage(
                    imageUrl = item.imageUrl,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize()
                )

                // Top-left Availability Pill
                Surface(
                    shape = CircleShape,
                    color = if (item.isAvailable) {
                        AtelierColors.SurfaceContainerLowest.copy(alpha = 0.92f)
                    } else {
                        AtelierColors.SurfaceDim.copy(alpha = 0.95f)
                    },
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (item.isAvailable) AtelierColors.EmeraldAvailable else AtelierColors.Secondary
                                )
                        )
                        Text(
                            text = if (item.isAvailable) "TERSEDIA" else "TIDAK TERSEDIA",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (item.isAvailable) AtelierColors.PrimaryContainer else AtelierColors.OnSurfaceVariant
                        )
                    }
                }

                // Top-right Favorite Heart
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AtelierColors.SurfaceContainerLowest.copy(alpha = 0.85f))
                ) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Simpan Favorit",
                        tint = if (item.isFavorite) AtelierColors.Secondary else AtelierColors.PrimaryContainer,
                        modifier = Modifier.size(19.dp)
                    )
                }

                // Bottom-left Category Tag
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = AtelierColors.Primary.copy(alpha = 0.82f),
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

            // Details & Actions
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = AtelierColors.Primary,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = AtelierColors.OnTertiaryContainer,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = item.colorPalette,
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OutlineVariant
                    )
                    Text(
                        text = item.sizeInfo,
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "TARIF SEWA",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtelierColors.OnSurfaceVariant
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = item.priceFormatted,
                        style = MaterialTheme.typography.titleLarge,
                        color = AtelierColors.Primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.durationLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = AtelierColors.OnSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDetailClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierColors.SurfaceContainerHigh,
                            contentColor = AtelierColors.Primary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("detail_btn_${item.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Detail Busana", style = MaterialTheme.typography.labelMedium)
                    }

                    Button(
                        onClick = onRentClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (item.isAvailable) {
                                AtelierColors.PrimaryContainer
                            } else {
                                AtelierColors.SurfaceContainerHighest
                            },
                            contentColor = if (item.isAvailable) {
                                AtelierColors.OnPrimary
                            } else {
                                AtelierColors.OnSurfaceVariant
                            }
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("rent_btn_${item.id}")
                    ) {
                        Icon(
                            imageVector = if (item.isAvailable) Icons.Default.CheckCircle else Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
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
