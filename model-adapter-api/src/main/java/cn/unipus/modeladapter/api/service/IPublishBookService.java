package cn.unipus.modeladapter.api.service;

import cn.hutool.core.exceptions.ValidateException;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.UnitMappingDTO;
import cn.unipus.modeladapter.base.common.utils.BookUtils;
import cn.unipus.modeladapter.base.db.entity.Book;
import cn.unipus.modeladapter.base.db.repository.BookRepository;
import cn.unipus.modeladapter.remote.starter.http.ipublish.IPublishTemplate;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.AccessTokenRequest;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.BookStructDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum.PARAM_ERROR;

/**
 * iPublish 教材服务
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@Slf4j
@Service
public class IPublishBookService {

    @Resource
    private BookRepository bookRepository;

    @Resource
    private IPublishTemplate iPublishTemplate;

    @Resource
    private IPublishNodeService iPublishNodeService;

    /**
     * 教材复制
     *
     * @param bookId 被复制的教材ID
     * @param openId 用户openId
     * @return 复制结果
     */
    public CopyCourseDataDTO copyBook(String bookId, String openId) {
        log.info("开始复制教材，openId: {}, bookId: {}", openId, bookId);
        if (BookUtils.isCustomBookId(bookId)) {
            return this.copyCustomBook(bookId, openId);
        }
        return this.copyOfficialBook(bookId, openId);
    }

    /**
     * 添加教材节点
     *
     * @param bookId   教材ID
     * @param nodeName 节点名称
     * @return 节点ID
     */
    public String addBookNode(String bookId, String nodeName) {
        String nodeId = bookRepository.findById(bookId)
                .map(b -> iPublishNodeService.addBookNode(b.getId(), nodeName, b.getCreator()))
                .orElseThrow(
                        () -> new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg()));
        log.info("新增教材节点：bookId：{}，nodeId：{}", bookId, nodeId);
        return nodeId;
    }

    /**
     * 修改教材节点
     *
     * @param bookId   教材ID
     * @param nodeId   节点ID
     * @param nodeName 节点名称
     */
    public void updateBookNode(String bookId, String nodeId, String nodeName) {
        this.bookValid(bookId);
        iPublishNodeService.updateBookNode(nodeId, nodeName);
        log.info("修改教材{}节点成功：{}", bookId, nodeId);
    }

    /**
     * 删除教材节点
     *
     * @param bookId 教材ID
     * @param nodeId 节点ID
     */
    public void deleteBookNode(String bookId, String nodeId) {
        this.bookValid(bookId);
        iPublishNodeService.deleteBookNode(nodeId);
        log.info("删除教材{}节点成功：{}", bookId, nodeId);
    }

    /**
     * 发布教材
     *
     * @param bookId 教材ID
     */
    public void publishBook(String bookId) {
        this.bookValid(bookId);
        iPublishNodeService.publishBookNodes(bookId);
        log.info("教材发布成功：{}", bookId);
    }

    /**
     * 复制官方教材
     *
     * @param bookId 官方教材ID
     * @param openId 用户openId
     * @return 复制结果
     */
    private CopyCourseDataDTO copyOfficialBook(String bookId, String openId) {
        log.info("复制官方教材，bookId: {}", bookId);
        BookStructDTO bookStruct = iPublishTemplate.getBookStruct(bookId,
                AccessTokenRequest.of(openId));
        if (bookStruct == null) {
            log.error("官方教材不存在：{}", bookId);
            throw new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg());
        }
        String id = BookUtils.genBookId();
        bookRepository.save(this.createBook(id, bookId, openId));
        // 返回结果
        CopyCourseDataDTO result = new CopyCourseDataDTO();
        result.setNewBookId(id);
        log.info("官方教材复制完成，id: {}", id);
        return result;
    }

    /**
     * 复制自建教材
     *
     * @param bookId 被复制的自建教材ID
     * @param openId 用户openId
     * @return 复制结果
     */
    private CopyCourseDataDTO copyCustomBook(String bookId, String openId) {
        log.info("复制自建教材，bookId: {}", bookId);
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg()));
        String id = BookUtils.genBookId();
        // 复制此教材的自建单元
        Map<String, String> destIdBySrcId = iPublishNodeService.copyBookNodes(book.getId(), id,
                openId);
        List<UnitMappingDTO> mappings = Optional.ofNullable(destIdBySrcId).map(Map::entrySet)
                .orElse(Collections.emptySet()).stream()
                .map(entry -> UnitMappingDTO.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        bookRepository.save(this.createBook(id, book.getRefId(), openId));
        CopyCourseDataDTO result = new CopyCourseDataDTO();
        result.setNewBookId(id);
        result.setUnitMappings(mappings);
        return result;
    }

    /**
     * 教材校验
     *
     * @param id 教材ID
     */
    private void bookValid(String id) {
        bookRepository.findById(id).orElseThrow(
                () -> new ValidateException(PARAM_ERROR.getCode(), PARAM_ERROR.getMsg()));
    }

    /**
     * 创建教材
     *
     * @param id     教材ID
     * @param refId  官方教材ID
     * @param openId 用户openId
     * @return 新的教材
     */
    private Book createBook(String id, String refId, String openId) {
        Book newBook = new Book();
        newBook.setId(id);
        newBook.setRefId(refId);
        newBook.setCreator(openId);
        newBook.setModifier(openId);
        newBook.setCreated(new Date());
        newBook.setModified(new Date());
        return newBook;
    }
}
