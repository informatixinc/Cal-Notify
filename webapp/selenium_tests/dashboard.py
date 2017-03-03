from selenium import webdriver
import common, time

def doDashboard(driver):

	button = driver.find_element_by_id('editAccountbtn')
	button.click()

	time.sleep(2)