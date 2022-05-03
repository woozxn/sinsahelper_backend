package com.ujm.sinsahelper.controller;

import com.ujm.sinsahelper.domain.ItemResponseDto;
import com.ujm.sinsahelper.service.Wish.WishItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WishItemController {

    private final WishItemService wishItemService;

    @GetMapping(value = "/wish/like/{id}")
    public String like(@PathVariable(value = "id") Long item_id) {
        System.out.println("item_id = " + item_id);
        wishItemService.addWishItem(item_id);

        return "suscess";
    }

    @DeleteMapping(value = "/wish/dislike/{id}")
    public void dislike(@PathVariable(value = "id") Long item_id) {

        wishItemService.deleteWishItem(item_id);

    }

    @GetMapping(value = "/wish/list")
    public List<ItemResponseDto> wishList() {
        return wishItemService.getWishList();
    }

}
