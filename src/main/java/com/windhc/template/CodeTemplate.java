package com.windhc.template;

/**
 * @author windhc
 */
public interface CodeTemplate {

    String MAPPER_TEMPLATE = "package ${basePackage}.dao;\n" +
            "\n" +
            "import ${basePackage}.domain.${className};\n" +
            "import tk.mybatis.mapper.common.Mapper;\n" +
            "\n" +
            "/**\n" +
            " * @author windhc\n" +
            " * @date ${.now}\n" +
            " */\n" +
            "public interface ${className}Mapper extends Mapper<${className}> {\n" +
            "\n" +
            "}";

    String PRESENTER_TEMPLATE = "package %s.presenter;\n" +
            "\n" +
            "/**\n * Created by MvpGenerator.\n */\n" +
            "public class %sPresenter extends BasePresenter<%sContract.View, %sContract.Model> implements %sContract.Presenter {\n" +
            "}";

    String MODEL_TEMPLATE = "package %s.model;\n" +
            "\n" +
            "/**\n * Created by MvpGenerator for CreditCard.\n */\n" +
            "public class %sModel implements %sContract.Model {\n" +
            "}";

}
