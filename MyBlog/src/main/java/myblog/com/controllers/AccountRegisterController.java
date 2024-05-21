package myblog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myblog.com.services.AccountService;

@Controller
public class AccountRegisterController {

//AccoountServiceのcreateAccountメソッドを使いたい
	@Autowired
	private AccountService accountService;

	//登録画面の表示
	@GetMapping("/account/register")
	public String getAccountRegisterPage() {
		//htmlの名前
		return "register.html";
	}
	
	//登録処理
	@PostMapping("/account/register/process")
	public String AccountRegisterProcess(@RequestParam String userName,
			@RequestParam String userEmail,
			@RequestParam String password) {
		//もし、createAccount結果　trueの場合、login画面に進む（成功创建账户，进入login页面）
		//そうでない場合、登録画面にとどまる（未成功停止在register页面）
		//createAccountメソッドを使う
		if (accountService.createAccount(userName, userEmail, password)) {
			return "login.html";
		}else {
			return "register.html";
		}
		
	}
}
