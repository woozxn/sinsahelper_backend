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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        System.out.println("mainCategory = " + mainCategory);
        System.out.println("subCategory = " + subCategory);
        List<ItemDto> items = itemService.findItemByPreference(mainCategory, subCategory,deliveryPreference,sizePreference,qualityPreference);


        System.out.println("qualityPreference = " + qualityPreference);
//        for(ItemDto i : items){
//            System.out.println(i.getTotalScore());
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

//        //KCH Django 연결전 score를 설정해주는 부분
//        item.setDeliveryScore(Long.valueOf((int)(Math.random()*100) + 1));
//        item.setSizeScore(Long.valueOf((int)(Math.random()*100) + 1));
//        item.setQualityScore(Long.valueOf((int)(Math.random()*100) + 1));


        Long result = itemService.saveItem(item);
        //중복 값일 경우 errorResponse 생성 후, 반납, 아닐 경우 item객체 반납
        if(result<0){
            return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("redundant item"));
        }
        //        쟝고 신호 보내는 코드
        String connectUrl = "http://localhost:8000/api/connect/";
        connectUrl+="?"+"item_id="+result;

        try{
            URL django = new URL(connectUrl);
            HttpURLConnection conn = (HttpURLConnection) django.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputline;
            String resultXmlText ="";
            while((inputline= br.readLine())!=null){
                resultXmlText+=inputline;
            }
            System.out.println(resultXmlText);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(itemService.findOne(result));
    }


    @Scheduled(cron = "0 0 5 * * * ")
    public void priceUpdate() throws IOException {
        System.out.println("시작");
        itemService.updateItemPrice();

        //        쟝고 신호 보내는 코드
        String connectUrl = "http://localhost:8000/api/connect/";
        try{
            URL django = new URL(connectUrl);
            HttpURLConnection conn = (HttpURLConnection) django.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputline;
            String resultXmlText ="";
            while((inputline= br.readLine())!=null){
                resultXmlText+=inputline;
            }
            System.out.println(resultXmlText);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}