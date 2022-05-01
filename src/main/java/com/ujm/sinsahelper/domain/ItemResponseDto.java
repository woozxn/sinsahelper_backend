package com.ujm.sinsahelper.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemResponseDto {

    private Long itemId;
    private String itemUrl;
    Long priceToday;
    Long priceYesterday;
    private String photo;

    public static ItemResponseDto toDto(Item item) {
        return new ItemResponseDto(
                item.getItemId(),
                item.getItemUrl(),
                item.getPriceToday(),
                item.getPriceYesterday(),
                item.getPhoto()
        );
    }
}
