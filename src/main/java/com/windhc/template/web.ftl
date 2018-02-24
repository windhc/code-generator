package ${basePackage}.web;

import com.github.pagehelper.PageInfo;
import ${basePackage}.domain.${className};
import ${basePackage}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author windhc
 * @date ${.now}
 */
@RestController
@RequestMapping("/web/${className?lower_case}s")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?lower_case}Service;

    @PostMapping("")
    public void save(@RequestBody ${className} ${className?lower_case}) {
        ${className?lower_case}Service.save(${className?lower_case});
    }

    @PutMapping("")
    public void update(@RequestBody ${className} ${className?lower_case}) {
        ${className?lower_case}Service.update(${className?lower_case});
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ${className?lower_case}Service.delete(id);
    }

    @GetMapping("/{id}")
    public ${className} get${className}(@PathVariable Long id) {
        return ${className?lower_case}Service.findById(id);
    }

}
