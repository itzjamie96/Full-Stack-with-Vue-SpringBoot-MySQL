package org.salem.domain.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.salem.domain.vo.ReviewVO;

@Mapper
public interface ReviewMapper {
	
	public List<ReviewVO> showSitterReview(int sitterNo);  //해당 시터의 리뷰 출력
	
	public int addReview(ReviewVO reviewVO); //리뷰 작성
	
	public int deleteReview(int reviewNo); //리뷰 삭제
	
	public List<ReviewVO> showFeaturedReview();	//메인 용 리뷰

}
