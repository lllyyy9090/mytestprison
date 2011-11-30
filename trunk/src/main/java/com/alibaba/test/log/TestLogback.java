package com.alibaba.test.log;

import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
import ch.qos.logback.classic.LoggerContext;  
import ch.qos.logback.classic.joran.JoranConfigurator;  
import ch.qos.logback.core.joran.spi.JoranException;  
  
/** 
 * @author Last changed by: $Author$ 
 * @version $Revision$ $Date$ 
 */  
public class TestLogback {  
    static Logger logbacklogger = LoggerFactory.getLogger(TestLogback.class);  
  
    // How many times should we try to log:  
    static int loop = 100000;  
  
    public static void main(String[] args) throws InterruptedException {  
        // initConfig();  
  
        // Let's run once for Just In Time compiler  
//        logbackDirectDebugCall();  
//        logbackDirectWarnCall();  
//        logbackTestedDebugCall();  
//        logbackTestedWarnCall();  
//        logbackParametrizedDebugCall();  
//        logbackParametrizedWarnCall();  
  
        // let's run the tests and display the results:  
        long result4 = logbackDirectDebugCall();  
        System.out.println(result4/1000f);
//        long result5 = logbackDirectWarnCall();  
//        long result6 = logbackTestedDebugCall();  
//        long result7 = logbackTestedWarnCall();  
//        long result8 = logbackParametrizedDebugCall();  
//        long result9 = logbackParametrizedWarnCall();  
//  
//        System.out.println("################## loop " + loop + " #############################");  
//        System.out.println("Logback direct debug call: " + result4);  
//        System.out.println("Logback direct warn call: " + result5);  
//        System.out.println("Logback tested (isDebugEnabled) debug call: " + result6);  
//        System.out.println("Logback tested (isWarnEnabled) warn call: " + result7);  
//        System.out.println("Logback parametrized debug call: " + result8);  
//        System.out.println("Logback parametrized warn call: " + result9);  
//        System.out.println("###############################################");  
    }  
  
    private static long logbackDirectDebugCall() {  
        Integer j = new Integer(5);  
        logbacklogger.debug("test");  
        long start = System.currentTimeMillis();  
        for (int i = 0; i < loop; i++) {  
            logbacklogger.debug("SEE IF THIS IS LOGGED " + j + ".");  
        }  
        return (System.currentTimeMillis() - start) ;  
    }  
  
    private static long logbackDirectWarnCall() {  
        Integer j = new Integer(6);  
        long start = System.nanoTime();  
        for (int i = 0; i < loop; i++) {  
            logbacklogger.warn("SEE IF THIS IS LOGGED logbackDirectWarnCall()" + j + ".");  
        }  
        return (System.nanoTime() - start) / loop;  
    }  
  
    private static long logbackTestedDebugCall() {  
        Integer j = new Integer(7);  
        long start = System.nanoTime();  
        for (int i = 0; i < loop; i++) {  
            if (logbacklogger.isDebugEnabled())  
                logbacklogger.debug("SEE IF THIS IS LOGGED " + j + ".");  
        }  
        return (System.nanoTime() - start) / loop;  
    }  
  
    private static long logbackTestedWarnCall() {  
        Integer j = new Integer(8);  
        long start = System.nanoTime();  
        for (int i = 0; i < loop; i++) {  
            if (logbacklogger.isWarnEnabled())  
                logbacklogger.warn("SEE IF THIS IS LOGGED logbackTestedWarnCall()" + j + ".");  
        }  
        long end = System.nanoTime();  
        long result = (end - start) / loop;  
        System.out.println("start = " + start + "end = " + end + "result = " + result);  
        return result;  
    }  
  
    private static long logbackParametrizedDebugCall() {  
        Integer j = new Integer(9);  
        long start = System.nanoTime();  
        for (int i = 0; i < loop; i++) {  
            logbacklogger.debug("SEE IF THIS IS LOGGED {}.", j);  
        }  
        return (System.nanoTime() - start) / loop;  
    }  
  
    private static long logbackParametrizedWarnCall() {  
        Integer j = new Integer(10);  
        long start = System.nanoTime();  
        for (int i = 0; i < loop; i++) {  
            logbacklogger.warn("SEE IF THIS IS LOGGED logbackParametrizedWarnCall() {}.", j);  
        }  
        long end = System.nanoTime();  
        long result = (end - start) / loop;  
        System.out.println("start = " + start + "end = " + end + "result = " + result);  
        return result;  
    }  
  
    private static void initConfig() {  
        // assume SLF4J is bound to logback in the current environment  
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();  
  
        try {  
            JoranConfigurator configurator = new JoranConfigurator();  
            configurator.setContext(lc);  
            configurator.doConfigure("src/main/resources/logback.xml");  
        } catch (JoranException je) {  
            // StatusPrinter will handle this  
        }  
  
        // create the loggers  
        LoggerFactory.getLogger("perfTest");  
        LoggerFactory.getLogger("perfTest.ch");  
        LoggerFactory.getLogger("perfTest.ch.qos");  
        LoggerFactory.getLogger("perfTest.ch.qos.logback");  
    }  
} 