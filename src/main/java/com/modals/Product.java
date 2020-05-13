package com.modals;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product {

	// "customer_seq" is Oracle sequence name.
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
	@SequenceGenerator(sequenceName = "product_seq", allocationSize = 1, name = "PRODUCT_SEQ")
	Long id;

	String title;

	String price;

	String category;

	String imageUrl;

	@OneToOne()
	@JoinColumn(name = "itemId")
	@JsonIgnore
	Item item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Product(Long id, String title, String price, String category, String imageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
		this.imageUrl = imageUrl;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

}