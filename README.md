# Checkablebutton
This is a customizable view that implements the checkable interface.
You can adjust the color attributes for both checked and unchecked
states.

<img src="http://clemgumbs.com/img/apps/checkablebutton_1487161683.png" width="40%" />
<img src="http://clemgumbs.com/img/apps/checkablebutton_1487161644.png" width="40%" />


## Sample
<a href="https://play.google.com/store/apps/details?id=com.codeogenic.myapplication"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Get_it_on_Google_play.svg/2000px-Get_it_on_Google_play.svg.png" width="25%"></a>

OR  download sample from repository
<a href="https://github.com/clem44/checkablebutton/blob/master/app/app-release.apk"> Sample App download</a>


## What you can do
* Adjust Border states (stroke, radius, color)
* Background (unchecked, checked, disabled)
* Set TextSize
* Set Typeface
* Adjust Text Color states (checked, unchecked)


### Installation

[for now] clone the repository, and add the checkablebutton library to your project (in Android Studio as a module) (in Eclipse import the project and add as a dependency)


### Usage

Include app namespace to the root element :

	xmlns:app="http://schemas.android.com/apk/res-auto"

 Basic usage :

   <com.codeogenic.checkablebutton.CheckableButton
        android:id="@+id/btn1"
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:cb_checkedborderWidth="5dp"
        app:cb_checkedBorderColor="#f68e56"
        app:cb_checkedColor="#FF33B5E5"
        app:cb_checkedTextColor="#ffffff"
        app:cb_radius="30dp"
        app:cb_text="btn1"
        app:cb_textSize="10pt"
        app:cb_borderWidth="4dp"
        app:cb_unCheckBorderColor="#b7b7b7"
        app:cb_unCheckColor="#FFAA66CC"
        app:cb_uncheckedTextColor="#ffffff"
         />


#### Editable Attributes

|xml    | Java| Description|
|:------|:----|:-----------|
|app:cb_checkedborderWidth|setCheckedBorderWidth(int)|border width in checked state|
|app:cb_checkedBorderColor|setCheckedBorderColor(color)|border color in checked state|
|app:cb_checkedColor|setCheckedBackgroundColor(color)|button color in checked state|
|app:cb_checkedTextColor|setCheckedTextColor(color)|text color in checked state|
|app:cb_radius|setRadius(int)|button corner radius |
|app:cb_text|setText(String)|button text|
|app:cb_textSize|setDefaultTextSize(dimension)|button text size|
|app:cb_borderWidth|setBorderWidth(int)|border width in uncheck state|
|app:cb_unCheckBorderColor|setBorderColor(color)|border color in uncheck state|
|app:cb_unCheckColor|setUncheckedBackgroundColor(color)|button color in uncheck state|
|app:cb_uncheckedTextColor|setUncheckedTextColor(color)|button text color in uncheck state|


### TODO
* make available through jcenter
* make provision for adding icon fonts
* make provision for adding images
* Add check state animations


## Inspiration
Inspiration for this library came from toying around with Meydo's FancyButton https://github.com/medyo/Fancybuttons
It's an awesome library and you should check his work out.

### Licence

MIT
http://opensource.org/licenses/MIT

### Developed By

Clemaurde Gumbs

Connect [@Clem](https://www.linkedin.com/in/clemaurdegumbs25/) on LinkedIn for new releases.
