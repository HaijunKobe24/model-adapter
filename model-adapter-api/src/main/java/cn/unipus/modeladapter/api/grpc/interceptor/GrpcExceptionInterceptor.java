package cn.unipus.modeladapter.api.grpc.interceptor;

import cn.hutool.core.exceptions.ValidateException;
import cn.unipus.modeladapter.remote.starter.common.exception.HttpException;
import com.google.common.collect.Maps;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

import static cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum.PARAM_ERROR;
import static cn.unipus.modeladapter.remote.starter.common.constant.CodeEnum.SERVER_ERROR;

/**
 * gRPC统一异常处理拦截器
 *
 * @author haijun.gao
 * @date 2025/7/1
 */
@Slf4j
@Order(2)
@GRpcGlobalInterceptor
public class GrpcExceptionInterceptor implements ServerInterceptor {

    /**
     * 参数错误异常获取器
     */
    private static final Function<Exception, Pair<Integer, String>> PARAM_ERR_GETTER = e -> Pair.of(
            PARAM_ERROR.getCode(), PARAM_ERROR.getMsg());

    /**
     * 服务端异常获取器
     */
    private static final Function<Exception, Pair<Integer, String>> SERVER_ERR_GETTER =
            e -> Pair.of(
            SERVER_ERROR.getCode(), SERVER_ERROR.getMsg());

    /**
     * 异常类型-异常码获取器 映射关系
     */
    private static final Map<Class<? extends Exception>, Function<Exception, Pair<Integer,
            String>>> EXP_MAP = Maps.newHashMap();

    static {
        EXP_MAP.put(MethodArgumentNotValidException.class, PARAM_ERR_GETTER);
        EXP_MAP.put(BindException.class, PARAM_ERR_GETTER);
        EXP_MAP.put(ConstraintViolationException.class, PARAM_ERR_GETTER);
        EXP_MAP.put(ValidateException.class, e -> {
            ValidateException exp = (ValidateException) e;
            return Pair.of(exp.getStatus(), exp.getMessage());
        });
        EXP_MAP.put(HttpException.class, e -> {
            HttpException exp = (HttpException) e;
            return Pair.of(exp.getCode(), exp.getMessage());
        });
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
            Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(
                        new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                            @Override
                            public void sendMessage(RespT message) {
                                super.sendMessage(message);
                            }
                        }, headers)) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    handleException(call, headers, e);
                }
            }
        };
    }

    /**
     * 处理异常
     *
     * @param call    服务调用
     * @param headers 请求头信息
     * @param exp     异常
     */
    private <ReqT, RespT> void handleException(ServerCall<ReqT, RespT> call, Metadata headers,
            Exception exp) {
        log.error("gRPC execute error: {}", call.getMethodDescriptor().getFullMethodName(), exp);
        try {
            // 获取响应类型并构建错误响应
            RespT response = this.buildErrorResponse(call.getMethodDescriptor(), exp);
            if (response != null) {
                call.sendHeaders(headers);
                call.sendMessage(response);
                call.close(Status.OK, headers);
                return;
            }
        } catch (Exception e) {
            log.error("GrpcExceptionInterceptor Exception", e);
        }
        call.close(Status.UNKNOWN.withDescription(exp.getMessage()), headers);
    }


    /**
     * 创建错误状态
     *
     * @param responseBuilder 响应构建器
     * @param exp             异常
     * @return 错误状态
     */
    private Object createErrorStatus(Object responseBuilder, Exception exp)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> statusClass = this.getStatusClass(responseBuilder);
        if (statusClass == null) {
            return null;
        }
        // 通过反射创建Status Builder
        Method newBuilderMethod = statusClass.getMethod("newBuilder");
        Object statusBuilder = newBuilderMethod.invoke(null);
        // 获取异常信息
        Pair<Integer, String> pair = EXP_MAP.getOrDefault(exp.getClass(), SERVER_ERR_GETTER)
                .apply(exp);
        // 设置错误码和消息
        Method setCodeMethod = statusBuilder.getClass().getMethod("setCode", int.class);
        Object builderWithCode = setCodeMethod.invoke(statusBuilder, pair.getLeft());
        Method setMsgMethod = builderWithCode.getClass().getMethod("setMsg", String.class);
        Object builderWithMsg = setMsgMethod.invoke(builderWithCode, pair.getRight());
        // 构建最终的Status对象
        Method buildMethod = builderWithMsg.getClass().getMethod("build");
        return buildMethod.invoke(builderWithMsg);
    }

    /**
     * 获取状态类
     *
     * @param builder 构建器
     * @return 状态类
     */
    private Class<?> getStatusClass(Object builder) {
        Method[] builderMethods = builder.getClass().getMethods();
        for (Method method : builderMethods) {
            if (method.getName().equals("setStatus") && method.getParameterCount() == 1) {
                Class<?> type = method.getParameterTypes()[0];
                if (type.getName().contains("$")) {
                    continue;
                }
                return type;
            }
        }
        return null;
    }


    /**
     * 构建错误响应
     *
     * @param methodDescriptor 方法描述
     * @param exp              异常
     * @return 错误响应
     */
    @SuppressWarnings("unchecked")
    private <RespT> RespT buildErrorResponse(MethodDescriptor<?, RespT> methodDescriptor,
            Exception exp)
            throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {
        // 通过反射获取响应类型信息
        Class<?> responseClass = this.getResponseClass(methodDescriptor);
        if (responseClass == null) {
            log.error("response class is null：{}", methodDescriptor.getFullMethodName());
            return null;
        }
        // 通过反射获取newBuilder方法
        Method newBuilderMethod = responseClass.getMethod("newBuilder");
        Object builder = newBuilderMethod.invoke(null);
        // 通过反射调用setStatus方法
        Object status = this.createErrorStatus(builder, exp);
        if (status == null) {
            log.error("status is null：{}", methodDescriptor.getFullMethodName());
            return null;
        }
        Method setStatusMethod = builder.getClass().getMethod("setStatus", status.getClass());
        Object builderWithStatus = setStatusMethod.invoke(builder, status);
        // 通过反射调用build方法
        Method buildMethod = builderWithStatus.getClass().getMethod("build");
        return (RespT) buildMethod.invoke(builderWithStatus);
    }


    /**
     * 获取响应类型
     *
     * @param methodDescriptor 方法描述
     * @return 响应类型
     */
    private Class<?> getResponseClass(MethodDescriptor<?, ?> methodDescriptor)
            throws NoSuchFieldException, IllegalAccessException {
        MethodDescriptor.Marshaller<?> responseMarshaller =
                methodDescriptor.getResponseMarshaller();
        if (responseMarshaller == null) {
            return null;
        }
        // 如果是ProtoUtils.marshaller，获取其中的prototype对象类型
        Field prototypeField = responseMarshaller.getClass().getDeclaredField("defaultInstance");
        prototypeField.setAccessible(true);
        return prototypeField.get(responseMarshaller).getClass();
    }
}