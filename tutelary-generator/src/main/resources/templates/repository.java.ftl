package ${repositoryPackage};

import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import ${entityPackage}.${entityClassName};
import ${daoPackage}.${daoClassName};
import cn.easii.tutelary.common.repository.BaseRepository;
import java.util.List;

/**
* <p>
* ${table.comment!} 数据操作层
* </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${repositoryClassName} extends BaseRepository<${queryDomainName}, ${domainClassName}, ${entityClassName}>, ${daoClassName} {

    boolean add(${domainClassName} ${domainClassName ? uncap_first});

    boolean edit(${domainClassName} ${domainClassName ? uncap_first});

    List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first}, long pageIndex, long pageSize);

    List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first});

    long count(${queryDomainName} ${queryDomainName? uncap_first});

}