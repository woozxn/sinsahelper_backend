package com.ujm.sinsahelper.service.Wish;

import com.ujm.sinsahelper.domain.Item;
import com.ujm.sinsahelper.domain.Member;
import com.ujm.sinsahelper.domain.WishItem;
import com.ujm.sinsahelper.repository.AuthRepository;
import com.ujm.sinsahelper.repository.ItemRepository;
import com.ujm.sinsahelper.repository.WishItemRepository;
import com.ujm.sinsahelper.domain.ItemResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishItemService {
    private final AuthRepository authRepository;
    private final ItemRepository itemRepository;
    private final WishItemRepository wishItemRepository;

    @Transactional
    public void addWishItem(Long item_id) {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //email

        Optional<Member> MemberbyEmail = authRepository.findMemberByEmail(email);
        Member findMember = MemberbyEmail.get();
        Item findItem = itemRepository.findItemByItemId(item_id);

        WishItem createWishItem = WishItem.builder()
                .item(findItem)
                .member(findMember)
                .build();

        wishItemRepository.save(createWishItem);
    }

    @Transactional
    public void deleteWishItem(Long item_id) {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //email

        Optional<WishItem> WishItem = wishItemRepository.findByEmailAndItemId(email, item_id);
        if(WishItem.isPresent()){
            wishItemRepository.delete(WishItem.get());
        }

    }

    @Transactional
    public List<ItemResponseDto> getWishList() {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //email
        List<WishItem> wishItemList = wishItemRepository.findByEmail(email);

        List<ItemResponseDto> resultList = wishItemList.stream()
                .map(wishItem -> ItemResponseDto.toDto(wishItem.getItem()))
                .collect(Collectors.toList());

        return resultList;
    }


    @Transactional
    public boolean getWishByEmailAndItemId(Long item_id) {
        String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()); //email

        Optional<WishItem> WishItem = wishItemRepository.findByEmailAndItemId(email, item_id);

        if(WishItem.isEmpty()){
            return false;
        }
        else{
            return true;
        }

    }

}
