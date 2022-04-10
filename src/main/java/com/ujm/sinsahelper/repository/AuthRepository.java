package com.ujm.sinsahelper.repository;

import com.ujm.sinsahelper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Member, Long> {


    /**
     * 밑에 두개는 다 같은 역할 수행
     */

//    Optional<Member> findMemberByEmail(@Param("email") String email);

    @Query(value = "SELECT M FROM Member M WHERE M.email = :email")
    Optional<Member> findMemberByEmail(@Param("email") String email);

}
