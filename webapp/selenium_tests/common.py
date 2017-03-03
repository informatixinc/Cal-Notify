from selenium.webdriver.support.ui import Select

def updateInputs(driver):
	return generic(driver, "input")

# def updateInputs(driver):
# 	return generic(driver, "h1")

def updateLinks(driver):
	return generic(driver, "a")

def generic(driver, elementType):
	all_elements = driver.find_elements_by_tag_name(elementType)
	elements = []

	for element in all_elements:
		if(element.get_attribute("type") != "hidden"):
			elements.append(element)

	return elements