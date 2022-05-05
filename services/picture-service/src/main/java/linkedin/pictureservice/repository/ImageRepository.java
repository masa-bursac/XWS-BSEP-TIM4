package linkedin.pictureservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.pictureservice.model.Image;

public interface ImageRepository extends MongoRepository<Image,Integer>{

	Image save(Image image);
    Image findOneById(int id);
}
