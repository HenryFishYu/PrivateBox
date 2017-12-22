package priv.henryyu.privatebox.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import priv.henryyu.privatebox.entity.User;
/**
 * UserController class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
	User findByUsername(String username);
	
}
