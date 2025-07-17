package cn.unipus.modeladapter.api.service;

import cn.unipus.modeladapter.api.ModelAdapterApiApplication;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.consumer.listener.IPublishContentListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * IPublishBookService 单元测试
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ModelAdapterApiApplication.class})
public class IPublishBookServiceTest {

    @Resource
    private IPublishBookService iPublishBookService;

    @Resource
    private IPublishContentListener iPublishContentListener;


    @Test
    public void testConsumeRawMsg() {
        String msg = "{\"bizId\":\"c1ca297a-777e-4674-b198-d08734081f48\",\"dataPackage\":\"{\\\"courseId\\\":\\\"course-v3:Unipus+testzdyjccs+20250707073246:custom+11205+1752543041756\\\",\\\"instanceId\\\":\\\"course-v3:Unipus+testzdyjccs+20250707073246:custom+11205+1752543041756\\\",\\\"openId\\\":\\\"4a8c470eb36a4236b83142b533ab270f\\\",\\\"userId\\\":2461646}\",\"event\":\"ADD\",\"type\":1}";
        iPublishContentListener.consumeRawMsg(msg);
    }
    /**
     * 测试添加教材节点
     */
    @Test
    public void testAddBookNode() {
        String bookId = "test_book_id";
        String nodeName = "测试节点";
        String nodeId = iPublishBookService.addBookNode(bookId, nodeName);
        assertNotNull("节点ID不能为空", nodeId);
        System.out.println("新增节点ID：" + nodeId);
    }

    /**
     * 测试发布教材
     */
    @Test
    public void testPublishBook() {
        String bookId = "test_book_id";
        iPublishBookService.publishBook(bookId);
        System.out.println("教材发布成功: " + bookId);
    }

    /**
     * 测试教材复制
     */
    @Test
    public void testCopyBook() {
        String bookId = "test_book_id";
        String openId = "test_open_id";
        CopyCourseDataDTO result = iPublishBookService.copyBook(bookId, openId);
        assertNotNull("复制结果不能为空", result);
        assertNotNull("新教材ID不能为空", result.getNewBookId());
        System.out.println("复制教材结果: " + result);
    }
} 