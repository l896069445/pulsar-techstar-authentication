package auth.server;

import org.apache.pulsar.broker.ServiceConfiguration;
import org.apache.pulsar.broker.authentication.AuthenticationDataSource;
import org.apache.pulsar.broker.authentication.AuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.Set;

/**
 * @author cc
 * @function
 * @date 2021/7/27 14:19
 */
public class VVAuthenticationProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(VVAuthenticationProvider.class);
    private static final String methodName = "vv_auth_v2";

    private String header = "vv_auth";

    @Override
    public void initialize(ServiceConfiguration config) throws IOException {
        log.info(methodName + " initialize");
        if (config == null) {
            return;
        }

        Set<String> superRoles = config.getSuperUserRoles();
        if (superRoles == null) {
            return;
        }
        for (String role : superRoles) {
            log.info(methodName + " initialize " + role);
        }
    }

    @Override
    public String getAuthMethodName() {
        log.info(methodName + " getAuthMethodName");
        return methodName;
    }

    @Override
    public String authenticate(AuthenticationDataSource authData) throws AuthenticationException {
        log.info(methodName + " authenticate");

        String roleToken = "unknown";
        if (authData.hasDataFromCommand()) {
            roleToken = authData.getCommandData();
        } else if (authData.hasDataFromHttp()) {
            roleToken = authData.getHttpHeader(header);
        } else {
            throw new AuthenticationException("Authentication data source does not have a role token");
        }

        log.info(methodName + " authenticate " + roleToken);
        return roleToken;
    }

    @Override
    public void close() throws IOException {
        log.info(methodName + " close");
    }
}


