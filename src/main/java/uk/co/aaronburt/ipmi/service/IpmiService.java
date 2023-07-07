package uk.co.aaronburt.ipmi.service;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class IpmiService {
    public String pbExec(List<String> command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true); // merge stdout and stderr
            Process p = pb.start();
            InputStream stdOutput = p.getInputStream();
            Writer writer = new StringWriter();
            IOUtils.copy(stdOutput, writer, StandardCharsets.UTF_8);
            return writer.toString().replace("[\r\n]+$", "").trim();
        } catch (Exception error) {
            error.printStackTrace();
            return "Something went wrong with exec";
        }
    }

    public List<String> ipmiCredentialsBuilder(String ipmitoolPath, String ip, String username, String password) {
        return new ArrayList<>(Arrays.asList(ipmitoolPath, "-I", "lanplus", "-H", ip, "-U", username, "-P", password));
    }

    public boolean setPowerStatus(String status) {
        try {
            Dotenv dotenv = Dotenv.load();
            List<String> ipmiCommand = ipmiCredentialsBuilder(
                    dotenv.get("IPMITOOL_PATH"),
                    dotenv.get("IPMI_IP_ADDRESS"),
                    dotenv.get("IPMI_USERNAME"),
                    dotenv.get("IPMI_PASSWORD")
            );

            ipmiCommand.addAll(Arrays.asList("power", status));
            return pbExec(ipmiCommand).contains("Chassis Power Control: Up/On");
        } catch (Exception error) {
            log.error("Something went wrong", error);
            return false;
        }
    }

    public boolean getPowerStatus() {
        try {
            Dotenv dotenv = Dotenv.load();
            List<String> ipmiCommand = ipmiCredentialsBuilder(
                    dotenv.get("IPMITOOL_PATH"),
                    dotenv.get("IPMI_IP_ADDRESS"),
                    dotenv.get("IPMI_USERNAME"),
                    dotenv.get("IPMI_PASSWORD")
            );

            ipmiCommand.addAll(Arrays.asList("power", "status"));
            return pbExec(ipmiCommand).contains("Chassis Power is on");
        } catch (Exception error) {
            log.error("Something went wrong", error);
            return false;
        }
    }

    public void turnOffAutomaticFans() {
        try {
            Dotenv dotenv = Dotenv.load();
            List<String> ipmiCommand = ipmiCredentialsBuilder(
                    dotenv.get("IPMITOOL_PATH"),
                    dotenv.get("IPMI_IP_ADDRESS"),
                    dotenv.get("IPMI_USERNAME"),
                    dotenv.get("IPMI_PASSWORD")
            );

            ipmiCommand.addAll(Arrays.asList("raw", "0x30", "0x30", "0x01", "0x00"));
            pbExec(ipmiCommand);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public String setFanSpeed(Integer fanSpeed) {
        try {
            Dotenv dotenv = Dotenv.load();
            List<String> ipmiCommand = ipmiCredentialsBuilder(
                    dotenv.get("IPMITOOL_PATH"),
                    dotenv.get("IPMI_IP_ADDRESS"),
                    dotenv.get("IPMI_USERNAME"),
                    dotenv.get("IPMI_PASSWORD")
            );

            turnOffAutomaticFans();
            ipmiCommand.addAll(Arrays.asList("raw", "0x30", "0x30", "0x02", "0xff", "0x" + Integer.toHexString(fanSpeed)));
            return pbExec(ipmiCommand);
        } catch (Exception error) {
            error.printStackTrace();
            return "Set fan speed failed";
        }
    }
}
