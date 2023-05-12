package com.example.S20230403.service.jjhService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPaging {
	private int currentPage = 1;   private int rowPage = 10;
	   private int pageBlock = 10;
	   private int start;            private int end;
	   private int startPage;          private int endPage;
	   private int total;             private int totalPage;
	   
	   public AdminPaging(int total, String strCurrentPage) {
	      this.total = total; //
	      if(strCurrentPage != null) {
	         this.currentPage = Integer.parseInt(strCurrentPage); //2
	      }
	      start    = (currentPage -1) * rowPage + 1;   // 시작시 1   11
	      end      = start + rowPage - 1;            // 시작시 10   20
	      
	      totalPage = (int)Math.ceil((double)total/rowPage);   // 시작시 3   5   14
	            //      2         2
	      startPage = currentPage - (currentPage - 1) % pageBlock; // 시작시 1
	      endPage = startPage + pageBlock - 1; // 10
	      if(endPage > totalPage) {
	         endPage = totalPage;
	      }
	   }
}
