from selenium import webdriver
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import login, dashboard, signup, addlocations, updateaccount, savenotifications

baseUrl = "http://localhost:3000/"

email = "test123@gmail.com"
updateEmail = "test123@gmail.com"
password = "Password1"
updateOldPassword = "Password1"
updatePassword = "Password1"
updateConfPassword = "Password1"
fname = "first"
updateFname = "first updated"
lname = "last"
updateLname = "last updated"
addressOne = "2485 Natomas Park Dr"
updateAddress1 = "1133 N La Brea Ave"
addressTwo = "suite #430"
updateAddress2 = ""
city = "Sacramento"
updateCity = "West Hollywood"
state = "CA"
updateState = "CA"
zipCode = "95833"
updateZipcode = "90038"
phoneNumber = "4086948765"
updatePhone = "5867296787"
nickname = "test location"

def runTests():
	binary = FirefoxBinary()
	driver = webdriver.Firefox(firefox_binary=binary)

	# Signup
	driver.get(baseUrl+"signup")
	signup.signup(fname, lname, addressOne, addressTwo, city, state, zipCode, phoneNumber, email, password, driver)

	#Login
	driver.get(baseUrl+"home")
	login.doLogin(email,password,driver)
	
	# dashboard.doDashboard(driver)

	# #Add new address location
	driver.get(baseUrl+"notification")
	addlocations.addLoc(nickname, addressOne, city, state, zipCode, driver)
	savenotifications.savenotifications(driver)
	dashboard.doDashboard(driver)

	# #Update account information
	driver.get(baseUrl+"account")
	updateaccount.account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver)
	dashboard.doDashboard(driver)

	driver.find_element_by_id('logout').click()
	driver.close()

def main():
	runTests()
	print ("Test suite has executed successfully!")

if __name__ == "__main__":
    main()