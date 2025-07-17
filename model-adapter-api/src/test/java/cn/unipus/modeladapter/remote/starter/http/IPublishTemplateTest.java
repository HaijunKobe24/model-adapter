package cn.unipus.modeladapter.remote.starter.http;

import cn.unipus.modeladapter.api.ModelAdapterApiApplication;
import cn.unipus.modeladapter.remote.starter.http.ipublish.IPublishTemplate;
import cn.unipus.modeladapter.remote.starter.http.ipublish.dto.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * IPublishTemplate 单元测试
 *
 * @author haijun.gao
 * @date 2025/7/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ModelAdapterApiApplication.class})
public class IPublishTemplateTest {

    @Resource
    private IPublishTemplate iPublishTemplate;

    /**
     * 测试获取访问令牌
     */
    @Test
    public void testAccessToken() {
        // 准备测试数据
        AccessTokenRequest request = AccessTokenRequest.of("86998c4b7fd841fea2bd308b97b0534d");

        // 执行测试
        String accessToken = iPublishTemplate.accessToken(request);

        // 验证结果
        assertNotNull("访问令牌不能为空", accessToken);
        System.out.println("获取到访问令牌：" + accessToken);
    }

    /**
     * 测试复制内容
     */
    @Test
    public void testCopyContent() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        CopyContentRequest request = CopyContentRequest.of(Arrays.asList("unit001", "unit002"), 0);
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试
        CopyContentDTO result = iPublishTemplate.copyContent(request, tokenRequest);

