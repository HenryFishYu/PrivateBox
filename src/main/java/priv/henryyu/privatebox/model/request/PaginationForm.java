package priv.henryyu.privatebox.model.request; 
/**
 * easyUI PaginationForm class
 * 
 * @author HenryYu
 * @date 2018年1月8日下午3:24:44
 * @version 1.0.0
 */
public class PaginationForm {
	private int page;
	private int rows;
	private String sort;
	private String order;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
 

