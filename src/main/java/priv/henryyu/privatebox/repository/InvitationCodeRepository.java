package priv.henryyu.privatebox.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.User;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月26日上午11:27:45
 * @version 1.0.0
 */
public interface InvitationCodeRepository extends PagingAndSortingRepository<InvitationCode,String>{
	public Page<InvitationCode> findByCreateUserAndCodeLike(User user,String code,Pageable pageable);
}
 

