package com.ujm.sinsahelper.repository;

import com.ujm.sinsahelper.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //    List<Item> findByMainCategoryAndSubCategoryOrderByTotalScoreDesc(String mainCategory, String subCategory);
    List<Item> findByMainCategoryAndSubCategory(String mainCategory, String subCategory);
}
