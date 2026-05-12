package com.example.internship_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [SeatEntity::class, CommentEntity::class, PlayEntity::class, CastEntity::class],
    version = 2,
    exportSchema = false
)
abstract class NammaMelaDatabase : RoomDatabase() {
    abstract fun dao(): NammaMelaDao

    companion object {
        @Volatile
        private var instance: NammaMelaDatabase? = null

        fun getInstance(context: Context): NammaMelaDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NammaMelaDatabase::class.java,
                    "namma_mela.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { instance = it }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `play` (
                        `id` INTEGER NOT NULL,
                        `name` TEXT NOT NULL,
                        `duration` TEXT NOT NULL,
                        `posterUrl` TEXT NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `cast_members` (
                        `id` INTEGER NOT NULL,
                        `name` TEXT NOT NULL,
                        `role` TEXT NOT NULL,
                        `imageUrl` TEXT NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
