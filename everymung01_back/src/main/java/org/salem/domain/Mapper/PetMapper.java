package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.PetVO;

@Mapper
public interface PetMapper {

	List<PetVO> showAllPets(int userNo);
	PetVO showPetDetail(int petNo);
	int addPet(PetVO petVo);
	void deletePet(int petNo);
	void updatePet(PetVO petvo);
	PetVO getPetInfo(int petNo);
	
}
