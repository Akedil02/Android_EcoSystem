# AI Usage

AI tools were used as a development support tool during this project.

## How AI was used

AI assistance was mainly used for:

* Suggesting possible solutions when encountering Android Jetpack Compose issues.
* Helping identify and resolve compilation errors and dependency/import problems.
* Providing suggestions for Compose UI structure and reusable components.
* Assisting with debugging Navigation Compose and application state.
* Reviewing parts of the implementation against the assignment requirements.

## Developer Responsibility

The application structure, feature selection, design decisions, and final implementation were reviewed and adapted by the developer.

AI-generated suggestions were treated as development assistance rather than as a replacement for understanding or testing the code. The final code was checked and adjusted to work with the project's Gradle configuration and Android environment.

## Verification

The application was built and tested during development. Compilation errors and dependency issues were resolved before the final version was submitted.

## SIS4 architecture review

One misplaced responsibility from the earlier trip-list implementation was search and category filtering inside `TripListScreen`. For SIS4, that logic was moved into `TripListViewModel.filterTrips`, while the screen now renders `TripListUiState` and emits user actions through lambdas. This keeps UI rendering stateless and puts application behavior in the ViewModel.
