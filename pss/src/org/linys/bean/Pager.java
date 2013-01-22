package org.linys.bean;

import java.util.List;

/**
 * 
 * @Description: Bean类 分页
 * @Copyright: 福州骏华科技信息有限公司 (c)2012
 * @Created Date : 2012-3-28
 * @author longweier
 * @vesion 1.0
 */

public class Pager {
	
	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制

	private Integer pageNumber = 1;// 当前页码
	
	private Integer pageSize = 20;// 每页记录数
	
	private Long totalCount = 0L;// 总记录数
	
	private Integer pageCount = 0;// 总页数
	
	@SuppressWarnings("rawtypes")
	private List list;// 数据List

	public Pager(){
		
	}
	
	public Pager(Integer pageNumber,Integer pageSize){
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount.intValue() / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getBeginCount() {
		
		if(pageNumber ==null||pageNumber <0){
			pageNumber = 1;
		}
		
		if(pageSize==null||pageSize<0){
			pageSize = 20;
		}
		
		Integer begin = (pageNumber-1)*pageSize;
		
		return begin;
	}

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

}