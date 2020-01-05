package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.BreedVO;

@Mapper
public interface BreedMapper {
	
	 
	public List<BreedVO> showAllBreed();

	

}
