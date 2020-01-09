package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.UsersMapper;
import org.salem.domain.file.FileResponse;
import org.salem.domain.file.StorageService;
import org.salem.domain.vo.LoginVO;
import org.salem.domain.vo.SearchIdVO;
import org.salem.domain.vo.SearchPwVO;
import org.salem.domain.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class UsersController {

	
	@Autowired
	UsersMapper mapper;
	

	@Autowired
	StorageService storageService;
	
	@PostMapping("/searchIdUser")
	public String searchId(@RequestBody SearchIdVO search){
		String msg="존재하지 않습니다";
		List<UsersVO> lsm = mapper.showAllUsers();
		for(UsersVO vo : lsm) {
			if(vo.getUserName().equals(search.getName())) {
				if(vo.getUserPhone().equals(search.getPhone())) {
					msg=vo.getUserEmail();
				}
			}
		}
		return msg;
	}

	@PostMapping("/searchPwUser")
	public String searchPw(@RequestBody SearchPwVO search){
		String msg="정보가 일치하지 않습니다";
		List<UsersVO> lsm = mapper.showAllUsers();
		for(UsersVO vo : lsm) {
			if(vo.getUserEmail().equals(search.getEmail())) {
				if(vo.getUserPhone().equals(search.getPhone())) {
					int leng = vo.getUserPw().length();
					String realPw = vo.getUserPw().substring(0, Math.round(leng/2));
					String fakePw = "";
					for(int i=0 ; i<leng-realPw.length();i++) {
						fakePw += "*";
					}
					msg = realPw+fakePw;
					//msg=vo.getUserPw();
				}
			}
		}
		return msg;
	}
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping("/userlist")
	public List<UsersVO> showAllUsers(){
		System.out.println("1");
		return mapper.showAllUsers();
	}
	


	//회원 정보 변경 
	@PostMapping("/updateUser") 
	public int updateUser(@RequestBody UsersVO usersVo){

		/*
		 * System.out.println("updateUser 메소드 실행 "); System.out.println("uservo=>" +
		 * usersVo); UsersVO vo = mapper.getUserVO(usersVo.getUserNo());
		 * System.out.println("vo ==>"+ vo);
		 * 
		 * vo.setUserPhone(usersVo.getUserPhone()); vo.setUserPw(usersVo.getUserPw());
		 * vo.setUserEmail(usersVo.getUserEmail());
		 * vo.setUserAddress(usersVo.getUserAddress());
		 */
	    
		return mapper.updateUser(usersVo);

		

	}
	
	
	//회원 삭제 하기 
	@PostMapping("/deleteUser/{userNo}")
	public int deleteUser(@PathVariable int userNo) {
		return mapper.deleteUser(userNo);
	}
	

//	@Autowired
//	UsersRepository rep;
//	
	
	@PostMapping("signinUser") //일반유저 로그인
	public UsersVO signin(@RequestBody LoginVO vo) {
		UsersVO user =mapper.showUserDetail(vo.getEmail());
		if(user == null) {
			return new UsersVO();
		}
		System.out.println(user);
		if(user.getUserPw().equals(vo.getPassword()))
		{return user;}
		else {
		return new UsersVO();}
		
	}
	
	@PostMapping("refreshUser")//새로고침
	public UsersVO refreshE(@RequestBody LoginVO vo) {
		
		//System.out.println(vo);
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

	

	//이미지 업로드 (단일 업로드 ) 
	@PostMapping("/upload-userImg/{userEmail}/{userNo}")
    public FileResponse uploadFile(@PathVariable("userNo") int userNo, @PathVariable("userEmail") String userEmail, @RequestParam("file") MultipartFile file) {
    	System.out.println("upload-userImg 추가  메소드 실행 ");
    	System.out.println("file=>"+file);
    	UsersVO userVo = mapper.getUserVO(userNo);
    	
    	System.out.println(userVo);
    	
    	
    	String name = storageService.store(file,userEmail+userNo);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();
        System.out.println(uri);
        
        userVo.setUserProfile(name);
        
        mapper.updateUser(userVo);
        
        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }
	
	@RequestMapping("/deleteUserImg/{userNo}")
	public UsersVO deleteUserImg(@PathVariable("userNo")int userNo) {
		System.out.println("deleteUserImg 메소드 실행 ");
	    UsersVO uservo = mapper.getUserVO(userNo);
	    
	    storageService.deleteO(uservo.getUserProfile());
		
	    return uservo;
	}
	
	

}
