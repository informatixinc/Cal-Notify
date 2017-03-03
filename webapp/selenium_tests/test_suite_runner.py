from selenium import webdriver
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import login, dashboard, signup, addlocations, updateaccount

baseUrl = "http://localhost:3000/"

def doRun(email, password, updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword):
	binary = FirefoxBinary()
	driver = webdriver.Firefox(firefox_binary=binary)

	#Signup
	driver.get(baseUrl+"signup")
	signup.signup(fname, lname, address1, address2, city, state, zipcode, phone, email, password, confPassword, driver)

	#Login
	driver.get(baseUrl+"home")
	login.doLogin(email,password,driver)
	# dashboard.doDashboard(driver)

	#Add new address location
	driver.get(baseUrl+"notification")
	addlocations.addLoc(nickname, address, city, state, zipcode, driver)
	addlocations.addLoc(driver)

	#Update account information
	updateaccount.account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver)
	dashboard.doDashboard(driver)
	driver.close()

def main():
	doRun("test@gmail.com", "Password1", "first", "last", "2485 Natomas Park Dr", "suite #430", "Sacramento", "CA", "95833", "4086948765", "test@gmail.com", "Password1", "Password12", "Password12")
	print ("Test suite has executed successfully!")

if __name__ == "__main__":
    main()