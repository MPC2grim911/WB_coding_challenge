Android Studio TestCases - Murphy Chu


Contents and Status

   TestCase 1: complete
   TestCase 2: complete
   TestCase 3: mostly complete 

   -Note: Am unfamiliar with navigating 
          TestCases have been debugged on Samsung Android 
          mobile device
      



TestCase 1: Launch WiFiman app

   Description:
	Simple app that launches existing WiFiman upon 
        pressing the icon's image button.

        - Note: If doesn't work on Emulator, try running
                through USB test device


   UI Instructions:

      * To launch WiFiman app, press on WiFiman app image


   Warnings:

      * If WiFiman app has not been installed on device:

          - TextView message will appear on screen

          - Launching WiFiman will crash TestCase app


   Future Improvement Notes:
	
      - Modify warning message into prompt user pop up tab

      - Close out of TestCase app after pop up tab warning


   Additional notes:
      
      - App been adapted to fit different screen sizes in
        landscape mode



TestCase 2: Print signal of user input SSID

   Description:
       Simple app that checks scans wifi network of device.
       The app obtains user input of wifi name (SSID) and 
       prints the signal strength every second for a minute 
       and then the average signal strength from there 
       after.

         - Note: Might not perform properly on Emulator.
                 If Emulator does not work, try running
                 through USB test device


   UI Instructions:

      1) Enable location permissions for app in device 
         settings prior to starting app

             - If this is not done, the app will:
                  * Crash and then
                  * prompt user to allow access to 
                    location permissions in settings

             - If the above happens:
                  - enable app to use locations
                  - reboot/restart app

      2) Click and type in SSID name into EditText box and
         Press the Submit button or keyboard enter/done

      3) Results for strength will be printed and displayed 
         for 60 secs before switching over to average


   Warnings:
	
      * Must have location permissions enabled or available on
        device in order to get printed/visual results

              - App crashes temporarily after prompting user
                to enable permissions

      * Since I am unfamiliar with Emulator settings,
        I am assuming that the app either does not perform  
        as intended on Emulator or I did not set up the 
        Emulator device(s) network/connections properly. 

               - However, the app does work with the instructions
                 above based my testing and debugging sessions on 
                 my mobile device.

      * Virtual Keyboard will show upon clicking on EditText
        regardless of if physical keyboard is attached/connected

               -Both input methods are accepted


   Future Improvement Notes:

      - Needs to refine detection of connected/attached keyboards 
        so that virtual keyboard doesn't show on clicking EditText

      - Fix the app crashes that occur when prompting user to 
        enable permission locations

      - (Possibly) Needs to be adapted to be Emulator friendly

      - Probaly combine strength and average display in UI design


   Additional notes:
      
      - App been adapted to fit different screen sizes in
        landscape mode



Bonus - TestCase 3: Implement Selenium Page Object Model 

   Description:
      A Selenium Page Object Model that has same framework as
      Appium and uses a webdriver to search google for specific
      string. Returns True or False if desired content is found
      in search results. App UI displays search result contents 
      and boolean result.

   - Note: TestCase Incomplete  
          
   Status:

     * FireFox - Mostly Complete (test and debug code logic)
     * Chrome - Incomplete   
     * UI -  Complete    (set to test, debug, and refine)


    (Intended) UI Instructions:

        * Choosing a button will launch instantiate Selenium
          for respective FireFox and Chrome browsers

        * Wait time of 30 seconds to display/update results

               - Contents shown are search results and 
                 boolean assertion


    (Potential) Warnings:

       * Different FireFox versions may have different content 
         names for Google's javascript html className and id 

       * Coding method for FireFox search might not be 
         requested Selenium page object model format 
       
       * See project FIXME notes for additional warnings

   
    Future Progress Notes:

	- Personal study and research into Selenium coding 
          concept and how Appium is utilized for a better
          understanding before further attempts 

        - See project code FIXME's for additional notes 
          and developments

