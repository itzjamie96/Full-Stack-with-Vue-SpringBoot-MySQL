package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.UsersMapper;
import org.salem.domain.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	
	@Autowired
	UsersMapper mapper;
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/userlist")
	public List<UsersVO> showAllUsers(){
		return (List<UsersVO>) mapper.showAllUsers();
	}
	
	@PostMapping("/update") 
	public int updateUser(@RequestBody UsersVO usersVo){
		System.out.println(usersVo);
		return mapper.updateUser(usersVo);
		
		
	}
	@PostMapping("/delete/{userNo}")
	public int deleteUser(@PathVariable int userNo) {
		System.out.println(userNo);
		return mapper.deleteUser(userNo);
	}
	
//	@PostMapping("/add") public void addException(@RequestBody Users user){
//		System.out.println(mapper.test0(user));
//		System.out.println(mapper.test());
//		
//	}
//	@PostMapping("/update") 
//	public void updateException(@RequestBody Users user){
//		System.out.println(user);
//		System.out.println(mapper.test1(user));
//		System.out.println(mapper.test());
//		
//	}
//	@GetMapping("/delete/{id}")
//	public void deleteLsm(@PathVariable String id) {
//		System.out.println(id);
//		System.out.println(mapper.test2(id));
//		System.out.println(mapper.test());
//	}

}
