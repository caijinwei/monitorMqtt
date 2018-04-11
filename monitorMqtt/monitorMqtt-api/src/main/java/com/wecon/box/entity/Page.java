package com.wecon.box.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
public class Page<T> {

    /**
     * 表示查询后一共得到多少条结果记录
     */
    private int totalRecord;
    /**
     * 表示页面一次要显示多少条记录
     */
    private int pageSize;
    /**
     * 表示将所有的记录进行分页后，一共有多少页
     */
    private int totalPage;
    /**
     * 表示从所有的结果记录中的哪一个编号开始分页查询
     */
    private int startIndex;
    /**
     * 表示用户想看的页数
     */
    private int currentPage;

    /**
     * list集合是用来装载一个页面中的所有记录的
     */
    private List<T> list = new ArrayList<T>();

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param totalRecord
     */
    public Page(final int pageNum, final int pageSize, final int totalRecord) {
        this.currentPage = pageNum;
        this.totalRecord = totalRecord;

        this.pageSize = pageSize;// 设置一页默认显示10条查询记录
        this.startIndex = (this.currentPage - 1) * this.pageSize;// 至于为什么this.page要减1，
        // 是因为mysql数据库对于分页查询时，得到的所有的查询记录，第一条记录的编号是从0开始。
        if (this.totalRecord % this.pageSize == 0) {
            this.totalPage = this.totalRecord / this.pageSize;
        } else {
            this.totalPage = this.totalRecord / this.pageSize + 1;
        }
    }

    /**
     *
     * @return
     */
    public int getTotalRecord() {
        return totalRecord;
    }

    /**
     *
     * @param totalRecord
     */
    public void setTotalRecord(final int totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     *
     * @param pageSize
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     *
     * @return
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     *
     * @param totalPage
     */
    public void setTotalPage(final int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     *
     * @return
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     */
    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     */
    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     *
     * @param list
     */
    public void setList(final List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalRecord=" + totalRecord +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", startIndex=" + startIndex +
                ", currentPage=" + currentPage +
                ", list=" + list +
                '}';
    }
}
