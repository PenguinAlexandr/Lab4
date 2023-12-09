package stu.cn.ua.lab4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Launch> launches);

    @Query("SELECT * FROM launches")
    List<Launch> getAllLaunches();
}
