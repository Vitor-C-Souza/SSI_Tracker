package com.ssitracker.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssitracker.app.data.local.entity.SSIEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SSIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSSI(response: SSIEntity)

    @Query("SELECT * FROM ssi_table")
    fun getAllSSI(): Flow<List<SSIEntity>>

    @Query("SELECT * FROM ssi_table WHERE id = :id")
    suspend fun getSSIById(id: String): SSIEntity?

    @Query("DELETE FROM ssi_table WHERE id = :id")
    suspend fun deleteSSIById(id: String)
}