# Currency Converter App

This is an Android mobile application developed as a technical task, featuring a currency converter that uses the **TransferGo FX Rates API**.

## Features

* **Real-time FX Rates:** Fetches current conversion rates using the `GET https://my.transfergo.com/api/fx-rates` endpoint.
* **Currency Selection:** Allows the user to search and select the **FROM** currency and **TO** currency from a mocked list of supported pairs
* **Sending Limits:** Implements mocked sending limits for the 'sending currency' (20000 PLN, 5000 EUR, 1000 GBP, 50000 UAH). There are no limits on the receiving amount.
* **Swap Functionality:** A button allows the user to swap **FROM** and **TO** currencies, which updates the conversion rate and amounts.
* **Quality Assurance:** Includes tests of core functionalities.


## 🛠️ Technology Stack

* App was written in Kotlin using MVVM architecture, UI was created with Jetpack Compose ❤️, ap
* Ktor for communication with API
* Koin as a Dependency Injection Library
* JUnit, Mockito for testing

<p align="center">
  <img src="https://github.com/user-attachments/assets/4e270265-2bab-44f2-b8b9-1c7d302e5e3e" alt="Home Screen" width="30%"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/e6a88e97-cde2-4c53-b181-5833bfff1ddf" alt="Error Screen (Limit Exceeded)" width="30%"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/530104c3-4d8f-4bfe-85d0-d98a03872db6" alt="Currency Selection List" width="30%"/>
</p>
