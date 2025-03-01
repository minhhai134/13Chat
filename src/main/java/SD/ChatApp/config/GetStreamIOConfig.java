package SD.ChatApp.config;

import io.getstream.services.framework.StreamSDKClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetStreamIOConfig {
    private final String STREAM_API_KEY = "gq9z7eegc8mj";
    private final String STREAM_API_SECRET = "xnhce9t957nhvbz9cqerdymx3qnayw3ft6wzwz5c5bn5vu3tru9skvfc92nczhws";

    @Bean
    public StreamSDKClient streamSDKClient(){
        return new StreamSDKClient(STREAM_API_KEY, STREAM_API_SECRET);
    }
}
