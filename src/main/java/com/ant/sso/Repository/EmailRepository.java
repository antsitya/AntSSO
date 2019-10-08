package com.ant.sso.Repository;

import com.ant.sso.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {
    @Query("from Email where state = 0 or state=2")
    List<Email> findAllNotSend();
}
