package br.com.mysafeestablishmentcompany.domain;

import javax.persistence.*;

@Entity
@Table(name = "product_details")
public class ProductDetails extends AbstractEntity {

    @Column(
            nullable = false,
            unique = true
    )
    private String imgurId;
    @Column(
            nullable = false,
            unique = true
    )
    private String imgurDeleteId;
    @Column(
            nullable = false
    )
    private String urlImage;

    public String getImgurId() {
        return imgurId;
    }

    public void setImgurId(String imgurId) {
        this.imgurId = imgurId;
    }

    public String getImgurDeleteId() {
        return imgurDeleteId;
    }

    public void setImgurDeleteId(String imgurDeleteId) {
        this.imgurDeleteId = imgurDeleteId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ProductDetails() {
    }

    public ProductDetails(String imgurId, String imgurDeleteId, String urlImage) {
        this.imgurId = imgurId;
        this.imgurDeleteId = imgurDeleteId;
        this.urlImage = urlImage;
    }
}
