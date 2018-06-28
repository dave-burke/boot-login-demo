package one.brk.dev.logindemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/changepassword")
public class ChangePasswordController {

	private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordController.class);

	@Autowired private InMemoryUserDetailsManager users;
	@Autowired private PasswordEncoder encoder;

	@GetMapping
	public String get() {
		LOG.info("Loading change password page");
		return "changepassword";
	}

	@PostMapping
	public String post(@RequestParam String oldPassword, @RequestParam(defaultValue = "") String newPassword, @RequestParam(defaultValue = "") String newPasswordCheck) {
		LOG.info("Submitting password change for someone");
		if (newPassword.equals(newPasswordCheck)) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// There must be a better way
			users.deleteUser(user.getUsername());
			users.createUser(User.builder()
					.authorities("USER")
					.username(user.getUsername())
					.password(newPassword)
					.passwordEncoder(encoder::encode)
					.build());
		} else {
			LOG.warn("Passwords don't match. Not changing.");
		}
		return "login";
	}

}
