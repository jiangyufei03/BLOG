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

	//編集画面を表示する時のcheck
	//findByBlogIdを利用して、編集したい内容を探す、blogIdが必要（controllerからもらう）
	//もしcontrollerからもらった　blogId == null　だったら、nullを返す
	//そうでない場合、BLogDaoのfindByBlogIdメソッドの情報をコントローラーに渡す
	//戻り値のデータ型はBlog
	public Blog blogEditCheck(Long blogId) {
		//もしblogId == null　だったら、探すことができない
		if (blogId == null) {
			return null;
		}else {
			//編集したい情報をコントローラーに渡す
			return blogDao.findByBlogId(blogId);
		}
	}
	
	//更新処理のcheck
	//もしblogId　== null だったら、更新処理できない、falseを返す
	//そうでない場合、
	//更新処理をする（データの変わった部分だけを更新する）
	//コントローラーからもらったblogIdを使って、編集する前のデータを取得する
	//変更すべきところだけ、setterを使用して、データの更新をする、trueを返す
	//							受け取る内容はentityに参照(可能会改变的内容)
	public boolean blogUpdate(Long blogId,
				String blogTitle,
				String categoryName,
				String blogImage, 
				String article,
				Long accountId) {
		if (blogId == null) {
			return false;
		}else {
			//BlogDaoのfindByBlogIdメソッドをりようして、blogIdを使って、編集する前のデータをとる
			//データ型はBlog、内容を変数blogに格納
			Blog blog = blogDao.findByBlogId(blogId);
			//setterを使う
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setBlogImage(blogImage);
			blog.setArticle(article);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
	}
	
	//削除処理のcheck
	//blogIdをコントローラーからもらって、削除する
	//もしblogId == nullだったら、削除できない、falseを返す
	//そうでない場合、
	//BlogDaoのdeleteByBlogIdメソッドを使って、商品削除、trueを返す
	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		}else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}
	
	
	
}
