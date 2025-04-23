package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        return this.accountDAO.insertUser(account);
    }

    public Account userLogin(Account account) {
        return this.accountDAO.getAccountLogin(account);
    }
}
