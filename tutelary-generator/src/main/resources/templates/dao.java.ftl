package ${daoPackage};

import ${domainClassPackage}.${domainClassName};
import ${queryDomainPackage}.${queryDomainName};
import cn.easii.tutelary.dao.common.QueryDAO;
import java.util.List;

/**
* <p>
* ${table.comment!} 数据操作层
* </p>
*
* @author ${author}
* @since ${date}
*/
public interface ${daoClassName} extends QueryDAO<${queryDomainName}, ${domainClassName}>  {
}
