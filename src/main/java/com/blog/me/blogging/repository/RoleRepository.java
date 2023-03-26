package com.blog.me.blogging.repository;

import com.blog.me.blogging.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
