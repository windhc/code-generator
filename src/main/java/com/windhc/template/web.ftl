package ${basePackage}.web;

import com.github.pagehelper.PageInfo;
import ${domainPackage};
import ${basePackage}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author windhc
 * @date ${.now}
 */
@RestController
@RequestMapping("/web/${className?uncap_first}s")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

    @PostMapping("")
    public void save(@RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.save(${className?uncap_first});
    }

    @PutMapping("")
    public void update(@RequestBody ${className} ${className?uncap_first}) {
        ${className?uncap_first}Service.update(${className?uncap_first});
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ${className?uncap_first}Service.delete(id);
    }

    @GetMapping("/{id}")
    public ${className} get${className}(@PathVariable Long id) {
        return ${className?uncap_first}Service.findById(id);
    }

}
