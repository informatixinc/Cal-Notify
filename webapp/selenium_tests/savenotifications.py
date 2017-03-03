from selenium import webdriver
from selenium.webdriver.support.ui import Select
import common, time

def savenotifications(driver):

	button = driver.find_element_by_id('submit')
	button.click()

	time.sleep(2)