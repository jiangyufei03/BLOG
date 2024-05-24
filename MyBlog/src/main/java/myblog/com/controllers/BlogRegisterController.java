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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;
import myblog.com.services.BlogService;

@Controller
public class BlogRegisterController {
//blogServiceのメソッドを使いたい
	@Autowired
	private BlogService blogService;
	
	//sessionの情報があったら、blog登録できる
	@Autowired
	private HttpSession session;
	
	//blog登録画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		//sessionからlogin人の情報をaccountという変数に格納
		//在AccountLoginController中，已经记住，保存了session的信息(key: "loginAccountInfo")
		//データ型はAccount   キャスト
		Account account = (Account) session.getAttribute("loginAccountInfo");
		
		//もしaccount == nullだったら、login画面にリダイレクト
		if (account == null) {
			return "redirect:/account/login";
			//そうでない場合、login人名前を画面に渡す
			//blog登録のhtmlを表示
		}else {
			model.addAttribute("accountName", account.getAccountName());
			return "article_add.html";
		}
	}
		
		//blogの登録処理
		@PostMapping("/blog/register/process")
		public String blogRegisterProcess(@RequestParam String blogTitle,
				@RequestParam String categoryName,
				@RequestParam MultipartFile blogImage,
				@RequestParam String article) {
			//loginしていない人操作できない
			//sessionからlogin人の情報をaccountという変数に格納
			Account account = (Account) session.getAttribute("loginAccountInfo");
			//もし account == nullだったら、login画面にリダイレクト
			//そうでない場合、保存する
			//画像のファイル名を取得、画像のアップロード
			//もし、同じファイル名がなかったら保存、blog一覧画面にリダイレクトする
			//そうでない場合、blog画面にとどまる
			
			if(account == null) {
				return "redirect:/account/login";
			}else {
				//ファイル名を取得			//new Date() 取得建立时间
				String fileName = new SimpleDateFormat("yyyy-MM-dd-HH--mm-ss-")
						.format(new Date()) + blogImage.getOriginalFilename();
				
				//ファイルの保存作業				
				try {
					Files.copy(blogImage.getInputStream(), Path.of("./images" + fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(blogService.createBLog(blogTitle, categoryName, fileName, article, account.getAccountId())){
					//如果创建成功
					return "redirect:/blog/list";
				}else {
					return "article_add.html";
				}
					
			}
			
			
			
	}
	
}
