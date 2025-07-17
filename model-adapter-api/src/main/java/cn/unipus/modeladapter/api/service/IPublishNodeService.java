package cn.unipus.modeladapter.api.service;

import cn.unipus.modeladapter.base.common.constant.ContentStatusEnum;
import cn.unipus.modeladapter.base.common.constant.ContentTypeEnum;
import cn.unipus.modeladapter.base.common.utils.BookUtils;
import cn.unipus.modeladapter.base.db.entity.BookUnit;
import cn.unipus.modeladapter.base.db.repository.BookUnitRepository;
import cn.unipus.modeladapter.remote.starter.http.ipublish.IPublishTemplate;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.*;
import cn.unipus.qs.common.em.CommonApiCodeEnum;
import cn.unipus.qs.common.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * iPublish 教材单元服务
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@Slf4j
@Service
public class IPublishNodeService {

    @Resource
    private BookUnitRepository bookUnitRepository;

    @Resource
    private IPublishTemplate iPublishTemplate;

    /**
     * 添加教材节点
     *
     * @param bookId   教材ID
     * @param nodeName 节点名称
     * @param openId   用户openId
     * @return 新节点ID
     */
    public String addBookNode(String bookId, String nodeName, String openId) {
        int sort = bookUnitRepository.findMaxSortByBookId(bookId).orElse(0) + 1;
        String unitId = BookUtils.getNodeId();
        SaveCustomContentRequest request = new SaveCustomContentRequest();
        request.setBizId(unitId);
        request.setType(ContentTypeEnum.CHAPTER.getType());
        request.setName(nodeName);
        iPublishTemplate.saveCustomContent(request, AccessTokenRequest.of(openId));
        bookUnitRepository.save(this.createUnit(unitId, bookId, (short) sort, openId));
        log.info("添加教材单元成功，教材ID：{}，节点ID：{}，节点名称：{}", bookId, unitId, nodeName);
        return unitId;
    }

    /**
     * 修改教材节点
     *
     * @param nodeId   节点ID
     * @param nodeName 节点名称
     */
    public void updateBookNode(String nodeId, String nodeName) {
        BookUnit unit = bookUnitRepository.findById(nodeId)
                .orElseThrow(() -> new ValidateException(CommonApiCodeEnum.PARAM_CHECK_ERROR));
        SaveCustomContentRequest request = new SaveCustomContentRequest();
        request.setBizId(nodeId);
        request.setName(nodeName);
        request.setType(ContentTypeEnum.CHAPTER.getType());
        iPublishTemplate.saveCustomContent(request, AccessTokenRequest.of(unit.getCreator()));
        if (Objects.equals(unit.getStatus(), ContentStatusEnum.PUBLISHED.getStatus())) {
            unit.setStatus(ContentStatusEnum.DRAFT.getStatus());
        }
        unit.setModified(new Date());
        bookUnitRepository.save(unit);
        log.info("修改教材单元成功，节点ID：{}，节点名称：{}", nodeId, nodeName);
    }

    /**
     * 删除教材节点
     *
     * @param nodeId 节点ID
     */
    public void deleteBookNode(String nodeId) {
        // 检查本地节点，检查节点状态
        BookUnit unit = bookUnitRepository.findById(nodeId)
                .orElseThrow(() -> new ValidateException(CommonApiCodeEnum.PARAM_CHECK_ERROR));
        if (Objects.equals(unit.getStatus(), ContentStatusEnum.PUBLISHED.getStatus())) {
            throw new ValidateException(CommonApiCodeEnum.PARAM_CHECK_ERROR);
        }
        iPublishTemplate.deleteCustomContentByBizIds(
                CustomContentIdRequest.of(Collections.singletonList(nodeId)),
                AccessTokenRequest.of(unit.getCreator()));
        bookUnitRepository.delete(unit);
        log.info("删除教材节点成功，节点ID：{}", nodeId);
    }

