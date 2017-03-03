from selenium import webdriver
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import login, dashboard, signup, addlocations, updateaccount

baseUrl = "http://localhost:3000/"

# def doRun(fname, lname, address1, address2, city, state, zipcode, phone, email, password, confPassword):
# def doRun(email, password, nickname, address, city, state, zipcode):
def doRun(email, password, updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword):
	binary = FirefoxBinary('C:/Program Files (x86)/Mozilla Firefox/firefox.exe')
	driver = webdriver.Firefox(firefox_binary=binary)
	driver.get(baseUrl+"home")
	login.doLogin(email,password,driver)
	# dashboard.doDashboard(driver)

	# driver.get(baseUrl+"signup")
	# signup.signup(fname, lname, address1, address2, city, state, zipcode, phone, email, password, confPassword, driver)

	# driver.get(baseUrl+"notification")
	# addlocations.addLoc(nickname, address, city, state, zipcode, driver)
	# addlocations.addLoc(driver)

	updateaccount.account(updateFname, updateLname, updateAddress1, updateAddress2, updateCity, updateState, updateZipcode, updatePhone, updateEmail, updateOldPassword, updatePassword, updateConfPassword, driver)
	dashboard.doDashboard(driver)
	driver.close()

def main():
	# doRun("fname", "lname", "2485 Natomas Park Dr", "suite #430", "Sacramento", "CA", "95833", "5876948765", "test@gmail.com", "Password1", "Password1")
	# doRun("test@gmail.com", "Password1", "office2", "1133 N La Brea Ave", "West Hollywood", "CA", "90038")
	doRun("test@gmail.com", "Password1", "first", "last", "2485 Natomas Park Dr", "suite #430", "Sacramento", "CA", "95833", "4086948765", "test@gmail.com", "Password1", "Password12", "Password12")
	print ("Test suite has executed successfully!")

if __name__ == "__main__":
    main()