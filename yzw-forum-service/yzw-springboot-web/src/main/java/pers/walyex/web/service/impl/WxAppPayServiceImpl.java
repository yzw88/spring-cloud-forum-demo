package pers.walyex.web.service.impl;

import org.springframework.stereotype.Service;
import pers.walyex.web.annotation.PayTradeTypeAnnotation;
import pers.walyex.web.enums.PayEnum;
import pers.walyex.web.service.WxBaseAbstractPayService;

/**
 * 微信App支付实现类
 *
 * @author Waldron Ye
 * @date 2020/4/25 11:30
 */
@Service
@PayTradeTypeAnnotation(payEnums = PayEnum.WX_APP)
public class WxAppPayServiceImpl extends WxBaseAbstractPayService {
}
