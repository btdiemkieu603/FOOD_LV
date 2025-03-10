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
@Table(name = "delivery_info")
public class DeliveryInfo {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer deliveryInfoId;

	    @ManyToOne
	    @JoinColumn(name = "orderOnline_id")
	    private Order orderOnline;

	    @ManyToOne
	    @JoinColumn(name = "address_id")
	    private Address address;
	    
//	    @Column(name = "address")
//	    private String address;

//	    @Column(name = "staff_email")
//	    private String staffEmail;
	    
	    @ManyToOne
	    @JoinColumn(name = "staff_id", nullable = true)
	    private Staff deliveryStaff;

	    @Column(name = "deliveryFee")
	    private Double deliveryFee;

	    @Column(name = "deliveryTime")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date deliveryTime;
	    //get set
	    public Integer getDeliveryInfoId() {
	        return deliveryInfoId;
	    }

	    public void setDeliveryInfoId(Integer deliveryInfoId) {
	        this.deliveryInfoId = deliveryInfoId;
	    }

	    public Order getOrderOnline() {
	        return orderOnline;
	    }

	    public void setOrderOnline(Order orderOnline) {
	        this.orderOnline = orderOnline;
	    }

//	    public Address getAddress() {
//	        return address;
//	    }
//
//	    public void setAddress(Address address) {
//	        this.address = address;
//	    }

//	    public String getStaffEmail() {
//	        return staffEmail;
//	    }
//
//	    public String getAddress() {
//			return address;
//		}
//
//		public void setAddress(String address) {
//			this.address = address;
//		}
//
//		public void setStaffEmail(String staffEmail) {
//	        this.staffEmail = staffEmail;
//	    }

	    public Double getDeliveryFee() {
	        return deliveryFee;
	    }

	    public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public Staff getDeliveryStaff() {
			return deliveryStaff;
		}

		public void setDeliveryStaff(Staff deliveryStaff) {
			this.deliveryStaff = deliveryStaff;
		}

		public void setDeliveryFee(Double deliveryFee) {
	        this.deliveryFee = deliveryFee;
	    }

	    public Date getDeliveryTime() {
	        return deliveryTime;
	    }

	    public void setDeliveryTime(Date deliveryTime) {
	        this.deliveryTime = deliveryTime;
	    }
}
