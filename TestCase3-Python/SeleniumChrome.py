#Selenium Chrome

#should work on a device that has chrome

#FIXME: may have typos

#change Path name

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time



#change accordingly to appropriate webdriver destination
PATH = "C:\Program Files (x86)\WebDrivers\chromedriver.exe" #


#constants
s = "Tenet Movie Warner Brothers";
check = "Tenet is an upcoming spy film written, produced, and directed by Christopher Nolan. It is a co-production between the United Kingdom and United States, and stars John David Washington, Robert Pattinson, Elizabeth Debicki, Dimple Kapadia, Michael Caine, and Kenneth Branagh.";


#open browser
driver = webdriver.Chrome(PATH)

driver.get("https://google.com")


#search website on driver
search = driver.find_element_by_name("q")
search.send_keys(s)
search.send_keys(Keys.RETURN)


#wait and show results
try:
    main = WebDriverWait(driver,30).until(
        EC.presence_of_element_located((By.ID,"kp-wp-tab-overview"))

    )

    #get text

    desc = find_element_by_class_name("kno-rdesc")

    if desc.text == check:
        print(desc.text)
        print("\nTrue\n")
    else:
        print("\nFalse\n")
        
    
finally:
    driver.quit() #close browsere

