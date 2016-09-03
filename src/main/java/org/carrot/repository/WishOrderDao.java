package org.carrot.repository;

import org.carrot.domain.Account;
import org.carrot.domain.WishOrder;
import org.springframework.data.repository.CrudRepository;

/**
 * 基于Spring Data JPA的Dao接口, 自动根据接口生成实现.
 * 
 * CrudRepository默认有针对实体对象的CRUD方法.
 * 
 * Spring Data JPA 还会解释新增方法名生成新方法的实现.
 */
public interface WishOrderDao extends CrudRepository<WishOrder, Long> {

}
