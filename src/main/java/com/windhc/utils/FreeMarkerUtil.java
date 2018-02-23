package com.windhc.utils;

import com.intellij.openapi.diagnostic.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author windhc
 */
public class FreeMarkerUtil {

    private static final Logger LOGGER = Logger.getInstance(FreeMarkerUtil.class);

    public static Configuration configuration;

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * 获取解析后的值.
     * @param params
     * @param templatePath template全路径
     * @return
     */
    public static String getProcessValue(Map<String, String> params, String templatePath) {
        try {
            Template template = new Template("template", new StringReader(templatePath), configuration);
            LOGGER.info("template-templatePath-path:" + templatePath);
//            if (template == null) {
//                return null;
//            }
            LOGGER.info("template:" + template.getName());
            StringWriter sw = new StringWriter();
            template.process(params, sw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
