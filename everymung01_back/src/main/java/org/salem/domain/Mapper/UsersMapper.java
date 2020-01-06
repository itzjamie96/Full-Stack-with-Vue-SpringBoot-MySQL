package org.salem.domain.Mapper;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.salem.domain.vo.UsersVO;


@Mapper
public interface UsersMapper {

	@Select("select * from users")
	public List<UsersVO> showAllUsers();
	

	@Delete("delete from users where userNo=#{userNo}")
	public int deleteUser(int userNo);

	@Update("Update users set userName=#{userName},userEmail=#{userEmail},"
			+ "userPw=#{userPw},userPhone=#{userPhone},userAddress=#{userAddress},"
			+ "userProfile=#{userProfile} where userNo=#{userNo}")
	public int updateUser(UsersVO usersVo);
	
//	@Insert("insert into users values (#{id},#{address},#{email},#{name},#{pw})")
//	public int test0(Users users);
//	
//	@Update("Update users set address=#{address},email=#{email},name=#{name},pw=#{pw} where id=#{id}")
//	public int test1(Users users);
//	
//	@Delete("delete from users where id=#{id}")
//	public int test2(String id);

	@Select("select * from users where userEmail=#{email}")
	public UsersVO showUserDetail(String email);
	
  @Insert(
  		"INSERT INTO users (userEmail, userName, userPw, userPhone, userAddress, userProfile, userDate)" + 
  		" VALUES (#{userEmail},#{userName},#{userPw},#{userPhone},#{userAddress},#{userProfile},sysdate())")
	public int addUser(UsersVO users);
	
//	@Update("Update users set address=#{address},email=#{email},name=#{name},pw=#{pw} where id=#{id}")
//	public int test1(UsersVO users);
	
	@Delete("delete from users where email=#{email}")
	public int test2(String email);
	
	@Select("SELECT * FROM USERS WHERE userNo = #{userNo}")
	public UsersVO getUserVO(int userNo);
	
	
	
	

}
