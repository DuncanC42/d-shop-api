package bzh.duncan.dshop.service.image;

import bzh.duncan.dshop.dto.ImageDto;
import bzh.duncan.dshop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    List<Image> getAllImages();
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
