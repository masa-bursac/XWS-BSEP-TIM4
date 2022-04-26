package linkedin.postservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import linkedin.postservice.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

	Post save(Post post);
    Post findOneById(int id);
    List<Post> findAll();
}
