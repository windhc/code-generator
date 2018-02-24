package ${basePackage}.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ${basePackage}.dao.${className}Mapper;
import ${basePackage}.domain.${className};

/**
 * @author windhc
 * @date ${.now}
 */
@Service
public class ${className}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${className}Service.class);

    @Autowired
    private ${className}Mapper ${className?lower_case}Mapper;

    public void save(${className} ${className?lower_case}) {
        ${className?lower_case}Mapper.insertSelective(${className?lower_case});
    }

    public void delete(Long id) {
        LOGGER.info("根据ID删除:{}", id);
        ${className?lower_case}Mapper.deleteByPrimaryKey(id);
    }

    public void update(${className} ${className?lower_case}) {
        addressMapper.updateByPrimaryKeySelective(${className?lower_case});
    }

    public ${className} findById(Long id) {
        LOGGER.info("根据ID查询详情:{}", id);
        return ${className?lower_case}Mapper.selectByPrimaryKey(id);
    }
}
