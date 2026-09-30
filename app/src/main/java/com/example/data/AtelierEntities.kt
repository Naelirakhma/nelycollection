package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kebaya_catalog")
data class KebayaCatalogItem(
    @PrimaryKey val id: String,
    val title: String,
    val categoryFilter: String, // "Kebaya Kutubaru", "Kebaya Kartini", "Kebaya Modern", "Bridal/Akad", "Kebaya Encim"
    val badgeTag: String,       // e.g. "Kutubaru Klasik", "Kartini Modern", "Wisuda / Lamaran", "Royal Wedding Edition"
    val colorPalette: String,
    val sizeInfo: String,
    val priceValue: Int,
    val priceFormatted: String,
    val durationLabel: String = "/ 3 Hari",
    val isAvailable: Boolean = true,
    val isFavorite: Boolean = false,
    val isFeaturedCarousel: Boolean = false,
    val popularityRank: Int = 1,
    val imageUrl: String,
    val description: String
)

@Entity(tableName = "atelier_orders")
data class AtelierOrder(
    @PrimaryKey val orderCode: String,
    val serviceType: String,    // "Jasa Jahit Bespoke", "Sewa Kebaya Eksklusif", "Bespoke Royal Pengantin"
    val garmentTitle: String,
    val subNote: String,        // e.g. "Ukur Mandiri (Dossier Terverifikasi)" or "Ukuran M • Durasi: 3 Hari"
    val clientName: String,
    val clientPhone: String,
    val statusKey: String,      // "menunggu", "diproses", "siap", "selesai", "dibatalkan"
    val statusBadge: String,    // "Diproses", "Siap Diambil", "Selesai", "Menunggu DP 50%"
    val priceFormatted: String,
    val paymentNote: String,    // "(DP Rp 750.000 Lunas)", "(Deposit Rp 200.000)", "(Lunas Penuh)"
    val dpPaidFormatted: String,
    val remainingFormatted: String,
    val currentStep: Int,       // 1..5
    val totalSteps: Int = 5,
    val tailorNote: String,
    val pickupLocation: String,
    val pickupHours: String,
    val fittingDateNote: String,
    val artisanAssigned: String,
    val imageUrl: String,
    val ldCm: Int = 88,
    val lpCm: Int = 70,
    val pinggulCm: Int = 96,
    val bahuCm: Int = 38,
    val lenganCm: Int = 54,
    val panjangCm: Int = 78,
    val barcodeNumber: String = "2024 072 901",
    val userReview: String = ""
)

@Entity(tableName = "measurement_dossier")
data class MeasurementDossier(
    @PrimaryKey val id: Int = 1,
    val method: String = "boutique", // "boutique" or "self"
    val scheduleDate: String = "Sabtu, 28 Okt 2024",
    val scheduleTime: String = "14:00 - 15:30 WIB",
    val lingkarDada: String = "88",
    val lingkarPinggang: String = "70",
    val lingkarPinggul: String = "94",
    val lebarBahu: String = "38",
    val panjangLengan: String = "54",
    val panjangKebaya: String = "75",
    val panjangRok: String = "98",
    val tinggiBadan: String = "165",
    val selectedService: String = "Kebaya Kutubaru Adibusana",
    val clientName: String = "Ibu Ratna Prawira",
    val clientPhone: String = "+62 812-8899-2024",
    val fabricChoice: String = "Beludru Burgundy & Sutra Sekar Jagad"
)

@Entity(tableName = "artisan_members")
data class ArtisanMember(
    @PrimaryKey val id: String,
    val name: String,
    val initials: String,
    val roleTitle: String,
    val bankInfo: String,
    val phone: String,
    val yearsExperience: String,
    val skillCategory: String, // "Master Tailor & Pola", "Bordir & Payet"
    val workloadLabel: String, // "Beban Produksi (14 Busana)"
    val completedCount: Int,
    val inProgressCount: Int,
    val totalWageFormatted: String,
    val paidWageFormatted: String,
    val pendingWageValue: Int,
    val pendingWageFormatted: String,
    val isVerified: Boolean,
    val avatarUrl: String,
    val narrativeBio: String = ""
)

@Entity(tableName = "wage_slips")
data class WageSlip(
    @PrimaryKey val slipCode: String,
    val dateTimeLabel: String,
    val artisanId: String,
    val artisanName: String,
    val artisanRole: String,
    val orderCode: String,
    val eventTag: String,
    val clientName: String,
    val taskTitle: String,
    val taskDetail: String,
    val wageValue: Int,
    val wageFormatted: String,
    val rateTypeLabel: String,
    val workStatus: String,     // "Selesai", "Dikerjakan", "Proses Jahit (80%)"
    val isPaid: Boolean,
    val paymentStatusLabel: String // "PENDING", "LUNAS via BCA", "LUNAS via Mandiri"
)
