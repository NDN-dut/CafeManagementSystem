package com.cafe.dal;

import com.cafe.model.Account;
import java.util.List;

public interface IAccountDAO extends IBaseDAO<Account, Integer> {
    Account findByUsername(String username);
}