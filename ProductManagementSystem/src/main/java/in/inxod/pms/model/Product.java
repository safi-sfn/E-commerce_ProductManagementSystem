package in.inxod.pms.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Entity
@Table(name="product")
public class Product {

	@Id
	@Column(name="profuct_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "product_sequence")
	@SequenceGenerator(name = "product_sequence",sequenceName = "product_sequence",allocationSize = 1,initialValue = 1000)
	private Integer productId;
	
	@Column(name="profuct_name", nullable=false)
	private String productName;
	
	@Column(name="product_price",nullable = false)
	private Integer productPrice;
	
	@Column(name="product_quantity_available")
	private BigInteger quantityAvailable;

	@Column(name="product_rating",precision = 10,scale = 2)
	private BigDecimal productRating;
	
	@Column(name="product_image")
	@Lob
	private byte[] productImage;		
	
	@ManyToOne
	@JoinColumn(name="product_category_id")
	private ProductCategory productCategory;
	
	@ManyToOne
	@JoinColumn(name="product_brand_id")
	private ProductBrand productBrand;

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

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public ProductBrand getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}

	public Product(String productName, Integer productPrice, BigInteger quantityAvailable, BigDecimal productRating,
			byte[] productImage, ProductCategory productCategory, ProductBrand productBrand) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantityAvailable = quantityAvailable;
		this.productRating = productRating;
		this.productImage = productImage;
		this.productCategory = productCategory;
		this.productBrand = productBrand;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
}
