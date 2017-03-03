from selenium import webdriver
from selenium.webdriver.support.ui import Select
import common, time

def addLoc(nickname, address, city, state, zipcode, driver):
	inputs = []

	all_inputs = driver.find_elements_by_tag_name("input")
	inputs = common.updateInputs(driver)

	inputs[0].send_keys(nickname)
	inputs[1].send_keys(address)
	inputs[2].send_keys(city)
	driver.find_element_by_xpath("//select[@id='state']/option[text()='CA']").click()
	inputs[3].send_keys(zipcode)

	button = driver.find_element_by_id('add_loc')
	button.click()

	time.sleep(2)