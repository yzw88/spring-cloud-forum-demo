package pers.walyex.forum.dto;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import pers.walyex.forum.dto.user.req.UserRegisterReq;

/**
 * 模板的读取类
 * 有个很重要的点 DemoDataListener 不能被spring管理，
 * 要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 *
 * @author Waldron Ye
 * @date 2020/2/16 19:23
 */
@Slf4j
public class UploadDataListener extends AnalysisEventListener<UserRegisterReq> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    @Override
    public void invoke(UserRegisterReq o, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成");
    }
}
