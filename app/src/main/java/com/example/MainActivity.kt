package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.AtelierDatabase
import com.example.data.AtelierRepository
import com.example.ui.AdminTab
import com.example.ui.AtelierViewModel
import com.example.ui.PatronTab
import com.example.ui.components.AtelierBottomNavBar
import com.example.ui.components.AtelierTopHeader
import com.example.ui.components.BarcodeVoucherModal
import com.example.ui.components.CatalogDetailModal
import com.example.ui.components.OrderReviewModal
import com.example.ui.screens.AdminArtisanUpahScreen
import com.example.ui.screens.AdminPesananScreen
import com.example.ui.screens.AkunNarasiScreen
import com.example.ui.screens.BerandaScreen
import com.example.ui.screens.BespokeDossierScreen
import com.example.ui.screens.PesananSayaScreen
import com.example.ui.screens.SewaKebayaScreen
import com.example.ui.theme.AtelierColors
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val context = LocalContext.current
                val repository = remember {
                    AtelierRepository(AtelierDatabase.getDatabase(context).atelierDao())
                }
                val viewModel: AtelierViewModel = viewModel(
                    factory = AtelierViewModel.provideFactory(repository)
                )
                NelyAtelierApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun NelyAtelierApp(viewModel: AtelierViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val catalogItems by viewModel.catalogItems.collectAsStateWithLifecycle()
    val orders by viewModel.orders.collectAsStateWithLifecycle()
    val dossier by viewModel.measurementDossier.collectAsStateWithLifecycle()
    val artisans by viewModel.artisans.collectAsStateWithLifecycle()
    val wageSlips by viewModel.wageSlips.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        val msg = uiState.snackbarMessage
        if (msg != null) {
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    // BackHandler for secondary tabs / Admin mode
    BackHandler(enabled = uiState.isAdminMode || uiState.patronTab != PatronTab.BERANDA) {
        if (uiState.isAdminMode) {
            if (uiState.adminTab != AdminTab.PESANAN) {
                viewModel.selectAdminTab(AdminTab.PESANAN)
            } else {
                viewModel.setAdminMode(false)
            }
        } else {
            viewModel.selectPatronTab(PatronTab.BERANDA)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AtelierColors.Surface,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AtelierTopHeader(
                isAdminMode = uiState.isAdminMode,
                patronTab = uiState.patronTab,
                adminTab = uiState.adminTab,
                onToggleAdminMode = { isAdmin -> viewModel.setAdminMode(isAdmin) },
                onBackClick = { viewModel.selectPatronTab(PatronTab.BERANDA) },
                onSearchClick = {
                    if (!uiState.isAdminMode) {
                        viewModel.selectPatronTab(PatronTab.SEWA)
                    }
                },
                onNotificationClick = {
                    if (uiState.isAdminMode) {
                        viewModel.showSnackbar("3 jadwal fitting hari ini & 2 payroll artisan menunggu konfirmasi")
                    } else {
                        viewModel.selectPatronTab(PatronTab.PESANAN)
                        viewModel.showSnackbar("Pembaruan Atelier: #NC-2024-072 telah siap diambil di Butik Kebayoran")
                    }
                },
                onProfileClick = {
                    if (uiState.isAdminMode) {
                        viewModel.selectAdminTab(AdminTab.PENGATURAN)
                    } else {
                        viewModel.selectPatronTab(PatronTab.AKUN)
                    }
                }
            )
        },
        bottomBar = {
            // Hide main bottom bar on Jahit Dossier screen because it has its own Sticky Step Action Bar
            if (uiState.isAdminMode || uiState.patronTab != PatronTab.JAHIT) {
                AtelierBottomNavBar(
                    isAdminMode = uiState.isAdminMode,
                    patronTab = uiState.patronTab,
                    adminTab = uiState.adminTab,
                    onSelectPatronTab = { viewModel.selectPatronTab(it) },
                    onSelectAdminTab = { viewModel.selectAdminTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (!uiState.isAdminMode) {
                when (uiState.patronTab) {
                    PatronTab.BERANDA -> {
                        BerandaScreen(
                            catalogItems = catalogItems,
                            onNavigateToJahit = { viewModel.selectPatronTab(PatronTab.JAHIT) },
                            onNavigateToSewa = { viewModel.selectPatronTab(PatronTab.SEWA) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onOpenDetail = { viewModel.showCatalogDetail(it) },
                            onQuickRent = { viewModel.rentKebayaNow(it) },
                            onSwitchToAdmin = { viewModel.setAdminMode(true) }
                        )
                    }

                    PatronTab.JAHIT -> {
                        BespokeDossierScreen(
                            dossier = dossier,
                            currentStep = uiState.bespokeStep,
                            showVideoCallDialog = uiState.showVideoCallDialog,
                            onStepChange = { viewModel.setBespokeStep(it) },
                            onUpdateDossier = { viewModel.updateDossier(it) },
                            onConfirmOrder = { viewModel.confirmBespokeOrder() },
                            onSetShowVideoCallDialog = { viewModel.setShowVideoCallDialog(it) },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }

                    PatronTab.SEWA -> {
                        SewaKebayaScreen(
                            catalogItems = catalogItems,
                            searchQuery = uiState.sewaSearchQuery,
                            selectedCategory = uiState.sewaCategoryFilter,
                            selectedSort = uiState.sewaSortOption,
                            onSearchQueryChange = { viewModel.setSewaSearchQuery(it) },
                            onCategorySelect = { viewModel.setSewaCategoryFilter(it) },
                            onSortSelect = { viewModel.setSewaSortOption(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onOpenDetail = { viewModel.showCatalogDetail(it) },
                            onRentClick = { viewModel.rentKebayaNow(it) },
                            onNavigateToMeasureConsult = { viewModel.selectPatronTab(PatronTab.JAHIT) }
                        )
                    }

                    PatronTab.PESANAN -> {
                        PesananSayaScreen(
                            orders = orders,
                            selectedFilter = uiState.pesananFilterTab,
                            onSelectFilter = { viewModel.setPesananFilterTab(it) },
                            onShowBarcode = { viewModel.showBarcodeModal(it) },
                            onAdvanceStage = { viewModel.advanceOrderStage(it) },
                            onOpenReview = { viewModel.showReviewModal(it) },
                            onReorder = { viewModel.reorderGarment(it) },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }

                    PatronTab.AKUN -> {
                        AkunNarasiScreen(
                            onSwitchToAdminMode = { viewModel.setAdminMode(true) },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }
                }
            } else {
                when (uiState.adminTab) {
                    AdminTab.PESANAN -> {
                        AdminPesananScreen(
                            orders = orders,
                            searchQuery = uiState.adminOrderSearchQuery,
                            selectedFilter = uiState.adminOrderFilterTab,
                            expandedOrderCode = uiState.adminExpandedOrderCode,
                            showNewOrderDialog = uiState.showNewOrderDialog,
                            onSearchQueryChange = { viewModel.setAdminOrderSearchQuery(it) },
                            onSelectFilter = { viewModel.setAdminOrderFilterTab(it) },
                            onToggleExpandOrder = { viewModel.toggleAdminExpandedOrder(it) },
                            onAssignArtisan = { order, artisanSummary ->
                                viewModel.assignArtisanToOrder(order, artisanSummary)
                            },
                            onAdvanceOrderStage = { viewModel.advanceOrderStage(it) },
                            onShowBarcode = { viewModel.showBarcodeModal(it) },
                            onSetShowNewOrderDialog = { viewModel.setShowNewOrderDialog(it) },
                            onCreateAdminOrder = { name, phone, garment, service, price ->
                                viewModel.createNewAdminOrder(name, phone, garment, service, price)
                            },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }

                    AdminTab.ARTISAN_UPAH -> {
                        AdminArtisanUpahScreen(
                            artisans = artisans,
                            wageSlips = wageSlips,
                            selectedSkillFilter = uiState.adminArtisanFilter,
                            showNewTaskDialog = uiState.showNewTaskDialog,
                            payrollModalArtisan = uiState.payrollModalArtisan,
                            onSelectSkillFilter = { viewModel.setAdminArtisanFilter(it) },
                            onSetShowNewTaskDialog = { viewModel.setShowNewTaskDialog(it) },
                            onSetPayrollModalArtisan = { viewModel.setPayrollModalArtisan(it) },
                            onSettleArtisanWage = { viewModel.settleArtisanWage(it) },
                            onCreateWageSlip = { code, artisan, job, formatted, amount, notes ->
                                viewModel.createNewWageSlip(code, artisan, job, formatted, amount, notes)
                            },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }

                    AdminTab.KATALOG -> {
                        SewaKebayaScreen(
                            catalogItems = catalogItems,
                            searchQuery = uiState.sewaSearchQuery,
                            selectedCategory = uiState.sewaCategoryFilter,
                            selectedSort = uiState.sewaSortOption,
                            onSearchQueryChange = { viewModel.setSewaSearchQuery(it) },
                            onCategorySelect = { viewModel.setSewaCategoryFilter(it) },
                            onSortSelect = { viewModel.setSewaSortOption(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onOpenDetail = { viewModel.showCatalogDetail(it) },
                            onRentClick = { viewModel.rentKebayaNow(it) },
                            onNavigateToMeasureConsult = { viewModel.selectPatronTab(PatronTab.JAHIT) }
                        )
                    }

                    AdminTab.PENGATURAN -> {
                        AkunNarasiScreen(
                            onSwitchToAdminMode = { viewModel.setAdminMode(false) },
                            onShowSnackbar = { viewModel.showSnackbar(it) }
                        )
                    }
                }
            }
        }
    }

    // Active Modals
    uiState.selectedCatalogDetail?.let { item ->
        CatalogDetailModal(
            item = item,
            onDismiss = { viewModel.showCatalogDetail(null) },
            onRentNow = { viewModel.rentKebayaNow(it) },
            onCustomizeBespoke = { viewModel.selectPatronTab(PatronTab.JAHIT) }
        )
    }

    uiState.activeBarcodeOrder?.let { order ->
        BarcodeVoucherModal(
            order = order,
            onDismiss = { viewModel.showBarcodeModal(null) }
        )
    }

    uiState.reviewingOrder?.let { order ->
        OrderReviewModal(
            order = order,
            onDismiss = { viewModel.showReviewModal(null) },
            onSubmitReview = { reviewText -> viewModel.submitOrderReview(order, reviewText) }
        )
    }
}
