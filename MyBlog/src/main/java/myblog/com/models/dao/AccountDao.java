package myblog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myblog.com.models.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

	//保存処理と更新処理（save）,insertとupdateの処理
	Account save (Account account);
	
	//SELECT * FROM account WHERE account_email = ?
	//同じメールアドレスがあれば登録させないようにする
	//一行だけしかレコード取得できない
	Account findByAccountEmail(String accountEmail);
	
	//login処理をする
	//SELECT * FROM account WHERE account_email = ? AND password = ?
	//入力したメールアドレスとパスワードが一致しているときだけ、データを取得する
	Account findByAccountEmailAndPassword(String accountEmail, String password);
	
	
	
	
	
}
