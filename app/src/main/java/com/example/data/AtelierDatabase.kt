package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AtelierDao {
    @Query("SELECT * FROM kebaya_catalog ORDER BY popularityRank ASC")
    fun getAllCatalogItems(): Flow<List<KebayaCatalogItem>>

    @Query("SELECT COUNT(*) FROM kebaya_catalog")
    suspend fun getCatalogCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogItems(items: List<KebayaCatalogItem>)

    @Update
    suspend fun updateCatalogItem(item: KebayaCatalogItem)

    @Query("SELECT * FROM atelier_orders")
    fun getAllOrders(): Flow<List<AtelierOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<AtelierOrder>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: AtelierOrder)

    @Update
    suspend fun updateOrder(order: AtelierOrder)

    @Query("SELECT * FROM measurement_dossier WHERE id = 1")
    fun getMeasurementDossier(): Flow<MeasurementDossier?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMeasurementDossier(dossier: MeasurementDossier)

    @Query("SELECT * FROM artisan_members")
    fun getAllArtisans(): Flow<List<ArtisanMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtisans(artisans: List<ArtisanMember>)

    @Update
    suspend fun updateArtisan(artisan: ArtisanMember)

    @Query("SELECT * FROM wage_slips")
    fun getAllWageSlips(): Flow<List<WageSlip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWageSlips(slips: List<WageSlip>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWageSlip(slip: WageSlip)

    @Update
    suspend fun updateWageSlip(slip: WageSlip)
}

@Database(
    entities = [
        KebayaCatalogItem::class,
        AtelierOrder::class,
        MeasurementDossier::class,
        ArtisanMember::class,
        WageSlip::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AtelierDatabase : RoomDatabase() {
    abstract fun atelierDao(): AtelierDao

    companion object {
        @Volatile
        private var INSTANCE: AtelierDatabase? = null

        fun getDatabase(context: Context): AtelierDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AtelierDatabase::class.java,
                    "nely_atelier_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
