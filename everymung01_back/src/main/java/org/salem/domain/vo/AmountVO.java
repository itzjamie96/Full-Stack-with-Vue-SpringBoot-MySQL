package org.salem.domain.vo;

import lombok.Data;

@Data
public class AmountVO {  //KakaoPay에서 사용
	
	private Integer total, tax_free, vat, point, discount;

	@Override
	public String toString() {
		return "AmountVO [total=" + total + ", tax_free=" + tax_free + ", vat=" + vat + ", point=" + point
				+ ", discount=" + discount + "]";
	}

}
