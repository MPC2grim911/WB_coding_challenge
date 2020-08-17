#Selenium Firefox

#Note: selenium deprecated firefox webdriver for my computer
#      current code can potentially run

#All HTML id and classes should be corrrect

#FIXME: selenium firefox webdriver issue
#FIXME: binary path issue when trying to run firefox



from selenium import webdriver
from selenium.webdriver import Firefox
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

from selenium.webdriver.firefox.options import Options as options
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary

#from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
#firefox_capabilities = DesiredCapabilities.FIREFOX
#firefox_capabilities['marionette'] = True
#firefox_capabilities['binary'] = 'C:\Users\test\tools\Firefox'
#driver = webdriver.Firefox(capabilities=firefox_capabilities)


#Selenium webdriver 


#change accordingly to path destination
PATH = "C:\\WebDrivers\\geckodriver.exe" #path to geckodriver


#bPath = FirefoxBinary('C:\WebDrivers\firefox.exe')#binary of a firefox executable


driver = webdriver.Firefox(PATH)#Deprecaated
#ops = options()
#ops.binary_location = bPath
#serv = Service(PATH) #webdriver.FireFox does not work

s = "Tenet Movie Warner Brothers";
check = "Tenet is an upcoming spy film written, produced, and directed by Christopher Nolan. It is a co-production between the United Kingdom and United States, and stars John David Washington, Robert Pattinson, Elizabeth Debicki, Dimple Kapadia, Michael Caine, and Kenneth Branagh.";


#driver = Firefox(service=serv, options=ops)

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


    desc = find_element_by_class_name("kno-rdesc")

    if desc.text == check:
        print(desc.text)
        print("\nTrue")
    else:
        print("False")
        
    
finally:
    driver.quit()
