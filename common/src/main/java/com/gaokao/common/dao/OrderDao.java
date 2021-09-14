package com.gaokao.common.dao;

import com.gaokao.common.meta.po.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDao extends PagingAndSortingRepository<Order, Long>  {
    List<Order> findAllByUserId(String userId,Pageable pageable);

    Order findByIdAndGoodsId(Long id, Long shopId);

    Page<Order> findAllByStatus(int status, Pageable pageable);

    Order findByOutTradeNo(String outTradeNo);

    Page<Order> findAll(Pageable pageable);

    void deleteById(Long orderId);

    List<Order> findOrderByIdContaining(String keyword, Pageable pageable);
}

