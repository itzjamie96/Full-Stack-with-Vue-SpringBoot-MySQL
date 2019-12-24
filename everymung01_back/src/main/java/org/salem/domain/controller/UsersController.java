package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.UsersMapper;
import org.salem.domain.vo.LoginVO;
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
//	@Autowired
//	UsersRepository rep;
//	
	@Autowired
	UsersMapper mapper;
	
	@PostMapping("signinUser") //일반유저 로그인
	public UsersVO signin(@RequestBody LoginVO vo) {
		UsersVO user =mapper.showUserDetail(vo.getEmail());
		System.out.println(user);
		if(user.getUserPw().equals(vo.getPassword()))
		{return user;}
		else {
		return new UsersVO();}
		
	}
	
	@PostMapping("refreshUser")//새로고침
	public UsersVO refreshE(@RequestBody LoginVO vo) {
		
		System.out.println(vo);
		return mapper.showUserDetail(vo.getEmail());
		
		
	}
	
	@PostMapping("signup")//일반 유저 회원가입
	public String signup(@RequestBody UsersVO vo) {
		String msg="";
		System.out.println(vo);
		if(mapper.showUserDetail(vo.getUserEmail())!=null)
		{	System.out.println("이미 있는 회원");
		}	
		else {
			mapper.addUser(vo);
			System.out.println(vo.getUserName()+"의 회원가입 완료");
			msg="회원가입 완료";
		}
		return msg;
		
		
	}
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
