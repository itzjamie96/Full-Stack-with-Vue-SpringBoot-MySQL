package org.salem.domain.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.PriceVO;

@Mapper
public interface PriceMapper {
	public List<PriceVO> showAllPrice(int userNo);
	
}
