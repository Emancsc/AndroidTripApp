# 🗺️ Attraction Planner - Android App

An Android application for planning and managing tourist attractions. Users can add, edit, search, and organize their favorite destinations with detailed information.

Submission info:
Eman Alghalban
1220611

## 📱 Features

### Core Functionality
- **Add Attractions**: Create new attraction entries with comprehensive details
- **Edit Attractions**: Modify existing attraction information
- **Delete Attractions**: Remove attractions with confirmation dialog
- **Search**: Real-time search by name or location
- **View Details**: Click on any attraction to see full details
- **Mark Favorites**: Flag attractions as favorites with visual indicators
- **Track Visits**: Mark attractions as visited/unvisited

### Attraction Information
Each attraction includes:
- Name
- Location
- Type (Museum, Park, Restaurant, Monument, Beach, Church, Theater)
- Entry Fee
- Visit Date & Time
- Duration (in hours)
- Rating (1-5 stars)
- Notes
- Favorite status
- Visited status

## 🏗️ Architecture

### Activities
1. **MainActivity**: Displays list of all attractions with search functionality
2. **AddAttractionActivity**: Form to create new attractions
3. **EditAttractionActivity**: Form to modify existing attractions
4. **AttractionDetailsActivity**: Shows complete attraction information with edit/delete options

### Data Management
- **SharedPreferencesManager**: Handles all data persistence using Android SharedPreferences
- **No external libraries**: Pure Android implementation without Gson
- 

### Adapter
- **AttractionAdapter**: RecyclerView adapter for displaying attraction cards

### Model
- **Attraction**: Data model with all attraction properties

## 📂 Project Structure

```
com.example.assignment1/
├── Activities/
│   ├── MainActivity.java
│   ├── AddAttractionActivity.java
│   ├── EditAttractionActivity.java
│   └── AttractionDetailsActivity.java
├── Adapter/
│   └── AttractionAdapter.java
├── Model/
│   └── Attraction.java
├── Manager/
│   └── SharedPreferencesManager.java
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── activity_add_attraction.xml
    │   ├── activity_edit_attraction.xml
    │   ├── activity_attraction_details.xml
    │   └── item_attraction.xml
    ├── drawable/
    │   └── background.xml
    └── font/
        └── cairo_medium.ttf
```

## 🎨 UI Components

### Main Screen
- **SearchView**: Filter attractions by name or location
- **RecyclerView**: Scrollable list of attraction cards
- **Add Button**: Opens form to create new attraction
- **Empty State**: Message when no attractions exist

### Attraction Card (RecyclerView Item)
- Attraction name
- Location with pin icon
- Type badge
- Visit date
- Star rating
- Favorite indicator (when applicable)
- Clickable to view full details

### Details Screen
- Complete attraction information
- Organized sections with icons
- Edit button
- Delete button with confirmation
- Back button

### Add/Edit Forms
- Text inputs for name, location, notes
- Spinner for attraction type selection
- Date picker for visit date
- Time picker for visit time
- Number inputs for fee and duration
- Radio buttons for rating (1-5 stars)
- Checkboxes for favorite and visited status
- Save and Cancel buttons

## 🔧 Technical Implementation

### Data Persistence
Uses SharedPreferences with a custom indexing system:
- Each attraction is stored with a unique UUID
- Data is saved with prefixed keys: `attraction_0_name`, `attraction_0_location`, etc.
- Counter tracks total number of attractions
- Update and delete operations rebuild the entire dataset

### Search Algorithm
- Case-insensitive search
- Searches through attraction names and locations
- Real-time filtering as user types

### UI/UX Features
- Custom font (Cairo Medium) throughout the app
- Gradient background
- Card-based design with elevation
- Color-coded buttons
- Icon integration (emoji-based)
- Scrollable forms for better usability
- Visual feedback on interactions


## 📊 Data Model

### Attraction Object
```java
- attractionId: String (UUID)
- name: String
- location: String
- type: String
- rating: int (1-5)
- entryFee: double
- visitDate: String (yyyy-MM-dd)
- visitTime: String (HH:mm)
- duration: int (hours)
- isVisited: boolean
- isFavorite: boolean
- notes: String
```



## 📝 Code Highlights

### Clean Separation of Concerns
- Each Activity handles specific functionality
- SharedPreferencesManager encapsulates all data operations
- Adapter follows ViewHolder pattern

### User-Friendly Features
- Confirmation dialogs for destructive actions
- Toast messages for user feedback
- Empty state handling
- Input validation
- Auto-refresh on data changes

### Material Design Principles
- Card-based layout
- Consistent spacing and padding
- Clear visual hierarchy


### Minimum Requirements
- **minSdkVersion**: 21
- **targetSdkVersion**: 33
- **compileSdkVersion**: 33

## 📄 License

This project is created for educational purposes.



---

**Version**: 1.0.0  
**Last Updated**: November 2025  
**Platform**: Android  
**Language**: Java
