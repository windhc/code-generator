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
            "import ${domainPackage}.${className};\n" +
            "import org.apache.ibatis.annotations.Mapper;\n" +
            "\n" +
            "/**\n" +
            " * @author windhc\n" +
            " * @date ${.now}\n" +
            " */\n" +
            "@Mapper\n" +
            "public interface ${className}Mapper extends tk.mybatis.mapper.common.Mapper<${className}> {\n" +
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
            "    private ${className}Mapper ${className?uncap_first}Mapper;\n" +
            "\n" +
            "    public void save(${className} ${className?uncap_first}) {\n" +
            "        ${className?uncap_first}Mapper.insertSelective(${className?uncap_first});\n" +
            "    }\n" +
            "\n" +
            "    public void delete(Long id) {\n" +
            "        LOGGER.info(\"根据ID删除:{}\", id);\n" +
            "        ${className?uncap_first}Mapper.deleteByPrimaryKey(id);\n" +
            "    }\n" +
            "\n" +
            "    public void update(${className} ${className?uncap_first}) {\n" +
            "        ${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first});\n" +
            "    }\n" +
            "\n" +
            "    public ${className} findById(Long id) {\n" +
            "        LOGGER.info(\"根据ID查询详情:{}\", id);\n" +
            "        return ${className?uncap_first}Mapper.selectByPrimaryKey(id);\n" +
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
            "@RequestMapping(\"/web/${className?uncap_first}s\")\n" +
            "public class ${className}Controller {\n" +
            "\n" +
            "    @Autowired\n" +
            "    private ${className}Service ${className?uncap_first}Service;\n" +
            "\n" +
            "    @PostMapping(\"\")\n" +
            "    public void save(@RequestBody ${className} ${className?uncap_first}) {\n" +
            "        ${className?uncap_first}Service.save(${className?uncap_first});\n" +
            "    }\n" +
            "\n" +
            "    @PutMapping(\"\")\n" +
            "    public void update(@RequestBody ${className} ${className?uncap_first}) {\n" +
            "        ${className?uncap_first}Service.update(${className?uncap_first});\n" +
            "    }\n" +
            "\n" +
            "    @DeleteMapping(\"/{id}\")\n" +
            "    public void delete(@PathVariable Long id) {\n" +
            "        ${className?uncap_first}Service.delete(id);\n" +
            "    }\n" +
            "\n" +
            "    @GetMapping(\"/{id}\")\n" +
            "    public ${className} get${className}(@PathVariable Long id) {\n" +
            "        return ${className?uncap_first}Service.findById(id);\n" +
            "    }\n" +
            "\n" +
            "}\n";

}
