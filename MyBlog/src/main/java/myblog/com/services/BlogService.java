package myblog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myblog.com.models.dao.BlogDao;
import myblog.com.models.entity.Blog;

@Service
public class BlogService {

	// BlogDaoのメソッドを使いたい
	@Autowired
	private BlogDao blogDao;

	// blog一覧のチェック
	// もしaccountId == null, nullを返す
	// findAll内容をコントローラーに渡す
	// 戻り値は List<Blog>
	public List<Blog> selectAllBlogList(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	// blogの登録チェック(同じブログ名が登録されないようにチェックに使用)
	// blogDaoのfindByBlogTitleのメソッドを使う
	// もしfindByBlogTitle==nullだったら、保存処理、trueを返す
	// そうでない場合、保存処理失敗、falseを返す
	public boolean createBLog(String blogTitle, 
			String categoryName, 
			String blogImage, 
			String article, 
			Long accountId) {
		if(blogDao.findByBlogTitle(blogTitle) == null) {
			//如果不存在同名，使用BlogDao的save方法保存
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId));
			//保存出来たら、trueを返す
			return true;
		}else {
			return false;
		}
	}

}
