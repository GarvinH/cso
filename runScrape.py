from selenium import webdriver
import json
import random
import highLevelScrape
from schedule import Schedule

options = webdriver.ChromeOptions()
options.add_argument('--incognito')
options.add_argument('--headless')
driver = webdriver.Chrome("./chromedriver", chrome_options=options)
meta1 = {}
meta2 = {}
meta3 = {}

def get_links():
    link_array = []
    with open('./data/links.txt', 'r') as f:
        for line in f:
            line = line.strip("\n")
            link_array.append(line)
    return link_array


def scrape_class_information(url):
    driver.get(url)
    finalRow = len(driver.find_elements_by_tag_name("tr"))-3
    failed_classes = []

    try:
        try:
            registration_term = driver.find_element_by_xpath("//table/tbody/tr[2]/td[2]").text
        except:
            registration_term = None
        try:
            crn = driver.find_element_by_xpath("//table/tbody/tr[3]/td[2]").text
        except:
            crn = None
        try:
            subject = driver.find_element_by_xpath("//table/tbody/tr[4]/td[2]").text
        except:
            subject = None
        try:
            long_title = driver.find_element_by_xpath("//table/tbody/tr[5]/td[2]").text
        except:
            long_title = None
        try:
            title = driver.find_element_by_xpath("//table/tbody/tr[6]/td[2]").text
        except:
            title = None
        try:
            stype = driver.find_element_by_xpath("//table/tbody/tr[9]/td[2]").text
        except:
            stype = None
        try:
            status = driver.find_element_by_xpath("//table/tbody/tr[11]/td[2]").text
        except:
            status = None
        try:
            meeting_date = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[1]").text
        except:
            meeting_date = None
        try:
            days = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[2]").text
        except:
            days = None
        try:
            time = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[3]").text
        except:
            time = None
        try:
            building = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[4]").text
        except:
            building = None
        try:
            room = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[5]").text
        except:
            room = None
        try:
            instructor = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[7]").text
        except:
            instructor = None
        
        class_data= Schedule(
            registration_term=f"{registration_term}",
            crn=f"{crn}",
            subject=f"{subject}",
            long_title=f"{long_title}",
            title=f"{title}",
            stype=f"{stype}",
            status=f"{status}",
            meeting_date=f"{meeting_date}",
            days=f"{days}",
            time=f"{time}",
            building=f"{building}",
            room=f"{room}",
            instructor=f"{instructor}")
        return class_data



    except Exception as Error: 
        problem_information = {'url link':str(url),'error': str(Error)}
        failed_classes.append(problem_information)
        
        with open("./debugger/failed classes.txt","a+") as f:
            f.write(str(failed_classes[-1]))
        return None


arr = get_links()
for url in arr:
    data = scrape_class_information(url)
    try:
        if data:
            meta1[data.subject] = data.get_master_format
            meta2[data.subject] = data.get_scheduler_format
            meta3[data.subject] = data.get_webpage_format
    except KeyError:
        meta[random.randint(0,100)]=data.get_master_format


with open("./data/master_data_file.json", "w+") as write_file:
    json.dump(meta1,write_file,indent=2)
with open("./data/website_data_file.json", "w+") as write_file:
    json.dump(meta2,write_file,indent=2)
with open("./data/scheduler_data_file.json", "w+") as write_file:
    json.dump(meta3,write_file,indent=2)


