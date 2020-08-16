Android Studio TestCases - Murphy Chu


Contents and Status

   TestCase 1: complete
   TestCase 2: complete
   TestCase 3: mostly complete 

   -Note: Am unfamiliar with navigating Android TV device so
      TestCases have been debugged on Samsung Android phone



TestCase 1: Launch WiFiman app


   Instructions:

      *To launch WiFiman app, press on WiFiman app image


   Warnings:

      *If WiFiman app has not been installed on device:

          -TextView message will appear on screen

          -launching WiFiman will crash TestCase app


   Future Improvement Notes:
	
      -Modify warning message into prompt user pop up tab

      -Close out of TestCase app after pop up tab warning





TestCase 2: Print signal of user input SSID

   -Note: Might not perform properly on Emulator


   Instructions:

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

      3) Results for strength will be printed for 60 secs
         before switching over to average


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




Bonus - TestCase 3: Implement Selenium Page Object Model 

   - Note: TestCase Incomplete  
          
   Status:

     * FireFox - Potentially Complete (need to test and debug)
     * Chrome - Incomplete   (coding logic still in progress)
     * UI -  Complete    (set to test, debug, and refine)


    (Intended) Instructions:

        * Choosing a button will launch instantiate Selenium
          for respective FireFox and Chrome browsers

        * Wait time of 30 seconds to display/update results


    (Potential) Warnings:

       * Different FireFox versions may have different content 
         names for Google's javascript html className and id 

   
   Future Progress Notes:

	- Personal study and research into Selenium coding 
          concept and how Appium is utilized for a better
          understanding before further attempts 

