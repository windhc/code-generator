package ${basePackage}.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ${basePackage}.dao.${className}Mapper;
import ${domainPackage}.${className};

/**
 * @author windhc
 * @date ${.now}
 */
@Service
public class ${className}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${className}Service.class);

    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;

    public void save(${className} ${className?uncap_first}) {
        ${className?uncap_first}Mapper.insertSelective(${className?uncap_first});
    }

    public void delete(Long id) {
        LOGGER.info("根据ID删除:{}", id);
        ${className?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    public void update(${className} ${className?uncap_first}) {
        ${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first});
    }

    public ${className} findById(Long id) {
        LOGGER.info("根据ID查询详情:{}", id);
        return ${className?uncap_first}Mapper.selectByPrimaryKey(id);
    }
}
