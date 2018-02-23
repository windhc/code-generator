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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        Messages.showMessageDialog(className, "ClassName", Messages.getInformationIcon());
        LOGGER.info("className:" + className);

        String currentPath = getCurrentPath(anActionEvent);
        if (currentPath == null || !currentPath.contains("domain")) {
            Messages.showMessageDialog("Your Contract should in package 'domain'.", "Error", Messages.getErrorIcon());
            return;
        }
        String basePath = currentPath.substring(0, currentPath.indexOf("domain/"));
        String basePackage = getPackageName(basePath);
        Messages.showMessageDialog(basePackage, "BasePackage", Messages.getInformationIcon());
        LOGGER.info("basePackage:" + basePackage);

        // 生成代码文件
        Map<String, String> params = new HashMap<>();
        params.put("basePackage", basePackage);
        params.put("className", className);
        String value = FreeMarkerUtil.getProcessValue(params, CodeTemplate.MAPPER_TEMPLATE);
        FileUtil.writeToFile(basePath + "/dao", value);
        Messages.showMessageDialog(value, "FTLvalue", Messages.getInformationIcon());
        LOGGER.info("FTLvalue:" + value);

//        String contractContent = String.format(CodeTemplate.CONTRACT_TEMPLATE, basePackage, className);
//        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> editor.getDocument().setText(contractContent));
//
//        try {
//            createPresenterClass(basePackage, basePath, className);
//            createModelClass(basePackage, basePath, className);
//        } catch (IOException e1) {
//            Messages.showMessageDialog("create file failed", "Error", Messages.getErrorIcon());
//            return;
//        }
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

    private void createModelClass(String basePackage, String path, String modelName) throws IOException {
        String dir = path + "model/" ;
        String filePath = dir + modelName + "Model.java";
        File dirs = new File(dir);
        File file = new File(filePath);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        String content = String.format(CodeTemplate.MODEL_TEMPLATE, basePackage, modelName, modelName);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private void createPresenterClass(String basePackage, String path, String modelName) throws IOException {
        String dir = path + "presenter/";
        String filePath = dir + modelName + "Presenter.java";
        File dirs = new File(dir);
        File file = new File(filePath);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        String content = String.format(CodeTemplate.PRESENTER_TEMPLATE, basePackage, modelName, modelName, modelName, modelName);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private String getPackageName(String path) {
        String[] strings = path.split("/");
        StringBuilder packageName = new StringBuilder();
        boolean packageBegin = false;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if ((string.equals("com")) || (string.equals("org")) || (string.equals("cn"))) {
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
