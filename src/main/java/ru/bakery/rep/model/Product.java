package ru.bakery.rep.model;

import java.math.BigDecimal;

public class Product {
    private long productId;
    private String name;
    private String category;
    private String description;
    private BigDecimal salePrice;
    private boolean available;
    private byte[] image;
    private boolean hasImage;

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
    public boolean isHasImage() { return hasImage; }
    public void setHasImage(boolean hasImage) { this.hasImage = hasImage; }
}
