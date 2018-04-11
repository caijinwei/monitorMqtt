package com.wecon.restful.doc;

import java.util.LinkedList;
import java.util.List;

public class ApiDoc
{
	public boolean deprecated = false;
	public String label;
	public String name;
	public boolean auth;
	public List<Param> params = new LinkedList<>();
	public String jsonData;
	public String remark;
}
