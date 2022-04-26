package linkedin.pictureservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.pictureservice.model.Image;

public interface ImageRepository extends JpaRepository<Image,Integer>{

	Image save(Image image);
    Image findOneById(int id);
}
