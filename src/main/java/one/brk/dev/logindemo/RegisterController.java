package one.brk.dev.logindemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

	@Autowired private InMemoryUserDetailsManager users;
	@Autowired private PasswordEncoder encoder;

	@GetMapping
	public String get() {
		LOG.info("Loading registration page");
		return "register";
	}

	@PostMapping
	public String post(@RequestParam String username, @RequestParam String password) {
		LOG.info("Submitting registration for {}", username);
		users.createUser(User.builder()
				.authorities("USER")
				.username(username)
				.password(password)
				.passwordEncoder(encoder::encode)
				.build());
		return "login";
	}

}
