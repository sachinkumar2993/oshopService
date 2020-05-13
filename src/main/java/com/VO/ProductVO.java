package com.VO;

public class ProductVO {
	
	Long id;

    String title;

	String price;
	
	String category;
	
	String imageUrl;

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

	public ProductVO(Long id, String title, String price, String category, String imageUrl) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
		this.imageUrl = imageUrl;
	}


}
