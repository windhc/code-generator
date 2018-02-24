package com.windhc.template;

/**
 * @author windhc
 */
public interface CodeTemplate {

    /**
     * same as mapper.ftl
     */
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

    /**
     * same as service.ftl
     */
    String SERVICE_TEMPLATE = "package ${basePackage}.service;\n" +
            "\n" +
            "import java.util.List;\n" +
            "import org.slf4j.Logger;\n" +
            "import org.slf4j.LoggerFactory;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import com.github.pagehelper.PageHelper;\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "\n" +
            "import ${basePackage}.dao.${className}Mapper;\n" +
            "import ${basePackage}.domain.${className};\n" +
            "\n" +
            "/**\n" +
            " * @author windhc\n" +
            " * @date ${.now}\n" +
            " */\n" +
            "@Service\n" +
            "public class ${className}Service {\n" +
            "\n" +
            "    private static final Logger LOGGER = LoggerFactory.getLogger(${className}Service.class);\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ${className}Mapper ${className?lower_case}Mapper;\n" +
            "\n" +
            "    public void save(${className} ${className?lower_case}) {\n" +
            "        ${className?lower_case}Mapper.insertSelective(${className?lower_case});\n" +
            "    }\n" +
            "\n" +
            "    public void delete(Long id) {\n" +
            "        LOGGER.info(\"根据ID删除:{}\", id);\n" +
            "        ${className?lower_case}Mapper.deleteByPrimaryKey(id);\n" +
            "    }\n" +
            "\n" +
            "    public void update(${className} ${className?lower_case}) {\n" +
            "        addressMapper.updateByPrimaryKeySelective(${className?lower_case});\n" +
            "    }\n" +
            "\n" +
            "    public ${className} findById(Long id) {\n" +
            "        LOGGER.info(\"根据ID查询详情:{}\", id);\n" +
            "        return ${className?lower_case}Mapper.selectByPrimaryKey(id);\n" +
            "    }\n" +
            "}\n";

    /**
     * same as web.ftl
     */
    String WEB_TEMPLATE = "package ${basePackage}.web;\n" +
            "\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import ${basePackage}.domain.${className};\n" +
            "import ${basePackage}.service.${className}Service;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import org.springframework.web.bind.annotation.*;\n" +
            "\n" +
            "import java.util.List;\n" +
            "\n" +
            "/**\n" +
            " * @author windhc\n" +
            " * @date ${.now}\n" +
            " */\n" +
            "@RestController\n" +
            "@RequestMapping(\"/web/${className?lower_case}s\")\n" +
            "public class ${className}Controller {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ${className}Service ${className?lower_case}Service;\n" +
            "\n" +
            "    @PostMapping(\"\")\n" +
            "    public void save(@RequestBody ${className} ${className?lower_case}) {\n" +
            "        ${className?lower_case}Service.save(${className?lower_case});\n" +
            "    }\n" +
            "\n" +
            "    @PutMapping(\"\")\n" +
            "    public void update(@RequestBody ${className} ${className?lower_case}) {\n" +
            "        ${className?lower_case}Service.update(${className?lower_case});\n" +
            "    }\n" +
            "\n" +
            "    @DeleteMapping(\"/{id}\")\n" +
            "    public void delete(@PathVariable Long id) {\n" +
            "        ${className?lower_case}Service.delete(id);\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/{id}\")\n" +
            "    public ${className} get${className}(@PathVariable Long id) {\n" +
            "        return ${className?lower_case}Service.findById(id);\n" +
            "    }\n" +
            "\n" +
            "}\n";

}