        // 验证结果
        assertNotNull("复制结果不能为空", result);
        assertNotNull("映射关系不能为空", result.getBizIdMapping());
        System.out.println("复制内容结果：" + result);
    }

    /**
     * 测试获取已发布的自建内容简化信息
     */
    @Test
    public void testGetPublishedCustomContentSimple() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        CustomContentIdRequest request = CustomContentIdRequest.of(
                Arrays.asList("content001", "content002"));
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试
        CustomContentSimpleListDTO result = iPublishTemplate.getPublishedCustomContentSimple(
                request, tokenRequest);

        // 验证结果
        assertNotNull("获取结果不能为空", result);
        System.out.println("已发布内容简化信息：" + result);
    }

    /**
     * 测试获取编写中的自建内容简化信息
     */
    @Test
    public void testGetEditingCustomContentSimple() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        CustomContentIdRequest request = CustomContentIdRequest.of(
                Arrays.asList("content001", "content002"));
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试
        CustomContentSimpleListDTO result = iPublishTemplate.getEditingCustomContentSimple(request,
                tokenRequest);

        // 验证结果
        assertNotNull("获取结果不能为空", result);
        System.out.println("编写中内容简化信息：" + result);
    }

    /**
     * 测试发布自建内容
     */
    @Test
    public void testPublishCustomContent() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        PublishCustomContentRequest request = PublishCustomContentRequest.of(
                Collections.singletonList("content001"));
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试
        Boolean result = iPublishTemplate.publishCustomContent(request, tokenRequest);

        // 验证结果
        assertNotNull("发布结果不能为空", result);
        System.out.println("发布自建内容结果：" + result);
    }

    /**
     * 测试保存编写中的自建内容信息
     */
    @Test
    public void testSaveCustomContent() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        SaveCustomContentRequest request = new SaveCustomContentRequest();
        request.setBizId("content001");
        request.setType(1);
        request.setName("测试内容");
        request.setContent("测试内容数据");
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试，只要不抛异常即为通过
        try {
            iPublishTemplate.saveCustomContent(request, tokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
            fail("保存自建内容时抛出异常: " + e.getMessage());
        }
    }

    /**
     * 测试获取教材结构
     */
    @Test
    public void testGetBookStruct() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        String bookId = "book123";
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试
        BookStructDTO result = iPublishTemplate.getBookStruct(bookId, tokenRequest);

        // 验证结果
        assertNotNull("教材结构不能为空", result);
        assertNotNull("教材ID不能为空", result.getBookId());
        System.out.println("教材结构信息：" + result);
    }

    /**
     * 测试访问令牌缓存功能
     */
    @Test
    public void testAccessTokenCache() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        AccessTokenRequest request = AccessTokenRequest.of(openId);

        // 第一次获取令牌
        long startTime1 = System.currentTimeMillis();
        String token1 = iPublishTemplate.accessToken(request);
        long endTime1 = System.currentTimeMillis();

        // 第二次获取令牌（应该从缓存获取）
        long startTime2 = System.currentTimeMillis();
        String token2 = iPublishTemplate.accessToken(request);
        long endTime2 = System.currentTimeMillis();

        // 验证结果
        assertNotNull("第一次获取令牌不能为空", token1);
        assertNotNull("第二次获取令牌不能为空", token2);
        assertEquals("两次获取的令牌应该相同", token1, token2);

        // 第二次应该更快（从缓存获取）
        long time1 = endTime1 - startTime1;
        long time2 = endTime2 - startTime2;
        System.out.println("第一次获取耗时：" + time1 + "ms");
        System.out.println("第二次获取耗时：" + time2 + "ms");
        System.out.println("缓存生效，第二次获取更快：" + (time2 < time1));
    }

    /**
     * 测试批量操作
     */
    @Test
    public void testBatchOperations() {
        String openId = "86998c4b7fd841fea2bd308b97b0534d";

        // 测试批量复制内容
        CopyContentRequest copyRequest = CopyContentRequest.of(
                Arrays.asList("unit001", "unit002", "unit003"), 0);
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        CopyContentDTO copyResult = iPublishTemplate.copyContent(copyRequest, tokenRequest);
        assertNotNull("批量复制结果不能为空", copyResult);

        // 测试批量获取内容信息
        CustomContentIdRequest contentRequest = CustomContentIdRequest.of(
                Arrays.asList("content001", "content002", "content003"));

        CustomContentSimpleListDTO publishedResult =
                iPublishTemplate.getPublishedCustomContentSimple(
                contentRequest, tokenRequest);
        assertNotNull("批量获取已发布内容结果不能为空", publishedResult);

        CustomContentSimpleListDTO editingResult = iPublishTemplate.getEditingCustomContentSimple(
                contentRequest, tokenRequest);
        assertNotNull("批量获取编写中内容结果不能为空", editingResult);

        System.out.println("批量操作测试完成");
    }

    /**
     * 测试异常情况处理
     */
    @Test
    public void testExceptionHandling() {
        try {
            // 测试无效的教材ID
            String openId = "86998c4b7fd841fea2bd308b97b0534d";
            String invalidBookId = "invalid_book_id";
            AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

            BookStructDTO result = iPublishTemplate.getBookStruct(invalidBookId, tokenRequest);

            // 如果没有抛出异常，验证结果
            if (result != null) {
                System.out.println("无效教材ID处理成功，返回：" + result);
            }

        } catch (Exception e) {
            System.out.println("异常处理测试：捕获到预期异常 - " + e.getMessage());
            // 验证异常类型和消息
            assertNotNull("异常消息不能为空", e.getMessage());
        }
    }

    /**
     * 测试删除自建内容
     */
    @Test
    public void testDeleteCustomContentByBizIds() {
        // 准备测试数据
        String openId = "86998c4b7fd841fea2bd308b97b0534d";
        CustomContentIdRequest request = CustomContentIdRequest.of(
                Arrays.asList("content001", "content002"));
        AccessTokenRequest tokenRequest = AccessTokenRequest.of(openId);

        // 执行测试，只要不抛异常即为通过
        try {
            iPublishTemplate.deleteCustomContentByBizIds(request, tokenRequest);
        } catch (Exception e) {
            e.printStackTrace();
            fail("删除自建内容时抛出异常: " + e.getMessage());
        }
    }
}