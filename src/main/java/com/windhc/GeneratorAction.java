package com.windhc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.windhc.template.CodeTemplate;
import com.windhc.utils.FileUtil;
import com.windhc.utils.FreeMarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author windhc
 */
public class GeneratorAction extends AnAction {

    private static final Logger LOGGER = Logger.getInstance(GeneratorAction.class);

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // 得到IDEA文本编辑器实例
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        Document document = editor.getDocument();
        String content = document.getText();

        String className = getClassName(content);
        if (className == null) {
            Messages.showMessageDialog("Can't found class name", "Error", Messages.getErrorIcon());
            return;
        }
        LOGGER.info("className:" + className);

        String currentPath = getCurrentPath(anActionEvent);
        if (currentPath == null || !currentPath.contains("domain")) {
            Messages.showMessageDialog("Your Contract should in package 'domain'.", "Error", Messages.getErrorIcon());
            return;
        }
        String basePath = currentPath.substring(0, currentPath.indexOf("domain/"));
        LOGGER.info("currentPath:" + currentPath);
        LOGGER.info("basePath:" + basePath);
        Messages.showMessageDialog(currentPath, "CurrentPath", Messages.getInformationIcon());
        Messages.showMessageDialog(basePath, "BasePath", Messages.getInformationIcon());
        String basePackage = getPackageName(basePath);
        String domainPackage = getPackageName(currentPath);
        LOGGER.info("basePackage:" + basePackage);

        // 生成代码文件
        Map<String, String> params = new HashMap<>(3);
        params.put("domainPackage", domainPackage);
        params.put("basePackage", basePackage);
        params.put("className", className);

        // 生成dao文件
        String value = FreeMarkerUtil.getProcessValue(params, CodeTemplate.MAPPER_TEMPLATE);
        String daoFilePath = basePath + "/dao/" + className + "Mapper.java";
        if (!FileUtil.exists(daoFilePath)) {
            FileUtil.writeToFile(daoFilePath, value);
        }

        // 生成service文件
        value = FreeMarkerUtil.getProcessValue(params, CodeTemplate.SERVICE_TEMPLATE);
        String serviceFilePath = basePath + "/service/" + className + "Service.java";
        if (!FileUtil.exists(serviceFilePath)) {
            FileUtil.writeToFile(serviceFilePath, value);
        }

        // 生成web文件
        value = FreeMarkerUtil.getProcessValue(params, CodeTemplate.WEB_TEMPLATE);
        String webFilePath = basePath + "/web/" + className + "Controller.java";
        if (!FileUtil.exists(webFilePath)) {
            FileUtil.writeToFile(webFilePath, value);
        }

        Messages.showMessageDialog("created success! please wait a moment", "Success", Messages.getInformationIcon());
        refreshProject(anActionEvent);
    }

    /**
     * 获取类名
     */
    private String getClassName(String content) {
        String[] words = content.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("class")) {
                return words[i+1];
            }
        }
        return null;
    }

    /**
     * 刷新项目
     * @param e AnActionEvent
     */
    private void refreshProject(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }
        project.getBaseDir().refresh(false, true);
    }

    private String getPackageName(String path) {
        String[] strings = path.split("/");
        StringBuilder packageName = new StringBuilder();
        boolean packageBegin = false;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if ("com".equals(string) || "org".equals(string) || "cn".equals(string) || "net".equals(string)
                    || "cc".equals(string) || "info".equals(string)) {
                packageBegin = true;
            }
            if (packageBegin) {
                packageName.append(string);
                if (i != strings.length - 1) {
                    packageName.append(".");
                }
            }
        }
        return packageName.toString();
    }

    private String getCurrentPath(AnActionEvent e) {
        VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (currentFile != null) {
            return currentFile.getPath();
        }
        return null;
    }
}
