package com.example.data

import kotlinx.coroutines.flow.Flow

class AtelierRepository(private val dao: AtelierDao) {
    val catalogItems: Flow<List<KebayaCatalogItem>> = dao.getAllCatalogItems()
    val orders: Flow<List<AtelierOrder>> = dao.getAllOrders()
    val measurementDossier: Flow<MeasurementDossier?> = dao.getMeasurementDossier()
    val artisans: Flow<List<ArtisanMember>> = dao.getAllArtisans()
    val wageSlips: Flow<List<WageSlip>> = dao.getAllWageSlips()

    suspend fun ensureSeeded() {
        if (dao.getCatalogCount() == 0) {
            dao.insertCatalogItems(AtelierSeedData.initialCatalog)
            dao.insertOrders(AtelierSeedData.initialOrders)
            dao.saveMeasurementDossier(MeasurementDossier())
            dao.insertArtisans(AtelierSeedData.initialArtisans)
            dao.insertWageSlips(AtelierSeedData.initialWageSlips)
        }
    }

    suspend fun toggleFavorite(item: KebayaCatalogItem) {
        dao.updateCatalogItem(item.copy(isFavorite = !item.isFavorite))
    }

    suspend fun saveDossier(dossier: MeasurementDossier) {
        dao.saveMeasurementDossier(dossier)
    }

    suspend fun addOrder(order: AtelierOrder) {
        dao.insertOrder(order)
    }

    suspend fun updateOrder(order: AtelierOrder) {
        dao.updateOrder(order)
    }

    suspend fun updateArtisan(artisan: ArtisanMember) {
        dao.updateArtisan(artisan)
    }

    suspend fun addArtisan(artisan: ArtisanMember) {
        dao.insertArtisans(listOf(artisan))
    }

    suspend fun addWageSlip(slip: WageSlip) {
        dao.insertWageSlip(slip)
    }

    suspend fun updateWageSlip(slip: WageSlip) {
        dao.updateWageSlip(slip)
    }
}
