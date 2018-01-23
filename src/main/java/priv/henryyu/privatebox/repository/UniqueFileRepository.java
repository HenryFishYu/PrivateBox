package priv.henryyu.privatebox.repository; 
/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月12日下午2:21:57
 * @version 1.0.0
 */

import org.springframework.data.repository.PagingAndSortingRepository;

import priv.henryyu.privatebox.entity.UniqueFile; 

public interface UniqueFileRepository extends PagingAndSortingRepository<UniqueFile, String>{

}
 

