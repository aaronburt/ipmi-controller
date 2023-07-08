package uk.co.aaronburt.ipmi.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "ipmi")
@Configuration
@Data

public class IpmiConfigurationProperties {
    private String path;
    private String ip;
    private String username;
    private String password;

}
