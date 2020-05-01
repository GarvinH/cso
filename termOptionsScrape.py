import json;
from selenium import webdriver;

def termOptionsScrape():
    chromeOptions = webdriver.ChromeOptions();
    chromeOptions.add_argument("--incognito");
    chromeOptions.binary_location = "/usr/bin/brave-browser"

    driver = webdriver.Chrome(executable_path="./chromedriver", options=chromeOptions);
    driver.get("https://central.carleton.ca/prod/bwysched.p_select_term?wsea_code=EXT");
    terms = driver.find_elements_by_xpath("//select[@id='term_code']/option");
    data = {}
    for i in range(len(terms)):
        term = terms[i];
        text = term.text;
        termNum = term.get_attribute("value");
        data["option"+str(i)] = {
            "text": text,
            "termNum": termNum
        }
        
    termOptions = open("termOptions.json", "w");
    termOptions.write(json.dumps(data));
    termOptions.close();
    driver.close();
    driver.quit();