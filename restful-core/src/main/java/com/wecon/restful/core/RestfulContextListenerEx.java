package com.wecon.restful.core;

import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.doc.DocManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zengzhipeng on 2017/12/4.
 */
public class RestfulContextListenerEx extends ContextLoaderListener {
    private static final Logger logger = LogManager.getLogger(RestfulContextListenerEx.class);

    /**
     * 打包环境
     */
    public static String ENV = "server";

    public RestfulContextListenerEx()
    {
        super();
    }

    public RestfulContextListenerEx(WebApplicationContext context)
    {
        super(context);
    }

    public static boolean isServer()
    {
        return "product".equals(ENV) || "server".equals(ENV);
    }

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        // 初始化spring mvc context
        super.contextInitialized(event);

        ENV = event.getServletContext().getInitParameter("spring.profiles.active");
        logger.info("spring.profiles.action = " + ENV);

        // 初始化restful context
        try
        {
            this.init();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        // 动态注册heartbeat servlet
		/*WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext sc = webApplicationContext.getServletContext();
		ServletRegistration sr = sc.addServlet("heartbeat", HeartBeat.class);
		sr.addMapping("/heartbeat");
		logger.info("dynamic regist heartbeat servlet " + HeartBeat.class.getName() + ", mapping /heartbeat");*/
    }

    private void init() throws Exception
    {
        logger.info("ex restful context init ...");

        List<String> packs = new LinkedList<>();
        InputStream input = AppContext.class.getResourceAsStream("/spring.xml");
        byte[] buf = new byte[input.available()];
        input.read(buf);
        String spring = new String(buf, "utf-8");
        Pattern pattern = Pattern.compile("base-package=\"(.*?)\"");
        Matcher matcher = pattern.matcher(spring);
        while (matcher.find())
        {
            packs.add(matcher.group(1).trim());
        }
        input.close();
        String[] scanPackages = new String[packs.size()];
        packs.toArray(scanPackages);
        logger.info("scan packages:" + Arrays.toString(scanPackages));

        ClassCollector collector = new ClassCollector(scanPackages);
        Map<String, List<Class<?>>> clazz = collector.collect();
        List<Class<?>> classes = clazz.get("controller");
        for (Class<?> it : classes)
        {
            String baseUrl = "";
            RequestMapping baseMap = it.getAnnotation(RequestMapping.class);
            if (baseMap != null)
            {
                baseUrl = baseMap.value()[0];
                if (!baseUrl.startsWith("/"))
                {
                    baseUrl = "/" + baseUrl;
                }
            }

            for (Method m : it.getDeclaredMethods())
            {
                RequestMapping map = m.getAnnotation(RequestMapping.class);
                if (map != null)
                {
                    String tmp = map.value()[0];
                    if (!tmp.startsWith("/"))
                    {
                        tmp = "/" + tmp;
                    }
                    //提供给外部接口必须以“/we-data/”开头
                    if (!tmp.startsWith("/we-data/"))
                    {
                        continue;
                    }

                    WebApiInstance instance = new WebApiInstance();
                    instance.id = baseUrl + tmp;
                    instance.controller = it;
                    instance.method = m;
                    instance.forceAuth = false;
                    instance.master = true;
                    instance.authority = new String[0];

                    WebApi webapi = m.getAnnotation(WebApi.class);
                    if (webapi != null)
                    {
                        instance.forceAuth = webapi.forceAuth();
                        instance.master = webapi.master();
                        instance.authority = webapi.authority();
                        instance.skipSign = webapi.skipSign();
                    }

                    AppContext.webapiMap.put(instance.id, instance);

                    // 生成文档
					/*try
					{
						DocManager.genApiDoc(instance);
					}
					catch (Exception e)
					{
						logger.warn("解析接口" + instance.id + "文档错误: " + e.getMessage());
					}*/

                    logger.info("load api " + instance.toString());
                }
            }
        }

        logger.info("restful context init success.");
    }
}
