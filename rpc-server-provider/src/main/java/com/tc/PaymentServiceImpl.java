package com.tc;

/**
 * @author taosh
 * @create 2019-12-14 19:13
 */
@RpcService(value = IPaymentService.class, version = "V2.0.0")
public class PaymentServiceImpl implements IPaymentService {
    @Override
    public void doPay() {
        System.out.println("执行支付方法");
    }
}
