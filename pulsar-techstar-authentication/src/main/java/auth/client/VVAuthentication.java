package auth.client;

import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.AuthenticationDataProvider;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author cc
 * @function
 * @date 2021/7/27 14:00
 */
public class VVAuthentication implements Authentication {
    private static final Logger log = LoggerFactory.getLogger(VVAuthentication.class);
    private static final String methodName = "vv_auth_v2";

    @Override
    public String getAuthMethodName() {
        log.info(methodName + " getAuthMethodName");
        return methodName;
    }

    @Override
    public AuthenticationDataProvider getAuthData() throws PulsarClientException {
        log.info(methodName + " getAuthData");
        return new VVAuthenticationDataProvider();
    }

    @Override
    public void configure(Map<String, String> authParams) {
        log.info(methodName + " configure");
        if (authParams == null) {
            return;
        }

        authParams.forEach((key, value) -> {
            log.info(methodName + " configure " + key + "=" + value);
        });
    }

    @Override
    public void start() throws PulsarClientException {
        log.info(methodName + " start");
    }

    @Override
    public void close() throws IOException {
        log.info(methodName + " close");
    }
}
