package com.danielmehlber.sandbox.kafka.apisix.services;


import com.danielmehlber.sandbox.kafka.apisix.entities.User;
import com.danielmehlber.sandbox.kafka.apisix.exceptions.ApiServiceFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApisixService {


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.apisix.url}")
    private String apisixUrl;

    @Value("${services.apisix.port}")
    private String apisixPort;

    @Value("${services.apisix.api.key}")
    private String apiAdminKey;



    private String getApisixUrl() {
        return String.format("http://%s:%s", apisixUrl, apisixPort);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders requestAttributes = new HttpHeaders();

        // add needed header values
        requestAttributes.add("X-API-KEY", apiAdminKey); // API key for accessing manager API of APISIX

        return requestAttributes;
    }


    /**
     * This will communicate with the Gateway REST API via JSON.
     * A new consumer will be added with jwt-auth information. After this information has been added to the Gateway
     * the user can access more services.
     *
     * This method will be retried up to 3 times in case it fails.
     *
     * @param user userdata for consumer
     * @param token generated token for consumer
     */
    @Retryable(value = ApiServiceFailedException.class)
    public void addConsumerAndToken(final User user, final String token) throws ApiServiceFailedException, RuntimeException {

        // 1) Create JSON string for HTTP body (for REST API call)
        String body = String.format("{\n" +
                "    \"username\": \"%s\",\n" +
                "    \"plugins\": {\n" +
                "        \"jwt-auth\": {\n" +
                "            \"key\": \""+token+"\",\n" +    // TODO: insert JWT
                "            \"secret\": \"my-secret-key\"\n" + // TODO: insert secret
                "        }\n" +
                "    }\n" +
                "}", user.getUsername());

        // 2) Create HTTP request of body (=JSON) and required headers (e.g. access key)
        HttpEntity<String> data = new HttpEntity<>(body, getHeaders());

        // 3) Try to call REST API
        try {
            String _url = getApisixUrl() + "/apisix/admin/consumers";
            restTemplate.exchange(getApisixUrl() + "/apisix/admin/consumers", HttpMethod.PUT, data, String.class);
        } catch (final RestClientException e) {
            throw new ApiServiceFailedException("APISIX", "cannot add consumer with JWT token", e);
        }
    }
}
