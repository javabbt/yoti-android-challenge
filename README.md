# Introduction
Welcome to Yoti's code challenge project. This repository contains an Android application with some basic functionality
to support the development of the code test required during the hiring process for an Android developer in Yoti.

# The challenge
A candidate have to add the required code in this project to implement the following feature:

- When the user open the app, we need to display a list of crypto currency assets (see CoincapService and AssetsApiData),
  displaying the asset name and symbol.

- When the user tap on an asset, we navigate to a new Fragment where we need to display detailed view of one Market (see CoincapService and MarketsApiData) 
  that has the selected crypto currency as the "baseId", and has the highest volume transacted in the last 24 hours ("volumeUsd24Hr").
  The information to display in this view is:
  + Exchange ID
  + Rank
  + Price
  + Updated date with the format "Day/Month/Year"

# Guideline
The candidate is free to implement the solution in any way that consider appropriated but bear in mind that we 
in Yoti love well designed, clear and simple code. SOLID principles and Clean Architecture are fundamental concepts in our development philosophy.

Below some points and tips that we evaluate:
- The functionality needs to be testable, and adding different types of tests as the candidate consider appropriated is not only a very positive bonus but almost a requirement. The absence of tests can be a very strong factor to fail this exercise.
- The presentation layer can be implemented following any of the industry well known patterns: MVVM, MVP, etc. 
  Although is not mandatory, using one of these paradigms is a positive point in the evaluation.
- Use any third party library not included in the project if needed.
- The webserver API used in this exercise can frequently return errors when many requests (sometimes just a few) are performed from the same IP address. Take this into account as part of the exercise to treat errors properly.

# How to submit the code challenge to Yoti
- The candidate can checkout the project locally and push to a newly created repo with public permissions so we can access it (please DO NOT use Fork button from github). Alternatively, you can clone the project and send the recruiter the updated project in a zip file.
 
# Reference links
- Coincap API: https://docs.coincap.io/

