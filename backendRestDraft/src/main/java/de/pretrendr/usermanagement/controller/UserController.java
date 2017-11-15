package de.pretrendr.usermanagement.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.pretrendr.usermanagement.businesslogic.UserService;
import de.pretrendr.usermanagement.dataccess.UserDAO;
import de.pretrendr.usermanagement.model.User;

/**
 * @author Tristan Schneider
 */
@RequestMapping("/api/user")
@RestController
@CrossOrigin
public class UserController {

	private final UserService userService;
	private final UserDAO userDAO;
	// private final UserRedisDAO userRedisDAO;

	/**
	 * @param userService
	 * @param userDAO
	 * @param userRedisDAO
	 * @author Tristan Schneider
	 */
	@Autowired
	public UserController(UserService userService, UserDAO userDAO) {
		// , UserRedisDAO userRedisDAO) {
		this.userService = userService;
		this.userDAO = userDAO;
		// this.userRedisDAO = userRedisDAO;
	}

	/**
	 * @param id
	 * @return
	 * @author Tristan Schneider
	 */
	@RequestMapping("/get/{id}")
	public ResponseEntity<User> user(@PathVariable final UUID id) {
		return new ResponseEntity<User>(userDAO.findOne(id), HttpStatus.OK);
	}

	/**
	 * @return
	 * @author Tristan Schneider
	 */
	@RequestMapping("/getAll")
	public ResponseEntity<Iterable<User>> users() {
		return new ResponseEntity<Iterable<User>>(userDAO.findAll(), HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 * @author Florian
	 */
	@RequestMapping("/getAllTest")
	public ResponseEntity<Iterable<User>> usersTest() {
		return new ResponseEntity<Iterable<User>>(userDAO.findAll(), HttpStatus.OK);
	}
}