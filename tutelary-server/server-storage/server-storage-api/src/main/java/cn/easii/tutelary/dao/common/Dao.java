package cn.easii.tutelary.dao.common;

import java.util.List;

public interface Dao<Domain> {

    boolean add(Domain domain);

    boolean edit(Domain domain);

    boolean addAll(List<Domain> domains);

}
