package com.app.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.app.persistence.model.es.user.User;
import com.app.repository.es.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String>, UserRepositoryCustom {

	User findByEmail(String email);

	Boolean existsByEmail(String email);

}
