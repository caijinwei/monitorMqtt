package com.wecon.restful.authotiry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by zengzhipeng
 */
public class RightResults implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int state;
	private String message;
	private ResponseSystem system;
	private ResponseUser user;
	private List<ResponseRight> rights;

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public ResponseSystem getSystem()
	{
		return system;
	}

	public void setSystem(ResponseSystem system)
	{
		this.system = system;
	}

	public ResponseUser getUser()
	{
		return user;
	}

	public void setUser(ResponseUser user)
	{
		this.user = user;
	}

	public List<ResponseRight> getRights()
	{
		return rights;
	}

	public void setRights(List<ResponseRight> rights)
	{
		this.rights = rights;
	}

	public class ResponseSystem implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private int id;
		private String name;
		private String url;

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}
	}

	public class ResponseUser
	{
		private int id;
		private String account;
		private String trueName;
		private String userType;
		private String email;
		private String department;
		private Date lastLoginTime;

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getAccount()
		{
			return account;
		}

		public void setAccount(String account)
		{
			this.account = account;
		}

		public String getTrueName()
		{
			return trueName;
		}

		public void setTrueName(String trueName)
		{
			this.trueName = trueName;
		}

		public String getUserType()
		{
			return userType;
		}

		public void setUserType(String userType)
		{
			this.userType = userType;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public String getDepartment()
		{
			return department;
		}

		public void setDepartment(String department)
		{
			this.department = department;
		}

		public Date getLastLoginTime()
		{
			return lastLoginTime;
		}

		public void setLastLoginTime(Date lastLoginTime)
		{
			this.lastLoginTime = lastLoginTime;
		}
	}

	public class ResponseRight implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private int id;
		private int pid;
		private String name;
		private String level;
		private String type;
		private int sortIndex;
		private String url;

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public int getPid()
		{
			return pid;
		}

		public void setPid(int pid)
		{
			this.pid = pid;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getLevel()
		{
			return level;
		}

		public void setLevel(String level)
		{
			this.level = level;
		}

		public String getType()
		{
			return type;
		}

		public void setType(String type)
		{
			this.type = type;
		}

		public int getSortIndex()
		{
			return sortIndex;
		}

		public void setSortIndex(int sortIndex)
		{
			this.sortIndex = sortIndex;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}
	}
}
