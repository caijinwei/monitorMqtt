package com.wecon.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linkaixun on 2016/1/7.
 */
public class PagedList<T> //extends ICollection<T>
{
    //数据总数
    private int recordCount;
    //每页显示的总数
    private int pageSize;
    //当前页码
    private int pageIndex;
    //总的页数
    private int pageCount;
    //当前页起始索引
    private int startIndex;
    //当前页结束索引
    private int endIndex;

    private List<T> list;

    public PagedList()
    {
        //super();
    }

    /**
     *
     * @param list
     * @param pageIndex
     * @param pageSize
     */
    public PagedList(List<T> list, int pageIndex, int pageSize)
    {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        if (list == null)
        {
            this.recordCount = 0;
            this.list = list;
        }
        else if (list.size() < pageSize)
        {
            this.recordCount = list.size();
            this.list = list;
        }
        else
        {
            this.recordCount = list.size();
            init();
            this.list = list.subList(this.startIndex, this.endIndex);
        }
    }

    /**
     * @param pageIndex
     * @param pageSize
     * @param recordCount
     */
    public PagedList(int pageIndex, int pageSize, int recordCount)
    {
        //super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        init();
    }

    /**
     *
     * @param list 已经分页过的数据
     * @param pageIndex
     * @param pageSize
     * @param recordCount
     */
    public PagedList(List<T> list, int pageIndex, int pageSize, int recordCount)
    {
        //super();
        this.list = list;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.recordCount = recordCount;
        init();
    }

    public boolean isAtLastPage()
    {
        return this.recordCount <= this.pageSize || this.pageIndex >= this.pageCount;
    }

    private void init()
    {
        /*计算出总共能分成多少页*/
        if (recordCount % pageSize > 0)//数据总数和每页显示的总数不能整除的情况
        {
            pageCount = recordCount / pageSize + 1;
        }
        else   //数据总数和每页显示的总数能整除的情况
        {
            pageCount = recordCount / pageSize;
        }

        if (recordCount > 0)
        {
            if (pageIndex <= pageCount)
            {
                if (pageIndex == 1)     //当前页数为第一页
                {
                    if (recordCount <= pageSize)  //数据总数小于每页显示的数据条数
                    {
                        //截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
                        startIndex = 0;
                        endIndex = recordCount;
                    }
                    else
                    {
                        startIndex = 0;
                        endIndex = pageSize;
                    }
                }
                else
                {
                    //截取起始下标
                    int fromIndex = (pageIndex - 1) * pageSize;
                    //截取截止下标
                    int toIndex = pageIndex * pageSize;
                    /*计算截取截止下标*/
                    if (toIndex > recordCount)
                    {
                        toIndex = recordCount;
                    }
                    if (recordCount >= toIndex)
                    {
                        startIndex = fromIndex;
                        endIndex = toIndex;
                    }
                }
            }
            else
            {
                startIndex = 0;
                endIndex = 0;
            }
        }
    }

    public List<T> getDisplayResult()
    {
        return list.subList(this.startIndex, this.endIndex);
    }

    public int getRecordCount()
    {
        return recordCount;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public int getPageCount()
    {
        return pageCount;
    }


    public int getStartIndex()
    {
        return startIndex;
    }

    public int getEndIndex()
    {
        return endIndex;
    }

    public List<T> getList()
    {
        return list;
    }


    public static void main(String[] args)
    {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        //list.add("15");
        //Object[] strs = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
        PagedList<String> page = new PagedList(list, 1, 50);
        //List<String> list1 = page.getDisplayResult();

        for (String i : page.list)
        {
            System.out.println("值:" + i);
        }
        System.out.println("总条数：" + page.getRecordCount());
        System.out.println("是否最后一页:" + page.isAtLastPage());
        // System.out.println("当前页起始索引:" + page.getStartIndex() + "页");
        // System.out.println("当前页结束索引:" + page.getEndIndex());
        // System.out.println("是否为最后一页:" + page.getIsLastPage());
    }
}
