package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ArtisanMember
import com.example.data.AtelierAssets
import com.example.data.AtelierOrder
import com.example.data.AtelierRepository
import com.example.data.KebayaCatalogItem
import com.example.data.MeasurementDossier
import com.example.data.WageSlip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class PatronTab {
    BERANDA, JAHIT, SEWA, PESANAN, AKUN
}

enum class AdminTab {
    PESANAN, ARTISAN_UPAH, KATALOG, PENGATURAN
}

data class AtelierUiState(
    val isAdminMode: Boolean = false,
    val patronTab: PatronTab = PatronTab.BERANDA,
    val adminTab: AdminTab = AdminTab.PESANAN,
    val sewaSearchQuery: String = "",
    val sewaCategoryFilter: String = "Semua",
    val sewaSortOption: String = "Terpopuler",
    val pesananFilterTab: String = "all",
    val adminOrderSearchQuery: String = "",
    val adminOrderFilterTab: String = "all",
    val adminExpandedOrderCode: String = "#NC-2024-089",
    val adminArtisanFilter: String = "Semua Keahlian (8)",
    val bespokeStep: Int = 4, // Defaulting to Step 4: Ukuran (Bespoke Measurement Dossier)
    val selectedCatalogDetail: KebayaCatalogItem? = null,
    val activeBarcodeOrder: AtelierOrder? = null,
    val trackingDetailOrder: AtelierOrder? = null,
    val reviewingOrder: AtelierOrder? = null,
    val showNewTaskDialog: Boolean = false,
    val payrollModalArtisan: ArtisanMember? = null,
    val showNewOrderDialog: Boolean = false,
    val showVideoCallDialog: Boolean = false,
    val snackbarMessage: String? = null
)

