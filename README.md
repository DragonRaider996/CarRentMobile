# CARRENT

The CARRENT is the easiest way to rent a vehicle from the prime selection of different types of vehicles. With the help of this app, the user can select a car, boat or bike from the options and the app will provide exclusive result based on the user's requirements. The user can also put their vehicle on rent by capturing images of their vehicle and providing a description of the same. The user can contact the owner for more details about the vehicle and also rate the vehicle as per the experience. 

## Group members

| Name                   | Banner ID  | Email               |
| ---------------------- | ---------- | ------------------- |
| Anant Pillai               | B00826642  | anant.pillai@dal.ca     |
| Milap Bhanderi              | B00823109  | milap@dal.ca     |

## Access Repository
##### CARRENT
`https://git.cs.dal.ca/pillai/carrent`


##### CARRENT Admin
`https://git.cs.dal.ca/bhanderi/carrentadmin`

## Description

The “CARRENT” is an application which is designed with a view to provide easy access to hand-picked vehicles within the specific range of user’s location. Many people want to travel by car or want to sail in the boat but as they do not have one, renting is the best option for them and our app will provide many options based on their filtration choices so that they can rent a vehicle. There are many apps available online but our app will provide the best option for the user as the vehicles displayed in our app will be verified by the admin and if it does not match the proper requirements, the posting for that vehicle will be rejected. This way we will provide the appropriate range of vehicles to our users.

Moreover, other apps just focus on any single type of vehicle, we are covering different types of vehicles such as Car, Boat and Bike under our app. Also, this app will be specifically designed for the people of Nova Scotia so that the users do not need to travel more to rent the vehicle.  


### Users

There are mainly two kinds of users but as per the functionalities, we've subdivided them into three which are as following:
**1. The user who want to rent the vehicle**
The user who wants to rent the vehicle can search for the specific vehicle type and can sort down the options. This user can see the description of the vehicle and Owner’s details such as Location and Contact information. The result will be shown as per his/her current location. 
**2. The user/owner who wants to put their vehicle on rent**
The second user, who is the owner of the vehicle can put their vehicle on rent by posting it on the app. For that, the user needs to provide vehicle description, vehicle photos and contact information.
**3. Admin**
The admin will verify the posts and blocklist any of the vehicle posts is not appropriate in terms of required and provided information. The admin will perform its operations from the standalone application which will be completely separate from the CARRENT app and it is specifically designed for the admin.


### Features

* **Rent a vehicle**
- Register/Login 
- Search for the vehicle to rent
- Book the vehicle 
- Contact Owner
- Rate the vehicle
* **Put vehicle on rent**
- Register/Login
- Post vehicle with Images
* **Admin**
- Check the list of all vehicles
- Approve or Reject the vehicle
- Blacklist the currently listed vehicles



## Techical Matters

