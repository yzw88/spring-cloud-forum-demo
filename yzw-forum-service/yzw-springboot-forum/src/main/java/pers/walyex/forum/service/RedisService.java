package pers.walyex.forum.service;

/**
 * redis服务接口
 *
 * @author Waldron Ye
 * @date 2019/12/21 12:46
 */
public interface RedisService {

    /**
     * 获取订单号
     *
     * @return 订单号
     */
    String getOrderNo();
}
