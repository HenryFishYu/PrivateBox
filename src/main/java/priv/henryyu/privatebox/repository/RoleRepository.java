package priv.henryyu.privatebox.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import priv.henryyu.privatebox.entity.Role;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月26日上午9:12:54
 * @version 1.0.0
 */
@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role,String> {

}
 

