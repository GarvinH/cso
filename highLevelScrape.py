import time
import json
from selenium import webdriver
from selenium.webdriver.support.select import Select

def highLevelScrape(jsonFile:str):
    jsonFile = json.load(jsonFile)
    chromeOptions = webdriver.ChromeOptions()
    chromeOptions.add_argument("--incognito")
    chromeOptions.add_argument("--headless")

    driver = webdriver.Chrome('./chromedriver', options=chromeOptions)
    driver.get("https://central.carleton.ca/prod/bwysched.p_select_term?wsea_code=EXT")
    select = Select(driver.find_element_by_id("term_code"))
    for key, val in jsonFile.items():
        term = key
        courseList = val
    select.select_by_value(term)
    driver.find_element_by_xpath("//div[@id='ws_div']/input[1]").click()
    allLinks = open("./data/links.txt", "w+")
    for course, courseData in courseList.items():
        print(course)
        level = courseData["level"]
        subject = courseData["subject"]
        number = courseData["number"]
        Select(driver.find_element_by_id("levl_id")).select_by_value(level)
        time.sleep(3)
        Select(driver.find_element_by_id("subj_id")).select_by_value(subject)
        driver.find_element_by_id("number_id").clear()
        driver.find_element_by_id("number_id").send_keys(number)
        driver.find_element_by_css_selector("input[value='Search'][type='submit']").click()
        links = driver.find_elements_by_link_text(subject +" "+number)
        for l in links:
            link = l.get_attribute("href")
            allLinks.write(link+"\n")
        driver.back()
    allLinks.close()
    time.sleep(1)

testJSON = open("./data/testJSON.json")
highLevelScrape(testJSON)