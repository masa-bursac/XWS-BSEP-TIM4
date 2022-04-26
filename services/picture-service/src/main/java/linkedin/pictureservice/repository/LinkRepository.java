package linkedin.pictureservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.pictureservice.model.Link;

public interface LinkRepository extends JpaRepository<Link, Integer>{

	Link save(Link link);
	Link findOneById(int id);
	Link findOneByPath(String path);
    List<Link> findAll();
}
