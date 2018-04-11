package com.wecon.restful.doc;

import java.util.LinkedList;
import java.util.List;

public class Param
{
	public String name;
	public String label;
	public boolean must = false;
	public List<String> format = new LinkedList<>();
}
