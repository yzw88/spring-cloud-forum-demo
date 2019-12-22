package pers.walyex.forum.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页对象基类
 *
 * @author Waldron Ye
 * @date 2019/11/10 13:08
 */
@Data
public class BasePageQueryDTO implements Serializable {
    private static final long serialVersionUID = -3938770827697092618L;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页显示的行数
     */
    private Integer pageSize;


    public Integer getPageNum() {
        //一般不要在get方法进行业务处理，这里特殊情况
        if (this.pageNum == null) {
            return 1;
        }

        return pageNum;
    }

    public Integer getPageSize() {
        //一般不要在get方法进行业务处理，这里特殊情况
        if (this.pageSize == null) {
            return 20;
        }
        return pageSize;
    }
}
