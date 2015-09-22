# x-plane-map
Google Map plugin for X-Plane. You can find a [compiled version](http://forums.x-plane.org/index.php?app=downloads&showfile=25569) on the x-plane forums.

SETUP INSTRUCTIONS
------------------
1. X-Plane Setup
  * Start X-Plane. Go to Settings -> Data Input & Output.
  * Activate the leftmost checkbox of line 20 (Internet output of latitude, longitude, altitude).
  * Make sure no other checkboxes for Internet data output are active.
  * In Settings -> Net Connections, go to the Data tab and fill out the IP and port : 127.0.0.1 (that's your computer) and 49003. Check the "IP of Data Receiver" checkbox.
 
2. Building and Running the Map Server

I have not yet gotten around to use a build file, just build this with res/ in the classpath. You also need to include [com.sun.net.httpserver](http://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html) and [json-simple](https://github.com/fangyidong/json-simple) in the build path.
	
VIEWING AND USING THE MAP
-------------------------
On the computer where the map server is running, it automatically opened your Web browser at the right page.
The map shows your plane at its latest known location and fetches the new location every second.

  * Clicking on your plane will show a tooltip with the plane's IP, heading, speed and altitude. Heading and speed are computed from the plane's last two known locations.
  * Hit Tab or click the button at the bottom right to reveal the control panel. The control panel shows a list of all planes.
  * Hit N or click the button at the bottom right to hide or show the navaid overlay.
  * Click on a plane in the list to keep the map focused on it. Click on the top bar, or move the map, to un-focus. 
  * Double-click on the plane's name to change it to whatever you want (e.g. its callsign, registration number, flight number ...). Hit Enter to confirm or Escape to cancel. By default, planes are named after their IP address.
  * Use the checkboxes and buttons to control the plane's trace or remove the plane from the map.
  * The map is viewable from any computer or device on your home network at the same address ( http://your-local-IP-address:8000/ ) 
  
  
MULTIPLAYER INSTRUCTIONS
------------------------
External Moving Map is multiplayer-ready, it can show multiple planes from computers on the same network. Here's how:
  * Choose a computer to be the "map server" : on that computer, follow the instructions above.
  * Make sure that computer's firewall allows incoming UDP traffic to port 49003.
  * All other computers must follow *JUST PART 1* of the instructions but replace the IP "127.0.0.1" with the IP of the map server. They sould NOT follow part 2, running the map server, as there must be only one map server.
 The map should now show every plane, and everyone should be able to see it at the same address ( http://the-map-servers-IP-address:8000/ )
 
 
FAQ
---
 Q: X-Plane won't start anymore, I get a "udp_socket_read udp_init failed" error message.

 A: Open file Output/Preferences/X-Plane.prf, in your X-Plane directory, look for the lines starting with `_rcv_port`, `_snd_port` and `_flr_port` and check that the port values are 49000, 49001 and 49002 respectively. After that, X-Plane should be able to restart.

	
I ALSO HAVE A QUESTION FOR YOU:
-------------------------------
What features do you want added to this ? I really want to make it the best map application out there, and to do that, I have to catch up on the features existing apps have.
Here are my next ideas:
* Look up for a navaid: type its name, the map pans to it.
* Enter and view flight plans.
* As requested, choose the plane's color.
Anything else ? Feel free to leave an improvement issue!
