package org.salem.domain.controller;


import org.salem.domain.Mapper.AdminMapper;
import org.salem.domain.vo.AdminVO;
import org.salem.domain.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	@Autowired
	AdminMapper mapper;
	
	@PostMapping("signinAdmin")
	public AdminVO adminLog(@RequestBody LoginVO vo) {
		System.out.println(vo);
		AdminVO lsm = mapper.adminLogin(vo.getEmail());
		if(lsm == null)
			return new AdminVO();
		else {
			if(lsm.getAdminPw().equals(vo.getPassword()))
			return lsm;
		}
		return new AdminVO();
	}
	
	@PostMapping("refreshAdmin")//새로고침
	public AdminVO refreshE(@RequestBody LoginVO vo) {
		AdminVO lsm =mapper.adminLogin(vo.getEmail());
		
		return lsm;
		
		
	}
	
}
