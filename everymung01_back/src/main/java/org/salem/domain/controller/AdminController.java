package org.salem.domain.controller;

import org.salem.domain.Mapper.AdminMapper;
import org.salem.domain.vo.AdminVO;
import org.salem.domain.vo.LoginVO;
import org.salem.domain.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	@Autowired
	AdminMapper mapper;
	
	@PostMapping("signinAdmin")
	public AdminVO adminLog(@RequestBody AdminVO vo) {
		System.out.println(vo);
		return mapper.adminLogin(vo);
	}
	
	@PostMapping("refreshAdmin")//새로고침
	public AdminVO refreshE(@RequestBody LoginVO vo) {
		
		System.out.println(vo);
		return mapper.adminLogin(new AdminVO(vo.getEmail(),vo.getPassword()));
		
		
	}
	
}