### Libraries
**Volley:** *"Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster. Volley is available on [GitHub](https://github.com/google/volley) [4]"*. We have used Volley to fetch the details from the server.
**App  Rating Dialogue:** *"This library allows to use customized Rating Dialog inside applications."* This library is available on [Github](https://github.com/stepstone-tech/android-material-app-rating) [5]. We have used this library for providing rating feature.
**Material Date and Time Picker:** *"Material DateTime Picker tries to offer you the date and time pickers with an easy themable API."* The library uses the [code from the Android frameworks](https://android.googlesource.com/platform/frameworks/opt/datetimepicker/) as a base and tweaked it to be as close as possible to Material Design example. This library is available on [Github](https://github.com/wdullaer/MaterialDateTimePicker) [6]. We have used this library for the selection of From and To date.
**Material Dialog:** *" "AndroidMaterialDialog" is an Android-library, which provides builders for creating dialogs, which are designed according to Android 5's Material Design guidelines even on pre-Lollipop devices."* This library is available on [Github](https://github.com/michael-rapp/AndroidMaterialDialog) [7]. We have used this dialog for sorting options.
### API
**Google Map API:** We have used this API to display images of different vehicle types. You can provide the key, latitude and longitude to this [link](https://maps.googleapis.com/maps/api/staticmap?zoom=15&size=600x300&maptype=roadmap&markers=color:blue|label:S|"+latitude+","+longitude+"&key=apikey) to use this API.


## Requirements

* **Android Studio:** This is an android studio project.
* **Java:** Java programming language is used. Download the latest version.
* **Git:** Git is used for code versioning and collaboration.
* **minSDKversion Version:** API 21
* **Compile SDKversion Version:** API 28


## Installation Notes

As we are having two separate apps, one for regular users and one for admin, the marker needs to have both the app to check the all the functionalities of the project.


## Final Project Status

At the end, we managed to complete all our basic, expected and one of our proposed bonus functionalities. Currently, our app provides searching, renting and rating features as a basic functionalities. It provides calling, camera to uploading images, using cellular data and location-based search as a device features usage. We are also covering one of the bonus functionalities in which we are also providing other vehicle types such as a boat, bike etc.

For future work, we would like to expand these sections such to be available everywhere in the world as the rest of the functionalities. We would also like to keep improving our User Interface and User Experience to provide a better service to the user.

### Basic Functionality
- Connecting to the backend (Completed)
- User Sign-up/Sign-in (Completed)
- Location-based result (Completed)
- Calling the owner (Completed)

### Expected Functionality
- Ratings (Completed)
- Viewing all cars (Admin) (Completed)
- Approval of Request (Admin) (Completed)
- Blacklisting Cars (Admin) (Completed)

### Bonus Functionality
- More Vehicle types (Completed)
- Chat with the owner (Not Implemented)

### Device Features
- Location (Implemented)
- Camera (Implemented)
- Call Feature (Implemented)
- Cellular Data (Implemented)


## Code Examples

**Problem 1: Viewing XML Files while using AndoidX**

While using AndroidX, we came some errors and one of them was where it won’t allow us to visualize the XML files in IDE while using AppBarLayout tag. So, we used to switch back to the previous stable version of AndroidX when working with those XML files. The below code was causing rendering issues with the layout in the IDE so we were not able to preview the layout of activity. Hence, we had to change the Gradle every time when we want to visualize the layout.
```
<com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appBarCarDetail"
        android:layout_width="match_parent"
        android:layout_height="300dp">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/collapsingToolbarCarDetail"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:contentScrim="?attr/colorPrimary"
            app:expandedTitleMarginEnd="64dp"
            app:expandedTitleMarginStart="48dp"
            app:expandedTitleTextAppearance="@style/AppBar"
            app:collapsedTitleTextAppearance="@style/AppBarCollapsed"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <ImageView
                android:id="@+id/imageCarDetail"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:contentDescription="@string/app_name"
                android:fitsSystemWindows="true"
                android:scaleType="centerCrop"
                android:src="@drawable/image"
                app:layout_collapseMode="parallax" />

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbarCarDetail"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:layout_collapseMode="pin"
                app:popupTheme="@style/ThemeOverlay.MaterialComponents.Light"
                app:title="Profile"
                app:titleTextColor="@color/colorWhite" />
        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>
```
![Error Snapshot](https://user-images.githubusercontent.com/47186638/61835807-c01aa100-ae53-11e9-8434-382d16786dba.png)


## Functional Decomposition

#### Model-View-Controller (MVC)

*"The Model-View-Controller (MVC) is an architectural pattern that separates an application into three main logical components: the model, the view, and the controller. Each of these components are built to handle specific development aspects of an application. MVC is one of the most frequently used industry-standard web development framework to create scalable and extensible projects [2]."*

![MVC](https://user-images.githubusercontent.com/47186638/61761629-a3c02b00-ada5-11e9-8a12-1ba610d6c12d.png)

#### Model
*"The Model component corresponds to all the data-related logic that the user works with. This can represent either the data that is being transferred between the View and Controller components or any other business logic-related data [2]."* In the model, we have two models - Car (vehicle) and URL. Car is for vehicle details such as ID, model, price etc. The URL is used for all the URLs which we going to hit.
#### View
*"The View component is used for all the UI logic of the application [2]."* In the view, we have all our XML files in the layout folder.
#### Controller
*"Controllers act as an interface between Model and View components to process all the business logic and incoming requests, manipulate data using the Model component and interact with the Views to render the final output [2]."* For the controller part of the app, all the activities, fragments and the adpaters will come under the controller section.

This way we ensured and followed the MVC model for our apps.

![MVC_Project](https://user-images.githubusercontent.com/47186638/61839362-a6815580-ae63-11e9-905f-d69a0c66c6ed.png)


## High-level Organization

The below shown sitemap will provide the information organization for the app.

![Sitemap](https://user-images.githubusercontent.com/47186638/61763135-a2ddc800-adaa-11e9-9bac-0f6a87d3c9e0.png)


## Clickstreams
##### **Login Activity:**
The user will login/Register by following the below steps. If teh user does not have logged in already then he/she needs to first register for the app.
![Click1](https://user-images.githubusercontent.com/47186638/61838600-6c628480-ae60-11e9-9117-8e55ecf61d76.png)


##### **Searching for the vehicle**
The user needs to select the City and type of the vehicle along with From and To date and when the user tap on Search, he/she will be redirected to the vehicle result screen.
![Click2 (1)](https://user-images.githubusercontent.com/47186638/61838611-797f7380-ae60-11e9-987c-5e7f46239a6c.png)

##### **Booking for the vehicle**
To book the vehicle, the user needs to click on the "Book Now" button and the vehicle will be added in the "Booking History" which user can see by navigating to the Profile Screen.
![Click3](https://user-images.githubusercontent.com/47186638/61838793-599c7f80-ae61-11e9-8442-8e3d20fba362.png)

##### **Put Vehicle on rent**
To put vehicle on the rent, the user needs to click on "Put Vehicle on Rent" in the Profile screen and below shown screen will appear where the user needs to provide related details and image of the vehicle and when the user clicks on the "Submit", the vehicle will be added to the "Listed Vehicle" section.
![Click4](https://user-images.githubusercontent.com/47186638/61838935-fc54fe00-ae61-11e9-9653-690f2380a498.png)




## Layout/Wireframe

#### Regular User
![WireFrame1](https://user-images.githubusercontent.com/47186638/61763032-4bd7f300-adaa-11e9-8bd7-fc5ae149d5b1.png)

![WireFrame2](https://user-images.githubusercontent.com/47186638/61763073-7164fc80-adaa-11e9-9526-0a9672fd1c55.png)

#### CARRENT Admin
![LFP_Admin (1)](https://user-images.githubusercontent.com/47186638/61819863-3f937a80-ae2a-11e9-8689-f15d1fe8d376.png)


## Prototypes

#### Low Fidelity Prototype
The low fidelity prototype was helpful to organize the information properly. It is also cost-effective so that one can make amendments as per the requirements.

![LFP1 (1)](https://user-images.githubusercontent.com/47186638/61813320-cab94400-ae1b-11e9-96df-5f4d35d152b9.png)

![LFP2 (1)](https://user-images.githubusercontent.com/47186638/61813331-d147bb80-ae1b-11e9-89ea-ad815acc5f46.png)

#### CARRENT Admin
![W_Admin (1)](https://user-images.githubusercontent.com/47186638/61820964-83877f00-ae2c-11e9-9cbc-3fabbef9c31a.png)



#### High Fidelity Prototype
The high fidelity prototype is more like the visual design of the app and it is more presentable to the stakeholders. It gives the feel like interacting with the actual app so the user can exactly pinpoint every functionality and developer can also find where the UX is compromised.

![HFP1 (1)](https://user-images.githubusercontent.com/47186638/61817157-415a3f80-ae24-11e9-8fe2-a01b064210fc.png)

![HFP2 (1)](https://user-images.githubusercontent.com/47186638/61817161-461ef380-ae24-11e9-8958-e6e2104b7ab0.png)

#### CARRENT Admin
![HFP_Admin (1)](https://user-images.githubusercontent.com/47186638/61818565-7b791080-ae27-11e9-8624-ecf1e7db851c.png)

## Implementation

The implementation of both the apps can be seen in the images below. This implementation might differ from the prototypes as some of the functionalities were added later on such as different vehicle types and some functionalities were implemened differentely than designed in the prototypes.
### Regular User
![UserImp1](https://user-images.githubusercontent.com/47186638/61839058-79807300-ae62-11e9-9fff-50cee56492e8.png)

![UserImp2](https://user-images.githubusercontent.com/47186638/61839195-10e5c600-ae63-11e9-8ed2-667c46e4709d.png)


### CARRENT Admin 
![AdminImp (1)](https://user-images.githubusercontent.com/47186638/61838340-3b358480-ae5f-11e9-8fc6-842055822e7d.png)

## Completion Report
To design this application, we divided the development work into mainly three milestones. In the first milestone, we focused on the UI of the application. For any app in the world, the UI is very important as it is the front face of the app with which user will interact whole the time and technically based on your UI, you can have the idea of the user experience. So, for the UI part, we decided to first create some wireframes and Hight Fidelity Prototype so that we can have some basic idea about how our app will look like. After referencing some online apps [1] and with some amendments in the prototype design we finally started our implementation. For the implementation, we started with the Android support library and created the Splash screen, Login and Register screen but afterwards, we moved to the `AndroidX` library as we wanted to work with the latest features upcoming in the Android field.

In the second Milestone, we focused on the residuary basic functionalities and expected functionalities. In the basic functionalities, we had some functionalities which use device features such as calling and location. To implement the calling feature, we have used the implicit `intent` so that when the user taps to call the owner, the intent will make a direct call to the user. To fetch the user's location, we have used `FINE_LOCATION`, `COARSE_LOCATION` and `FuseLocationProvider`.  After completion of the basic functionality, we started working on other features of the app in which we completed searching for the vehicle, Vehicle result screen in which the user can sort the result by price, location and rating. We also created two features for Home screen and Profile screen with the help of  `BottomNavigationView`. When the user taps on the "Home", he/she will be redirected to the main page that is the search screen and when the user taps on the "Profile", he/she will be redirected to the Profile screen from which the user can find the booking history and also rate the vehicles rented before.

After the completion of Milestone 2, we started working on the rest of the functionalities in which we created the connection with the backend which is a dummy server of `Spring Boot` which will fetch the data for the app. For the admin part, we created the standalone app - `CARRENT ADMIN` that includes the main three functionalities of admin in which admin can view all the vehicle postings, Approve/Reject of the post and can blacklist the post of the vehicle posted by the user. To implement the admin app, we have used `BottomNavigationView` to display the three main functionalities of the admin and for each functionality, we have used fragments which contained `RecyclerView` that shows custom details in it about the vehicle. After that, we started our bonus functionalities in that we provided other vehicle types such as Boat and Motorbike which user can select from the "Home Screen" by using the dropdown for the vehicle type. In the end, for polishing of the app, we refined out rating module and other features such as booking and viewing of the booked vehicles. We also added "Help" in the Profile Screen in which provided steps to rent the vehicle and to put the vehicles on rent. The user can also email us for more questions and suggestions.

![Timeline Gantt Chart](https://user-images.githubusercontent.com/47186638/61841273-8a34e700-ae6a-11e9-85c7-793e2d934d0e.png)


Overall, we have completed all our basic and expected functionalities and one of the bonus functionalities. We have followed the Milestone timeline as per the Gantt chart shown in the Figure above. While developing both the apps, we kept Jakob Nielsen's Heuristics [3] in the focus to make the user experience rich and made sure that every activity is clean, simplistic and aesthetic for the naive user too. We've provided necessary error messages so that the user can get to know what is going on the screen. The user can exit or go back from any screen by selecting the appropriate option such as Back or options from the BottomnavigationView. We started the implementation of this app with the motive to provide people of Halifax a convenient way to rent the vehicles and we are proud to present our app "CARRENT" which is perfect for aforesaid usage and provides sufficient functionalities to the users of the app.

## Brief Description of every Activity 

In the login and register screen, we have used the `TextInputLayout` which allows us to follow provide a better way to get input from the user and also notify them about the error in a good way.

For the home screen, we have used the `BottomNavigationView` with two bottom tabs. On the landing screen, we take input from the user to book the vehicle and for which city. On the click of search, our application will fetch the data and display the list of data in a `RecyclerView` with custom layout. In the profile section, the user can see his/her booking history, which will provide the information of all the cars which are currently booked and which were booked in the past in a `RecyclerView`. The previously booked car can be rated by the user and currently booked cars can be cancelled. 

Admin Application will have a home screen with `BottomNavigationView` and 3 tabs. In the list of all vehicle tab, the admin can view all the cars available and can blacklist any car, in the vehicle requests the admin can view all the cars currently requested for approval and the admin can either accept or reject the car request, in the blacklisted vehicle tab, all the blacklisted cars can be seen and can be removed from the blacklist.

## Future Work

For future work, we will focus on making our backend more strong, secure and dynamic. We will provide offers and discounts to the user so we can achieve customer delight. We will also implement other bonus functionalities such as Chat feature for the user who want to rent the car and the owner. With those functionalities, we will ensure that we further increase user experience.


## Sources

[1] Play.google.com, 2019. [Online]. Available: https://play.google.com/store/apps/details?id=com.ehi.enterprise.android&hl=en. [Accessed: 24- Jul- 2019]
[2] "MVC Framework Introduction", www.tutorialspoint.com, 2019. [Online]. Available: https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm. [Accessed: 24- Jul- 2019]
[3] "10 Heuristics for User Interface Design: Article by Jakob Nielsen", Nielsen Norman Group, 2019. [Online]. Available: https://www.nngroup.com/articles/ten-usability-heuristics/. [Accessed: 25- Jul- 2019]
[4] "Volley overview  |  Android Developers", Android Developers, 2019. [Online]. Available: https://developer.android.com/training/volley. [Accessed: 25- Jul- 2019]
[5] GitHub. (2019). stepstone-tech/android-material-app-rating. [online] Available at: https://github.com/stepstone-tech/android-material-app-rating [Accessed 25 Jul. 2019].
[6] GitHub. (2019). wdullaer/MaterialDateTimePicker. [online] Available at: https://github.com/wdullaer/MaterialDateTimePicker [Accessed 25 Jul. 2019].
[7] GitHub. (2019). michael-rapp/AndroidMaterialDialog. [online] Available at: https://github.com/michael-rapp/AndroidMaterialDialog [Accessed 25 Jul. 2019].
[8] GitHub. (2019). adam-p/markdown-here. [online] Available at: https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#links [Accessed 25 Jul. 2019].
[9] Cdntdreditorials.azureedge.net. (2019). [online] Available at: http://cdntdreditorials.azureedge.net/cache/4/3/2/e/5/9/432e59f335d84871e233016fb9d78208b7cfff71.jpg [Accessed 25 Jul. 2019].
[10] Carspecs.us. (2019). [online] Available at: https://www.carspecs.us/photos/420c9548fc921169801d87a1090fa295f5f83562-2000.jpg [Accessed 25 Jul. 2019].
[11] Poihq.com. (2019). [online] Available at: http://poihq.com/wp-content/uploads/2019/06/94-A-Kia-Lineup-2020-Images.jpg [Accessed 25 Jul. 2019].
[12] Taladauto.com. (2019). [online] Available at: http://www.taladauto.com/wp-content/uploads/2018/09/2019-honda-cr-v-hybrid-euro-spec.jpg [Accessed 25 Jul. 2019].
[13] Cdntdreditorials.azureedge.net. (2019). [online] Available at: https://cdntdreditorials.azureedge.net/cache/9/b/e/e/9/7/9bee9786ef41d4ef3e0f88a1835f65fc911e19e0.jpg [Accessed 25 Jul. 2019].
[14] Santaspeedorun.com. (2019). [online] Available at: http://santaspeedorun.com/wp-content/uploads/2019/03/best-nissan-micra-2018-price.jpg [Accessed 25 Jul. 2019].
[15] Cdn-w.v12soft.com. (2019). [online] Available at: https://cdn-w.v12soft.com/photos/LoPGGCL/11014957/001_0706201909030816843_800600.jpg [Accessed 25 Jul. 2019].
[16] Upload.wikimedia.org. (2019). [online] Available at: https://upload.wikimedia.org/wikipedia/commons/3/36/2015_Toyota_Corolla_Altis_%28ZRE172R%29_1.6_sedan_%282016-01-03%29_01.jpg [Accessed 25 Jul. 2019].
[17] 1635243468cda3039c02-3fde5a3082e850a813cc8ccdde10a1d4.ssl.cf1.rackcdn.com. (2019). [online] Available at: https://1635243468cda3039c02-3fde5a3082e850a813cc8ccdde10a1d4.ssl.cf1.rackcdn.com/1GTV9CED4KZ169266/62cd4fcc2dcff8b78e1602b070e596c2.jpg [Accessed 25 Jul. 2019].
[18] Photos.motorcar.com. (2019). [online] Available at: https://photos.motorcar.com/used-2014-chevrolet-impala-4drsedanltw2lt-13470-18771937-2-640.jpg [Accessed 25 Jul. 2019].
[19] formula), C., Minto, R. and Dali, S. (2019). Calculate distance between two latitude-longitude points? (Haversine formula). [online] Stack Overflow. Available at: https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula/12600225#12600225 [Accessed 25 Jul. 2019].
[20] Boatingworld.com. (2019). [online] Available at: https://www.boatingworld.com/wp-content/uploads/2015/06/Glastron-GTS-245_03.jpg [Accessed 25 Jul. 2019].
[21] Cdnmedia.endeavorsuite.com. (2019). [online] Available at: https://cdnmedia.endeavorsuite.com/images/usedequipment/89f26152-4b99-46c7-905e-d02396cc1b8f/detail/eb4d2f1e-e54c-4999-8159-9af102e0a82d.jpg [Accessed 25 Jul. 2019].
[22] Benningtonmarine.com. (2019). [online] Available at: https://www.benningtonmarine.com/wp-content/uploads/Fiberglass-Ponton-Boats-2572-2575-QX-2016-Swingback-2.jpg [Accessed 25 Jul. 2019].
[23] Boats-from-usa.com. (2019). [online] Available at: https://boats-from-usa.com/sites/default/files/boats/not-specified/6988/not-specified-724sl-limited-250hp-verado-73891.jpg [Accessed 25 Jul. 2019].
[24] Rjmachine.ca. (2019). [online] Available at: https://www.rjmachine.ca/wp-content/uploads/2017/02/2017-pwcfixed-1024x576.jpg [Accessed 25 Jul. 2019].
[25] Lundboats.com. (2019). [online] Available at: https://www.lundboats.com/wp-content/uploads/1775_Impact_Sport_Header-2018-1440x0-c-default.jpg [Accessed 25 Jul. 2019].
[26] Images0.boattrader.com. (2019). [online] Available at: https://images0.boattrader.com/resize/1/12/2/5841202_20180929081637807_1_LARGE.jpg?t=1301805 [Accessed 25 Jul. 2019].
[27] App.louisianasportsman.com. (2019). [online] Available at: https://app.louisianasportsman.com/classifieds/pics/p1461459144300338.jpg [Accessed 25 Jul. 2019].
[28] Wizardlakemarine.com. (2019). [online] Available at: https://www.wizardlakemarine.com/wp-content/uploads/19-G23-94338-Gunmetal-Reef-7.jpg [Accessed 25 Jul. 2019].
[29] Images0.boattrader.com. (2019). [online] Available at: https://images0.boattrader.com/resize/1/68/32/6586832_0_310320181033_2.jpg?t=1291725 [Accessed 25 Jul. 2019].
[30] D2qn5pre0p0oeu.cloudfront.net. (2019). [online] Available at: https://d2qn5pre0p0oeu.cloudfront.net/i5/17efcde9/53647ce1/753618942n.jpg [Accessed 25 Jul. 2019].
[31] Thestar.com. (2019). [online] Available at: https://www.thestar.com/content/dam/thestar/autos/2015/04/03/honda-cb300f-preview-going-naked-in-savannah/honda-front-bike.jpg [Accessed 25 Jul. 2019].
[32] I.pinimg.com. (2019). [online] Available at: https://i.pinimg.com/originals/0e/ed/f1/0eedf12ec9fd1ab6ef5447b7d0f1e3c9.jpg [Accessed 25 Jul. 2019].
[33] Cdntdreditorials.azureedge.net. (2019). [online] Available at: https://cdntdreditorials.azureedge.net/cache/8/c/f/4/c/7/8cf4c7cdc18c9c36f3ad0afa931577cf8d3a6eac.jpg [Accessed 25 Jul. 2019].
[34] Postmediadriving.files.wordpress.com. (2019). [online] Available at: https://postmediadriving.files.wordpress.com/2018/12/IMG_0432.jpg?quality=80&strip=all&w=960&h=480&crop=1 [Accessed 25 Jul. 2019].
[35] Png.pngtree.com. (2019). [online] Available at: https://png.pngtree.com/svg/20170824/done_1252221.png [Accessed 25 Jul. 2019].
[36] Image.flaticon.com. (2019). [online] Available at: https://image.flaticon.com/icons/png/128/128/128397.png [Accessed 25 Jul. 2019].
[37] One, J., Biggs, S., Jones, D., Burmeister, A., Bourque, B., Biggs, S. and Singh, Y. (2019). Java Calendar: Getting Difference Between Two Dates/Times - Off by One. [online] Stack Overflow. Available at: https://stackoverflow.com/questions/13198609/java-calendar-getting-difference-between-two-dates-times-off-by-one [Accessed 25 Jul. 2019].
[38] GitHub. (2019). DragonRaider996/PocketDOC. [online] Available at: https://github.com/DragonRaider996/PocketDOC [Accessed 25 Jul. 2019].
[39] Android Developers. (2019). Set up RequestQueue  |  Android Developers. [online] Available at: https://developer.android.com/training/volley/requestqueue#java [Accessed 25 Jul. 2019].


