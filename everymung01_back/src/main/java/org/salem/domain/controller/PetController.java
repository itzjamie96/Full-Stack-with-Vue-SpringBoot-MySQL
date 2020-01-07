package org.salem.domain.controller;

import java.util.List;

import org.salem.domain.Mapper.PetMapper;
import org.salem.domain.file.FileResponse;
import org.salem.domain.file.StorageService;
import org.salem.domain.vo.PetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PetController {

	@Autowired
	private PetMapper petMapper;

//	@Autowired
//	private FileStorageService fileStorageService;
	@Autowired
	private StorageService storageService;
	

	@RequestMapping("/showAllpets/{userNo}")
	public List<PetVO> showAllPets(@PathVariable int userNo) {
		return petMapper.showAllPets(userNo);

	}
	
	
	//이미지 업로드 (단일 업로드 ) 
	@PostMapping("/upload-petImg/{userEmail}/{petNo}")
    public FileResponse uploadFile(@PathVariable("petNo") int petNo, @PathVariable("userEmail") String userEmail, @RequestParam("file") MultipartFile file) {

    	if(file == null) {
    		return new FileResponse("default.jpg","zz","zz",1);
    	}
		System.out.println("uploadfile 메소드 실행 ");

    	System.out.println("file=>"+file);
		PetVO petvo = petMapper.getPetInfo(petNo);
    	
    	System.out.println(petvo);
    	String name = storageService.store(file,userEmail+petNo);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();
        System.out.println(uri);
        
        petvo.setPetImg(name);
        
        // 업데이트 petvo 에 대한 쿼리문 메소드 
        petMapper.updatePet(petvo);
        
return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }
	
	
//	@RequestMapping("/download/{fileName}")
//   public ResponseEntity<Resource> downloadPetImgFile(@PathVariable("fileName") String fileName){
//	   System.out.println("downloadPetImgFile 메소드 실행 ");
//	   System.out.println(fileName);
//	   HttpHeaders headers = new HttpHeaders();
//	   headers.setContentType(MediaType.IMAGE_JPEG);
//	   Resource resource = storageService.loadAsResource(fileName);
//	   
//	   return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
//			   .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename()+"\"").body(resource);
//	   
//	   
//   }
	

	public PetVO showPetDetail(int petNo) {

		return petMapper.showPetDetail(petNo);

	}

	// PetVO 객체에 있는 petImg file upload
	@PostMapping("/addPet")
	public PetVO addPet(@RequestBody PetVO petVo ) {
		System.out.println("addPet 메소드 실행 ");

		System.out.println(petVo);
		PetVO temp=null;
		 int a = petMapper.addPet(petVo);
		 
		 if(a !=0) {
			 List<PetVO> lsm = petMapper.showAllPets(petVo.getUserNo());
			 for(PetVO zz : lsm) {
				 if(zz.getPetName().equals(petVo.getPetName())){
					 temp =zz;
				 }
			 }
		 }
		 return temp;
				 
	}
	
	@RequestMapping("/deletePet/{petNo}")
	public void deletePet(@PathVariable("petNo") int petNo) {
		System.out.println("deletePet 메소드 실행");
//		List<PetVO> vo = petMapper.showAllPets(userNo);
//		PetVO petVO = null;
//		for(PetVO tmp : vo) {
//			if(tmp.getUserNo() == userNo) {
//				petVO = tmp;
//			}
//		}
//		System.out.println(petVO);
		
		
		PetVO petVO = petMapper.showPetDetail(petNo);
 	    System.out.println(petVO);
		
 	    if(petVO.getPetImg() == null || petVO.getPetImg() != null) {
 	    	storageService.deleteO(petVO.getPetImg());	
 	    }
		
		petMapper.deletePet(petVO.getPetNo());
		System.out.println("getPetNo =>" + petVO.getPetNo());
		System.out.println("deletePet 완료");
		
	
	}

	
	@RequestMapping("/updatePet")
	public void updatePet(@RequestBody PetVO petVo) {
		System.out.println("updatePet 메소드 실행 ");
		System.out.println("petVo==>"+  petVo);
		int userNo = petVo.getUserNo();
		List<PetVO> list = petMapper.showAllPets(userNo);
		PetVO petImg =null;
		for(PetVO vo: list) {
			if(vo.getUserNo() == petVo.getUserNo()) {
				petImg = vo;
				
			}
		}
		System.out.println(petImg.getPetImg());
		
		System.out.println("vo==>" +  petImg);
		petImg.setPetName(petVo.getPetName());
		petImg.setPetWeight(petVo.getPetWeight());
		petImg.setPetAge(petVo.getPetAge());
		petImg.setPetGender(petVo.getPetGender());
		petImg.setNeutralization(petVo.isNeutralization());
		petImg.setPetAnswer(petVo.getPetAnswer());
		//petImg.setPetImg(petImg.getPetImg());
		petMapper.updatePet(petImg);
		
		
	}
	
	@RequestMapping("/deletePetImg/{userNo}")
    public PetVO deletePetImg(@PathVariable("userNo") int userNo) {
    	System.out.println("deletePetImg 메소드 실행 ");
    	List<PetVO> vo = petMapper.showAllPets(userNo);
    	PetVO petVo = null;
    	
    	for(PetVO tmp : vo) {
    		if(tmp.getUserNo() == userNo) {
    			petVo = tmp;
    		}
    	}
    	storageService.deleteO(petVo.getPetImg());
    	
        return petVo;

	}

	
	
	
	
	
	
	
	
}//PetController
