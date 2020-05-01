#This code takes in a website and scrapes information about classes and outputs JSON objects
from selenium import webdriver
import json
import random

options = webdriver.ChromeOptions()
options.add_argument('--incognito')
options.add_argument('--headless')
options.binary_location = "/usr/bin/brave-browser"
driver = webdriver.Chrome("./chromedriver", options=options)
# HTML = driver.page_source
meta = {}

def scrape_class_information(url):
    driver.get(url)
    finalRow = len(driver.find_elements_by_tag_name("tr"))-3

    subject = driver.find_element_by_xpath("//table/tbody/tr[4]/td[2]").text
    stype = driver.find_element_by_xpath("//table/tbody/tr[9]/td[2]").text
    status = driver.find_element_by_xpath("//table/tbody/tr[11]/td[2]").text
    days = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[2]").text
    time = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[3]").text

    try: 
        class_data = {
            "subject":f"{subject}",
            "stype":f"{stype}",
            "status":f"{status}",
            "days":f"{days}",
            "time":f"{time}",
        }
    except:
        print("Error occured!")
        
    return class_data

allLinks = open("./links.txt", "r").readlines()
for link in allLinks:
    data = scrape_class_information(link)
    try:
        meta[data["subject"]]=data
    except KeyError:
        meta[random.randint(0,100)]=data
    except:
        print(link);

with open("data_file.json", "w") as write_file:
    json.dump(meta,write_file,indent=2)