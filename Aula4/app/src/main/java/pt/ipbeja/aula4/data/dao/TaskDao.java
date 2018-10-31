package pt.ipbeja.aula4.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ipbeja.aula4.data.Task;

@Dao
public interface TaskDao {

    @Insert
    long insert(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);

    @Query("SELECT * from tasks")
    List<Task> getAllTasks();

    @Query("SELECT * from tasks WHERE id = :taskId")
    Task getTask(long taskId);


}













