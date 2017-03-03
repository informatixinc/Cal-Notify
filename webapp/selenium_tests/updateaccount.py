from selenium import webdriver
from selenium.webdriver.support.ui import Select
import common, time

def account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver):

	inputs = []

	all_inputs = driver.find_elements_by_tag_name("input")
	inputs = common.updateInputs(driver)
	updateFnameDiv = driver.find_element_by_id('fname')
	updateLnameDiv = driver.find_element_by_id('lname')
	updateAddress1Div = driver.find_element_by_id('address1')
	updateAddress2Div = driver.find_element_by_id('address2')
	updateCityDiv = driver.find_element_by_id('city')
	updateZipcodeDiv = driver.find_element_by_id('zipcode')
	updatePhoneDiv = driver.find_element_by_id('phone')
	updateEmailDiv = driver.find_element_by_id('email')
	updateOldPasswordDiv = driver.find_element_by_id('oldPassword')
	updatePasswordDiv = driver.find_element_by_id('password')
	updateConfPasswordDiv = driver.find_element_by_id('confPassword')
	

	updateFnameDiv.clear()
	updateFnameDiv.send_keys(updateFname)
	updateLnameDiv.clear()
	updateLnameDiv.send_keys(updateLname)
	updateAddress1Div.clear()
	updateAddress1Div.send_keys(updateAddress1)
	updateAddress2Div.clear()
	updateAddress2Div.send_keys(updateAddress2)
	updateCityDiv.clear()
	updateCityDiv.send_keys(updateCity)
	updateZipcodeDiv.clear()
	updateZipcodeDiv.send_keys(updateZipcode)
	updatePhoneDiv.clear()
	updatePhoneDiv.send_keys(updatePhone)
	updateEmailDiv.clear()
	updateEmailDiv.send_keys(updateEmail)
	updateOldPasswordDiv.clear()
	updateOldPasswordDiv.send_keys(updateOldPassword)
	updatePasswordDiv.clear()
	updatePasswordDiv.send_keys(updatePassword)
	updateConfPasswordDiv.clear()
	updateConfPasswordDiv.send_keys(updateConfPassword)

	driver.find_element_by_xpath("//select[@id='state']/option[text()='CA']").click()

	button = driver.find_element_by_id('submit')
	button.click()

	time.sleep(2)