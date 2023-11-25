package cn.easii.tutelary.mapper;

import cn.easii.tutelary.bean.entity.PermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> selectPermissionsByUserId(String userId);

}
