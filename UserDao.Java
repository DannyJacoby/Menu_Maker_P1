import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;



@Dao
public abstract class UserDao {


    @Query("select count(*) from users")
    public abstract int count();

    @Query("select * from users")
    public abstract List<User> getAllUser();

    @Query("select * from users where the_username = :username")
    public abstract User fineByName(String username);

    @Query("select * from users where the_password = :password")
    public abstract User checkpassword(String password);

    @Query("select * from users where userId = :userId")
    public abstract User findById(int userId);

    @Insert
    public abstract long addUser(User user);

    @Insert
    public abstract long[] insertAgents(User... users);
}

