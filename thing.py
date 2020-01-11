#Description: This code scrapes information about 
from bs4 import BeautifulSoup
from selenium import webdriver
import json
import time

options = webdriver.ChromeOptions()
options.add_argument('--incognito')
options.add_argument('--headless')
driver = webdriver.Chrome("/Users/student/Desktop/hackathon/chromedriver", chrome_options=options)

URL = "https://central.carleton.ca/prod/bwysched.p_display_course?wsea_code=EXT&term_code=202010&disp=11370009&crn=11047"
driver.get(URL)
time.sleep(10)


soup = BeautifulSoup(page_source, 'html.parser')
print(soup.prettify())
