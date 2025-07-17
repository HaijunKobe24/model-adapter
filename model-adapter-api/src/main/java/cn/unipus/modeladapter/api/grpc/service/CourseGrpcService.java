package cn.unipus.modeladapter.api.grpc.service;

import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.api.proto.client.service.CourseServiceGrpc;
import cn.unipus.modeladapter.api.grpc.convert.CourseProtoObjectConvert;
import cn.unipus.modeladapter.api.mapper.course.CourseProtoMapper;
import cn.unipus.modeladapter.api.service.impl.CourseServiceImpl;
import cn.unipus.modeladapter.api.dto.UnitStatusInfo;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.CreateUnitDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.CourseStructDTO;
import cn.unipus.modeladapter.api.proto.client.model.CourseStructPO;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程gRPC服务实现
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Slf4j
@GRpcService
public class CourseGrpcService extends CourseServiceGrpc.CourseServiceImplBase {

    @Resource
    private CourseServiceImpl courseServiceImpl;

    @Resource
    private CourseProtoMapper courseProtoMapper;

    /**
     * 复制课程
     *
     * @param request 请求参数
     * @param responseObserver 响应观察者
     */
    @Override
    public void copyCourse(CopyCourseRequestPO request,
            StreamObserver<CopyCourseResponsePO> responseObserver) {
        log.info("复制课程请求: {}", request);

        // 调用业务逻辑
        CopyCourseDataDTO copyCourseDataDTO = courseServiceImpl.copyCourse(request.getBookId(), request.getOpenId());

        //类型转换
        CopyCourseDataPO copyCourseDataPO = courseProtoMapper.copyCourseDataDTOToPOWithCheck(copyCourseDataDTO);
        // 构建响应
        CopyCourseResponsePO response = CourseProtoObjectConvert.buildCopyCourseResponse(copyCourseDataPO);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 创建单元
     *
     * @param request 创建单元请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void createUnit(CreateUnitRequestPO request,
            StreamObserver<CreateUnitResponsePO> responseObserver) {
        log.info("创建单元请求: {}", request);

        // 调用业务逻辑创建单元
        CreateUnitDTO result = courseServiceImpl.createUnit(request.getBookId(), request.getName());
        // 构建响应
        CreateUnitResponsePO response = CourseProtoObjectConvert.buildCreateUnitResponse(result.getBookId(), result.getNewUnitId());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 发布课程方法
     *
     * @param request 发布课程请求参数对象
     * @param responseObserver 发布课程响应观察者对象
     */
    @Override
    public void publishCourse(PublishCourseRequestPO request,
            StreamObserver<PublishCourseResponsePO> responseObserver) {
        log.info("发布课程请求: {}", request);

        // 调用业务逻辑发布课程
        courseServiceImpl.publishCourse(request.getBookId());

        PublishCourseResponsePO response = CourseProtoObjectConvert.buildPublishCourseResponse();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 查询单元状态的方法
     *
     * @param request 查询单元状态的请求参数
     * @param responseObserver 返回查询结果的观察者
     */
    @Override
    public void queryUnitStatus(QueryUnitStatusRequestPO request,
            StreamObserver<QueryUnitStatusResponsePO> responseObserver) {
        log.info("查询单元状态请求: {}", request);

        // 业务逻辑实现 - 拦截器会自动处理异常
        List<UnitStatusInfo> unitStatusList = courseServiceImpl.queryUnitStatus(request.getBookId());

        QueryUnitStatusResponsePO response = CourseProtoObjectConvert.buildQueryUnitStatusResponse(unitStatusList);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 处理自定义课程结构请求的方法。
     *
     * @param request 请求参数，包含书籍ID等
     * @param responseObserver 响应观察者，用于发送响应结果
     */
    @Override
    public void customCourseStruct(CustomCourseStructRequestPO request,
            StreamObserver<CustomCourseStructResponsePO> responseObserver) {

            log.info("获取自定义课程结构请求: {}", request);
            // 调用业务逻辑获取课程结构列表
            List<CourseStructDTO> courseStructList = courseServiceImpl.customCourseStruct(request.getBookId());
            List<CourseStructPO> courseStructPOList = courseProtoMapper.courseStructDTOListToPOList(courseStructList);

        // 构建响应
            CustomCourseStructResponsePO response = CourseProtoObjectConvert.buildCustomCourseStructResponsePO(courseStructPOList);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

    }

    /**
     * 修改单元名称
     *
     * @param request 修改单元名称请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void updateUnitName(UpdateUnitNameRequestPO request,
            StreamObserver<UpdateUnitNameResponsePO> responseObserver) {
        log.info("修改单元名称请求: {}", request);
        courseServiceImpl.updateUnitName(request.getBookId(), request.getUnitId(), request.getName());
        UpdateUnitNameResponsePO response = CourseProtoObjectConvert.buildUpdateUnitNameResponse();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 删除单元
     *
     * @param request 删除单元请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void deleteUnit(DeleteUnitRequestPO request,
            StreamObserver<DeleteUnitResponsePO> responseObserver) {
        log.info("删除单元请求: {}", request);
         courseServiceImpl.deleteUnit(request.getBookId(), request.getUnitId());

        DeleteUnitResponsePO response = CourseProtoObjectConvert.buildDeleteUnitResponse();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}