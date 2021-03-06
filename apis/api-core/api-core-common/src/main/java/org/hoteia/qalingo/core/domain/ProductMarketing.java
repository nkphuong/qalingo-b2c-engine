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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.OrderBy;
import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.enumtype.AssetType;
import org.hoteia.qalingo.core.domain.enumtype.ImageSize;

@Entity
@Table(name="TECO_PRODUCT_MARKETING", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
public class ProductMarketing extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5408836788685407465L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "BUSINESS_NAME")
    private String businessName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isDefault;

    @Column(name = "CODE", nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BRAND_ID", insertable = false, updatable = false)
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_TYPE_ID", insertable = false, updatable = false)
    private ProductMarketingType productMarketingType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductMarketingAttribute> productMarketingAttributes = new HashSet<ProductMarketingAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductSku> productSkus = new HashSet<ProductSku>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    private Set<ProductAssociationLink> productAssociationLinks = new HashSet<ProductAssociationLink>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_MARKETING_ID")
    @OrderBy(clause = "ordering asc")
    private Set<Asset> assets = new HashSet<Asset>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DEFAULT_CATALOG_CATEGORY_ID", insertable = false, updatable = false)
    private CatalogCategoryVirtual defaultCatalogCategory;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

	public ProductMarketing(){
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public ProductBrand getProductBrand() {
		return productBrand;
	}
	
	public void setProductBrand(ProductBrand productBrand) {
		this.productBrand = productBrand;
	}
	
	public ProductMarketingType getProductMarketingType() {
		return productMarketingType;
	}
	
	public void setProductMarketingType(ProductMarketingType productMargetingType) {
		this.productMarketingType = productMargetingType;
	}
	
	public Set<ProductMarketingAttribute> getProductMarketingAttributes() {
        return productMarketingAttributes;
    }
	
	public void setProductMarketingAttributes(Set<ProductMarketingAttribute> productMarketingAttributes) {
        this.productMarketingAttributes = productMarketingAttributes;
    }
	
	public List<ProductMarketingAttribute> getProductMarketingGlobalAttributes() {
        List<ProductMarketingAttribute> productMarketingGlobalAttributes = new ArrayList<ProductMarketingAttribute>();
        if (productMarketingAttributes != null) {
            for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && attributeDefinition.isGlobal()) {
                    productMarketingGlobalAttributes.add(attribute);
                }
            }
        }        
        return productMarketingGlobalAttributes;
	}

	public List<ProductMarketingAttribute> getProductMarketingMarketAreaAttributes(Long marketAreaId) {
        List<ProductMarketingAttribute> productMarketingMarketAreaAttributes = new ArrayList<ProductMarketingAttribute>();
        if (productMarketingAttributes != null) {
            for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributes.iterator(); iterator.hasNext();) {
                ProductMarketingAttribute attribute = (ProductMarketingAttribute) iterator.next();
                AttributeDefinition attributeDefinition = attribute.getAttributeDefinition();
                if (attributeDefinition != null 
                        && !attributeDefinition.isGlobal()) {
                    productMarketingMarketAreaAttributes.add(attribute);
                }
            }
        }        
        return productMarketingMarketAreaAttributes;
	}

	public Set<ProductSku> getProductSkus() {
		return productSkus;
	}
	
	public ProductSku getDefaultProductSku() {
		ProductSku defaultProductSku = null;
		Set<ProductSku> productSkus = getProductSkus();
		if(productSkus != null
				&& productSkus.size() > 0){
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				if(productSku.isDefault()){
					defaultProductSku = productSku;
				}
			}
			if(defaultProductSku == null){
				Iterator<ProductSku> iterator = productSkus.iterator();
				defaultProductSku = (ProductSku) iterator.next();
			}
		}
		
		return defaultProductSku;
	}
	
	public void setProductSkus(Set<ProductSku> productSkus) {
		this.productSkus = productSkus;
	}
	
	public Set<ProductAssociationLink> getProductAssociationLinks() {
		return productAssociationLinks;
	}
	
	public void setProductAssociationLinks(Set<ProductAssociationLink> productAssociationLinks) {
		this.productAssociationLinks = productAssociationLinks;
	}
	
	public Set<Asset> getAssets() {
        return assets;
    }
	
	public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
	
	public List<Asset> getAssetsIsGlobal() {
        List<Asset> assetsIsGlobal = new ArrayList<Asset>();
        if (assets != null) {
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null 
                        && asset.isGlobal()) {
                    assetsIsGlobal.add(asset);
                }
            }
        }        
        return assetsIsGlobal;
	}
	
	public List<Asset> getAssetsByMarketArea() {
        List<Asset> assetsIsGlobal = new ArrayList<Asset>();
        if (assets != null) {
            for (Iterator<Asset> iterator = assets.iterator(); iterator.hasNext();) {
                Asset asset = (Asset) iterator.next();
                if (asset != null 
                        && !asset.isGlobal()) {
                    assetsIsGlobal.add(asset);
                }
            }
        }        
        return assetsIsGlobal;
	}
	
	public CatalogCategoryVirtual getDefaultCatalogCategory() {
        return defaultCatalogCategory;
    }
	
	public void setDefaultCatalogCategory(CatalogCategoryVirtual defaultCatalogCategory) {
        this.defaultCatalogCategory = defaultCatalogCategory;
    }
	
	public Date getDateCreate() {
		return dateCreate;
	}
	
	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	// Attributes
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode) {
		return getProductMarketingAttribute(attributeCode, null, null);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, String localizationCode) {
		return getProductMarketingAttribute(attributeCode, null, localizationCode);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, Long marketAreaId) {
		return getProductMarketingAttribute(attributeCode, marketAreaId, null);
	}
	
	public ProductMarketingAttribute getProductMarketingAttribute(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttributeToReturn = null;

		// 1: GET THE GLOBAL VALUE
		ProductMarketingAttribute productMarketingGlobalAttribute = getProductMarketingAttribute(getProductMarketingGlobalAttributes(), attributeCode, marketAreaId, localizationCode);

		// 2: GET THE MARKET AREA VALUE
		ProductMarketingAttribute productMarketingMarketAreaAttribute = getProductMarketingAttribute(getProductMarketingMarketAreaAttributes(marketAreaId), attributeCode, marketAreaId, localizationCode);
		
		if(productMarketingMarketAreaAttribute != null){
			productMarketingAttributeToReturn = productMarketingMarketAreaAttribute;
		} else if (productMarketingGlobalAttribute != null){
			productMarketingAttributeToReturn = productMarketingGlobalAttribute;
		}
		
		return productMarketingAttributeToReturn;
	}
	
	private ProductMarketingAttribute getProductMarketingAttribute(List<ProductMarketingAttribute> productMarketingAttributes, String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttributeToReturn = null;
		List<ProductMarketingAttribute> productMarketingAttributesFilter = new ArrayList<ProductMarketingAttribute>();
		if(productMarketingAttributes != null) {
			// GET ALL ProductMarketingAttributes FOR THIS ATTRIBUTE
			for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributes.iterator(); iterator.hasNext();) {
				ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
				AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
				if(attributeDefinition != null
						&& attributeDefinition.getCode().equalsIgnoreCase(attributeCode)) {
					productMarketingAttributesFilter.add(productMarketingAttribute);
				}
			}
			// REMOVE ALL ProductMarketingAttributes NOT ON THIS MARKET AREA
			if(marketAreaId != null) {
				for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					AttributeDefinition attributeDefinition = productMarketingAttribute.getAttributeDefinition();
					if(BooleanUtils.negate(attributeDefinition.isGlobal())) {
						if(productMarketingAttribute.getMarketAreaId() != null
								&& BooleanUtils.negate(productMarketingAttribute.getMarketAreaId().equals(marketAreaId))){
							iterator.remove();
						}
					}
				}
			}
			// FINALLY RETAIN ONLY ProductMarketingAttributes FOR THIS LOCALIZATION CODE
			if(StringUtils.isNotEmpty(localizationCode)) {
				for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributesFilter.iterator(); iterator.hasNext();) {
					ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
					String attributeLocalizationCode = productMarketingAttribute.getLocalizationCode();
					if(StringUtils.isNotEmpty(attributeLocalizationCode)
							&& BooleanUtils.negate(attributeLocalizationCode.equals(localizationCode))){
						iterator.remove();
					}
				}
				if(productMarketingAttributesFilter.size() == 0){
					// TODO : warning ?

					// NOT ANY ProductMarketingAttributes FOR THIS LOCALIZATION CODE - GET A FALLBACK
					for (Iterator<ProductMarketingAttribute> iterator = productMarketingAttributes.iterator(); iterator.hasNext();) {
						ProductMarketingAttribute productMarketingAttribute = (ProductMarketingAttribute) iterator.next();
						
						// TODO : get a default locale code from setting database ?
						
						if(Constants.DEFAULT_LOCALE_CODE.equals(productMarketingAttribute.getLocalizationCode())){
							productMarketingAttributeToReturn = productMarketingAttribute;
						}
					}
				}
			}
		}
		
		if(productMarketingAttributesFilter.size() == 1){
			productMarketingAttributeToReturn = productMarketingAttributesFilter.get(0);
		} else {
			// TODO : throw error ?
		}
		
		return productMarketingAttributeToReturn;
	}
	
	public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
		ProductMarketingAttribute productMarketingAttribute = getProductMarketingAttribute(attributeCode, marketAreaId, localizationCode);
		if(productMarketingAttribute != null) {
			return productMarketingAttribute.getValue();
		}
		return null;
	}
	
	public String getI18nName(String localizationCode) {
		String i18nName = (String) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_I18N_NAME, null, localizationCode);
		if(StringUtils.isEmpty(i18nName)){
			i18nName = getBusinessName();
		}
		return i18nName;
	}
	
	public Integer getOrder(Long marketAreaId) {
		return (Integer) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_ORDER, marketAreaId, null);
	}
	
   public Boolean isFeatured(){
       Boolean isFeatured = (Boolean) getValue(ProductMarketingAttribute.PRODUCT_MARKETING_ATTRIBUTE_FEATURED, null, null);
        if (isFeatured == null) {
            return Boolean.FALSE;
        } else {
            return isFeatured;
        }
    }

	// ASSET
	public Asset getDefaultPaskshotImage(ImageSize size) {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null
				&& size != null){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productAsset = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productAsset.getType())
						&& size.equals(productAsset.getSize())
						&& productAsset.isDefault()){
					defaultProductImage = productAsset;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.PACKSHOT.equals(productImage.getType())
						&& size.equals(productImage.getSize())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public Asset getDefaultBackgroundImage() {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.BACKGROUND.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.BACKGROUND.equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
	public Asset getDefaultIconImage() {
		Asset defaultProductImage = null;
		if(getAssetsIsGlobal() != null){
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.ICON.equals(productImage.getType())
						&& productImage.isDefault()){
					defaultProductImage = productImage;
				}
			}
			for (Iterator<Asset> iterator = getAssetsIsGlobal().iterator(); iterator.hasNext();) {
				Asset productImage = (Asset) iterator.next();
				if(AssetType.ICON.equals(productImage.getType())){
					defaultProductImage = productImage;
				}
			}
		}
		return defaultProductImage;
	}
	
}