package com.ujm.sinsahelper.service.Item.dto;

import com.ujm.sinsahelper.domain.Item;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemResponseDto {

    private Long itemId;
    private String itemUrl;
    private String priceToday;
    private String priceYesterday;
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
