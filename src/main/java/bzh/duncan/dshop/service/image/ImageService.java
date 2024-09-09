package bzh.duncan.dshop.service.image;

import bzh.duncan.dshop.dto.ImageDto;
import bzh.duncan.dshop.model.Image;
import bzh.duncan.dshop.model.Product;
import bzh.duncan.dshop.repository.ImageRespository;
import bzh.duncan.dshop.repository.ProductRepository;
import bzh.duncan.dshop.service.product.IProductService;
import bzh.duncan.dshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.lang.module.Configuration;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRespository imageRespository;
    private final IProductService iProductService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    private Configuration config;


    @Override
    public Image getImageById(Long id) {
        return imageRespository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id : " + id));
    }

    @Override
    public List<Image> getAllImages() {
        return imageRespository.findAll();
    }

    @Override
    public void deleteImageById(Long id) {
        imageRespository.findById(id).ifPresentOrElse(imageRespository :: delete,
                () -> {
                    throw new ImageNotFoundException("No image with id : " + id);
                });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();

        for(MultipartFile file : files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);



                String buildDownloadPath = "/api/v1/images/image/download/";

                String downloadUrl = buildDownloadPath + image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRespository.save(image);

                savedImage.setDownloadUrl(buildDownloadPath + savedImage.getId());
                imageRespository.save(savedImage);


                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());


                savedImageDto.add(imageDto);
            } catch (IOException  | SQLException exception){
                throw new RuntimeException(exception.getMessage());
            }
            return savedImageDto;
        }

        return null;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage( new SerialBlob(file.getBytes()));
            imageRespository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
