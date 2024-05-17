package com.codingrecipe.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Data
public class Page {

    private static final int PAGE_NUM = 1; // 현재 페이지 번호 기본값
    private static final int ROWS = 10; // 페이지당 게시글 수 기본값
    private static final int COUNT = 10; // 노출 페이지 갯수 기본값

    private  int page;  // 페이지 번호
    private  int rows;  // 페이지당 글 수
    private  int count; // 노출 페이지 갯수
    private  int total; // 전체 데이터 갯수

    private int start;  // 시작번호
    private int end;   // 끝 번호
    private int first;  // 첫번호
    private int last;   // 마지막번호

    private int prev;   // 이전번호
    private int next;  // 다음번호
    private int index;  // 데이터 순서 번호

    public Page() {
        this(0);
    }

    public Page(int total) {
        this(PAGE_NUM, total);
    }

    public Page(int page, int total) {
        this(page, ROWS, COUNT, total);
    }

    public Page(int page, int rows, int count, int total) {
        this.page = page;
        this.rows = rows;
        this.count = count;
        this.total = total;
        calc();
    }

    // setter
    // - 데이터 갯수 지정 후, 페이징 수식 재계산
    public void setTotal(int total) {
        this.total = total;
        calc();
    }

    // 페이지 처리 수식
    public void calc() {
        this.first = 1;
        this.last = (this.total -1) / rows + 1;
        this.start = ((page-1) / count ) * count + 1;
        this.end = ((page-1) / count + 1) * count ;
        if( this.end > this.last) this.end = this.last;

        // 이전 번호
        this.prev = this.page -1;
        // 다음 번호
        this.next = this.page + 1;
        // 데이터 순서 번호(index)
        this.index = (this.page - 1) * this.rows;
    }

}