class AtelierViewModel(private val repository: AtelierRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AtelierUiState())
    val uiState: StateFlow<AtelierUiState> = _uiState.asStateFlow()

    val catalogItems: StateFlow<List<KebayaCatalogItem>> = repository.catalogItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders: StateFlow<List<AtelierOrder>> = repository.orders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val measurementDossier: StateFlow<MeasurementDossier> = repository.measurementDossier
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MeasurementDossier())
        as StateFlow<MeasurementDossier>

    val artisans: StateFlow<List<ArtisanMember>> = repository.artisans
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wageSlips: StateFlow<List<WageSlip>> = repository.wageSlips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    fun setAdminMode(isAdmin: Boolean) {
        _uiState.update { it.copy(isAdminMode = isAdmin) }
    }

    fun selectPatronTab(tab: PatronTab) {
        _uiState.update { it.copy(isAdminMode = false, patronTab = tab) }
    }

    fun selectAdminTab(tab: AdminTab) {
        _uiState.update { it.copy(isAdminMode = true, adminTab = tab) }
    }

    fun setSewaSearchQuery(query: String) {
        _uiState.update { it.copy(sewaSearchQuery = query) }
    }

    fun setSewaCategoryFilter(category: String) {
        _uiState.update { it.copy(sewaCategoryFilter = category) }
    }

    fun setSewaSortOption(sort: String) {
        _uiState.update { it.copy(sewaSortOption = sort) }
    }

    fun setPesananFilterTab(filter: String) {
        _uiState.update { it.copy(pesananFilterTab = filter) }
    }

    fun setAdminOrderSearchQuery(query: String) {
        _uiState.update { it.copy(adminOrderSearchQuery = query) }
    }

    fun setAdminOrderFilterTab(filter: String) {
        _uiState.update { it.copy(adminOrderFilterTab = filter) }
    }

    fun toggleAdminExpandedOrder(orderCode: String) {
        _uiState.update {
            it.copy(adminExpandedOrderCode = if (it.adminExpandedOrderCode == orderCode) "" else orderCode)
        }
    }

    fun setAdminArtisanFilter(filter: String) {
        _uiState.update { it.copy(adminArtisanFilter = filter) }
    }

    fun setBespokeStep(step: Int) {
        _uiState.update { it.copy(bespokeStep = step.coerceIn(1, 5)) }
    }

    fun toggleFavorite(item: KebayaCatalogItem) {
        viewModelScope.launch {
            repository.toggleFavorite(item)
            val msg = if (!item.isFavorite) {
                "${item.title} disimpan ke koleksi favorit"
            } else {
                "${item.title} dihapus dari favorit"
            }
            showSnackbar(msg)
        }
    }

    fun updateDossier(updater: (MeasurementDossier) -> MeasurementDossier) {
        viewModelScope.launch {
            val current = measurementDossier.value ?: MeasurementDossier()
            repository.saveDossier(updater(current))
        }
    }

    fun rentKebayaNow(item: KebayaCatalogItem) {
        viewModelScope.launch {
            val codeSuffix = (100..999).random()
            val newOrder = AtelierOrder(
                orderCode = "#NC-2024-$codeSuffix",
                serviceType = "Sewa Kebaya Eksklusif",
                garmentTitle = item.title,
                subNote = "${item.sizeInfo} • Durasi: 3 Hari",
                clientName = "Ibu Ratna Prawira",
                clientPhone = "+62 812-8899-2024",
                statusKey = if (item.isAvailable) "siap" else "menunggu",
                statusBadge = if (item.isAvailable) "Siap Diambil" else "Daftar Tunggu",
                priceFormatted = item.priceFormatted,
                paymentNote = "(Deposit Rp 200.000)",
                dpPaidFormatted = "Lunas + Jaminan ID",
                remainingFormatted = "Rp 0",
                currentStep = if (item.isAvailable) 4 else 1,
                totalSteps = 5,
                tailorNote = "Busana siap diambil di Butik Kebayoran Baru lengkap dengan jarik wiru & manset senada.",
                pickupLocation = "Butik Nely Collection Kebayoran, Jakarta Selatan",
                pickupHours = "Batas jam pengambilan hari ini: 10.00 - 19.30 WIB",
                fittingDateNote = "Pengambilan di Butik Kebayoran",
                artisanAssigned = "Pak Joko (QC & Steam)",
                imageUrl = item.imageUrl,
                barcodeNumber = "2024 $codeSuffix 901"
            )
            repository.addOrder(newOrder)
            _uiState.update {
                it.copy(
                    selectedCatalogDetail = null,
                    patronTab = PatronTab.PESANAN,
                    pesananFilterTab = "all",
                    snackbarMessage = "Reservasi ${item.title} (${newOrder.orderCode}) berhasil dibuat!"
                )
            }
        }
    }

    fun confirmBespokeOrder() {
        viewModelScope.launch {
            val d = measurementDossier.value ?: MeasurementDossier()
            val codeSuffix = (110..980).random()
            val newOrder = AtelierOrder(
                orderCode = "#NC-2024-$codeSuffix",
                serviceType = "Jasa Jahit Bespoke",
                garmentTitle = "${d.selectedService} • ${d.fabricChoice}",
                subNote = if (d.method == "boutique") {
                    "Fitting Butik (${d.scheduleDate})"
                } else {
                    "Ukur Mandiri (Dossier Terverifikasi)"
                },
                clientName = d.clientName,
                clientPhone = d.clientPhone,
                statusKey = "diproses",
                statusBadge = "Diproses",
                priceFormatted = "Rp 1.450.000",
                paymentNote = "(DP Rp 750.000 Lunas)",
                dpPaidFormatted = "Rp 750.000",
                remainingFormatted = "Rp 700.000",
                currentStep = 2,
                totalSteps = 5,
                tailorNote = "Dossier ukuran anatomis (${d.lingkarDada}/${d.lingkarPinggang}/${d.lingkarPinggul} cm) telah diterima Master Tailor Ibu Nely.",
                pickupLocation = "Butik Nely Collection Kebayoran, Jakarta Selatan",
                pickupHours = "10.00 - 18.00 WIB",
                fittingDateNote = "${d.scheduleDate} (${d.scheduleTime})",
                artisanAssigned = "Bu Siti (Pola), Mbak Ani (Bordir)",
                imageUrl = AtelierAssets.ORDER_089_URL,
                ldCm = d.lingkarDada.toIntOrNull() ?: 88,
                lpCm = d.lingkarPinggang.toIntOrNull() ?: 70,
                pinggulCm = d.lingkarPinggul.toIntOrNull() ?: 94,
                bahuCm = d.lebarBahu.toIntOrNull() ?: 38,
                lenganCm = d.panjangLengan.toIntOrNull() ?: 54,
                panjangCm = d.panjangKebaya.toIntOrNull() ?: 75
            )
            repository.addOrder(newOrder)
            _uiState.update {
                it.copy(
                    bespokeStep = 4,
                    patronTab = PatronTab.PESANAN,
                    pesananFilterTab = "all",
                    snackbarMessage = "Pesanan Bespoke ${newOrder.orderCode} berhasil dikonfirmasi!"
                )
            }
        }
    }

    fun reorderGarment(order: AtelierOrder) {
        viewModelScope.launch {
            val codeSuffix = (200..899).random()
            val copyOrder = order.copy(
                orderCode = "#NC-2024-$codeSuffix",
                statusKey = "diproses",
                statusBadge = "Diproses",
                currentStep = 2,
                subNote = "Jahit Ulang Dossier Tersimpan",
                tailorNote = "Pesanan jahit ulang menggunakan rekaman pola & ukuran patron sebelumnya."
            )
            repository.addOrder(copyOrder)
            showSnackbar("Pesanan jahit ulang ${copyOrder.orderCode} telah dibuat!")
        }
    }

    fun submitOrderReview(order: AtelierOrder, review: String) {
        viewModelScope.launch {
            repository.updateOrder(order.copy(userReview = review))
            _uiState.update {
                it.copy(
                    reviewingOrder = null,
                    snackbarMessage = "Terima kasih atas ulasan mahakarya untuk ${order.orderCode}!"
                )
            }
        }
    }

    fun advanceOrderStage(order: AtelierOrder) {
        viewModelScope.launch {
            val nextStep = (order.currentStep + 1).coerceAtMost(5)
            val newStatusKey = when (nextStep) {
                1 -> "menunggu"
                2, 3 -> "diproses"
                4 -> "siap"
                else -> "selesai"
            }
            val newBadge = when (nextStep) {
                1 -> "Menunggu DP"
                2 -> "Konfirmasi Pola"
                3 -> "Diproses (3/5)"
                4 -> "Siap Diambil"
                else -> "Selesai"
            }
            repository.updateOrder(
                order.copy(
                    currentStep = nextStep,
                    statusKey = newStatusKey,
                    statusBadge = newBadge,
                    artisanAssigned = if (order.artisanAssigned.contains("Belum")) "Bu Siti (Master Tailor)" else order.artisanAssigned
                )
            )
            showSnackbar("Status pesanan ${order.orderCode} diperbarui ke: $newBadge")
        }
    }

    fun assignArtisanToOrder(order: AtelierOrder, artisanSummary: String) {
        viewModelScope.launch {
            repository.updateOrder(
                order.copy(
                    artisanAssigned = artisanSummary,
                    statusKey = "diproses",
                    statusBadge = "Diproses",
                    currentStep = 2
                )
            )
            showSnackbar("Artisan $artisanSummary ditugaskan ke ${order.orderCode}")
        }
    }

    fun settleArtisanWage(artisan: ArtisanMember) {
        viewModelScope.launch {
            repository.updateArtisan(
                artisan.copy(
                    paidWageFormatted = artisan.totalWageFormatted,
                    pendingWageValue = 0,
                    pendingWageFormatted = "Rp 0"
                )
            )
            // Also mark pending slips for this artisan as paid
            wageSlips.value.filter { it.artisanId == artisan.id && !it.isPaid }.forEach { slip ->
                repository.updateWageSlip(
                    slip.copy(isPaid = true, paymentStatusLabel = "LUNAS via Transfer")
                )
            }
            _uiState.update {
                it.copy(
                    payrollModalArtisan = null,
                    snackbarMessage = "Upah ${artisan.name} sebesar ${artisan.pendingWageFormatted} berhasil dicairkan!"
                )
            }
        }
    }

    fun createNewWageSlip(
        orderCode: String,
        artisan: ArtisanMember,
        jobType: String,
        wageFormatted: String,
        wageValue: Int,
        notes: String
    ) {
        viewModelScope.launch {
            val slipNum = (8911..9999).random()
            val newSlip = WageSlip(
                slipCode = "#SLIP-$slipNum",
                dateTimeLabel = "Baru saja • WIB",
                artisanId = artisan.id,
                artisanName = artisan.name,
                artisanRole = artisan.skillCategory,
                orderCode = orderCode,
                eventTag = "Bespoke",
                clientName = "Patron Atelier",
                taskTitle = "$jobType • $orderCode",
                taskDetail = notes.ifBlank { "Instruksi jahit standar haute couture" },
                wageValue = wageValue,
                wageFormatted = wageFormatted,
                rateTypeLabel = "Tarif Borongan",
                workStatus = "Dikerjakan",
                isPaid = false,
                paymentStatusLabel = "PENDING"
            )
            repository.addWageSlip(newSlip)
            val newPending = artisan.pendingWageValue + wageValue
            repository.updateArtisan(
                artisan.copy(
                    inProgressCount = artisan.inProgressCount + 1,
                    pendingWageValue = newPending,
                    pendingWageFormatted = "Rp ${formatRupiahNumber(newPending)}"
                )
            )
            _uiState.update {
                it.copy(
                    showNewTaskDialog = false,
                    snackbarMessage = "Tugas ${newSlip.slipCode} untuk ${artisan.name} berhasil diterbitkan!"
                )
            }
        }
    }

    fun createNewAdminOrder(
        clientName: String,
        clientPhone: String,
        garmentTitle: String,
        serviceType: String,
        priceFormatted: String
    ) {
        viewModelScope.launch {
            val codeSuffix = (101..998).random()
            val newOrder = AtelierOrder(
                orderCode = "#NC-2024-$codeSuffix",
                serviceType = serviceType,
                garmentTitle = garmentTitle,
                subNote = "Dossier Baru • Butik Kebayoran",
                clientName = clientName,
                clientPhone = clientPhone,
                statusKey = "diproses",
                statusBadge = "Diproses",
                priceFormatted = priceFormatted,
                paymentNote = "(DP 50% Terverifikasi)",
                dpPaidFormatted = "DP 50% Masuk",
                remainingFormatted = "Sisa 50%",
                currentStep = 2,
                totalSteps = 5,
                tailorNote = "Pesanan baru dicatat melalui konsol Admin Atelier.",
                pickupLocation = "Butik Nely Collection Kebayoran, Jakarta Selatan",
                pickupHours = "10.00 - 18.00 WIB",
                fittingDateNote = "Jadwal Fitting I segera",
                artisanAssigned = "Bu Siti (Pola & Jahit)",
                imageUrl = AtelierAssets.ORDER_089_URL
            )
            repository.addOrder(newOrder)
            _uiState.update {
                it.copy(
                    showNewOrderDialog = false,
                    adminExpandedOrderCode = newOrder.orderCode,
                    snackbarMessage = "Pesanan baru ${newOrder.orderCode} untuk $clientName berhasil ditambahkan!"
                )
            }
        }
    }

    fun showCatalogDetail(item: KebayaCatalogItem?) {
        _uiState.update { it.copy(selectedCatalogDetail = item) }
    }

    fun showBarcodeModal(order: AtelierOrder?) {
        _uiState.update { it.copy(activeBarcodeOrder = order) }
    }

    fun showTrackingDetailModal(order: AtelierOrder?) {
        _uiState.update { it.copy(trackingDetailOrder = order) }
    }

    fun showReviewModal(order: AtelierOrder?) {
        _uiState.update { it.copy(reviewingOrder = order) }
    }

    fun setShowNewTaskDialog(show: Boolean) {
        _uiState.update { it.copy(showNewTaskDialog = show) }
    }

    fun setPayrollModalArtisan(artisan: ArtisanMember?) {
        _uiState.update { it.copy(payrollModalArtisan = artisan) }
    }

    fun setShowNewOrderDialog(show: Boolean) {
        _uiState.update { it.copy(showNewOrderDialog = show) }
    }

    fun setShowVideoCallDialog(show: Boolean) {
        _uiState.update { it.copy(showVideoCallDialog = show) }
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    private fun formatRupiahNumber(value: Int): String {
        return String.format(java.util.Locale.US, "%,d", value).replace(',', '.')
    }

    companion object {
        fun provideFactory(repository: AtelierRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AtelierViewModel(repository) as T
                }
            }
    }
}
