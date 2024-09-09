package bzh.duncan.dshop.repository;

import bzh.duncan.dshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRespository extends JpaRepository<Image, Long> {

}
