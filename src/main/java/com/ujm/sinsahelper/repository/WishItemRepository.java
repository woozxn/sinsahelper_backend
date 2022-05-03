package com.ujm.sinsahelper.repository;

import com.ujm.sinsahelper.domain.Item;
import com.ujm.sinsahelper.domain.Member;
import com.ujm.sinsahelper.domain.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long> {

    /**
     * A user -> have 10 Wishitem
     * 1. Wishitem SQL (1)
     * 2. WishItem -> Item SQL (10)
     * 1 + 10
     * 1 + n
     */
    @Query("SELECT W FROM WishItem W JOIN FETCH W.item I JOIN W.member M WHERE M.email = :email")
    // SELECT W FROM WishItem W JOIN W.item I ON W.item_id = I.item_id JOIN W.member M ON W.member_seq = M.member_seq WHERE M.email = 'abc@abc.com'
    // SELECT * FROM WishItem W JOIN W.item I ON W.item_id = I.item_id JOIN W.member M ON W.member_seq = M.member_seq WHERE M.email = 'abc@abc.com'
    List<WishItem> findByEmail(@Param("email") String email);


    @Query("SELECT W FROM WishItem W JOIN FETCH W.item I JOIN W.member M WHERE M.email = :email and I.itemId = :itemId")
    Optional<WishItem> findByEmailAndItemId(@Param("email") String email, @Param("itemId") Long itemId);
}
