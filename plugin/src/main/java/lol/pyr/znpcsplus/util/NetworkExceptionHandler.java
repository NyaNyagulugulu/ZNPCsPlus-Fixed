package lol.pyr.znpcsplus.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * 网络异常处理工具类，用于处理与网络连接相关的异常
 * 这个类可以帮助插件更好地处理网络连接异常，避免在服务器网络层出现问题时影响插件运行
 */
public class NetworkExceptionHandler {
    private static final Logger logger = Logger.getLogger(NetworkExceptionHandler.class.getName());
    
    /**
     * 安全地执行可能引发网络异常的操作
     * @param plugin 插件实例
     * @param operation 可能引发异常的操作
     */
    public static void safeNetworkOperation(Plugin plugin, Runnable operation) {
        try {
            operation.run();
        } catch (Exception e) {
            // 检查是否是网络连接相关的异常
            if (isNetworkRelatedException(e)) {
                logger.warning("网络连接异常已捕获并处理: " + e.getMessage());
                // 不重新抛出异常，避免影响插件其他功能
            } else {
                // 非网络相关异常，按原方式处理
                throw e;
            }
        }
    }
    
    /**
     * 检查异常是否与网络连接相关
     * @param e 异常对象
     * @return 是否为网络相关异常
     */
    private static boolean isNetworkRelatedException(Throwable e) {
        if (e == null) return false;
        
        // 检查异常消息是否包含网络连接相关内容
        String message = e.getMessage();
        if (message != null) {
            message = message.toLowerCase();
            if (message.contains("channel") && message.contains("null")) {
                return true;
            }
            if (message.contains("connection") && (message.contains("null") || message.contains("closed"))) {
                return true;
            }
        }
        
        // 检查异常类型
        String className = e.getClass().getName().toLowerCase();
        if (className.contains("network") || className.contains("channel") || className.contains("connection")) {
            return true;
        }
        
        // 检查堆栈跟踪
        for (StackTraceElement element : e.getStackTrace()) {
            String methodName = element.getClassName().toLowerCase();
            if (methodName.contains("networkmanager") || methodName.contains("channel") || 
                methodName.contains("connection") || methodName.contains("netty")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 注册一个监听器来处理网络连接事件
     * @param plugin 插件实例
     */
    public static void registerNetworkListeners(Plugin plugin) {
        // 目前主要用于记录信息，因为 Bukkit API 限制了对底层网络连接的直接访问
        try {
            Bukkit.getConsoleSender().sendMessage("ZNPCsPlus: 网络异常处理机制已启动");
        } catch (Exception e) {
            // 静默处理可能的异常，确保即使Bukkit API不可用也不会导致问题
            logger.fine("无法发送网络异常处理启动消息到控制台");
        }
    }
}