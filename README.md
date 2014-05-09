G-kart, an android app, allow the users to build a shopping list of groceries by scanning barcodes to shop from home. This prototype is developed in Andriod. It could be extended to other platforms such as BB, iOS and Win.

Detailed explanation of G-Kart fnctionality and screens is available in DOCS folder.

Development Information:

Note 1: Try out! our "G-Kart.apk" which is available in bin folder by transfering it to your smart phones.

Note 2: In this prototype we have not done field validation. That is, the fields Name, Email, Address, City, Pincode are mandatory but if empty it would still allow to navigate to next screen.


This app uses "Google ZXing" API for bar code scanning.

Prerequisites:

The bar code scanner app needs to be installed in the device for scan functionality to work (will prompt for installation if not available in device):

Google play link: https://play.google.com/store/apps/details?id=com.google.zxing.client.android

External libraries used:

Below Open source classes are used from Google ZXing API:
com.google.zxing.integration.android.IntentIntegrator.java
com.google.zxing.integration.android.IntentResult.java

License:

Apache License, Version 2.0 (refer: http://www.apache.org/licenses/LICENSE-2.0)
