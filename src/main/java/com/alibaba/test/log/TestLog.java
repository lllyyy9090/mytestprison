package com.alibaba.test.log;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Last changed by: $Author$
 * @version $Revision$ $Date$
 */
public class TestLog {
	static Logger log4jlogger = Logger.getLogger(TestLog.class);

	// How many times should we try to log:
	static int loop = 100000;

	public static void main(String[] args) throws InterruptedException {
		initConfig();

		// Let's run once for Just In Time compiler
		log4jDirectDebugCall();
		log4jTestedDebugCall();

		// let's run the tests and display the results:

		long result1 = log4jDirectDebugCall();
		long result2 = log4jTestedDebugCall();

		System.out.println("################### loop " + loop
				+ " ############################");
		System.out.println("Log4j direct debug call: " + result1 );
		System.out.println("Log4j log4jTestedDebugCall call: " + result2 );
		System.out
				.println("#############################################################");
	}

	private static long log4jDirectDebugCall() {
		Integer j = new Integer(2);
		long start = System.nanoTime();
		for (int i = 0; i < loop; i++) {
			log4jlogger.debug("SEE IF THIS IS LOGGED " + j + ".");
		}
		return (System.nanoTime() - start) / loop;
	}

	private static long log4jTestedDebugCall() {
		Integer j = new Integer(2);
		long start = System.nanoTime();
		for (int i = 0; i < loop; i++) {
			if (log4jlogger.isDebugEnabled()) {
				log4jlogger.debug("SEE IF THIS IS LOGGED " + j + ".");
			}
		}
		return (System.nanoTime() - start) / loop;
	}

	private static void initConfig() {
		DOMConfigurator.configure("src/main/resources/log4j.xml");

		// create the loggers
		org.apache.log4j.Logger.getLogger("perfTest");
		org.apache.log4j.Logger.getLogger("perfTest.ch");
		org.apache.log4j.Logger.getLogger("perfTest.ch.qos");
		org.apache.log4j.Logger.getLogger("perfTest.ch.qos.logback");
	}
}