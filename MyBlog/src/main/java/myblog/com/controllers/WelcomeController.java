package myblog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import myblog.com.models.entity.Account;

@Controller
public class WelcomeController {

	@Autowired
	private HttpSession session;

	@GetMapping("/account/welcome")
	public String getAccountWelcomePage(Model model) {
		// sessionからloginしている人の情報を取得
		// データ型はAccount //データ型 //keyの名前
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし account == null、login画面にリダイレクト
		// そうでない場合、blog一覧画面のhtmlを表示し、loginしている人の名前を画面に表示
		if (account == null) {
			return "redirect:/login";
		} else {
			// login人の名前(accountのたくさんの情報の中で名前を取得)
			model.addAttribute("accountName", account.getAccountName());
			//login人のemail
			model.addAttribute("accountEmail", account.getAccountEmail());
			return "welcome.html";
		}
	}
}
