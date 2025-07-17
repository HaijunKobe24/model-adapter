package cn.unipus.modeladapter.base.service;

import cn.hutool.core.exceptions.ValidateException;
import cn.unipus.modeladapter.base.common.utils.BookUtils;
import cn.unipus.modeladapter.base.db.entity.Book;
import cn.unipus.modeladapter.base.db.entity.BookUnit;
import cn.unipus.modeladapter.base.db.repository.BookRepository;
import cn.unipus.modeladapter.base.db.repository.BookUnitRepository;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import cn.unipus.modeladapter.remote.starter.http.ipublish.IPublishTemplate;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum.PARAM_ERROR;

/**
 * 教材服务
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@Slf4j
@Service
public class BookService {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookUnitRepository bookUnitRepository;

    @Resource
    private IPublishTemplate iPublishTemplate;


    /**
     * 获取课程教材节点
     *
     * @param bookId 教材ID
     * @return 教材节点列表
     */
    public List<UnitStructDTO> courseBookNodes(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg()));
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(book.getCreator());
        BookStructDTO bookStruct = iPublishTemplate.getBookStruct(book.getRefId(), tokenRequest);
        if (CollectionUtils.isEmpty(bookStruct.getChapterList())) {
            log.error("官方教材章节为空：{}", bookId);
            throw new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg());
        }
        return BookUtils.bookNodesConvert(bookStruct.getChapterList(), getCustomNodes(book));
    }

    /**
     * 获取自定义教材节点
     *
     * @param book 教材
     * @return 自定义教材节点列表
     */
    private List<CustomContentSimpleDTO> getCustomNodes(Book book) {
        List<BookUnit> units = bookUnitRepository.findByBookId(book.getId());
        if (CollectionUtils.isEmpty(units)) {
            return Collections.emptyList();
        }
        List<String> unitIds = units.stream().map(BookUnit::getId).collect(Collectors.toList());
        CustomContentSimpleListDTO listDTO = iPublishTemplate.getEditingCustomContentSimple(
                CustomContentIdRequest.of(unitIds), AccessTokenRequest.of(book.getCreator()));
        List<CustomContentSimpleDTO> customNodes = listDTO.getCustomContentList();
        if (CollectionUtils.isEmpty(customNodes) || customNodes.size() < unitIds.size()) {
            log.error("自定义教材的自定义的单元数据缺失：{}，unitIds：{}", book.getId(), unitIds);
            throw new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg());
        }
        return customNodes;
    }
}
