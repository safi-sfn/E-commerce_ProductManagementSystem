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
@Table(name="product_category")
public class ProductCategory { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq")
	@SequenceGenerator(name = "category_seq",sequenceName = "category_seq",initialValue = 500,allocationSize = 1)
	@Column(name="category_id")
	private Integer categoryId;
	
	@Column(name="category_name",nullable = false)
	private String categoryName;	
	
	@Column(name="category_occassion")
	private String occassion;
	
	@Column(name="category_consumer_type")
	private String consumerType;
	
	@OneToMany(mappedBy="productCategory" , cascade = CascadeType.ALL)
	private List<Product> products;

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public ProductCategory(String categoryName, String occassion, String consumerType, List<Product> products) {
		super();
		this.categoryName = categoryName;
		this.occassion = occassion;
		this.consumerType = consumerType;
		this.products = products;
	}

	public ProductCategory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}