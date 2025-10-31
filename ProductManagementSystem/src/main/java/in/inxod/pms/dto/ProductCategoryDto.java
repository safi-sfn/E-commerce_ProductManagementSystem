package in.inxod.pms.dto;


public class ProductCategoryDto { 	
	
	private Integer categoryId;
	
	private String categoryName;	
	
	private String occassion;
	
	private String consumerType;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOccassion() {
		return occassion;
	}

	public void setOccassion(String occassion) {
		this.occassion = occassion;
	}

	public String getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	public ProductCategoryDto(String categoryName, String occassion, String consumerType) {
		super();
		this.categoryName = categoryName;
		this.occassion = occassion;
		this.consumerType = consumerType;
	}

	public ProductCategoryDto() {
		super();
		
	}	
	

}