package com.kt.backend.dto;

import java.util.Date;

import jakarta.persistence.Column;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
public class DeliveryRatesDto {

	 private Integer deliveryRateId;
	 //   private String deliveryArea;
	 @Column(name = "province", nullable = false)
	    private String province;

	    @Column(name = "district", nullable = false)
	    private String district;
	    private Double rate;

	    // Getters and Setters

	    public Integer getDeliveryRateId() {
	        return deliveryRateId;
	    }

	    public void setDeliveryRateId(Integer deliveryRateId) {
	        this.deliveryRateId = deliveryRateId;
	    }

//	    public String getDeliveryArea() {
//	        return deliveryArea;
//	    }
//
//	    public void setDeliveryArea(String deliveryArea) {
//	        this.deliveryArea = deliveryArea;
//	    }

	    public Double getRate() {
	        return rate;
	    }

//	    public String getCity() {
//			return city;
//		}
//
//		public void setCity(String city) {
//			this.city = city;
//		}
	    

		public String getDistrict() {
			return district;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public void setRate(Double rate) {
	        this.rate = rate;
	    }
    
}
