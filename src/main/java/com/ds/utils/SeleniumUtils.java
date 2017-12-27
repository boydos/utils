package com.ds.utils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.ds.utils.Log;

public class SeleniumUtils {
	
	public static Set<Cookie> getCookiesFireFox(String url) {
		System.setProperty("webdriver.firefox.bin","/usr/bin/firefox");
		System.setProperty("webdriver.firefox.driver","/usr/bin/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get(url);
        Set<Cookie> coo = driver.manage().getCookies();
        driver.quit();
        return coo;
	}
	public static Set<Cookie> getCookiesPhantomjs(String url) {
		System.setProperty("phantomjs.binary.path","/opt/phantomjs/bin/phantomjs");
		DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"/opt/phantomjs/bin/phantomjs");

        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Set<Cookie> coo = driver.manage().getCookies();
        try{
        Log.d("driver test:"+driver.findElementsByTagName("body").get(0).getText());
        }catch(Exception e) {
        	Log.e("driver no element");
        }
        driver.close();
        driver.quit();
        return coo;
	}
	
	public static void main(String[] args) {
		System.out.println(getCookiesFireFox("http://www.baidu.com"));
		//System.out.println(getCookiesPhantomjs("http://www.baidu.com"));
	}
	
}
