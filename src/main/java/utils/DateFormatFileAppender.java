package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import testcore.scenarios.SupportTest;

public class DateFormatFileAppender extends SupportTest {

	public void setFile(String file) {
		super.setFile(file);
	}

	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		Date d = new Date();

		//To add class name to log4j logger files under ./log dir
		String className = testClassName;

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		fileName = fileName.replaceAll("%timestamp", className + "_" + format.format(d));
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}

}
