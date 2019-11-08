package com.spring.postgres.services;

import com.spring.postgres.models.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
}
