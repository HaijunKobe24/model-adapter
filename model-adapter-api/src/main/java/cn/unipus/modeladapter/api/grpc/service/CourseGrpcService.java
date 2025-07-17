package cn.unipus.modeladapter.api.grpc.service;

import cn.unipus.modeladapter.api.grpc.convert.CourseProtoObjectConvert;
import cn.unipus.modeladapter.api.proto.client.model.*;
import cn.unipus.modeladapter.api.proto.client.service.CourseServiceGrpc;
import cn.unipus.modeladapter.api.service.IPublishBookService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import javax.annotation.Resource;

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
    private IPublishBookService iPublishBookService;

    /**
     * 创建单元
     *
     * @param request          创建单元请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void createUnit(CreateUnitRequestPO request,
            StreamObserver<CreateUnitResponsePO> responseObserver) {
        String nodeId = iPublishBookService.addBookNode(request.getBookId(), request.getName());
        CreateUnitResponsePO response = CourseProtoObjectConvert.buildCreateUnitResponse(
                request.getBookId(), nodeId);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 发布课程方法
     *
     * @param request          发布课程请求参数对象
     * @param responseObserver 发布课程响应观察者对象
     */
    @Override
    public void publishCourse(PublishCourseRequestPO request,
            StreamObserver<PublishCourseResponsePO> responseObserver) {
        iPublishBookService.publishBook(request.getBookId());
        PublishCourseResponsePO response = CourseProtoObjectConvert.buildPublishCourseResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 修改单元名称
     *
     * @param request          修改单元名称请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void updateUnitName(UpdateUnitNameRequestPO request,
            StreamObserver<UpdateUnitNameResponsePO> responseObserver) {
        iPublishBookService.updateBookNode(request.getBookId(), request.getUnitId(),
                request.getName());
        UpdateUnitNameResponsePO response = CourseProtoObjectConvert.buildUpdateUnitNameResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 删除单元
     *
     * @param request          删除单元请求对象
     * @param responseObserver 响应观察者
     */
    @Override
    public void deleteUnit(DeleteUnitRequestPO request,
            StreamObserver<DeleteUnitResponsePO> responseObserver) {
        iPublishBookService.deleteBookNode(request.getBookId(), request.getUnitId());
        DeleteUnitResponsePO response = CourseProtoObjectConvert.buildDeleteUnitResponse();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}