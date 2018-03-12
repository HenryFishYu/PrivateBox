package priv.henryyu.privatebox.repository;

import java.sql.Time;
import java.sql.Timestamp;

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
	public Page<File> findByUsernameAndOriginalNameLikeAndExtensionLikeAndDeletedAndCreateTimeBetween(String username,String originalName,String extension,Boolean deleted,Timestamp beginCreateTime,Timestamp endCreateTime,Pageable pageable);
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public File findById(String id);
}
 

