Node js:
https://nodejs.org/en/download/

install ionic:
npm install -g ionic

run project in browser lab:
ionic serve -l

-------------------------------
command line in project:

"start": "ng serve",
"build": "ng build",
"test": "ng test",
"lint": "ng lint",
"e2e": "ng e2e"

-------------------------------
Deploy on real device:
ionic cordova build android --prod

-------------------------------
open -a Google\ Chrome --args --disable-web-security --user-data-dir

-------------------------------
//set up firebase from ionic document
//ionic cordova plugin add cordova-plugin-firebase
//npm install @ionic-native/firebase
[
ionic cordova plugin add cordova-plugin-fcm-with-dependecy-updated
npm install @ionic-native/fcm
]

for fireBase:
npm install firebase angularfire2

-------------------------------

Geolocation: (remove )
ionic cordova plugin add cordova-plugin-geolocation --variable GEOLOCATION_USAGE_DESCRIPTION="To locate you"
npm install --save @ionic-native/geolocation@beta

Geocoder: (remove)
ionic cordova plugin add cordova-plugin-nativegeocoder
npm install --save @ionic-native/native-geocoder@beta

typescript google-maps: (remove)
npm install @types/google-maps --save

remove(---
//native-storage:
//ionic cordova plugin add cordova-plugin-nativestorage
//npm install @ionic-native/native-storage
---)
---------------
camera

ionic cordova plugin add cordova-plugin-camera
npm install @ionic-native/camera

---------------
image resize (not install yet)

ionic cordova plugin add info.protonet.imageresizer
npm install @ionic-native/image-resizer

--------------
geolocation
ionic cordova plugin add cordova-plugin-geolocation
npm install @ionic-native/geolocation

fcm (remove)
ionic cordova plugin add cordova-plugin-fcm-with-dependecy-updated
npm install @ionic-native/fcm