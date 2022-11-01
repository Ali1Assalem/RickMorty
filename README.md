# RickMorty
This is a simple app which has been implemented using Clean Architecture alongside MVVM design to run (online/offline) using :<br>
     [  MVVM ,Hilt DI ,LiveData ,Flow ,Room ,Retrofit ,Coroutine , <br>
      Navigation Component ,DataStore ,DataBinding , ViewBinding, Coil  ] <br>  
Also I used the free API https://rickandmortyapi.com/api the content of app. IT CONTAINS A SINGLE ACTIVITY WITH A SIMPLE UI. And the goal of sharing this code is to have a better understanding of the techniques I mentioned above for newbies.

# Main Features :books:
1. Kotlin
2. MVVM - Single Activity Architecture
3. DataBinding - ViewBinding
4. Flow - LiveData
5. Coroutine
6. Hilt DI
7. Paging3 , RemoteMediator
8. Room
9. Navigation Components - Safe Args
10. Retrofit2
 11. Coil

# App Architecture 
<p align="center">
    <img  src="https://user-images.githubusercontent.com/76838562/173254331-aa79eb39-653b-4a1c-8c65-ad3b337ff368.jpg" width="500"/>
</p>
<br>

# Screenshots 
<p align="center">
  <img src="https://user-images.githubusercontent.com/76838562/189928370-4eecf56a-8171-43ac-a887-16545d44a11e.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189929024-3a2a7abb-3b76-4074-8c94-0445e0da126d.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189929340-3dcbfb13-a4a2-4c5d-a8cd-5a4fa1f1ece2.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189929398-46b05ac2-da3f-4b29-b2ed-e1058eba809e.png" width="200"/>
</p>
<br>

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/76838562/199320526-2a73da12-c7cb-45fb-8320-baf5e4c1d462.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/199320551-1e9c7bb9-246a-4882-b73f-c54949b097f4.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/199320695-405991dd-f2ab-40f1-a43c-b702fc9d39b9.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189930434-da4274b9-4e39-4c24-9026-4f2ea6a22cf5.png" width="200"/>
</p>
<br>

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/76838562/189930121-33055e30-f675-447d-94cc-55182521b9e6.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189930190-647e84b3-57a1-45f3-bfcc-26aa162032c0.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189930481-ac81d32b-e3c0-4f51-ba8c-a62c0f9740f9.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189930961-047ba524-b1c6-457c-bfdc-529ca900a6a0.png" width="200"/>
</p>
<br>

<br>
<p align="center">
  <img src="https://user-images.githubusercontent.com/76838562/189931031-45bee429-312e-4369-bbd0-a6a1c5b68995.png" width="200"/>
  <img src="https://user-images.githubusercontent.com/76838562/189931226-fadb404b-2f76-479f-8835-369937112c3e.png" width="200"/>
</p>
<br>

## Advantages Of Using Single Activity Architecture

 1. From the name only we can understand that only one Activity will exist in the whole architecture. So, no need to update the Manifest every time. Just once, we have to declare the Navigation graph XML file in the Manifest.
 2.  No need to declare the boilerplate method like startActivityForResult() every time as we will navigate between the fragments now and the navigation between the fragments get also simplified with the use of the Navigation Component library included in JetPack. 
 3. The transition Animation problem has also been resolved after using the fragments.
 4. As all the Fragments will be bounded inside the Activity, we can easily share the data between the fragments. 
 5. We might counter incidents like we need to pass information to the fragments. It may consist of nullable data. Manually, if we have to resolve this problem then we have to write several lines of code. But in Navigation Component, Android introduced a feature called “Safe Args Gradle Plug-in”. Builder classes will be generated from this plug-in to assure type-safe access to the arguments for the particular action.

## BENIFITS OF NAVIGATION COMPONENT

[Navigation Architecture Component](https://developer.android.com/codelabs/android-navigation#0) provides a number of benefits, including:

- Automatic handling of fragment transactions
- Correctly handling up and back by default
- Default behaviors for animations and transitions
- Deep linking as a first class operation
- Implementing navigation UI patterns (like navigation drawers and bottom nav**)** with little additional work
- Type safety when passing information while navigating
- Android Studio tooling for visualizing and editing the navigation flow of an app<br><br><br>


### Author

[@ALI](https://www.linkedin.com/in/ali-assalem-4769371a8/)
