package com.blog.me.blogging;

import com.blog.me.blogging.config.APIConstants;
import com.blog.me.blogging.entity.Role;
import com.blog.me.blogging.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(passwordEncoder.encode("hello"));

		try {
			Role role = new Role();
			role.setRoleId(APIConstants.ADMIN_USER);
			role.setRole(APIConstants.ADMIN);

			Role role1 = new Role();
			role1.setRoleId(APIConstants.NORMAL_USER);
			role1.setRole(APIConstants.NORMAL);

			List<Role> roleList = List.of(role, role1);

			List<Role> savedRoles = this.roleRepository.saveAll(roleList);

			savedRoles.forEach(role2 -> System.out.println(role2.getRole()));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
