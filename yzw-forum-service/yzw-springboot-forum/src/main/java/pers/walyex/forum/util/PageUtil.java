package pers.walyex.forum.util;

import com.github.pagehelper.PageInfo;
import pers.walyex.common.core.dto.ResponseDataDTO;
import pers.walyex.common.core.enums.ResultEnum;
import pers.walyex.forum.dto.PageResp;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Waldron Ye
 * @date 2019/12/22 10:26
 */
public class PageUtil {


    /**
     * 获取分页数据
     *
     * @param pageList list
     * @param <T>      object
     * @return responseDataDTO
     */
    public static <T> ResponseDataDTO<PageResp<T>> getPageRespResult(List<T> pageList) {
        ResponseDataDTO<PageResp<T>> responseDataDTO = new ResponseDataDTO<>();
        responseDataDTO.setCode(ResultEnum.SUCCESS.getCode());
        responseDataDTO.setMessage(ResultEnum.SUCCESS.getMsg());

        PageInfo<T> pageInfo = new PageInfo<>((pageList == null || pageList.isEmpty()) ? new ArrayList<>() : pageList);
        PageResp<T> pageResp = new PageResp<>();
        pageResp.setList(pageInfo.getList());
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setPageNum(pageInfo.getPageNum());
        pageResp.setPageSize(pageInfo.getPageSize());

        responseDataDTO.setData(pageResp);
        return responseDataDTO;
    }
}
