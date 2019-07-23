package utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 项目名称：gateway-server
 * 包名称:utils
 * 类描述：
 * 创建人：hejian
 * 创建时间：2019/7/23 11:24
 * 修改人：hejian
 * 修改时间：2019/7/23 11:24
 * 修改备注：
 *
 * @author hejian
 */
public class ClassPathFileUtils {

    public static String getStringFromTxt(String relativePath) {
        ClassPathResource resource = new ClassPathResource(relativePath);
        String content;
        try {
            content = IOUtils.toString(resource.getInputStream());
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
        return content;
    }
}
