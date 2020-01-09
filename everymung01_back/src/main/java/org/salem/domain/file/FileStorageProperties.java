package org.salem.domain.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")    // 일반유저가 펫 이미지 추가및 자기 집 이미지 추가 관련클래스 
public class FileStorageProperties {

	private String petUploadDir;

	public String getPetUploadDir() {
		return petUploadDir;
	}

	public void setPetUploadDir(String petUploadDir) {
		this.petUploadDir = petUploadDir;
	}
	
	
	
	
}
