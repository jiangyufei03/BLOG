package myblog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;
import myblog.com.models.entity.Blog;
import myblog.com.services.BlogService;

@Controller
public class BlogListController {
//loginしている人の情報を表示する(sessionを使う)
	@Autowired
	private HttpSession session; 
	
	//BLogServiceのselectAllBlogListメソッドを使いたい
	@Autowired
	private BlogService blogService; 
	
	//blog一覧画面を表示する
	@GetMapping("/blog/list")
	public String getBlogListPage(Model model) {
		//sessionからloginしている人の情報を取得
		//データ型はAccount	//データ型					//keyの名前
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし　account == null、login画面にリダイレクト
		//そうでない場合、blog一覧画面のhtmlを表示し、loginしている人の名前を画面に表示
		if (account == null) {
			return "redirect:/login";
		}else {
			//blog内容を取得し(ユーザーidを利用して[通过之前的account变数来取得])、 blogListという変数に格納
			List<Blog> blogList = blogService.selectAllBlogList(account.getAccountId());
			
			//login人の名前(accountのたくさんの情報の中で名前を取得)
			model.addAttribute("accountName", account.getAccountName());
			// blogListを画面に渡す
			model.addAttribute("blogList", blogList);
			return "article_list.html";
		}
	}
}