    /**
     * 发布教材节点
     *
     * @param bookId 教材ID
     */
    public void publishBookNodes(String bookId) {
        List<BookUnit> units = bookUnitRepository.findByBookId(bookId);
        if (CollectionUtils.isEmpty(units)) {
            return;
        }
        List<String> unitIds = units.stream().map(BookUnit::getId).collect(Collectors.toList());
        iPublishTemplate.publishCustomContent(PublishCustomContentRequest.of(unitIds),
                AccessTokenRequest.of(units.get(0).getCreator()));
        bookUnitRepository.updateStatusByBookId(bookId, ContentStatusEnum.PUBLISHED.getStatus());
        log.info("发布教材单元成功，教材ID：{}，节点ID：{}", bookId, unitIds);
    }

    /**
     * 教材节点复制
     *
     * @param srcBookId  被复制的教材ID
     * @param destBookId 复制后的教材ID
     * @param openId     用户openId
     * @return 复制结果，格式：<源单元ID, 新单元ID>
     */
    public Map<String, String> copyBookNodes(String srcBookId, String destBookId, String openId) {
        log.info("开始复制教材的单元，openId: {}, srcBookId: {}", openId, srcBookId);
        // 查询此教材的自建单元列表
        List<BookUnit> units = bookUnitRepository.findByBookId(srcBookId);
        if (CollectionUtils.isEmpty(units)) {
            return null;
        }
        Map<String, String> destIdBySrcId = this.copyBookNodes(units, openId);
        units = this.createUnits(destBookId, units, destIdBySrcId, openId);
        bookUnitRepository.saveAll(units);
        return destIdBySrcId;
    }

    /**
     * 复制教材节点
     *
     * @param units  源单元列表
     * @param openId 用户openId
     * @return 单元映射关系
     */
    private Map<String, String> copyBookNodes(List<BookUnit> units, String openId) {
        Map<Boolean, List<BookUnit>> unitsByPub = units.stream().collect(Collectors.groupingBy(
                unit -> Objects.equals(unit.getStatus(), ContentStatusEnum.PUBLISHED.getStatus())));
        if (!CollectionUtils.isEmpty(unitsByPub.get(false))) {
            log.error("存在未发布的单元：{}", unitsByPub.get(false).stream().map(BookUnit::getId)
                    .collect(Collectors.toList()));
            throw new ValidateException(CommonApiCodeEnum.PARAM_CHECK_ERROR);
        }
        List<String> unitIds = unitsByPub.get(true).stream().map(BookUnit::getId)
                .collect(Collectors.toList());
        log.info("开始复制教材单元，节点ID: {}", unitIds);
        // 准备复制请求
        CopyContentDTO copyResult = iPublishTemplate.copyContent(
                CopyContentRequest.of(unitIds, ContentStatusEnum.DRAFT.getStatus().intValue()),
                AccessTokenRequest.of(openId));
        Map<String, String> destIdBySrcId = copyResult.getBizIdMapping();
        if (CollectionUtils.isEmpty(destIdBySrcId) || destIdBySrcId.size() < units.size()) {
            log.error("复制教材单元失败：源ID：{}，复制结果ID：{}", unitIds, destIdBySrcId);
            throw new ValidateException(CommonApiCodeEnum.PARAM_CHECK_ERROR);
        }
        return destIdBySrcId;
    }

    /**
     * 保存新的教材单元
     *
     * @param bookId        新教材ID
     * @param units         源单元列表
     * @param destIdBySrcId 单元映射关系
     * @param openId        用户openId
     */
    private List<BookUnit> createUnits(String bookId, List<BookUnit> units,
            Map<String, String> destIdBySrcId, String openId) {
        return units.stream().map(unit -> this.createUnit(destIdBySrcId.get(unit.getId()), bookId,
                unit.getSort(), openId)).collect(Collectors.toList());
    }

    /**
     * 创建教材单元
     *
     * @param id     单元ID
     * @param bookId 教材ID
     * @param sort   排序
     * @param openId 用户openId
     * @return 教材单元
     */
    private BookUnit createUnit(String id, String bookId, short sort, String openId) {
        BookUnit unit = new BookUnit();
        unit.setId(id);
        unit.setBookId(bookId);
        unit.setSort(sort);
        unit.setStatus(ContentStatusEnum.DRAFT.getStatus());
        unit.setCreator(openId);
        unit.setModifier(openId);
        unit.setCreated(new Date());
        unit.setModified(new Date());
        return unit;
    }
}
