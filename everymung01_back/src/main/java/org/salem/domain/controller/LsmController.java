package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.LsmMapper;
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
public class LsmController {
//	@Autowired
//	UsersRepository rep;
//	
//	@Autowired
//	LsmMapper mapper;
//	
//	//@CrossOrigin(origins = "http://localhost:8080")
//	@RequestMapping("test")
//	public List<Users> getAllUsers(){
//		return (List<Users>) rep.findAll();
//	}
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
