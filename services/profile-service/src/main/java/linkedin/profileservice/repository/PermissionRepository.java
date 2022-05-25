package linkedin.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import linkedin.profileservice.model.Permission;

public interface PermissionRepository  extends MongoRepository<Permission,Integer> {

}
