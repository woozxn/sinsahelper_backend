package com.ujm.sinsahelper.controller;


import com.ujm.sinsahelper.domain.ItemDto;
import com.ujm.sinsahelper.service.Item.CrawlingService;
import com.ujm.sinsahelper.service.Item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CrawlingService crawlingService;

    @GetMapping(value = "/getSearch")
    public String getSearch(@RequestParam("mainCategory") String mainCategory, @RequestParam("subCategory") String subCategory,
                            @RequestParam("deliveryPreference") Long deliveryPreference, @RequestParam("sizePreference") Long sizePreference,
                            @RequestParam("qualityPreference") Long qualityPreference, Model model) {
        System.out.println("mainCategory = " + mainCategory);
        System.out.println("subCategory = " + subCategory);
        System.out.println("deliveryPreference = " + deliveryPreference);
        System.out.println("sizePreference = " + sizePreference);
        System.out.println("qualityPreference = " + qualityPreference);

        List<ItemDto> items = itemService.findItemByPreference(mainCategory, subCategory, deliveryPreference, sizePreference, qualityPreference);


        for(ItemDto i : items){
            System.out.println(i);
        }


        model.addAttribute("items", items);
        return "redirect:/Search/SearchResult";
    }


    // KCH : test용으로 만든 컨트롤러
    @GetMapping(value = "/test")
    public String test() {
        ItemDto a = new ItemDto();
        a.setItemUrl("https://store.musinsa.com/app/goods/1557508");
        a.setMainCategory("상의");
        a.setSubCategory("맨투맨");

        a.setPriceToday(crawlingService.CrawlingPrice(a.getItemUrl()));
        a.setPriceYesterday("0");
        a.setPhoto(crawlingService.CrawlingPhoto(a.getItemUrl()));
        a.setReview(crawlingService.CrawlingReview(a.getItemUrl()));

        a.setDeliveryScore(12L);
        a.setSizeScore(22L);
        a.setQualityScore(23L);
        itemService.saveItem(a);

        ItemDto b = new ItemDto();
        b.setItemUrl("https://store.musinsa.com/app/goods/2038497");
        b.setMainCategory("하의");
        b.setSubCategory("맨투맨");

        b.setPriceToday(crawlingService.CrawlingPrice(b.getItemUrl()));
        b.setPriceYesterday("0");
        b.setPhoto(crawlingService.CrawlingPhoto(b.getItemUrl()));
        b.setReview(crawlingService.CrawlingReview(b.getItemUrl()));

        b.setDeliveryScore(13L);
        b.setSizeScore(14L);
        b.setQualityScore(22L);
        itemService.saveItem(b);

        ItemDto c = new ItemDto();
        c.setItemUrl("https://store.musinsa.com/app/goods/2085371");
        c.setMainCategory("상의");
        c.setSubCategory("반팔");

        c.setPriceToday(crawlingService.CrawlingPrice(c.getItemUrl()));
        c.setPriceYesterday("0");
        c.setPhoto(crawlingService.CrawlingPhoto(c.getItemUrl()));
        c.setReview(crawlingService.CrawlingReview(c.getItemUrl()));

        c.setDeliveryScore(55L);
        c.setSizeScore(1L);
        c.setQualityScore(1L);
        itemService.saveItem(c);

        ItemDto d = new ItemDto();
        d.setItemUrl("https://store.musinsa.com/app/goods/947067");
        d.setMainCategory("상의");
        d.setSubCategory("맨투맨");

        d.setPriceToday(crawlingService.CrawlingPrice(d.getItemUrl()));
        d.setPriceYesterday("0");
        d.setPhoto(crawlingService.CrawlingPhoto(d.getItemUrl()));
        d.setReview(crawlingService.CrawlingReview(d.getItemUrl()));

        d.setDeliveryScore(1L);
        d.setSizeScore(55L);
        d.setQualityScore(1L);
        itemService.saveItem(d);

        ItemDto e = new ItemDto();
        e.setItemUrl("https://store.musinsa.com/app/goods/1582356");
        e.setMainCategory("상의");
        e.setSubCategory("맨투맨");

        e.setPriceToday(crawlingService.CrawlingPrice(e.getItemUrl()));
        e.setPriceYesterday("0");
        e.setPhoto(crawlingService.CrawlingPhoto(e.getItemUrl()));
        e.setReview(crawlingService.CrawlingReview(e.getItemUrl()));

        e.setDeliveryScore(1L);
        e.setSizeScore(1L);
        e.setQualityScore(55L);
        itemService.saveItem(e);

        return "hello";
    }



}
