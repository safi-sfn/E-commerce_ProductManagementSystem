package in.inxod.pms.dto;


import java.math.BigDecimal;
import java.math.BigInteger;


public class ProductDto {

	private Integer productId;
	
	private String productName;
			 
	private Integer productPrice;
	
	private BigInteger quantityAvailable;
	
	private BigDecimal productRating;

	private String productImageUrl;
	
//	private byte[] productImage;
	
	private ProductCategoryDto productCategory;

	private ProductBrandDto productBrand;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public BigInteger getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(BigInteger quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public BigDecimal getProductRating() {
		return productRating;
	}

	public void setProductRating(BigDecimal productRating) {
		this.productRating = productRating;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	
//	
//
//	public byte[] getProductImage() {
//		return productImage;
//	}
//
//	public void setProductImage(byte[] productImage) {
//		this.productImage = productImage;
//	}
	
	public ProductCategoryDto getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategoryDto productCategory) {
		this.productCategory = productCategory;
	}

	public ProductBrandDto getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrandDto productBrand) {
		this.productBrand = productBrand;
	}

	public ProductDto(String productName, Integer productPrice, BigInteger quantityAvailable, BigDecimal productRating,
			String productImageUrl, ProductCategoryDto productCategory, ProductBrandDto productBrand) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantityAvailable = quantityAvailable;
		this.productRating = productRating;
		this.productImageUrl = productImageUrl;
		this.productCategory = productCategory;
		this.productBrand = productBrand;
	}

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
