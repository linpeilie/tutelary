package ${repositoryImplPackage};

import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import ${repositoryPackage}.${repositoryClassName};
import ${entityPackage}.${entityClassName};
import ${mapperPackage}.${mapperClassName};
import cn.easii.tutelary.common.repository.AbstractRepository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import io.github.linpeilie.Converter;
import java.util.List;
import ${domainClassPackage}.${domainClassName};

/**
* <p>
* ${table.comment!} 数据操作层
* </p>
*
* @author ${author}
* @since ${date}
*/
@Repository
public class ${repositoryImplClassName}  extends AbstractRepository<${queryDomainName}, ${domainClassName}, ${entityClassName}, ${mapperClassName}>
    implements ${repositoryClassName} {

    @Override
    public boolean edit(${domainClassName} ${domainClassName ? uncap_first}) {
        LambdaUpdateWrapper<${entityClassName}> updateWrapper = Wrappers.lambdaUpdate();
        // TODO:修改项
        return super.update(new ${entityClassName}(), updateWrapper);
    }
}