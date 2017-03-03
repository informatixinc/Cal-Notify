from selenium import webdriver
from selenium.webdriver.support.ui import Select
import common, time

def account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, state, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver):

	inputs = []

	all_inputs = driver.find_elements_by_tag_name("input")
	inputs = common.updateInputs(driver)
	updateFname = driver.find_element_by_id('fname')
	updateLname = driver.find_element_by_id('lname')
	updateAddress1 = driver.find_element_by_id('address1')
	updateAddress2 = driver.find_element_by_id('address2')
	updateCity = driver.find_element_by_id('city')
	updateZipcode = driver.find_element_by_id('zipcode')
	updatePhone = driver.find_element_by_id('phone')
	updateEmail = driver.find_element_by_id('email')
	updateOldPassword = driver.find_element_by_id('oldPassword')
	updatePassword = driver.find_element_by_id('password')
	updateConfPassword = driver.find_element_by_id('confPassword')
	

	updateFname.send_keys(fname)
	updateLname.send_keys(lname)
	updateAddress1.send_keys(address1)
	updateAddress2.send_keys(address2)
	updateCity.send_keys(city)
	driver.find_element_by_xpath("//select[@id='state']/option[text()='CA']").click()
	updateZipcode.send_keys(zipcode)
	updatePhone.send_keys(phone)
	updateEmail.send_keys(email)
	updateOldPassword.send_keys(oldPassword)
	updatePassword.send_keys(password)
	updateConfPassword.send_keys(confPassword)

	button = driver.find_element_by_id('submit')
	button.click()

	time.sleep(2)