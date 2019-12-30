package org.salem.domain.file;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

//@Service
//public class FileStorageService { // 일반유저가 펫 이미지 추가및 자기 집 이미지 추가 관련클래스
//
//	private final Path fileStorageLocation;
//
//	public FileStorageService(FileStorageProperties fileStorageProperties) {
//		this.fileStorageLocation = Paths.get(fileStorageProperties.getPetUploadDir()).toAbsolutePath().normalize();
//
//		try {
//			Files.createDirectories(this.fileStorageLocation);
//		} catch (Exception e) {
//			throw new FileStorageException("업로드 파일이 저장될 디렉토리를 만들 수 없습니다. ", e);
//
//		}
//
//	}
//
//	public String storeFile(MultipartFile file) {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//		try {
//			if (fileName.contains("..")) {
//				throw new FileStorageException("죄송합니다! 파일 이름에 잘못된 경로 시퀀스가 포함되어 있습니다. " + fileName);
//			}
//
//			Path tartgetLocation = this.fileStorageLocation.resolve(fileName);
//			Files.copy(file.getInputStream(), tartgetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//			return fileName;
//
//		} catch (Exception e) {
//			throw new FileStorageException("파일을 저장할 수 없습니다." + fileName + ". 다시 시도 해주세요!", e);
//
//		}
//	}
//
//	public Resource loadFileAsResource(String fileName) {
//		try {
//
//			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//			Resource resource = new UrlResource(filePath.toUri());
//			if (resource.exists()) {
//				return resource;
//			} else {
//				throw new FileNotFoundException("파일을 찾을 수 없습니다. " + fileName);
//			}
//
//		} catch (MalformedURLException e) {
//			throw new FileNotFoundException("파일을 찾을 수 없습니다. " + fileName, e);
//
//		}
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//
//}//FileStorageService










