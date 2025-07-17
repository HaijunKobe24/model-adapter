package cn.unipus.modeladapter.base.common.utils;

import cn.unipus.modeladapter.base.common.constant.CourseConstants;
import cn.unipus.modeladapter.remote.starter.http.course.dto.BlockStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 教程工具类
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
public class BookUtils {


    /**
     * 生成教材ID
     *
     * @return 新的教材ID
     */
    public static String genBookId() {
        return String.format(CourseConstants.BOOK_ID_FMT, CourseConstants.SOURCE_COURSE,
                UUID.randomUUID());
    }

    /**
     * 是否为自建教材ID
     *
     * @return 是否
     */
    public static boolean isCustomBookId(String id) {
        return StringUtils.startsWith(id, CourseConstants.SOURCE_COURSE);
    }


    /**
     * 生成教材节点ID
     *
     * @return 新的教材ID
     */
    public static String getNodeId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 教材节点转换
     *
     * @param officialNodeList 官方节点列表
     * @param customNodeList   自定义节点列表
     * @return 单元节点列表
     */
    public static List<UnitStructDTO> bookNodesConvert(List<ChapterStructDTO> officialNodeList,
            List<CustomContentSimpleDTO> customNodeList) {
        List<UnitStructDTO> officialContentDTOList = processOfficalContentList(officialNodeList);
        List<UnitStructDTO> customized = processCustomContentList(customNodeList);
        List<UnitStructDTO> unitStructDTOList = Lists.newArrayList();
        unitStructDTOList.addAll(officialContentDTOList);
        unitStructDTOList.addAll(customized);
        return unitStructDTOList;
    }

    /**
     * 处理官方节点列表
     *
     * @param contentList 官方节点列表
     * @return 单元节点列表
     */
    private static List<UnitStructDTO> processOfficalContentList(
            List<ChapterStructDTO> contentList) {
        return contentList.stream().map(chapter -> {
            UnitStructDTO unit = new UnitStructDTO();
            unit.setId(chapter.getChapterId());
            unit.setText(chapter.getChapterName());
            Map<String, QuestionGroupDTO> questionMap = buildQuestionMap(
                    chapter.getQuestionGroupList());
            unit.setChildren(buildBlockStructList(chapter.getAllNodeTree(), questionMap,
                    CourseConstants.OfficialFlag.YES));
            return unit;
        }).collect(Collectors.toList());
    }

    /**
     * 处理自定义节点列表
     *
     * @param customContentList 自定义节点列表
     * @return 单元节点列表
     */
    private static List<UnitStructDTO> processCustomContentList(
            List<CustomContentSimpleDTO> customContentList) {
        List<UnitStructDTO> unitStructDTOList = Lists.newArrayList();

        for (CustomContentSimpleDTO simpleDTO : customContentList) {
            if (simpleDTO == null)
                continue;
            UnitStructDTO unit = new UnitStructDTO();
            unit.setId(simpleDTO.getBizId());
            unit.setText(simpleDTO.getName());
            unit.setCreatedType(CourseConstants.CREATED_TYPE);
            // number字段如有需要可补充
            // 构建questionMap
            Map<String, QuestionGroupDTO> questionMap = buildQuestionMap(
                    simpleDTO.getQuestionList());
            unit.setChildren(buildBlockStructList(simpleDTO.getTotalStructNodeTree(), questionMap,
                    CourseConstants.OfficialFlag.NO));
            unitStructDTOList.add(unit);
        }
        return unitStructDTOList;
    }

    /**
     * 构建试题映射
     *
     * @param questionList 试题列表
     * @return 试题映射
     */
    private static Map<String, QuestionGroupDTO> buildQuestionMap(
            List<QuestionGroupDTO> questionList) {
        if (questionList == null)
            return Collections.emptyMap();
        return questionList.stream().filter(qg -> qg != null && qg.getGroupId() != null)
                .collect(Collectors.toMap(QuestionGroupDTO::getGroupId, qg -> qg));
    }

    /**
     * 设置试题块详情
     *
     * @param block 试题块
     * @param qg    试题组
     */
    private static void setQuestionBlockDetails(BlockStructDTO block, QuestionGroupDTO qg) {
        if (block == null || qg == null)
            return;
        block.setQuestId(qg.getGroupId());
        block.setQuestVersion(qg.getVersionNumber());
        block.setQuestionType(qg.getGroupType());
        List<QuestionDTO> qList = qg.getList();
        block.setSubQuestionCount(qList == null ? 0 : qList.size());
        if (qList != null) {
            block.setSubQuestionScore(
                    qList.stream().map(q -> q.getScore() == null ? 0 : q.getScore().intValue())
                            .collect(Collectors.toList()));
            block.setIsScore(qList.stream().map(q -> q.getIsScoring() != null && q.getIsScoring())
                    .collect(Collectors.toList()));
            block.setIsJudgment(
                    qList.stream().map(q -> q.getIsJudgment() != null && q.getIsJudgment())
                            .collect(Collectors.toList()));
        }
    }

    /**
     * 构建块结构列表
     *
     * @param nodeList    节点列表
     * @param questionMap 试题映射
     * @param createdType 创建类型
     * @return 块结构列表
     */
    private static List<BlockStructDTO> buildBlockStructList(List<ContentNodeDTO> nodeList,
            Map<String, QuestionGroupDTO> questionMap, Integer createdType) {
        if (nodeList == null)
            return Collections.emptyList();
        return nodeList.stream().filter(Objects::nonNull).map(node -> {
            BlockStructDTO block = new BlockStructDTO();
            block.setId(node.getId());
            block.setText(node.getText());
            block.setType(node.getType());
            if (Objects.equals(createdType, CourseConstants.OfficialFlag.NO)) {
                block.setCreatedType(CourseConstants.CREATED_TYPE);
            }
            block.setWordCount(node.getWordCount() == null ? null : node.getWordCount().intValue());
            if (CourseConstants.QUERY_TYPE.equals(node.getType()) && questionMap != null) {
                QuestionGroupDTO qg = questionMap.get(node.getText());
                setQuestionBlockDetails(block, qg);
            }
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                block.setChildren(
                        buildBlockStructList(node.getChildren(), questionMap, createdType));
            }
            return block;
        }).collect(Collectors.toList());
    }
}
