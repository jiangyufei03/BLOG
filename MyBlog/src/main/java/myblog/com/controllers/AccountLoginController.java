package myblog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;
import myblog.com.services.AccountService;

@Controller
public class AccountLoginController {
//AccountServiceのloginCheckメソッドを使いたい
	// インスタンス
	@Autowired
	private AccountService accountService;
	
	//sessionが使えるように宣言
	@Autowired
	private HttpSession session;
	
	//login画面の表示
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "login.html";
	}
	
	//login処理
	@PostMapping("/account/login/process")
	public String accountLoginProcess(@RequestParam String userEmail,
			@RequestParam String password) {
		//loginCheckメソッドを呼び出し、結果をaccountという変数に格納 //データ型はAccount
		//如果有数据的话放入数据，没有数据的话存入null
		Account account = accountService.loginCheck(userEmail, password);
		//もしaccountがnullだったら、login画面にとどまる（用户不存在）
		//そうでない場合、sessionにlogin情報を保存する
		//ブログ一覧画面にダイレクトする	/blog/list
		
		if(account == null) {
			return "login.html";
		}else {
			//sessionに保存する
			//									前のaccount変数
			session.setAttribute("loginAccountInfo", account);
			return "redirect:/blog/list";
		}
		
	}
}
