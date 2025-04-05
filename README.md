# BMICalculator
Lab 13 of BMI Calculator App.

# BMI Calculator with Jetpack Compose

This project is an Android application that calculates the Body Mass Index (BMI) using the latest Android APIs and Jetpack Compose. The app collects user inputs for weight and height, performs the calculation, and provides a textual interpretation of the result.

## Features

- **Input Handling:**  
  - Input fields for weight (in lbs) and height (in cm).  
  - Input validation to ensure numeric values are provided within a reasonable range.

- **BMI Calculation:**  
  - A dedicated button to trigger the BMI calculation.
  - Calculation is performed using the standard BMI formula:
    
    BMI = (weight in kg)/(height in meters)^2

  - Conversion of weight from pounds to kilograms and height from centimeters to meters.

- **Interpretation:**  
  - Textual interpretation of the BMI value (e.g., Underweight, Normal weight, Overweight, Obese).

- **UI and State Management:**  
  - A single-screen app implemented with Jetpack Compose.  
  - Uses state hoisting for managing input and output states.  
  - Modern UI designed using Material3 guidelines, including a gradient background, cards for inputs, and a centered circular display for the BMI result.

- **Device Compatibility:**  
  - **Minimum SDK:** 24  
  - The app is optimized for devices running Android 7.0 (Nougat) and above.

## Local Setup Instructions

1. **Clone the Repository:**  
   Open a terminal and run:

   git clone https://github.com/bitvalo34/BMICalculator

2. **Open in Android Studio:**
  - Launch Android Studio.
  - Click on File > Open and select the cloned project folder.
  - Wait for the Gradle sync to complete.

3. **Build and Run:**
  - Connect an Android device (with API 24 or above) or launch an emulator.
  - Click the Run button to build and install the app on your device.

## Demo Video
A short demo video showing the app in action is available below:

(Demo video)[https://youtu.be/MG4S1BV0zck]
