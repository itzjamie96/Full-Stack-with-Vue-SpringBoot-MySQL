package org.salem.domain.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.salem.domain.vo.AdminVO;
@Mapper
public interface AdminMapper {
	
	@Select("select * from admin where adminId = #{adminId}")
	public AdminVO adminLogin(String adminId);
}
