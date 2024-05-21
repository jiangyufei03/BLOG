package myblog.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myblog.com.models.dao.AccountDao;
import myblog.com.models.entity.Account;

@Service
public class AccountService {
//AccountDaoのメソッドを使いたい
	@Autowired
//インスタンス
	private AccountDao accountDao;
	
	//登録処理（保存処理）
	//もし、findByAccountEmail＝＝null　だったら、登録処理をする（账号不存在，可以注册）
	//save methodを使用する
	//保存できたら、trueを返す
	//そうでない場合、保存処理失敗、falseを返す（account	既に存在する）
	
	public boolean createAccount(String accountName, String accountEmail, String password) {
			//accountDaoのメソッド
		if(accountDao.findByAccountEmail(accountEmail) == null) {
			//trueの場合、save method(コンストラクタの順番と一致)
			accountDao.save(new Account(accountName, accountEmail, password));
			return true;
		}else {
			//失敗
			return false;
		}
	}
	
	//login　処理
	//もし、findByAccountEmailAndPassword　== null;存在しないnullであることをコントローラーに知らせる
	//一致すれば、loginしている人の情報をコントローラーに渡す
	//戻り値はAccount
	public Account loginCheck(String accountEmail, String password) {
		//結果をaccountこの変数に入れる
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		if(account == null) {
			//存在しない
			return null;
		}else {
			//accountこの変数を返す (把刚刚存入的情报返回)
			return account;
		}
		
	}
	
}
