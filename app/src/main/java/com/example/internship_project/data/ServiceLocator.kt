package com.example.internship_project.data

import android.content.Context

object ServiceLocator {
    @Volatile
    private var repository: NammaMelaRepository? = null

    fun repository(context: Context): NammaMelaRepository {
        return repository ?: synchronized(this) {
            repository ?: NammaMelaRepository(
                NammaMelaDatabase.getInstance(context).dao()
            ).also { repository = it }
        }
    }
}
