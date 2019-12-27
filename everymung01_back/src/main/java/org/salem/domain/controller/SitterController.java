package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.SitterMapper;
import org.salem.domain.file.FileResponse;
import org.salem.domain.file.StorageService;
import org.salem.domain.vo.LoginVO;
import org.salem.domain.vo.SitterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
	SitterMapper sitterMapper;
	@Autowired
	private StorageService storageService;

   


  ///////////////////////////////////  시터 identityCheck,qualificationCheck 용 이미지 넣기 !! 다른데 쓰지 마시오 ////////////////////////

	@RequestMapping("/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
    @PostMapping("/upload-file/{kind}/{sitterEmail}")
    @ResponseBody
    public FileResponse uploadFile(@PathVariable("kind") String kind, @PathVariable("sitterEmail") String sitterEmail, @RequestParam("file") MultipartFile file) {
    	SitterVO sitter = sitterMapper.showUserDetailLogin(sitterEmail);
    	System.out.println(sitter);
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
		}
        sitterMapper.sitterCheck(sitter);
return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }
/////////////////////////////////////////////////
	
	@RequestMapping("/showAllSitters")
	public List<SitterVO> showAllSitters() {
		return (List<SitterVO>) sitterMapper.showAllSitters();
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

}
