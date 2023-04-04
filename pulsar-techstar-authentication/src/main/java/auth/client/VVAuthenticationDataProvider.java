package auth.client;

import org.apache.pulsar.client.api.AuthenticationDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cc
 * @function
 * @date 2021/7/27 14:08
 */
public class VVAuthenticationDataProvider implements AuthenticationDataProvider {
    private static final Logger log = LoggerFactory.getLogger(VVAuthenticationDataProvider.class);
    private static final String methodName = "vv_auth_v2";

    private String header = "vv_auth";
    private String token = "vv-role";

    @Override
    public boolean hasDataForHttp() {
        log.info(methodName + " hasDataForHttp");
        return true;
    }

    @Override
    public Set<Map.Entry<String, String>> getHttpHeaders() throws Exception {
        log.info(methodName + " getHttpHeaders");
        Map<String, String> headers = new HashMap<>();
        headers.put(header, token);
        return headers.entrySet();

    }

    @Override
    public boolean hasDataFromCommand() {
        log.info(methodName + " hasDataFromCommand");
        return true;
    }

    @Override
    public String getCommandData() {
        log.info(methodName + " getCommandData");
        return token;
    }
}

