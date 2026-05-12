package com.example.internship_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NammaMelaDao {
    @Query("SELECT * FROM seats ORDER BY `row`, `column`")
    fun observeSeats(): Flow<List<SeatEntity>>

    @Query("SELECT COUNT(*) FROM seats")
    suspend fun seatCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSeats(seats: List<SeatEntity>)

    @Query("UPDATE seats SET isReserved = 1 WHERE id = :seatId AND isReserved = 0")
    suspend fun reserveSeat(seatId: String): Int

    @Query("UPDATE seats SET isReserved = 0")
    suspend fun resetAllSeats()

    @Query("SELECT * FROM play WHERE id = 1")
    fun observePlay(): Flow<PlayEntity?>

    @Query("SELECT COUNT(*) FROM play")
    suspend fun playCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlay(play: PlayEntity)

    @Query("SELECT * FROM cast_members ORDER BY id")
    fun observeCast(): Flow<List<CastEntity>>

    @Query("SELECT COUNT(*) FROM cast_members")
    suspend fun castCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCast(cast: List<CastEntity>)

    @Query("SELECT * FROM comments ORDER BY timestamp DESC")
    fun observeComments(): Flow<List<CommentEntity>>

    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Query("DELETE FROM comments")
    suspend fun clearComments()
}
