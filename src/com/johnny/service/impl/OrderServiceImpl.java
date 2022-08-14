package com.johnny.service.impl;

import com.johnny.dao.BookDao;
import com.johnny.dao.OrderDao;
import com.johnny.dao.OrderItemDao;
import com.johnny.dao.impl.BookDaoImpl;
import com.johnny.dao.impl.OrderDaoImpl;
import com.johnny.dao.impl.OrderItemDaoImpl;
import com.johnny.pojo.*;
import com.johnny.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {

        System.out.println(" OrderServiceImpl 程式在[" +Thread.currentThread().getName() + "]中");

        // 訂單號===唯一性
        String orderId = System.currentTimeMillis()+""+userId;
        // 創建一個訂單物件
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(), 0,userId);
        // 保存訂單
        orderDao.saveOrder(order);

        //int i = 12 / 0;

        // 遍歷購物車中每一個商品項轉換成為訂單項保存到資料庫
        for (Map.Entry<Integer, CartItem>entry : cart.getItems().entrySet()){
            // 取得每一個購物車中的商品項
            CartItem cartItem = entry.getValue();
            // 轉換為每一個訂單項
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(), orderId);
            // 保存訂單項到資料庫
            orderItemDao.saveOrderItem(orderItem);

            // 更新庫存和銷量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales( book.getSales() + cartItem.getCount() );
            book.setStock( book.getStock() - cartItem.getCount() );
            bookDao.updateBook(book);

        }
        // 清空購物車
        cart.clear();

        return orderId;
    }
}
