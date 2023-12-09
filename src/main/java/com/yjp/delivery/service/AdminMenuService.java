package com.yjp.delivery.service;

import com.yjp.delivery.common.validator.MenuValidator;
import com.yjp.delivery.common.validator.ShopValidator;
import com.yjp.delivery.controller.admin.menu.dto.request.AddMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.DeleteMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.request.UpdateMenuReq;
import com.yjp.delivery.controller.admin.menu.dto.response.AddMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.DeleteMenuRes;
import com.yjp.delivery.controller.admin.menu.dto.response.UpdateMenuRes;
import com.yjp.delivery.service.provider.S3Provider;
import com.yjp.delivery.store.entity.MenuEntity;
import com.yjp.delivery.store.entity.ShopEntity;
import com.yjp.delivery.store.repository.MenuRepository;
import com.yjp.delivery.store.repository.ShopRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AdminMenuService {

    private final ShopRepository shopRepository;
    private final MenuRepository menuRepository;
    private final S3Provider s3Provider;

    @Value("${cloud.aws.s3.bucket.url}")
    private String url;

    public AddMenuRes addMenu(MultipartFile multipartFile, AddMenuReq addMenuReq)
        throws IOException {
        ShopEntity shopEntity = findShop(addMenuReq.getShopId());
        String imageUrl = s3Provider.saveFile(multipartFile, "menu");
        return AdminMenuServiceMapper.INSTANCE.toAddMenuRes(
            menuRepository.save(MenuEntity.builder()
                .imageUrl(imageUrl)
                .menuName(addMenuReq.getMenuName())
                .price(addMenuReq.getPrice())
                .shopEntity(shopEntity)
                .build()));
    }

    public UpdateMenuRes updateMenu(MultipartFile multipartFile, UpdateMenuReq updateMenuReq)
        throws IOException {
        ShopEntity shopEntity = findShop(updateMenuReq.getShopId());
        MenuEntity menuEntity = findMenu(updateMenuReq.getMenuId());
        String originalFilename = getOriginalFilename(menuEntity);
        String imageUrl;
        if (multipartFile != null) {
            if (originalFilename != null) {
                imageUrl = s3Provider.updateImage(originalFilename, multipartFile);
            } else {
                imageUrl = s3Provider.saveFile(multipartFile, "menu");
            }
        } else {
            imageUrl = menuEntity.getImageUrl();
        }
        return AdminMenuServiceMapper.INSTANCE.toUpdateMenuRes(
            menuRepository.save(MenuEntity.builder()
                .menuId(updateMenuReq.getMenuId())
                .imageUrl(imageUrl)
                .menuName(updateMenuReq.getMenuName())
                .price(updateMenuReq.getPrice())
                .shopEntity(shopEntity)
                .build()));
    }

    public DeleteMenuRes deleteMenu(DeleteMenuReq deleteMenuReq) {
        findShop(deleteMenuReq.getShopId());
        MenuEntity menuEntity = findMenu(deleteMenuReq.getMenuId());
        String originalFilename = getOriginalFilename(menuEntity);
        s3Provider.deleteImage(originalFilename);
        menuRepository.delete(menuEntity);
        return new DeleteMenuRes();
    }

    private String getOriginalFilename(MenuEntity menuEntity) {
        if (menuEntity.getImageUrl() == null) {
            return null;
        }
        return menuEntity.getImageUrl().replace(url, "");
    }

    private ShopEntity findShop(Long shopId) {
        ShopEntity shopEntity = shopRepository.findByShopId(shopId);
        ShopValidator.validate(shopEntity);
        return shopEntity;
    }

    private MenuEntity findMenu(Long menuId) {
        MenuEntity menuEntity = menuRepository.findByMenuId(menuId);
        MenuValidator.validate(menuEntity);
        return menuEntity;
    }

    @Mapper
    public interface AdminMenuServiceMapper {

        AdminMenuServiceMapper INSTANCE = Mappers.getMapper(AdminMenuServiceMapper.class);

        AddMenuRes toAddMenuRes(MenuEntity menuEntity);

        UpdateMenuRes toUpdateMenuRes(MenuEntity menuEntity);
    }
}
