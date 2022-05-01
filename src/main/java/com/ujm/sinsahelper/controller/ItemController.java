package com.ujm.sinsahelper.controller;


import com.ujm.sinsahelper.common.response.ErrorResponse;
import com.ujm.sinsahelper.domain.ItemDto;
import com.ujm.sinsahelper.service.Item.CrawlingService;
import com.ujm.sinsahelper.service.Item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final CrawlingService crawlingService;

    @GetMapping(value = "/search/result")
    public ResponseEntity<List<ItemDto>> getSearch(@RequestParam("mainCategory") String mainCategory, @RequestParam("subCategory") String subCategory,
                                                   @RequestParam("deliveryPreference") Long deliveryPreference, @RequestParam("sizePreference") Long sizePreference,
                                                   @RequestParam("qualityPreference") Long qualityPreference) {

        List<ItemDto> items = itemService.findItemByPreference(mainCategory, subCategory,deliveryPreference,sizePreference,qualityPreference);

        System.out.println("mainCategory = " + mainCategory);
        System.out.println("qualityPreference = " + qualityPreference);
        for(ItemDto i : items){
            System.out.println(i.getTotalScore());
        }
//        Long pnum = items.get(0).getItemId();

//        쟝고 신호 보내는 코드
//        try{
//            URL url = new URL("http://localhost:8000/api/connect/");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("pk", String.valueOf(pnum));
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String inputline;
//            String resultXmlText ="";
//            while((inputline= br.readLine())!=null){
//                resultXmlText+=inputline;
//            }
//            System.out.println(resultXmlText);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }


    @GetMapping(value = "/item")
    public ResponseEntity<Object> addItem(@RequestParam("url") String url) throws IOException {
        System.out.println("url = " + url);
        ItemDto item = new ItemDto();
        item.setItemUrl(url);
        item.setReview(crawlingService.crawlingReview(url));
        crawlingService.crawlingItemInfo(item, url);
        item.setPriceToday(crawlingService.crawlingPrice(url));

        Long result = itemService.saveItem(item);
        //중복 값일 경우 errorResponse 생성 후, 반납, 아닐 경우 item객체 반납
        if(result<0){
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("redundant item"));
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(itemService.findOne(result));


        //        쟝고 신호 보내는 코드
//        try{
//            URL django = new URL("http://localhost:8000/api/connect/");
//            HttpURLConnection conn = (HttpURLConnection) django.openConnection();
//            conn.setRequestMethod("GET");
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String inputline;
//            String resultXmlText ="";
//            while((inputline= br.readLine())!=null){
//                resultXmlText+=inputline;
//            }
//            System.out.println(resultXmlText);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }


    @Scheduled(cron = "0 0 5 * * * ")
    public String priceUpdate() throws IOException {
        itemService.updateItemPrice();
        return "hello";
    }

////    // KCH : test용으로 만든 컨트롤러
//    @GetMapping(value = "/test")
//    public String test() {
//        ItemDto a = new ItemDto();
//        a.setItemUrl("https://store.musinsa.com/app/goods/1557508");
//        a.setMainCategory("상의");
//        a.setSubCategory("맨투맨");
//
//        a.setPriceToday(crawlingService.CrawlingPrice(a.getItemUrl()));
//        a.setPriceYesterday("0");
//        a.setPhoto(crawlingService.CrawlingPhoto(a.getItemUrl()));
//        a.setReview(crawlingService.crawlingReview(a.getItemUrl()));
//
//        a.setDeliveryScore(12L);
//        a.setSizeScore(22L);
//        a.setQualityScore(23L);
//        itemService.saveItem(a);
//
//
//        return "hello";
//    }



}