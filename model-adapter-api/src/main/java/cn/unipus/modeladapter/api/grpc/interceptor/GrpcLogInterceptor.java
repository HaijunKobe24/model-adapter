package cn.unipus.modeladapter.api.grpc.interceptor;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.core.annotation.Order;

/**
 * gRPC日志拦截器，记录请求响应耗时、入参和出参
 *
 * @author haijun.gao
 * @date 2025/7/9
 */
@Slf4j
@Order(0)
@GRpcGlobalInterceptor
public class GrpcLogInterceptor implements ServerInterceptor {

    private static final String LOG_PREFIX = "[gRPC]";

    private static final JsonFormat.Printer JSON_PRINTER = JsonFormat.printer()
            .omittingInsignificantWhitespace().preservingProtoFieldNames();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
            Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        long startTime = System.currentTimeMillis();
        String methodName = call.getMethodDescriptor().getFullMethodName();
        log.info("{} [{}] Request started", LOG_PREFIX, methodName);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(new LoggingServerCall<>(call, startTime, methodName), headers)) {
            @Override
            public void onMessage(ReqT message) {
                // 记录请求参数
                try {
                    if (message instanceof Message) {
                        String requestJson = JSON_PRINTER.print((Message) message);
                        logRequest(methodName, requestJson);
                    } else {
                        logRequest(methodName, message.toString());
                    }
                } catch (Exception e) {
                    log.warn("{} [{}] Failed to serialize request: {}", LOG_PREFIX, methodName,
                            e.getMessage());
                    logRequest(methodName, message.toString());
                }
                super.onMessage(message);
            }

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    long duration = System.currentTimeMillis() - startTime;
                    log.error("{} [{}] Request failed after {}ms: {}", LOG_PREFIX, methodName,
                            duration, e.getMessage(), e);
                    throw e;
                }
            }
        };
    }

    /**
     * 包装ServerCall以记录响应信息
     *
     * @author haijun.gao
     * @date 2025/7/9
     */
    private static class LoggingServerCall<ReqT, RespT>
            extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> {
        private final long startTime;
        private final String methodName;

        protected LoggingServerCall(ServerCall<ReqT, RespT> delegate, long startTime,
                String methodName) {
            super(delegate);
            this.startTime = startTime;
            this.methodName = methodName;
        }

        @Override
        public void sendMessage(RespT message) {
            // 记录响应参数
            try {
                if (message instanceof Message) {
                    String responseJson = JSON_PRINTER.print((Message) message);
                    logResponse(methodName, responseJson);
                } else {
                    logResponse(methodName, message.toString());
                }
            } catch (Exception e) {
                log.warn("{} [{}] Failed to serialize response: {}", LOG_PREFIX, methodName,
                        e.getMessage());
                logResponse(methodName, message.toString());
            }
            super.sendMessage(message);
        }

        @Override
        public void close(Status status, Metadata trailers) {
            long duration = System.currentTimeMillis() - startTime;
            if (status.isOk()) {
                log.info("{} [{}] Request completed successfully in {}ms", LOG_PREFIX, methodName,
                        duration);
            } else {
                log.error("{} [{}] Request failed in {}ms - Status: {}, Description: {}",
                        LOG_PREFIX, methodName, duration, status.getCode(),
                        status.getDescription());
            }
            super.close(status, trailers);
        }
    }

    /**
     * 记录请求参数
     *
     * @param methodName  方法名称
     * @param requestJson 请求参数
     */
    private void logRequest(String methodName, String requestJson) {
        log.info("{} [{}] Request: {}", LOG_PREFIX, methodName, requestJson);
    }

    /**
     * 记录响应参数
     *
     * @param methodName   方法名称
     * @param responseJson 响应参数
     */
    private static void logResponse(String methodName, String responseJson) {
        log.info("{} [{}] Response: {}", LOG_PREFIX, methodName, responseJson);
    }
}