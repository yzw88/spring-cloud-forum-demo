package pers.walyex.forum.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 用户导出传输对象
 *
 * @author Waldron Ye
 * @date 2020/1/26 12:39
 */
@Data
public class UserInfoExportDTO {
    /**
     * 用户名称
     */
    @ExcelProperty("用户名称")
    private String username;

    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty("手机号码")
    private String mobile;

    /**
     * 状态  0：禁用   1：启用
     */
    @ExcelProperty("状态")
    private String statusStr;

    /**
     * 创建时间
     */
    @ExcelProperty("创建时间")
    private String createTimeStr;
}
