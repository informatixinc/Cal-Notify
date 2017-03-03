from selenium import webdriver
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import login, dashboard, signup, addlocations, updateaccount

baseUrl = "http://localhost:3000/"

email = "test@gmail.com"
password = "Password1"
fname = "first"
updateFname = "first updated"
lname = "last"
updateLname = "last updated"
addressOne = "2485 Natomas Park Dr"
addressTwo = "suite #430"
city = "Sacramento"
state = "CA"
zipCode = "95833"
phoneNumber = "4086948765"
nickname = "test location"

def runTests():
	binary = FirefoxBinary()
	driver = webdriver.Firefox(firefox_binary=binary)

	#Signup
	# driver.get(baseUrl+"signup")
	# signup.signup(fname, lname, addressOne, addressTwo, city, state, zipCode, phoneNumber, email, password, driver)

	#Login
	driver.get(baseUrl+"home")
	login.doLogin(email,password,driver)
	
	# dashboard.doDashboard(driver)

	# #Add new address location
	driver.get(baseUrl+"notification")
	addlocations.addLoc(nickname, address, city, state, zipcode, driver)
	addlocations.addLoc(driver)

	# #Update account information
	# updateaccount.account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver)
	# dashboard.doDashboard(driver)
	# driver.close()

def main():
	runTests()
	print ("Test suite has executed successfully!")

if __name__ == "__main__":
    main()