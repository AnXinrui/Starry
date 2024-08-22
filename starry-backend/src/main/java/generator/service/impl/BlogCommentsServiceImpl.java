package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.axr.starrybackend.model.domain.BlogComments;
import generator.service.BlogCommentsService;
import generator.mapper.BlogCommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【blog_comments(博客评论)】的数据库操作Service实现
* @createDate 2024-08-22 09:06:34
*/
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments>
    implements BlogCommentsService{

}




