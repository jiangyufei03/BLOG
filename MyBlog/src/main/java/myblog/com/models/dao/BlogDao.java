package myblog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import myblog.com.models.entity.Blog;

@Repository
@Transactional
public interface BlogDao extends JpaRepository<Blog, Long> {

	//保存処理と更新処理　insert,update
	Blog save(Blog blog);
	
	//一覧表示のメソッド
	//SELECT * FROM blog
	//複数のデータをとる　＝　List
	List<Blog>findAll();
	
	//blogの登録
	//SELECT * FROM blog WHERE blog_title = ?
	//用途：blogの登録チェックに使用、同じブログ名が登録されないようにチェックに使用
	Blog findByBlogTitle(String blogTitle);
	
	//SELECT * FROM blog WHERE blog_id = ?
	//用途編集する内容を表示するとき（一行だけ）
	Blog findByBlogId(Long blogId);
	
	//削除する
	//DELETE FROM blog WHERE blog_id = ?
	void deleteByBlogId(Long blogId);
}
