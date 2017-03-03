from selenium import webdriver
import common, time

def doLogin(email, password, driver):

	all_inputs = driver.find_elements_by_tag_name("input")
	inputs = common.updateInputs(driver)

	# heading = driver.find_elements_by_tag_name('h1')
	# assert "Welcome" in heading.body

	inputs[0].send_keys(email)
	inputs[1].send_keys(password)
	button = driver.find_element_by_id('loginbtn')
	button.click()

	time.sleep(2)