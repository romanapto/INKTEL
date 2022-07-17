package com.app.repository.es.custom;

import org.springframework.data.domain.Page;

import com.app.filter.PaginationInfo;
import com.app.filter.UserFilter;
import com.app.persistence.model.es.user.User;

public interface UserRepositoryCustom {

	Page<User> findUsers(UserFilter userFilter, PaginationInfo pagination);

}
