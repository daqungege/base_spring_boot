package org.carrot.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

// JPA实体类的标识
@Entity
public class Money {

	// JPA 主键标识, 策略为由数据库生成主键
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public Account account;

	public String name;
	public String type;
	public String platform;
	public double money;
	public double expectRevenueMin;
	public double expectRevenueMax;
	public Date startTime;
	public Date endTime;
	public double realRevenue;
	public double actualRevenue;
	public String status;
	public Date createTime;
	public Date updateTime;



	public Money() {

	}

	public Money(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
