# MT Script Checker
MT Script checker is an optional client-server mod that collects a hash of the scripts present on the client and sends them to the server to verify that they are the same.

## Installation 

- Make sure you have craft tweaker installed.
- Download the mod from the [releases page](https://github.com/Column01/MTScriptChecker/releases) 
- Drop it in the mod folder on your client (optional) and server.
- Connect to the server

## Notes

This is an optional mod, so clients without it will not be denied the connection to the server. Server administrators can add it to their server, and if you have a custom pack or launcher, you can add it for those clients too.

As of now, this only says whether or not they are the same. It doesn't know which side is missing scripts. In the future I plan to make the client collect the scripts list and send it off to the server to see what scripts are different or are missing on the client. In the far future I may think of adding a way to hack in recipes post init like MineTweaker used to be able to do (they should show in JEI and also have tooltips if I can get it working properly.)
