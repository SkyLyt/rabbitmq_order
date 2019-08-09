package com.lyt.rabbitmq_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyt.rabbitmq_order.base.BaseApiService;
import com.lyt.rabbitmq_order.base.ResponseBase;
import com.lyt.rabbitmq_order.service.OrderService;

@RestController
public class OrderController extends BaseApiService {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/addOrder")
	public ResponseBase addOrder() {
		return orderService.addOrderAndDispatch();
	}

}