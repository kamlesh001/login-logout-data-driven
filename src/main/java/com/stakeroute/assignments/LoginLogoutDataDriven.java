package com.stakeroute.assignments;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginLogoutDataDriven {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		// TODO Auto-generated method stub
		LoginLogoutDataDriven lldd = new LoginLogoutDataDriven();
		String [][] exceldata= lldd.readExcelData();
		lldd.LoginLogoutTest(exceldata);
	}
		
	public String [][] readExcelData() throws IOException
	{
		String currdir = System.getProperty("user.dir");
		System.out.println(currdir);
		FileInputStream fis = new FileInputStream(currdir+"/src/main/resources/Data/test_data.xlsx");
		System.out.println(currdir+"/src/main/resources/Data/user_data.xls");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("sheet1");
		int rowcount = sheet.getLastRowNum()+1;
		System.out.println(rowcount);
		int colcount = sheet.getRow(0).getLastCellNum();
		String loginData [][] = new String[rowcount][colcount];
		for(int i=0;i<rowcount;i++)
		{
		   for(int j=0;j<colcount;j++)
			{
			  loginData[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();
			  System.out.print(sheet.getRow(i).getCell(j).getStringCellValue()+"   ");
			}
		   System.out.print("\n");
		}
		return loginData;
		
	}
	
	public void LoginLogoutTest(String [][] data) throws InterruptedException
	{
		WebDriverManager.chromedriver().setup();
		int rowlen = data.length;
		int collen=data[0].length;
		for(int i=1;i<rowlen;i++)
		{
			for(int j=0;j<collen-1;j++)
			{
				WebDriver driver = new ChromeDriver();
				driver.get("https://opensource-demo.orangehrmlive.com/index.php/admin/viewSystemUsers");
				driver.manage().window().maximize();
				driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys(data[i][j]);
				driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(data[i][j+1]);
				driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
				driver.findElement(By.xpath("//a[@id='welcome']")).click();
				Thread.sleep(4000);
				driver.findElement(By.xpath("//a[text()='Logout']")).click();
				driver.close();
			}
		}
		
	}
	

}
