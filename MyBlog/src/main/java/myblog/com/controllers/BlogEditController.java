package myblog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;
import myblog.com.models.entity.Blog;
import myblog.com.services.BlogService;

@Controller
public class BlogEditController {

	// BLogServiceのblogEditCheckメソッドを使いたい
	@Autowired
	private BlogService blogService;

	// sessionを準備する（勝手に編集することはできないようにする）
	@Autowired
	private HttpSession session;

	// 編集画面の表示（article_listに書いてあるURL）
	@GetMapping("/blog/edit/{blogId}")
	// URLの中にはいている自分が取得して欲しい内容を引き出したい（/{blogId}の中の情報）
	public String getBLogEditPage(@PathVariable Long blogId, Model model) {
		// sessionからlogin人の情報をaccountという変数に格納
		// 在AccountLoginController中，已经记住，保存了session的信息(key: "loginAccountInfo")
		// データ型はAccount キャスト
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし account == nullだったら、login画面にリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// そうでない場合、
			// 編集画面に表示させる情報を変数に格納 blog
			// 用blog这个变数来接BlogService中利用blogId找到的想要编辑的内容
			// BlogServiceの編集画面を表示する時のcheck(blogEditCheck)メソッドを使う
			// データ型はBlog
			Blog blog = blogService.blogEditCheck(blogId);
			// もしblog == nullだったら、blog一覧画面にリダイレクトする
			if (blog == null) {
				return "redirect:/blog/list";
			} else {
				// そうでない場合、編集画面に編集内容渡す
				// 最後編集画面を表示
				// 前のblog変数
				model.addAttribute("blog", blog);
				// 名前を表示
				model.addAttribute("accountName", account.getAccountName());
				return "article_edit.html";
			}

		}

	}

	// 更新処理をする
	@PostMapping("/blog/edit/{blogId}")
	// 受け取るもの(article_edit.htmlに参照)
	// 画像のデータ型 特殊
	public String blogUpdate(@RequestParam String blogTitle,
			@RequestParam String categoryName,
			@RequestParam MultipartFile blogImage, 
			@RequestParam String article,
			@RequestParam Long blogId) {
		// sessionからlogin人の情報をaccountという変数に格納
		// 在AccountLoginController中，已经记住，保存了session的信息(key: "loginAccountInfo")
		// データ型はAccount キャスト
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし account == null だったら、login画面にリダイレクトする
		if (account == null) {
			return "redirect:/login.html";
			//そうでない場合、保存する
		} else {
			//画像のファイル名を取得、画像のアップロード
			String fileName = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss-")
					.format(new Date()) + blogImage.getOriginalFilename();
			//画像の保存							//保存のところ
			try {
				Files.copy(blogImage.getInputStream(),
						Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			if(blogService.blogUpdate(blogId, blogTitle, categoryName, fileName, article, account.getAccountId())) {
				//もし　BlogService の　blogUpdate　メソッドの結果がtrueの場合、blog一覧にリダイレクトする
				return "redirect:/blog/list";
			}else {
				//falseの場合、blog編集画面にリダイレクトする(没有blogId不能展示编辑画面)
				return "redirect:/blog/edit" + blogId;
			}
		}
	
		
	}

}
