package priv.henryyu.privatebox.repository;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;

import priv.henryyu.privatebox.entity.File;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月29日下午4:36:32
 * @version 1.0.0
 */
public interface FileRepository extends PagingAndSortingRepository<File, String> {
	public Page<File> findByUsernameAndOriginalNameLikeAndExtensionLikeAndDeleted(String username,String originalName,String extension,Boolean deleted,Pageable pageable);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public File findById(String id);
}
 

