package pedro.prueba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

public class CreateConsulKV {

  private static final Logger logger = LoggerFactory.getLogger(CreateConsulKV.class);

  /**
   * The main method
   *
   * @param args the arguments
   */
  public static void main(String[] args) throws IOException {

    String sb =
        "["
            + getKV("deploy-scripts/helm/api-gateway/preconfig/files/api-gateway.properties")
            + getKV("deploy-scripts/helm/fa-service/preconfig/files/fa.properties")
            + getKV(
                "deploy-scripts/helm/rest-service/preconfig/files/rest-service.properties")
            + getKV(
                "deploy-scripts/helm/validation-service/preconfig/files/validation-service.properties")
            + getKV(
                "deploy-scripts/helm/user-management-service/preconfig/files/user-management-service.properties");
    sb = removeLastCharacter(sb);
    sb = removeLastCharacter(sb);
    sb += "]";
    logger.info(sb);
    writeConsulKv(sb);
  }

  private static StringBuilder getKV(String filePath) {
    StringBuilder sb = new StringBuilder();
    File apiGatewayProperties = new File(filePath);
    Scanner myReader = null;
    try {
      myReader = new Scanner(apiGatewayProperties);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (myReader != null) {
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (!"".equals(data.trim())) {
          String[] keyValue = data.split("=");
          try {
            sb.append(transformToKV(keyValue[0], keyValue[1])).append("\n");
          } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(Arrays.toString(keyValue));
            throw e;
          }
        }
      }
      myReader.close();
    }
    return sb;
  }

  private static String transformToKV(String key, String value) {
    String result = "{\"key\": \"";
    result += key;
    result += "\",\"value\": \"";
    result += Base64.getEncoder().encodeToString(value.getBytes());
    result += "\"},";
    return result;
  }

  private static String removeLastCharacter(String str) {
    return Optional.ofNullable(str)
        .filter(sStr -> sStr.length() != 0)
        .map(sStr -> sStr.substring(0, sStr.length() - 1))
        .orElse(str);
  }

  private static void writeConsulKv(String str) throws IOException {
    try (FileOutputStream outputStream =
        new FileOutputStream("configuration\\consul\\consulKV.json")) {
      byte[] strToBytes = str.getBytes();
      outputStream.write(strToBytes);
    }
  }
}
