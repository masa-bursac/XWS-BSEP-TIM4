package linkedin.postservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.postservice.model.Post;

public interface PostRepository extends MongoRepository<Post, Integer>{

	Post save(Post post);
    Post findOneById(int id);
    List<Post> findAll();
}
