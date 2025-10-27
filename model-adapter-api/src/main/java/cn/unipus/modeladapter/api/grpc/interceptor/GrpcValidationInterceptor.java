package cn.unipus.modeladapter.api.grpc.interceptor;

import com.google.protobuf.Message;
import io.envoyproxy.pgv.ReflectiveValidatorIndex;
import io.envoyproxy.pgv.ValidationException;
import io.envoyproxy.pgv.Validator;
import io.envoyproxy.pgv.ValidatorIndex;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.core.annotation.Order;

/**
 * gRPC 参数校验拦截器
 * 使用 protoc-gen-validate 原生接口进行参数校验
 *
 * @author haijun.gao
 * @date 2025/10/23
 */
@Slf4j
@Order(0) // 最高优先级，在日志拦截器之前执行
@GRpcGlobalInterceptor
public class GrpcValidationInterceptor implements ServerInterceptor {

    private static final String LOG_PREFIX = "[gRPC-Validation]";

    private final ValidatorIndex validatorIndex;

    public GrpcValidationInterceptor() {
        // 使用反射式验证器索引，这是protoc-gen-validate的原生接口
        this.validatorIndex = new ReflectiveValidatorIndex();
        log.info("{} Initialized with ReflectiveValidatorIndex", LOG_PREFIX);
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
            Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String methodName = call.getMethodDescriptor().getFullMethodName();
        log.debug("{} [{}] Intercepting gRPC call", LOG_PREFIX, methodName);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT message) {
                Metadata metadata = new Metadata();
                try {
                    // 校验消息参数
                    validateMessage(message, methodName);
                    log.debug("{} [{}] Validation passed", LOG_PREFIX, methodName);
                    super.onMessage(message);
                } catch (ValidationException e) {
                    log.error("{} [{}] Validation failed: {}", LOG_PREFIX, methodName,
                            e.getMessage());
                    call.close(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e),
                            metadata);
                } catch (Exception e) {
                    log.error("{} [{}] Unexpected validation error", LOG_PREFIX, methodName, e);
                    call.close(Status.INTERNAL.withDescription(e.getMessage()).withCause(e),
                            metadata);
                }
            }
        };
    }

    /**
     * 校验消息参数
     *
     * @param message    要校验的消息
     * @param methodName 方法名称（用于日志）
     * @throws ValidationException 校验失败时抛出异常
     */
    private void validateMessage(Object message, String methodName) throws ValidationException {
        if (!(message instanceof Message)) {
            log.debug("{} [{}] Message is not a protobuf Message, skipping validation", LOG_PREFIX,
                    methodName);
            return;
        }
        Message protoMessage = (Message) message;
        String className = protoMessage.getClass().getSimpleName();
        log.debug("{} [{}] Validating message: {}", LOG_PREFIX, methodName, className);
        // 使用 protoc-gen-validate 原生接口进行校验
        Validator<Message> validator = validatorIndex.validatorFor(protoMessage.getClass());
        if (validator != null) {
            // 执行校验，如果校验失败会抛出ValidationException
            validator.assertValid(protoMessage);
        } else {
            log.debug("{} [{}] No validator found for message type: {}", LOG_PREFIX, methodName,
                    className);
        }
    }
}