package com.ujm.sinsahelper.domain;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemDto {
    private Long itemId;

    //    private String itemName;
    private String itemUrl;

    private String itemName;

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

    // preference * score 한 값들을 다 더한 것
    private Long totalScore;

    private boolean like;

    public Item toEntity(){
        Item build = Item.builder()
                .itemId(itemId)
                .itemName(itemName)
                .itemUrl(itemUrl)
                .mainCategory(mainCategory)
                .subCategory(subCategory)
                .priceToday(priceToday)
                .priceYesterday(priceYesterday)
                .photo(photo)
                .review(review)
                .deliveryScore(deliveryScore)
                .sizeScore(sizeScore)
                .qualityScore(qualityScore)
                .build();
        return build;
    }

    @Builder
    public ItemDto(Long itemId, String itemName, String itemUrl, String mainCategory, String subCategory, Long priceToday, Long priceYesterday, String photo, String review, Long deliveryScore, Long sizeScore, Long qualityScore, boolean like) {
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
        this.like = like;
    }
}
