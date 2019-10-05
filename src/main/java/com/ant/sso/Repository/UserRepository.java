package com.ant.sso.Repository;

import com.ant.sso.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("from User where email=:email and password =:password")
    User userLoginByEmail(@Param("email") String email, @Param("password")String password);

    @Query("from User where (email=:key or userName=:key or cellphone=:key) and password=:password")
    User userLogin(@Param("key")String key,@Param("password")String password);

    @Query("from User where email=:key or userName=:key or cellphone=:key")
    User loginFind(@Param("key")String key);

    @Query("from User where userId=:userId")
    User queryById(@Param("userId") Long userId);
}
