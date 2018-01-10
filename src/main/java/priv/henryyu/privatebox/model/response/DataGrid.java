package priv.henryyu.privatebox.model.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月5日下午3:48:49
 * @version 1.0.0
 */
public class DataGrid<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5632293815901382330L;

	/**
	 * 
	 */

	private int total = 0;

	private Collection<T> rows;

	public DataGrid() {
	}

	public DataGrid(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Collection<T> getRows() {
		return rows;
	}

	public void setRows(Collection<T> rows) {
		this.rows = rows;
	}

	

}

