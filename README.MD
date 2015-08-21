# YouTube Search GUI

This folder is a netbeans project. There are two important classes.

The first class is YouTubeSearch. This implements the YouTube Restful API provided by Google to search for a string when provided. This class was designed to make the implimentation easier, so it can be used by constructing it and using .search().  It will require a google api key, which can be created at code.google.com/apis/console. This key should be placed in the youtube.properties box. You also need client secrets, which is created at the same location and placed into the client_secrets.json file. 

 The second class is YouTubeGUI. This GUI was designed to implement the YouTubeSearch class to search for videos and export them in the JSON format. 


