package com.wecon.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class LoggerUtil {

    private final static Logger defaultLogger = Logger.getLogger(LoggerUtil.class);
    /**
     * Logger缓存池
     * Map<loggerName,Map<yyyyhhdd,LoggerObject>>
     */
    private final static Map<String, Map<String, Logger>> loggerObjectPool = new HashMap<>();

    private static synchronized Logger getLogger(final String loggerName) {
        final String fileFormat = new java.text.SimpleDateFormat("yyyyMMddHH").format(new Date());
        return getLogger(loggerName, fileFormat);
    }

    private static synchronized Logger getLogger(final String loggerName, final String fileFormat) {
        Map<String, Logger> loggerFormatMap = loggerObjectPool.get(loggerName);
        Logger logger;
        if (loggerFormatMap == null) {
            logger = loggerBuilder(loggerName, fileFormat);
            if (logger != null) {
                loggerFormatMap = new HashMap<>();
                loggerFormatMap.put(fileFormat, logger);
                loggerObjectPool.put(loggerName, loggerFormatMap);
                return logger;
            }
        }

        logger = loggerFormatMap.get(fileFormat);
        if (logger == null) {
            logger = loggerBuilder(loggerName, fileFormat);
            if (logger != null) {
                //把历史缓存对象全部清空
                loggerFormatMap.clear();
                loggerFormatMap.put(fileFormat, logger);
                loggerObjectPool.put(loggerName, loggerFormatMap);
            }
        }

        return logger;
    }

    private static Logger loggerBuilder(final String loggerName, final String logFileFormat) {

        final PatternLayout layout = new PatternLayout("%c %d{yyy-MM-dd HH:mm:ss} %n Message: %m %n%n");
        final RollingFileAppender rollingFileAppender = new RollingFileAppender();

        rollingFileAppender.setFile(getLogPath(loggerName, logFileFormat));
        rollingFileAppender.setLayout(layout);
        rollingFileAppender.setMaxFileSize("5MB");
        rollingFileAppender.setEncoding("UTF-8");
        rollingFileAppender.setMaxBackupIndex(30);
        rollingFileAppender.setAppend(true);
        rollingFileAppender.rollOver();
        final Logger logger = Logger.getLogger(loggerName);

        if (logger != null) {
            logger.removeAllAppenders();
            logger.setAdditivity(false);
            rollingFileAppender.activateOptions();
            logger.addAppender(rollingFileAppender);

            return logger;
        }
        return null;
    }


    //日志目录
    private static String logDir;
    //
    private final static String infoLogDir = "info";
    //
    private final static String exceptionLogDir = "exception";
    //
    private final static String errorLogDir = "error";
    /**
     *
     */
    private final static String debugLogDir = "debug";
    /**
     *
     */
    private final static String fatalLogDir = "fatal";

    /**
     * 设置日志目录
     * 可依赖注入或在程序入口赋值
     *
     * @param logDir
     */
    public void setLogDir(String logDir) {
        if (LoggerUtil.logDir != null) {
            return;
        }

        if (logDir == null || logDir.equals("")) {
            logDir = LoggerUtil.class.getResource("/").getPath();
        }

        LoggerUtil.logDir = logDir;

        final Properties properties = new Properties();
        properties.put("logDir", logDir);
        PropertyConfigurator.configure(properties);
    }

    /**
     * 日志目录是否为空
     *
     * @return
     */
    public static boolean logDirIsEmpty() {
        return LoggerUtil.logDir != null && !LoggerUtil.logDir.equals("");
    }

    /**
     * 将消息写入自定义文件夹中
     *
     * @param msg
     * @param fileName
     */
    public static void writeCustom(final String msg, final String fileName) {
        final Logger logger = getLogger(fileName);

        if (logger != null) {
            logger.info(msg);
        } else {
            defaultLogger.info(msg);
        }
    }

    /**
     * 写入消息
     *
     * @param msg
     */
    public static void writeInfo(final String msg) {
        final Logger logger = getLogger(infoLogDir);
        if (logger != null) {
            logger.info(msg);
        } else {
            defaultLogger.info(msg);
        }
    }

    /**
     * 写入异常
     *
     * @param msg
     * @param e
     */
    public static void writeException(final String msg, final Exception e) {
        final Logger logger = getLogger(exceptionLogDir);
        if (logger != null) {
            logger.info(msg, e);
        } else {
            defaultLogger.info(msg, e);
        }
    }

    /**
     * 写入debug信息
     *
     * @param msg
     */
    public static void writeDebug(final String msg) {
        final Logger logger = getLogger(debugLogDir);
        if (logger != null) {
            logger.info(msg);
        } else {
            defaultLogger.info(msg);
        }
    }

    /**
     * 写入fatal
     *
     * @param msg
     */
    public static void writeFatal(final String msg) {
        final Logger logger = getLogger(fatalLogDir);
        if (logger != null) {
            logger.info(msg);
        } else {
            defaultLogger.info(msg);
        }
    }

    /**
     * 写入Error
     *
     * @param msg
     */
    public static void writeError(final String msg) {
        final Logger logger = getLogger(errorLogDir);
        if (logger != null) {
            logger.info(msg);
        } else {
            defaultLogger.info(msg);
        }
    }

    private static String getLogPath(final String loggerName, final String fileFormat) {

        String logDir = LoggerUtil.logDir;
        if (logDir.equals("")) {
            final Class callClass = getCallClass();
            if (callClass != null) {
                logDir = callClass.getResource("/").getPath();
            }
        }
        final String logPath = String.format("%s/%s/log%s.log", logDir, loggerName, fileFormat);
        return logPath;
    }

    /**
     * 获取调用类对象
     *
     * @return
     */
    private static Class getCallClass() {
        final StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        final String callName = stack[2].getClassName();
        if (!callName.endsWith("SelectDefBindingContainer")) {
            try {
                final Class c = Class.forName(callName);
                if (c != null) {
                    return c;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return LoggerUtil.class;
    }

}
