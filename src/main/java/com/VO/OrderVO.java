package com.VO;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class OrderVO {

	Long orderId;
	
	Long userId;
	
    String username;
    
    ShippingVO shipping;
	
    List<ItemVO> items;
    
    int dateCreated;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ShippingVO getShipping() {
		return shipping;
	}

	public void setShipping(ShippingVO shipping) {
		this.shipping = shipping;
	}

	public List<ItemVO> getItems() {
		return items;
	}

	public void setItems(List<ItemVO> items) {
		this.items = items;
	}

	public int getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(int dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}	
	
	
}