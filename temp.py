from selenium import webdriver
import json
import random

options = webdriver.ChromeOptions()
options.add_argument('--incognito')
options.add_argument('--headless')
driver = webdriver.Chrome("./chromedriver", chrome_options=options)
meta = {}

def get_links():
    # url = "https://central.carleton.ca/prod/bwysched.p_display_course?wsea_code=EXT&term_code=202010&disp=11370009&crn=11047"
    # url2 = "https://central.carleton.ca/prod/bwysched.p_display_course?wsea_code=EXT&term_code=202010&disp=11371548&crn=11012"
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
        registration_term = driver.find_element_by_xpath("//table/tbody/tr[2]/td[2]").text
        crn = driver.find_element_by_xpath("//table/tbody/tr[3]/td[2]").text
        subject = driver.find_element_by_xpath("//table/tbody/tr[4]/td[2]").text
        long_title = driver.find_element_by_xpath("//table/tbody/tr[5]/td[2]").text
        title = driver.find_element_by_xpath("//table/tbody/tr[6]/td[2]").text
        stype = driver.find_element_by_xpath("//table/tbody/tr[9]/td[2]").text
        status = driver.find_element_by_xpath("//table/tbody/tr[11]/td[2]").text
        meeting_date = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[1]").text
        days = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[2]").text
        time = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[3]").text
        building = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[4]").text
        room = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[5]").text
        instructor = driver.find_element_by_xpath(f"//tr[{finalRow}]/td/table/tbody/tr[2]/td[7]").text
   

        class_data = {
            "registration_term":f"{registration_term}",
            "crn":f"{crn}",
            "subject":f"{subject}",
            "long_title":f"{long_title}",
            "title":f"{title}",
            "stype":f"{stype}",
            "status":f"{status}",
            "meeting_date":f"{meeting_date}",
            "days":f"{days}",
            "time":f"{time}",
            "building":f"{building}",
            "room":f"{room}",
            "instructor":f"{instructor}"
        }
        return class_data

    except Exception as Error: 
        problem_information = {'url link':str(url),'error': str(Error)}
        failed_classes.append(problem_information)
        
        with open("./debugger/failed classes.txt","a+") as f:
            f.write(str(failed_classes[-1]))
        return None


arr = get_links()
for url in arr:
    print("***",url)
    data = scrape_class_information(url)

    try:
        if data:
            meta[data["subject"]]=data
    except KeyError:
        meta[random.randint(0,100)]=data


with open("./data/data_file.json", "w+") as write_file:
    json.dump(meta,write_file,indent=2)


