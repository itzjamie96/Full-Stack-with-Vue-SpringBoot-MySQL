package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.PetMapper;
import org.salem.domain.vo.PetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

	@Autowired
	private PetMapper petMapper;

//	@Autowired
//	private FileStorageService fileStorageService;

	@RequestMapping("/showAllpets/{userNo}")
	public List<PetVO> showAllPets(@PathVariable int userNo) {
		System.out.println("showAllPets 메소드 실행 ");
		return petMapper.showAllPets(userNo);

	}

	//이미지 다운로드 
//	@RequestMapping("/uploadFile/{fileName}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//		Resource resource = fileStorageService.loadFileAsResource(fileName);
//
//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//				.body(resource);
//
//	}//downloadFile
	
	
	
	//이미지 업로드 (단일 업로드 ) 
//	@RequestMapping("/petImgUploadFile/{userNo}")
//    public FileResponse petImgUploadFile(@PathVariable("userNo")String petNo,  @RequestParam("file")MultipartFile file) {
//    	
//    	
//    }
	
	
	
	

//	public List<PetVO> showPetDetail(int petNo) {
//
//		return null;
//
//	}

	// PetVO 객체에 있는 petImg file upload
	@PostMapping("/addPet")
	public int addPet(@RequestBody PetVO petVo) {
		System.out.println("addPet 메소드 실행 ");

		System.out.println(petVo);

		return petMapper.addPet(petVo);
	}

	public int deletePet(int petNo) {
		return 0;
	}

	public int updatePet(int petNo) {

		return 0;
	}

}
