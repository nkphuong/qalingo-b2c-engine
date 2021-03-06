/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TECO_CART_ITEM")
public class CartItem extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 6636336983669678530L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "QUANTITY", nullable = false, columnDefinition = "int(11) default 0")
    private int quantity;

    @Column(name = "PRODUCT_SKU_CODE")
    private String productSkuCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_SKU_ID", insertable = false, updatable = false)
    private ProductSku productSku;

    @Column(name = "PRODUCT_MARKETING_CODE")
    private String productMarketingCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_SKU_ID", insertable = false, updatable = false)
    private ProductMarketing productMarketing;

    @Column(name = "VIRTUAL_CATEGORY_CODE")
    private String catalogCategoryCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "VIRTUAL_CATEGORY_ID", insertable = false, updatable = false)
    private CatalogCategoryVirtual catalogCategory;
	
	public CartItem(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getProductSkuCode() {
		return productSkuCode;
	}
	
	public void setProductSkuCode(String productSkuCode) {
		this.productSkuCode = productSkuCode;
	}
	
	public ProductSku getProductSku() {
		return productSku;
	}
	
	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}
	
	public String getProductMarketingCode() {
        return productMarketingCode;
    }

    public void setProductMarketingCode(String productMarketingCode) {
        this.productMarketingCode = productMarketingCode;
    }

    public ProductMarketing getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketing productMarketing) {
        this.productMarketing = productMarketing;
    }

    public String getCatalogCategoryCode() {
        return catalogCategoryCode;
    }

    public void setCatalogCategoryCode(String catalogCategoryCode) {
        this.catalogCategoryCode = catalogCategoryCode;
    }

    public CatalogCategoryVirtual getCatalogCategory() {
        return catalogCategory;
    }
    
    public void setCatalogCategory(CatalogCategoryVirtual catalogCategory) {
        this.catalogCategory = catalogCategory;
    }

    public BigDecimal getPrice() {
		
		// TODO !
		
		if(productSku != null
				&& productSku.getPrices() != null
				&& productSku.getPrices().size() > 0){
			ProductSkuPrice productSkuPrice = productSku.getPrices().iterator().next();
			return productSkuPrice.getSalePrice();
		}
		return null;
	}
    
    public String getPriceWithSign() {
        
        // TODO !
        
        if(productSku != null
                && productSku.getPrices() != null
                && productSku.getPrices().size() > 0){
            ProductSkuPrice productSkuPrice = productSku.getPrices().iterator().next();
            return productSkuPrice.getPriceWithStandardCurrencySign();
        }
        return null;
    }
	
	public BigDecimal getTotalAmountCartItem() {
		BigDecimal totalAmount = new BigDecimal("0");
		if(getPrice() != null){
			totalAmount = totalAmount.add(getPrice());
		}
		totalAmount = totalAmount.multiply(new BigDecimal(quantity));
		return totalAmount;
	}
}
