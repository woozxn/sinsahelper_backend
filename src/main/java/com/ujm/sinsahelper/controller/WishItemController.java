package com.ujm.sinsahelper.controller;

import com.ujm.sinsahelper.domain.ItemResponseDto;
import com.ujm.sinsahelper.service.Wish.WishItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WishItemController {

    private final WishItemService wishItemService;

    @PostMapping(value = "/wish/{id}")
    public void wish(@PathVariable(value = "id") Long item_id) {

        wishItemService.addWishItem(item_id);

    }

    @GetMapping(value = "/wish/list")
    public List<ItemResponseDto> wishList() {
        return wishItemService.getWishList();
    }

}
