package org.carrot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by xuan on 16/8/25.
 */
@Entity
public class WishOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long wishUserId;
    public String goodsId;
    public String goodsSize;
    public String goodsColor;
    public String goodsKeyword;
    public String ip;
    public String orderStatus;
    public Byte ratingsStar;
    public String ratingsMsg;
    public Date createTime;
    public Date updateTime;

    public WishOrder() {

    }

    public WishOrder(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
