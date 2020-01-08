package org.salem.domain.controller;

import java.io.File;
import java.util.List;

import org.salem.domain.Mapper.SitterMapper;
import org.salem.domain.file.FileResponse;
import org.salem.domain.file.FileSystemStorageService;
import org.salem.domain.file.StorageService;
import org.salem.domain.formMail.FormMailImp;
import org.salem.domain.vo.LoginVO;
import org.salem.domain.vo.SearchIdVO;
import org.salem.domain.vo.SearchPwVO;
import org.salem.domain.vo.SitterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SitterController {
	
	//testing
	
	@Autowired
	FileSystemStorageService fileService;
	
	@Autowired
	SitterMapper sitterMapper;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	FormMailImp formMail;

	@PostMapping("/searchIdSitter")
	public String searchId(@RequestBody SearchIdVO search){
		String msg="존재하지 않습니다";
		List<SitterVO> lsm = sitterMapper.showAllSitters();
		for(SitterVO vo : lsm) {
			if(vo.getSitterName().equals(search.getName())) {
				if(vo.getSitterPhone().equals(search.getPhone())) {
					msg=vo.getSitterEmail();
				}
			}
		}
		return msg;
	}
	@PostMapping("/searchPwSitter")
	public String searchPw(@RequestBody SearchPwVO search){
		String msg="정보가 일치하지 않습니다";
		List<SitterVO> lsm = sitterMapper.showAllSitters();
		for(SitterVO vo : lsm) {
			if(vo.getSitterEmail().equals(search.getEmail())) {
				if(vo.getSitterPhone().equals(search.getPhone())) {
					int leng = vo.getSitterPw().length();
					String realPw = vo.getSitterPw().substring(0, Math.round(leng/2));
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


  ///////////////////////////////////  시터 identityCheck,qualificationCheck 용 이미지 넣기 !! 다른데 쓰지 마시오 ////////////////////////

	@RequestMapping("/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
      File lsm = new File("./uploads/"+filename);
      Resource resource;
      if(lsm.exists()) {
         resource = storageService.loadAsResource(filename);
       }else {
          resource = storageService.loadAsResource("default.jpg");          
       }
      
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
    @PostMapping("/upload-file/{kind}/{sitterEmail}")
    @ResponseBody
    public FileResponse uploadFile(@PathVariable("kind") String kind, @PathVariable("sitterEmail") String sitterEmail, @RequestParam("file") MultipartFile file) {
    	if(file == null) {
    		return new FileResponse("default.jpg","zz","zz",1);
    	}
    	SitterVO sitter = sitterMapper.showUserDetailLogin(sitterEmail);
    	String name = storageService.store(file,sitterEmail);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();
        System.out.println(uri);
        
        switch (kind) {
		case "identityCheck":
				sitter.setIdentityCheck(name);
				System.out.println(sitter.getIdentityCheck());
			break;
		case "qualificationCheck":
			sitter.setQualificationCheck(name);
			System.out.println(sitter.getQualificationCheck());
			break;
		case "sitterImg1":
			sitter.setSitterImg1(name);
			System.out.println(sitter.getSitterImg1());
			break;
		case "sitterImg2":
			sitter.setSitterImg2(name);
			System.out.println(sitter.getSitterImg2());
			break;
		case "sitterImg3":
			sitter.setSitterImg3(name);
			System.out.println(sitter.getSitterImg3());
			break;
		case "sitterImg4":
			sitter.setSitterImg4(name);
			System.out.println(sitter.getSitterImg4());
			break;
		case "sitterImg5":
			sitter.setSitterImg5(name);
			System.out.println(sitter.getSitterImg5());
			break;
		}
        sitterMapper.sitterCheck(sitter);
        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }
/////////////////////////////////////////////////
	
	@RequestMapping("/showAllSitters")
	public List<SitterVO> showAllSitters() {
		return (List<SitterVO>) sitterMapper.showAllSitters();
	}
	//승인 안된 시터 지원자 확인
	@RequestMapping("/falseAllSitters")
	public List<SitterVO> falseAllSitters() {
		return sitterMapper.falseAllSitters();
	}
	

	@PostMapping("/deleteSitter/{sitterNo}")
	public int deleteSitter(@PathVariable int sitterNo) {
		SitterVO lsm = sitterMapper.showSitterDetail(sitterNo);
		fileService.deleteO(lsm.getQualificationCheck());
		fileService.deleteO(lsm.getIdentityCheck());
		/*
		 * try { formMail.sendEmail(lsm.getSitterEmail());
		 * System.out.println(lsm.getSitterEmail()); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		return sitterMapper.deleteSitter(sitterNo);
		
	}
	
	@PostMapping("/updateSitter")
	public int updateSitter(@RequestBody SitterVO sitterVo) {
		System.out.println(sitterVo);
		return sitterMapper.updateSitter(sitterVo);
	}
	
	@PostMapping("refreshSitter")//시터 새로고침
	public SitterVO refreshE(@RequestBody LoginVO vo) {
		
		System.out.println(vo);
		return sitterMapper.showUserDetailLogin(vo.getEmail());
		
		
	}
	
	@PostMapping("signinSitter") //시터로그인 로그인
	public SitterVO signin(@RequestBody LoginVO vo) {
		System.out.println(vo);
		SitterVO sitter =sitterMapper.showUserDetailLogin(vo.getEmail());
		if(sitter == null) {
			System.out.println("없는 아이디로 로그인 시도 ");
			return new SitterVO();
		}
		System.out.println(sitter);
		if(sitter.getSitterPw().equals(vo.getPassword()))
		{
			System.out.println("성공");
			return sitter;
		}
		else 
		{System.out.println("실패~");
		return new SitterVO();
		}
		
	}
	
	@PostMapping("signupSitter")
	public String signup(@RequestBody SitterVO vo) {
		System.out.println(vo);
		String msg="";
		if(sitterMapper.showUserDetailLogin(vo.getSitterEmail())== null) {
			msg="완료";
			sitterMapper.addSitter(vo);
		}
		else
			msg="이미 존재";
		
		return msg;
	}
	
	@RequestMapping("/showSitterDetail/{sitterNo}")
	public SitterVO showSitterDetail(@PathVariable int sitterNo) {
		return (SitterVO) sitterMapper.showSitterDetail(sitterNo);
	}
	
	@RequestMapping("/showDaySitters")
	public List<SitterVO> showDaySitters() {
		return (List<SitterVO>) sitterMapper.showDaySitters();
	}
	
	@RequestMapping("/showHomeSitters")
	public List<SitterVO> showHomeSitters() {
		return (List<SitterVO>) sitterMapper.showHomeSitters();
	}
	
	@PostMapping("/updateSitterProfile")
	public int updateSitterProfile(@RequestBody SitterVO sitterVO) { //시터 프로필 등록, 수정
		System.out.println(sitterVO);
		System.out.println("sitter profile update");
		return sitterMapper.updateSitterProfile(sitterVO);
	}

	
	@RequestMapping("/showSitterByAddress/{sittingType}/{area}") //시터 주소로 검색하기
	public List<SitterVO> showSitterByAddress(@PathVariable String sittingType, @PathVariable String area){
		if(sittingType.equals("daySitter")) {
			return (List<SitterVO>)sitterMapper.showDaySitterByAddress(area);
		}else {
			return (List<SitterVO>)sitterMapper.showHomeSitterByAddress(area);
		}
	}
	
	@RequestMapping("/showDaySitterByDate/{sittingType}/{date}")
	public List<SitterVO> showDaySitterByDate(@PathVariable String sittingType, @PathVariable String date){
		return (List<SitterVO>)sitterMapper.showDaySitterByDate(date);
	}
	
	@PostMapping("/updateSitterInfo")
	public int updateSitterInfo(@RequestBody SitterVO sitterVO) {
		System.out.println("sitterInfo update");
		return sitterMapper.updateSitterInfo(sitterVO);
	}



}