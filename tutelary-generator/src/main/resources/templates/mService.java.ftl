package ${package.Service};

import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import java.util.List;

/**
* <p>
* ${table.comment!} 服务类
* </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${serviceClassName}  {
    void add(${domainClassName} ${domainClassName? uncap_first});

    void edit(${domainClassName} ${domainClassName? uncap_first});

    List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first}, long pageIndex, long pageSize);

    List<${domainClassName}> list(${queryDomainName} ${queryDomainName? uncap_first});

    long count(${queryDomainName} ${queryDomainName? uncap_first});

}
