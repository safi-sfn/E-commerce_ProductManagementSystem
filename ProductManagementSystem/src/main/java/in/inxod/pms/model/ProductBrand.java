package in.inxod.pms.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;



@Entity
@Table(name="product_brand")
public class ProductBrand {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "brand_seq")
	@SequenceGenerator(name = "brand_seq",sequenceName = "brand_seq",initialValue = 200,allocationSize = 1)
	@Column(name="brand_id")
	private Integer brandId;
	
	@Column(name="brand_name",nullable = false)
	private String brandName;
	
	@OneToMany(mappedBy = "productBrand", cascade = CascadeType.ALL)
	private List<Product> products;

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductBrand(String brandName, List<Product> products) {
		super();
		this.brandName = brandName;
		this.products = products;
	}

	public ProductBrand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
