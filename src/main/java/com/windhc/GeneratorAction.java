package com.windhc;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.windhc.template.CodeTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author windhc
 */
public class GeneratorAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 得到IDEA文本编辑器实例
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
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

        String currentPath = getCurrentPath(e);
        if (currentPath == null || !currentPath.contains("domain")) {
            Messages.showMessageDialog("Your Contract should in package 'domain'.", "Error", Messages.getErrorIcon());
            return;
        }
        String basePath = currentPath.replace("contract/" + className + ".java", "");
        String basePackage = getPackageName(basePath);
        String modelName = className.substring(0, className.indexOf("Contract"));

        String contractContent = String.format(CodeTemplate.CONTRACT_TEMPLATE, basePackage, modelName);
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> editor.getDocument().setText(contractContent));

        try {
            createPresenterClass(basePackage, basePath, modelName);
            createModelClass(basePackage, basePath, modelName);
        } catch (IOException e1) {
            Messages.showMessageDialog("create file failed", "Error", Messages.getErrorIcon());
            return;
        }
        Messages.showMessageDialog("created success! please wait a moment", "Success", Messages.getInformationIcon());
        refreshProject(e);
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
