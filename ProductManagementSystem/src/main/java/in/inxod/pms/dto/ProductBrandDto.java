package in.inxod.pms.dto;



public class ProductBrandDto {	
	
	private Integer brandId;
	
	private String brandName;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public ProductBrandDto(String brandName) {
		super();
		this.brandName = brandName;
	}

	public ProductBrandDto() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	

}