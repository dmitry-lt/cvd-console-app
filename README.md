# cvd-console-app

### Technology stack
The app uses Java 8, Spring Boot (dependency injection, RestTemplate), lombok, gradle, JUnit 5, jacoco code coverage

### Design choices
 * All the data is loaded and cached at the application startup
 * If the data fails to load, the app won't start. See troubleshooting section below
 * The app allows entering many country names one by one, until the user types "exit"
 * The country name must be entered exactly as it is in the dataset, there is no help or autocompletion
 * The app only shows info for the countries that have valid data in all 3 datasets (cases, vaccines, cases history)
 * "Vaccinated level in % of total population" represents the percentage of fully vaccinated
 * "New confirmed cases since last data available" are calculated based on history data as `number of cases on the latest day - number of cases on the latest but one day`
 * Numbers in the output are formatted as ###,###.##

### Gradle tasks
#### Run the app
```
gradlew bootRun
```

#### Run unit tests

```
gradlew test
```
The coverage report is generated in `build/reports/jacoco/test/html/index.html`

#### Build the app

```
gradlew build
```

### Troubleshooting
If something goes wrong, logging can be enabled in `application.properties`
```
logging.pattern.console= %d{yyyy-MM-dd HH:mm:ss} - %msg%n
```