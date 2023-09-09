package com.example.fragmenttest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Query("SELECT COUNT(*) FROM Game")
    int getCount();

    @Query("SELECT SUM(`fail`) FROM Game")
    int getFailCount();

    @Query("SELECT MAX(delaySec) FROM Game")
    int getMaxDelay();

    @Query("SELECT MIN(delaySec) FROM Game")
    int getMinDelay();

    /*@Query("SELECT delaySec FROM Game " +
            "WHERE num1 IN(num2, num3, num4) OR num2 IN(num3, num4) OR num3 IN(num4)")
    int getPair();

    @Query("SELECT delaySec FROM Game " +
            "WHERE (num1 =num2 AND num3 = num4)OR(num1 = num3 AND num2 = num4)OR(num1 = num4 AND num2 = num3)")
    int getTwoPair();
*/
    @Insert
    void insert(Game game);

    @Update
    void Update(Game game);

    @Delete
    void Delete(Game game);
}
