package com.cxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxs.entity.Trees;
import com.cxs.service.TreesService;
import com.cxs.mapper.TreesMapper;
import org.springframework.stereotype.Service;

/**
* @author cxs.sakura
* @description 针对表【trees】的数据库操作Service实现
* @createDate 2023-03-09 15:13:19
*/
@Service
public class TreesServiceImpl extends ServiceImpl<TreesMapper, Trees>
    implements TreesService{

}




