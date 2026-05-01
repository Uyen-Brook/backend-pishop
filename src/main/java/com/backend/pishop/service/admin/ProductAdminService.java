package com.backend.pishop.service.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.entity.Product;
import com.backend.pishop.enums.ProductStatus;
import com.backend.pishop.enums.ResourceType;
import com.backend.pishop.mapper.ProductMapper;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.request.ProductCreateRequest;
import com.backend.pishop.request.ProductUpdateRequest;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.backend.pishop.service.CloudinaryService;
import com.backend.pishop.util.CloudinaryHelper;
import com.backend.pishop.util.ImageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductAdminService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final CloudinaryHelper cloudinaryHelper;

    // CREATE
    public ProductResponse createProduct(ProductCreateRequest request) {

        Product product = new Product();

        product.setModelName(request.getModelName());//1
        product.setModelNumber(request.getModelNumber());//2
        product.setPrice(request.getPrice());//3
        product.setImportPrice(request.getImportPrice());//4
        product.setDescription(request.getDescription());//5
        product.setQuanlity(request.getQuantity());//6

        product.setProductStatus(ProductStatus.NEW);//7

        product.setCreateAt(LocalDateTime.now());
        product.setUpdateAt(LocalDateTime.now());

        // =======================
        // thumbnail
        // =======================
        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadImage(
                    request.getThumbnail(),
                    ResourceType.PRODUCT
            );
            product.setThumbnail(thumbnailUrl);
        }

        // =======================
        // list image
        // =======================
        List<String> imageUrls = new ArrayList<>();

        if (request.getListImage() != null && !request.getListImage().isEmpty()) {
            for (MultipartFile file : request.getListImage()) {
                String url = cloudinaryService.uploadImage(file, ResourceType.PRODUCT);
                imageUrls.add(url);
            }
        }

        product.setListImage(ImageUtils.toJson(imageUrls));

        Product saved = productRepository.save(product);

        return mapToResponse(product);
        
    }
    
    
// UPDATE
    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setModelName(request.getModelName());
        product.setModelNumber(request.getModelNumber());
        product.setPrice(request.getPrice());
        product.setImportPrice(request.getImportPrice());
        product.setDescription(request.getDescription());
        product.setQuanlity(request.getQuantity());
        product.setUpdateAt(LocalDateTime.now());

        // thumbnail
        if (request.getThumbnail() != null && !request.getThumbnail().isEmpty()) {
            cloudinaryHelper.deleteImage(product.getThumbnail());

            String newThumbnail = cloudinaryService.uploadImage(
                    request.getThumbnail(),
                    ResourceType.PRODUCT
            );

            product.setThumbnail(newThumbnail);
        }

        // images
        List<String> finalImages = new ArrayList<>();

        if (request.getKeptImages() != null) {
            finalImages.addAll(request.getKeptImages());
        }

        if (request.getDeletedImages() != null) {
            for (String url : request.getDeletedImages()) {
                cloudinaryHelper.deleteImage(url);
            }
        }

        if (request.getNewImages() != null) {
            for (MultipartFile file : request.getNewImages()) {
                String url = cloudinaryService.uploadImage(file, ResourceType.PRODUCT);
                finalImages.add(url);
            }
        }

        product.setListImage(ImageUtils.toJson(finalImages));

        Product updated = productRepository.save(product);

        return mapToResponse(product);
    }
    //Detail
    public ProductResponse getProductDetail(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }
    
    // lấy tất cả
    public Page<ProductResponse> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

   
    public Page<ProductResponse> searchProducts(
            String keyword,
            Long categoryId,
            Long brandId,
            Long supplierId,
            Boolean deleted,
            Pageable pageable
    ) {

        return productRepository.searchProducts(
                keyword,
                categoryId,
                brandId,
                supplierId,
                deleted,
                pageable
        ).map(this::mapToResponse);
    }
    // xóa mềm
    public void softDeleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setDeleted(true);

        productRepository.save(product);
    }

   // xóa vĩnh viễn
    public void hardDeleteProduct(Long id) {

        productRepository.deleteById(id);
    }

    
    public void restoreProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setDeleted(false);

        productRepository.save(product);
    }

  // thay đôi trạng thái
    public ProductResponse changeStatus(Long id, String status) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductStatus(ProductStatus.valueOf(status));

        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setModelName(product.getModelName());
        response.setModelNumber(product.getModelNumber());
        response.setThumbnail(product.getThumbnail());

        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuanlity());

        response.setDescription(product.getDescription());

        response.setProductStatus(product.getProductStatus());

        response.setCreateAt(product.getCreateAt());
        response.setUpdateAt(product.getUpdateAt());

        // Brand
        if (product.getBrand() != null) {
            response.setBrandName(product.getBrand().getName());
        }

        // Supplier
        if (product.getSupplier() != null) {
            response.setSupplierName(product.getSupplier().getName());
        }

        // Category
        if (product.getCategory() != null) {
            response.setCategoryName(product.getCategory().getName());
        }

        return response;
    }
}