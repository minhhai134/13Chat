package SD.ChatApp.config;

import io.getstream.services.framework.StreamSDKClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetStreamIOConfig {
    private final String STREAM_API_KEY = "";
    private final String STREAM_API_SECRET = "";

    @Bean
    public StreamSDKClient streamSDKClient(){
        StreamSDKClient client = new StreamSDKClient(STREAM_API_KEY, STREAM_API_SECRET);
        return client;
    }
}
