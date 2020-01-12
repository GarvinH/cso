#This code takes in a website and scrapes information about classes and outputs JSON objects
from bs4 import BeautifulSoup
from selenium import webdriver
import json
import time
from collections import OrderedDict

options = webdriver.ChromeOptions()
options.add_argument('--incognito')
options.add_argument('--headless')
driver = webdriver.Chrome("./chromedriver", chrome_options=options)

URL = "https://central.carleton.ca/prod/bwysched.p_display_course?wsea_code=EXT&term_code=202010&disp=11370009&crn=11047"
url2 = "https://central.carleton.ca/prod/bwysched.p_display_course?wsea_code=EXT&term_code=202010&disp=11371548&crn=11012"
driver.get(URL)
# driver.get(url2)
HTML = driver.page_source
finalRow = len(driver.find_elements_by_tag_name("tr"))-3

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


print(finalRow,"*")
print("registration_term",registration_term)
print("crn",crn)
print("subject",subject)
print("long_title",long_title)
print("title",title)
print("stype",stype)
print("status",status)
print("meeting_date",meeting_date)
print("days",days)
print("time",time)
print("building",building)
print("room",room)
print("instructor",instructor)



# soup = BeautifulSoup(HTML, 'html.parser')
# soup.f
# f = open("html.txt", "w+")
# f.write(soup.prettify())
# f.close()


# a = soup.find("table")





























# dict_from_json = json.loads(soup.find("table").text)

# table_data = [[cell.text for cell in row("td")] for row in soup("tr")]
# for i in range(len(table_data)):
#     if len(table_data[i]) == 1 or len(table_data[i]) is None:
#         table_data.remove()
# print(table_data)
# print(json.dumps(dict(table_data)))

# print(json.dumps(OrderedDict(table_data)))
# print(soup.prettify())

