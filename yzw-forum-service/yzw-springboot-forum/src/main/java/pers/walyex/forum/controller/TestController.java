package pers.walyex.forum.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.forum.dto.BasePageQueryDTO;
import pers.walyex.forum.dto.PageResp;
import pers.walyex.forum.service.PayOrderService;
import pers.walyex.forum.util.PageUtil;
import pers.walyex.order.model.PayOrder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试控制器
 *
 * @author Waldron Ye
 * @date 2019/12/19 21:23
 */
@Controller
public class TestController {

    @Resource
    private PayOrderService payOrderService;

    @RequestMapping("/getPageOrderList")
    @ResponseBody
    public Object getPageOrderList() {

        //localhost:18180/getPageOrderList
        BasePageQueryDTO basePageQueryDTO = new BasePageQueryDTO();
        basePageQueryDTO.setPageSize(50);

        List<PayOrder> payOrders = this.payOrderService.listPageOrder(basePageQueryDTO);

        ResponseDataDTO<PageResp<PayOrder>> pageRespResponseDataDTO = PageUtil.getPageRespResult(payOrders);

        return pageRespResponseDataDTO;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        //添加响应头信息
        response.setHeader("Content-disposition", "attachment; filename=" + "orderInfo.xlsx");
        //设置类型
        response.setContentType("application/msexcel;charset=UTF-8");
        //设置头
        response.setHeader("Pragma", "No-cache");
        //设置头
        response.setHeader("Cache-Control", "no-cache");
        //设置日期头
        response.setDateHeader("Expires", 0);

        BasePageQueryDTO basePageQueryDTO = new BasePageQueryDTO();
        basePageQueryDTO.setPageSize(50);
        List<PayOrder> pageList = this.payOrderService.listPageOrder(basePageQueryDTO);

        PageInfo<PayOrder> pageInfo = new PageInfo<>((pageList == null || pageList.isEmpty()) ? new ArrayList<>() : pageList);

    }

}
