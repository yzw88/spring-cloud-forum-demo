package pers.walyex.forum.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应对象
 *
 * @author Waldron Ye
 * @date 2019/11/10 13:28
 */
@Data
public class PageResp<T> implements Serializable {

    private static final long serialVersionUID = -6145547509545489710L;
    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页的行数
     */
    private Integer pageSize;

    /**
     * 分页数据
     */
    private List<T> list;
}
