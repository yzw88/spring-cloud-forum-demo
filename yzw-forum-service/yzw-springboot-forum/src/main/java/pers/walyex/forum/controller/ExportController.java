package pers.walyex.forum.controller;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.walyex.common.util.DateUtil;
import pers.walyex.forum.dto.UserInfoExportDTO;
import pers.walyex.forum.dto.user.req.UserQueryReq;
import pers.walyex.forum.enums.UserStatusEnum;
import pers.walyex.forum.service.SysUserService;
import pers.walyex.order.model.SysUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导出控制器
 *
 * @author Waldron Ye
 * @date 2020/1/26 11:53
 */
@Controller
@RequestMapping("/export")
@Slf4j
public class ExportController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 导出
     * http://localhost:18180/export/userExport
     *
     * @param userQueryReq 用户查询请求对象
     * @param response     响应对象
     */
    @GetMapping("/userExport")
    public void userExport(UserQueryReq userQueryReq, HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            //设置文件名称
            String fileName = new String((DateUtil.format(new Date()) + "-用户列表").getBytes(), StandardCharsets.UTF_8);
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            List<SysUser> list = this.sysUserService.listUser(userQueryReq);
            List<UserInfoExportDTO> userInfoList = new ArrayList<>(list.size());
            for (SysUser sysUser : list) {
                UserInfoExportDTO dto = new UserInfoExportDTO();
                dto.setCreateTimeStr(DateUtil.format(sysUser.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
                dto.setEmail(sysUser.getEmail());
                dto.setMobile(sysUser.getMobile());
                dto.setUsername(sysUser.getUsername());
                dto.setStatusStr(UserStatusEnum.getContentByCode(sysUser.getStatus()));
                userInfoList.add(dto);
            }

            EasyExcel.write(response.getOutputStream(), UserInfoExportDTO.class).sheet("用户信息").doWrite(userInfoList);

        } catch (Exception e) {
            log.info("导出异常");
        }
    }
}
