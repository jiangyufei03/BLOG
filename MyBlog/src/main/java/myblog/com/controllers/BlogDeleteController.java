
package myblog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;
import myblog.com.services.BlogService;

@Controller
public class BlogDeleteController {
//BlogService の deleteBlog メソッドを使いたい
	@Autowired
	private BlogService blogService;
	
	//sessionを使う(不能所有人都可以删除)
	@Autowired
	private HttpSession session;
	
	//削除処理
	@PostMapping("/blog/delete")
	public String blogDelete(Long blogId) {
		//sessionからlogin人の情報をaccountという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし　account == nullだったら、login画面にリダイレクトする
		if (account == null) {
			return "redirect:/login";
		}else {
			//もし　BlogService の　deleteBlog メソッドの結果がtrueだったら、　＝＞　削除処理成功
			//ブログ一覧ページにリダイレクトする
			if (blogService.deleteBlog(blogId)) {
				return "redirect:/blog/list";
			}else {
				//そうでない場合、
				//編集画面にリダイレクトする
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}
