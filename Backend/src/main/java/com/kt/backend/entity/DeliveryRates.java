package com.kt.backend.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@NoArgsConstructor
//@Setter
//@Getter
@Entity
@Table(name = "delivery_rates")
public class DeliveryRates {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer deliveryRateId;

//	    @Column(name = "deliveryArea")
//	    private String deliveryArea;
	 
	

	    @Column(name = "province", nullable = false)
	    private String province;

	    @Column(name = "district", nullable = false)
	    private String district;


	    @Column(name = "rate")
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

	 

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public void setRate(Double rate) {
	        this.rate = rate;
	    }
}
