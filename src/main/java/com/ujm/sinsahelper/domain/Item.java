package com.ujm.sinsahelper.domain;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    private String itemName;
    private String itemUrl;

    // KCH : MainCate와 SubCate embedded로 바꾸는 거 고려하기
    private String mainCategory;
    private String subCategory;

    private Long priceToday;
    private Long priceYesterday;
    private String photo;
    private String review;

    // KCH : django에서 DB에 넣는 부분
    private Long deliveryScore;
    private Long sizeScore;
    private Long qualityScore;

    @OneToMany(mappedBy = "item")
    private List<WishItem> wishItems = new ArrayList<>();
    // preference * score 한 값들을 다 더한 것
//    private Long totalScore;
    @Builder
    public Item(Long itemId, String itemName, String itemUrl, String mainCategory, String subCategory, Long priceToday, Long priceYesterday, String photo, String review, Long deliveryScore, Long sizeScore, Long qualityScore) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemUrl = itemUrl;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.priceToday = priceToday;
        this.priceYesterday = priceYesterday;
        this.photo = photo;
        this.review = review;
        this.deliveryScore = deliveryScore;
        this.sizeScore = sizeScore;
        this.qualityScore = qualityScore;
    }
}