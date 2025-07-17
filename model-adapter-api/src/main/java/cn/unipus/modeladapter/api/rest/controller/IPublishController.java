package cn.unipus.modeladapter.api.rest.controller;

import cn.unipus.modeladapter.api.service.IPublishBookService;
import cn.unipus.modeladapter.api.service.IPublishNodeService;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.mutable.MutableObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * iPublish接口控制器
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Slf4j
@RestController
@RequestMapping("/ipublish")
public class IPublishController {

    @Resource
    private IPublishBookService iPublishBookService;

    @Resource
    private IPublishNodeService iPublishNodeService;

    /**
     * 教材复制
     *
     * @param openId       用户openId
     * @param sourceBookId 被复制的教材ID
     * @return 复制结果
     */
    @PostMapping("/copyBook")
    public CopyCourseDataDTO copyBook(@RequestParam String openId,
            @RequestParam String sourceBookId) {
        return iPublishBookService.copyBook(sourceBookId, openId);
    }

    /**
     * 添加教材单元节点测试接口
     *
     * @param bookId   教材ID
     * @param unitName 节点名称
     * @param openId   用户openId
     * @return 新节点ID
     */
    @PostMapping("/node/add")
    public MutableObject<String> addBookNode(@RequestParam String bookId,
            @RequestParam String unitName, @RequestParam String openId) {
        String res = iPublishNodeService.addBookNode(bookId, unitName, openId);
        return new MutableObject<>(res);
    }

    /**
     * 测试发布教材接口
     *
     * @param bookId 教材ID
     */
    @PostMapping("/book/publish")
    public void publishBookTest(@RequestParam String bookId) {
        iPublishBookService.publishBook(bookId);
    }
}