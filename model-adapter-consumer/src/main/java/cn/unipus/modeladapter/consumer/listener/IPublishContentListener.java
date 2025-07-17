package cn.unipus.modeladapter.consumer.listener;

import cn.unipus.modeladapter.base.db.entity.BookUnit;
import cn.unipus.modeladapter.base.db.repository.BookUnitRepository;
import cn.unipus.modeladapter.base.service.BookService;
import cn.unipus.modeladapter.base.common.constant.ContentStatusEnum;
import cn.unipus.modeladapter.consumer.model.IPublishContentMsg;
import cn.unipus.modeladapter.remote.starter.http.course.CourseTemplate;
import cn.unipus.modeladapter.remote.starter.http.course.dto.StructSyncRequest;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import cn.unipus.qs.common.mq.consumer.SingleRawMessageConsumer;
import cn.unipus.qs.common.mq.dto.MessageConsumerConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 监听 iPublish 内容变更消息
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Slf4j
@Component
public class IPublishContentListener extends SingleRawMessageConsumer {

    @Getter
    @Resource(name = "iPublishContent")
    private MessageConsumerConfig consumerConfig;

    @Resource
    private BookUnitRepository bookUnitRepository;

    @Resource
    private BookService bookService;

    @Resource
    private CourseTemplate courseTemplate;

    @Override
    public void consumeRawMsg(String message) {
        log.info("msg: {}", message);
        IPublishContentMsg msg = JSONObject.parseObject(message, IPublishContentMsg.class);
        if (Objects.equals(msg.getStatus(), ContentStatusEnum.PUBLISHED.getStatus().intValue())) {
            log.info("msg is publish unit: {}", msg.getBizId());
            return;
        }
        BookUnit unit = bookUnitRepository.findById(msg.getBizId()).orElse(null);
        if (unit == null) {
            log.error("unit not found: {}", msg.getBizId());
            return;
        }
        List<UnitStructDTO> unitList = bookService.courseBookNodes(unit.getBookId());
        courseTemplate.structSync(StructSyncRequest.of(JSON.toJSONString(unitList),unit.getBookId()), unit.getCreator());
        unit.setStatus(msg.getStatus().byteValue());
        unit.setModified(new Date());
        bookUnitRepository.save(unit);
        log.info("sync book struct success: {}", unit.getBookId());
    }
}
