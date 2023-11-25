package ${package.ServiceImpl};

import ${package.Service}.${serviceClassName};
import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import org.springframework.stereotype.Service;
import ${daoPackage}.${daoClassName};
import javax.annotation.Resource;
import java.util.List;

/**
* <p>
* ${table.comment!} 服务实现类
* </p>
*
* @author ${author}
* @since ${date}
*/
@Service
public class ${serviceImplClassName} implements ${serviceClassName} {

    @Resource
    private ${daoClassName} ${daoClassName? uncap_first};

    @Override
    public void add(${domainClassName} ${domainClassName? uncap_first}) {
        // TODO:校验重复、生成序列号
        ${daoClassName? uncap_first}.add(${domainClassName? uncap_first});
    }

    @Override
    public void edit(${domainClassName} ${domainClassName? uncap_first}) {
        ${daoClassName? uncap_first}.edit(${domainClassName? uncap_first});
    }

    @Override
    public List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first}, long pageIndex, long pageSize) {
        return ${daoClassName? uncap_first}.list(${queryDomainName? uncap_first}, pageIndex, pageSize);
    }

    @Override
    public List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first}) {
        return ${daoClassName? uncap_first}.list(${queryDomainName? uncap_first});
    }

    @Override
    public long count(${queryDomainName} ${queryDomainName? uncap_first}) {
        return ${daoClassName? uncap_first}.count(${queryDomainName? uncap_first});
    }

}