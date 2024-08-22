package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.axr.starrybackend.model.domain.Blog;
import generator.service.BlogService;
import generator.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【blog(博客)】的数据库操作Service实现
* @createDate 2024-08-22 09:06:24
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




