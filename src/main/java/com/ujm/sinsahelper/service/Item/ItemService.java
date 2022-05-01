package com.ujm.sinsahelper.service.Item;


import com.ujm.sinsahelper.domain.Item;
import com.ujm.sinsahelper.domain.ItemDto;
import com.ujm.sinsahelper.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CrawlingService crawlingService;

    @Transactional
    public Long saveItem(ItemDto itemDto){
        String url = itemDto.getItemUrl();
        if(!itemRepository.existsByItemUrl(url)){
            return itemRepository.save(itemDto.toEntity()).getItemId();
        }
        else{
            return -1L;
        }
    }

    @Transactional
    public void updateItemPrice() throws IOException {
        List<Item> items = itemRepository.findAll();
        for(Item item : items){
            System.out.println("item.getPriceToday() = " + item.getPriceToday());
            item.setPriceYesterday(item.getPriceToday());
            item.setPriceToday(crawlingService.crawlingPrice(item.getItemUrl()));
        }
    }


    public List<ItemDto> findItems() {
        List<Item> itemList = itemRepository.findAll();
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDto itemDto= ItemDto.builder()
                    .itemId(item.getItemId())
                    .itemName(item.getItemName())
                    .itemUrl(item.getItemUrl())
                    .mainCategory(item.getMainCategory())
                    .subCategory(item.getSubCategory())
                    .priceToday(item.getPriceToday())
                    .priceYesterday(item.getPriceYesterday())
                    .photo(item.getPhoto())
                    .review(item.getPhoto())
                    .deliveryScore(item.getDeliveryScore())
                    .sizeScore(item.getSizeScore())
                    .qualityScore(item.getQualityScore())
                    .build();
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

    public ItemDto findOne(Long itemId) {
        Item item = itemRepository.findById(itemId).get();
        ItemDto itemDto= ItemDto.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .itemUrl(item.getItemUrl())
                .mainCategory(item.getMainCategory())
                .subCategory(item.getSubCategory())
                .priceToday(item.getPriceToday())
                .priceYesterday(item.getPriceYesterday())
                .photo(item.getPhoto())
                .review(item.getPhoto())
                .deliveryScore(item.getDeliveryScore())
                .sizeScore(item.getSizeScore())
                .qualityScore(item.getQualityScore())
                .build();
        return itemDto;
    }

    public List<ItemDto> findItemByPreference(String mainCategory, String subCategory, Long dPref, Long sPref, Long qPref) {
        List<Item> itemList = itemRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory);
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDto itemDto= ItemDto.builder()
                    .itemId(item.getItemId())
                    .itemName(item.getItemName())
                    .itemUrl(item.getItemUrl())
                    .mainCategory(item.getMainCategory())
                    .subCategory(item.getSubCategory())
                    .priceToday(item.getPriceToday())
                    .priceYesterday(item.getPriceYesterday())
                    .photo(item.getPhoto())
                    .review(item.getPhoto())
                    .deliveryScore(item.getDeliveryScore())
                    .sizeScore(item.getSizeScore())
                    .qualityScore(item.getQualityScore())
                    .build();
            itemDtoList.add(itemDto);
        }
        for (ItemDto item : itemDtoList) {
            Long totalScore = calc(item, dPref, sPref, qPref);
            item.setTotalScore(totalScore);
            System.out.println(item.getTotalScore());
        }

        itemDtoList.sort(new Comparator<ItemDto>() {
            @Override
            public int compare(ItemDto o1, ItemDto o2) {
                return (int) (o2.getTotalScore()- o1.getTotalScore());
            }
        });
        return itemDtoList;
    }

    private Long calc(ItemDto itemDto, Long dPref, Long sPref, Long qPref){
        return itemDto.getDeliveryScore()*dPref+ itemDto.getSizeScore()*sPref+ itemDto.getQualityScore()*qPref;
    }
}