package com.example.internship_project.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NammaMelaRepository(private val dao: NammaMelaDao) {
    val seats: Flow<List<SeatEntity>> = dao.observeSeats()
    val comments: Flow<List<CommentEntity>> = dao.observeComments()
    val play: Flow<PlayEntity> = dao.observePlay().map { it ?: DefaultPlay }
    val cast: Flow<List<CastEntity>> = dao.observeCast().map { it.ifEmpty { DefaultCast } }

    suspend fun ensureSeeded() {
        ensureSeatsSeeded()
        ensurePlaySeeded()
        ensureCastSeeded()
    }

    private suspend fun ensureSeatsSeeded() {
        if (dao.seatCount() == 0) {
            val seats = (1..5).flatMap { row ->
                (1..8).map { column ->
                    SeatEntity(
                        id = "${row}-${column}",
                        row = row,
                        column = column,
                        isReserved = false
                    )
                }
            }
            dao.insertSeats(seats)
        }
    }

    private suspend fun ensurePlaySeeded() {
        if (dao.playCount() == 0) {
            dao.upsertPlay(DefaultPlay)
        }
    }

    private suspend fun ensureCastSeeded() {
        if (dao.castCount() == 0) {
            dao.upsertCast(DefaultCast)
        }
    }

    suspend fun reserveSeat(seatId: String): Boolean {
        return dao.reserveSeat(seatId) == 1
    }

    suspend fun resetSeats() {
        ensureSeatsSeeded()
        dao.resetAllSeats()
    }

    suspend fun resetFanComments() {
        dao.clearComments()
    }

    suspend fun savePlay(name: String, duration: String, posterUrl: String) {
        dao.upsertPlay(
            PlayEntity(
                name = name.trim().ifBlank { DefaultPlay.name },
                duration = duration.trim().ifBlank { DefaultPlay.duration },
                posterUrl = posterUrl.trim().ifBlank { DefaultPlay.posterUrl }
            )
        )
    }

    suspend fun saveCast(cast: List<CastEntity>) {
        dao.upsertCast(
            cast.map { member ->
                member.copy(
                    name = member.name.trim().ifBlank { "Actor ${member.id}" },
                    role = member.role.trim().ifBlank { "Performer" },
                    imageUrl = member.imageUrl.trim().ifBlank { DefaultCast.first().imageUrl }
                )
            }
        )
    }

    suspend fun saveShowAndResetSeats(
        name: String,
        duration: String,
        posterUrl: String,
        cast: List<CastEntity>
    ) {
        savePlay(name, duration, posterUrl)
        saveCast(cast)
        resetSeats()
        resetFanComments()
    }

    suspend fun addComment(text: String) {
        dao.insertComment(
            CommentEntity(
                text = text,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    companion object {
        val DefaultPlay = PlayEntity(
            name = "Karnataka Veera Sangama",
            duration = "2h 20m",
            posterUrl = "https://images.unsplash.com/photo-1503095396549-807759245b35?auto=format&fit=crop&w=900&q=80"
        )

        val DefaultCast = listOf(
            CastEntity(
                id = 1,
                name = "Asha Rao",
                role = "Lead",
                imageUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=400&q=80"
            ),
            CastEntity(
                id = 2,
                name = "Raghav Hegde",
                role = "Comedian",
                imageUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80"
            ),
            CastEntity(
                id = 3,
                name = "Meera Kulkarni",
                role = "Singer",
                imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=400&q=80"
            )
        )
    }
}
